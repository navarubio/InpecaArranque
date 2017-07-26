package Jsf;

import Modelo.Comprobanteislref;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.ComprobanteislrefFacade;
import Jpa.ComprobanteislrefFacadeLocal;
import Modelo.Comprobanteivaef;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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

@Named("comprobanteislrefController")
@ViewScoped
public class ComprobanteislrefController implements Serializable {

    @EJB
    private Jpa.ComprobanteislrefFacadeLocal ejbFacade;
    private List<Comprobanteislref> items = null;
    private Comprobanteislref selected;
    private Date fechadesde;
    private Date fechahasta; 
    private List <Comprobanteislref> comprobantesfiltrados=null;

    public ComprobanteislrefController() {
    }

    public Comprobanteislref getSelected() {
        return selected;
    }

    public void setSelected(Comprobanteislref selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ComprobanteislrefFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Comprobanteislref prepareCreate() {
        selected = new Comprobanteislref();
        initializeEmbeddableKey();
        return selected;
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

    public List<Comprobanteislref> getComprobantesfiltrados() {
        return comprobantesfiltrados;
    }

    public void setComprobantesfiltrados(List<Comprobanteislref> comprobantesfiltrados) {
        this.comprobantesfiltrados = comprobantesfiltrados;
    }
    
    public void actualizar(){
        comprobantesfiltrados= ejbFacade.buscarcomprobantesFiltrados(fechadesde, fechahasta);
    }
    
    public String getSubtotalGeneral() {
        double total = 0;
        
        if (comprobantesfiltrados!=null){
            for(Comprobanteislref inventa : getComprobantesfiltrados()) {
                total += inventa.getTotalbimponible();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    
    public String getTotalGeneral() {
        double total = 0;
        
        if (comprobantesfiltrados!=null){
            for(Comprobanteislref inventa : getComprobantesfiltrados()) {
                total += inventa.getTotalgeneral();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    
    public String getTotalRetenido() {
        double total = 0;
        
        if (comprobantesfiltrados!=null){
            for(Comprobanteislref inventa : getComprobantesfiltrados()) {
                total += inventa.getTotalislrretenido();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundletributos").getString("ComprobanteislrefCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundletributos").getString("ComprobanteislrefUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundletributos").getString("ComprobanteislrefDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Comprobanteislref> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundletributos").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundletributos").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Comprobanteislref getComprobanteislref(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Comprobanteislref> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Comprobanteislref> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Comprobanteislref.class)
    public static class ComprobanteislrefControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ComprobanteislrefController controller = (ComprobanteislrefController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "comprobanteislrefController");
            return controller.getComprobanteislref(getKey(value));
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
            if (object instanceof Comprobanteislref) {
                Comprobanteislref o = (Comprobanteislref) object;
                return getStringKey(o.getIdcomprobanteislref());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Comprobanteislref.class.getName()});
                return null;
            }
        }

    }
    public void verComprobantesislref() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/comprobantesislr.jasper");

        rArticulo.getMovimientoComprobantesiva(ruta, fechadesde,fechahasta);
        FacesContext.getCurrentInstance().responseComplete();
    }

}
