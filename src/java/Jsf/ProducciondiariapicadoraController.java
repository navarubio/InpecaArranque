package Jsf;

import Jpa.ArticuloFacadeLocal;
import Jpa.DetalleproduccionpicadoraFacadeLocal;
import Jpa.InventariopicadoraFacadeLocal;
import Jpa.MovimientoinventariopicadoraFacadeLocal;
import Modelo.Produccionpicadora;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.ProduccionpicadoraFacadeLocal;
import Modelo.Articulo;
import Modelo.Detalleproduccionpicadora;
import Modelo.Inventariopicadora;
import Modelo.Movimientoinventariopicadora;
import Modelo.Usuario;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@Named("producciondiariapicadoraController")
@SessionScoped
public class ProducciondiariapicadoraController implements Serializable {

    @EJB
    private Jpa.ProduccionpicadoraFacadeLocal ejbFacade;
    @EJB
    private ArticuloFacadeLocal articuloEJB;
    @EJB
    private DetalleproduccionpicadoraFacadeLocal detalleproduccionpicadoraEJB;
    @EJB
    private InventariopicadoraFacadeLocal inventariopicadoraEJB;
    @EJB
    private MovimientoinventariopicadoraFacadeLocal movimientoinventariopicadoraEJB;
    
    @Inject
    private Detalleproduccionpicadora detallepro;
    @Inject
    private Inventariopicadora inventariopro;
    @Inject
    private Movimientoinventariopicadora moviinventariopro;
    
    
    private List<Produccionpicadora> items = null;
    private Produccionpicadora selected;
    private List<Articulo> articulos = null;
    private double cantidad = 0;
    private int id = 0;
    private List<Detalleproduccionpicadora> listadetallepro = new ArrayList();
    private Produccionpicadora codPro;
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    private String correo;
    private Date fechaactual = new Date();
    private envioCorreo enviomail;
    private Inventariopicadora inventariomodificar=new Inventariopicadora();

    public ProducciondiariapicadoraController() {
    }

    public Produccionpicadora getSelected() {
        return selected;
    }

    public void setSelected(Produccionpicadora selected) {
        this.selected = selected;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Detalleproduccionpicadora getDetallepro() {
        return detallepro;
    }

    public void setDetallepro(Detalleproduccionpicadora detallepro) {
        this.detallepro = detallepro;
    }

    public List<Detalleproduccionpicadora> getListadetallesproduccion() {
        return listadetallepro;
    }

    public void setListadetallesproduccion(List<Detalleproduccionpicadora> listarequerimiento) {
        this.listadetallepro = listarequerimiento;
    }


    
    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProduccionpicadoraFacadeLocal getFacade() {
        return ejbFacade;
    }

     @PostConstruct
    public void init() {
        selected = new Produccionpicadora();
        articulos = articuloEJB.listadoAgregadospicadora();
        listadetallepro.clear();
        items = ejbFacade.produccionfechadesc();
    }
    
    public void anexar() {
        if (cantidad != 0) {
            Detalleproduccionpicadora reque = new Detalleproduccionpicadora();
            reque.setCodigo(detallepro.getCodigo());
            reque.setCantidad(cantidad);
            reque.setIddetalleproduccionpicadora(id);
            this.listadetallepro.add(reque);
            id++;
//            requerimientos = requerimientoEJB.findAll();
            cantidad = 0;
            detallepro.setCodigo(null);

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "No puede dejar el campo Cantidad en 0.0"));
        }
    }
    public void eliminar(Detalleproduccionpicadora requerim) {
        listadetallepro.remove(requerim.hashCode());
        int indice = 0;
        for (Detalleproduccionpicadora requeri : listadetallepro) {
            requeri.setIddetalleproduccionpicadora(indice);
            indice++;
            id = indice;
        }
        if (requerim.hashCode() == 0) {
            id = 0;
        }
    }

     public double totaltotal() {
        double montotgeneral = 0;

        for (Detalleproduccionpicadora requeri : listadetallepro) {
            montotgeneral += requeri.getCantidad();
        }

        return montotgeneral;
    }
     
        public void registrar() {
        Articulo art = new Articulo();
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        Inventariopicadora inventa=new Inventariopicadora();
         
        try {
            selected.setIdusuario(us);
            ejbFacade.create(selected);

            codPro = ejbFacade.ultimoInsertado();
            String subject;
            for (Detalleproduccionpicadora rq : listadetallepro) {
                Articulo arti = rq.getCodigo();
                inventariopro = inventariopicadoraEJB.buscarAgregado(arti.getCodigo());
                detallepro.setIdproduccionpicadora(codPro);
                detallepro.setCodigo(arti);
                detallepro.setCantidad(rq.getCantidad());
                detalleproduccionpicadoraEJB.create(detallepro);
                
                moviinventariopro.setCodigo(arti);
                moviinventariopro.setAumento(rq.getCantidad());
                moviinventariopro.setIdproduccionpicadora(codPro);
                movimientoinventariopicadoraEJB.create(moviinventariopro);
                
//                inventariopro = inventariopicadoraEJB.buscarAgregado(arti);
                if(inventariopro==null){
                    inventariopro=new Inventariopicadora();
                    inventariopro.setCodigo(rq.getCodigo());
                    inventariopro.setCantidad(rq.getCantidad());
                    inventariopicadoraEJB.create(inventariopro);
                }else{
                    double cant=inventariopro.getCantidad()+rq.getCantidad();
//                    inventariopro.setIdinventariopicadora(inventa.getIdinventariopicadora());
//                    inventariopro.setCodigo(rq.getCodigo());
                    inventariopro.setCantidad(cant);
                    inventariopicadoraEJB.edit(inventariopro);
                }
            }
            String fechapro = formateador.format(codPro.getFecha());
            correo = "PRODUCCION PICADORA DE FECHA: " + fechapro
                    + "HRS TRITURACION: "+ codPro.getHorastrituracion()
                    + "   OBSERVACIONES: " + codPro.getObservaciones();
            subject = "Carga de ProduccionPicadora dia  " + fechapro;
            enviomail = new envioCorreo(correo, subject);
            enviomail.start();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La Produccion fue Almacenado ",""));
            listadetallepro.clear();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Produccion", ""));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
 

    public Produccionpicadora prepareCreate() {
        selected = new Produccionpicadora();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlepicadora").getString("ProduccionpicadoraCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlepicadora").getString("ProduccionpicadoraUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlepicadora").getString("ProduccionpicadoraDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Produccionpicadora> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlepicadora").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlepicadora").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Produccionpicadora getProduccionpicadora(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Produccionpicadora> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Produccionpicadora> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Produccionpicadora.class)
    public static class ProduccionpicadoraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProducciondiariapicadoraController controller = (ProducciondiariapicadoraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "produccionpicadoraController");
            return controller.getProduccionpicadora(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Produccionpicadora) {
                Produccionpicadora o = (Produccionpicadora) object;
                return getStringKey(o.getIdproduccionpicadora());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Produccionpicadora.class.getName()});
                return null;
            }
        }

    }

}
