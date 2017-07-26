package Jsf;

import Modelo.Comprobanteivaef;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.ComprobanteivaefFacade;
import Jpa.ComprobanteivaefFacadeLocal;
import Modelo.Factura;

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

@Named("comprobanteivaefController")
@ViewScoped
public class ComprobanteivaefController implements Serializable {

    @EJB
    private Jpa.ComprobanteivaefFacadeLocal ejbFacade;
    private List<Comprobanteivaef> items = null;
    private Comprobanteivaef selected;
    private Date fechadesde;
    private Date fechahasta; 
    private List <Comprobanteivaef> comprobantesfiltrados=null;

    public ComprobanteivaefController() {
    }

    public Comprobanteivaef getSelected() {
        return selected;
    }

    public void setSelected(Comprobanteivaef selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ComprobanteivaefFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Comprobanteivaef prepareCreate() {
        selected = new Comprobanteivaef();
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

    public List<Comprobanteivaef> getComprobantesfiltrados() {
        return comprobantesfiltrados;
    }

    public void setComprobantesfiltrados(List<Comprobanteivaef> comprobantesfiltrados) {
        this.comprobantesfiltrados = comprobantesfiltrados;
    }
    
    public void actualizar(){
        comprobantesfiltrados= ejbFacade.buscarcomprobantesFiltrados(fechadesde, fechahasta);
    }
    
    public String getSubtotalGeneral() {
        double total = 0;
        
        if (comprobantesfiltrados!=null){
            for(Comprobanteivaef inventa : getComprobantesfiltrados()) {
                total += inventa.getTotalbimponible();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    public String getIvaGeneral() {
        double total = 0;
        
        if (comprobantesfiltrados!=null){
            for(Comprobanteivaef inventa : getComprobantesfiltrados()) {
                total += inventa.getTotaliva();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    
    public String getTotalGeneral() {
        double total = 0;
        
        if (comprobantesfiltrados!=null){
            for(Comprobanteivaef inventa : getComprobantesfiltrados()) {
                total += inventa.getTotalgeneral();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    
    public String getTotalRetenido() {
        double total = 0;
        
        if (comprobantesfiltrados!=null){
            for(Comprobanteivaef inventa : getComprobantesfiltrados()) {
                total += inventa.getTotalivaretenido();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundletributos").getString("ComprobanteivaefCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundletributos").getString("ComprobanteivaefUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundletributos").getString("ComprobanteivaefDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Comprobanteivaef> getItems() {
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

    public Comprobanteivaef getComprobanteivaef(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Comprobanteivaef> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Comprobanteivaef> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Comprobanteivaef.class)
    public static class ComprobanteivaefControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ComprobanteivaefController controller = (ComprobanteivaefController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "comprobanteivaefController");
            return controller.getComprobanteivaef(getKey(value));
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
            if (object instanceof Comprobanteivaef) {
                Comprobanteivaef o = (Comprobanteivaef) object;
                return getStringKey(o.getIdcomprobanteivaef());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Comprobanteivaef.class.getName()});
                return null;
            }
        }

    }
    
    public void verComprobantesivaef() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/comprobantesiva.jasper");

        rArticulo.getMovimientoComprobantesiva(ruta, fechadesde,fechahasta);
        FacesContext.getCurrentInstance().responseComplete();
    }

}
