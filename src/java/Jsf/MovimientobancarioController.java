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

import java.io.Serializable;
import java.sql.SQLException;
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
    private List<Movimientobancario> items = null;
    private List <Movimientobancario> itemsfiltrados=null;
    private Movimientobancario selected=new Movimientobancario();
    private List<Banco> bancos;
    private Banco bancoselec;
    private Cuentabancaria cuentaselec;
    private Date fechadesde;
    private Date fechahasta;
    private List<Cuentabancaria> lstCuentasSelecc;
   
    

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

    @PostConstruct
    public void init() {
        bancos=bancoEJB.findAll();
    }
    public List<Cuentabancaria> refrescarCuentasBancarias() {
        try {
            lstCuentasSelecc = cuentabancariaEJB.espxBanco(bancoselec.getIdbanco());
        } catch (Exception e) {
        }
        selected.setIdcuentabancaria(lstCuentasSelecc.get(0));
        return lstCuentasSelecc;
    }

    public void actualizar(){
        itemsfiltrados= ejbFacade.buscarmovimientoporfecha(selected.getIdcuentabancaria(), fechadesde, fechahasta);
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

        rArticulo.getMovimientoBancario(ruta, codigocuenta,fechadesde,fechahasta);
        FacesContext.getCurrentInstance().responseComplete();
    }
}
