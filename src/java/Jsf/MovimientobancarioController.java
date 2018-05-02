package Jsf;

import Jpa.BancoFacadeLocal;
import Jpa.CuentabancariaFacadeLocal;
import Modelo.Movimientobancario;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.MovimientobancarioFacade;
import Jpa.MovimientobancarioFacadeLocal;
import Modelo.Banco;
import Modelo.Cuentabancaria;
import Modelo.Detallecompra;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;

@Named("movimientobancarioController")
@ViewScoped
public class MovimientobancarioController implements Serializable {

    @EJB
    private Jpa.MovimientobancarioFacadeLocal ejbFacade;
    @EJB
    private CuentabancariaFacadeLocal cuentabancariaEJB;
    @EJB
    private BancoFacadeLocal bancoEJB;
    @EJB
    private MovimientobancarioFacadeLocal movimientobancarioEJB;

    private List<Movimientobancario> items = null;
    private List<Movimientobancario> itemsfiltrados = null;
    private Movimientobancario selected = new Movimientobancario();
    private List<Banco> bancos;
    private Banco bancoselec;
    private Cuentabancaria cuentaselec;
    private Date fechadesde;
    private Date fechahasta;
    private List<Cuentabancaria> lstCuentasSelecc;
    ArrayList<Movimientobancario> listaerrores = new ArrayList();
    ArrayList<Movimientobancario> listaerrores1 = new ArrayList();
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");
//    private List<Movimientobancario> listaerrores=new ArrayList<>;

    public MovimientobancarioController() {
    }

    public Movimientobancario getSelected() {
        return selected;
    }

    public List<Cuentabancaria> getLstCuentasSelecc() {
        return lstCuentasSelecc;
    }

    public void setLstCuentasSelecc(List<Cuentabancaria> lstCuentasSelecc) {
        this.lstCuentasSelecc = lstCuentasSelecc;
    }

    public void setSelected(Movimientobancario selected) {
        this.selected = selected;
    }

    public Banco getBancoselec() {
        return bancoselec;
    }

    public void setBancoselec(Banco bancoselec) {
        this.bancoselec = bancoselec;
    }

    public Cuentabancaria getCuentaselec() {
        return cuentaselec;
    }

    public void setCuentaselec(Cuentabancaria cuentaselec) {
        this.cuentaselec = cuentaselec;
    }

    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MovimientobancarioFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

    public Movimientobancario prepareCreate() {
        selected = new Movimientobancario();
        initializeEmbeddableKey();
        return selected;
    }

    public ArrayList<Movimientobancario> getListaerrores() {
        return listaerrores;
    }

    public void setListaerrores(ArrayList<Movimientobancario> listaerrores) {
        this.listaerrores = listaerrores;
    }

    public ArrayList<Movimientobancario> getListaerrores1() {
        return listaerrores1;
    }

    public void setListaerrores1(ArrayList<Movimientobancario> listaerrores1) {
        this.listaerrores1 = listaerrores1;
    }

    @PostConstruct
    public void init() {
        bancos = bancoEJB.findAll();
        if (itemsfiltrados!=null){
            itemsfiltrados.clear();
        }
    }

    public List<Cuentabancaria> refrescarCuentasBancarias() {
        try {
            lstCuentasSelecc = cuentabancariaEJB.espxBanco(bancoselec.getIdbanco());
        } catch (Exception e) {
        }
        selected.setIdcuentabancaria(lstCuentasSelecc.get(0));
        return lstCuentasSelecc;
    }

    public void actualizar() {
//        itemsfiltrados.clear();
        itemsfiltrados = ejbFacade.buscarmovimientoporfecha(selected.getIdcuentabancaria(), fechadesde, fechahasta);

    }

    public void conciliar() {
        listaerrores1.clear();
        itemsfiltrados = ejbFacade.buscarmovimientoporfecha(selected.getIdcuentabancaria(), fechadesde, fechahasta);
        conciliarSaldos();
    }

    public void conciliarSaldos() {
        double saldoant = 0;
        double saldoact = 0;
        double saldopost = 0;
        int marcador = 0;
        int sinnovedad = 0;
        String cadena = null;
        String cadenaactual;
        String anterior;
        for (Movimientobancario seleccion : itemsfiltrados) {
            saldoant = seleccion.getSaldoanterior();
            anterior = formatearnumero.format(saldoant);
            cadena = formatearnumero.format(saldopost);
            if (marcador == 0) {
                cadena = anterior;
            }

            if (anterior.equals(cadena)) {
                if (seleccion.getDebito() == null) {
                    saldopost = saldoant + seleccion.getCredito();
                } else {
                    saldopost = saldoant - seleccion.getDebito();

                }
                cadena = formatearnumero.format(saldopost);
                saldoact = seleccion.getSaldoactual();
                cadenaactual = formatearnumero.format(saldoact);

                if (cadena.equals(cadenaactual)) {

                } else {
                    seleccion.setSaldoactual(saldopost);
                    sinnovedad = 1;
                }
                listaerrores1.add(seleccion);
            } else {
                saldoant = saldopost;
                seleccion.setSaldoanterior(saldopost);
                if (seleccion.getDebito() == null) {
                    saldopost = saldoant + seleccion.getCredito();
                } else {
                    saldopost = saldoant - seleccion.getDebito();

                }
                cadena = formatearnumero.format(saldopost);
                saldoact = seleccion.getSaldoactual();
                cadenaactual = formatearnumero.format(saldoact);

                if (cadena.equals(cadenaactual)) {

                } else {
                    seleccion.setSaldoactual(saldopost);
                    sinnovedad = 1;
                }
                listaerrores1.add(seleccion);
            }
            if (marcador == 0) {
                marcador++;
            }
        }
        if (marcador == 0) {
            marcador++;
        }
        if (sinnovedad == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "LOS SALDOS ESTAN AJUSTADOS"));
        } else {
            corregirSaldos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "LOS SALDOS FUERON AJUSTADOS SATISFACTORIAMENTE"));
        }

    }

    public void corregirSaldos() {
        Movimientobancario movimientoListo;
        for (Movimientobancario movicorreg : listaerrores1) {
            movimientoListo = movicorreg;
            movimientobancarioEJB.edit(movimientoListo);
        }
    }

    public List<Movimientobancario> getItemsfiltrados() {
        return itemsfiltrados;
    }

    public void setItemsfiltrados(List<Movimientobancario> itemsfiltrados) {
        this.itemsfiltrados = itemsfiltrados;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlebancario").getString("MovimientobancarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlebancario").getString("MovimientobancarioUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlebancario").getString("MovimientobancarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Movimientobancario> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlebancario").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlebancario").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Movimientobancario getMovimientobancario(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Movimientobancario> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Movimientobancario> getItemsAvailableSelectOne() {
        return getFacade().findAll();

    }

    @FacesConverter(forClass = Movimientobancario.class)
    public static class MovimientobancarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MovimientobancarioController controller = (MovimientobancarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "movimientobancarioController");
            return controller.getMovimientobancario(getKey(value));
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
            if (object instanceof Movimientobancario) {
                Movimientobancario o = (Movimientobancario) object;
                return getStringKey(o.getIdmovimiento());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Movimientobancario.class.getName()});
                return null;
            }
        }

    }

    public void verMovimiento() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        int codigocuenta = selected.getIdcuentabancaria().getIdcuentabancaria();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/movimientobancario.jasper");

        rArticulo.getMovimientoBancario(ruta, codigocuenta, fechadesde, fechahasta);
        FacesContext.getCurrentInstance().responseComplete();
    }
}
