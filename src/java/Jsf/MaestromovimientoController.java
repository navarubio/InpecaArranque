package Jsf;

import Modelo.Maestromovimiento;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.MaestromovimientoFacade;
import Jpa.MaestromovimientoFacadeLocal;

import java.io.Serializable;
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

@Named("maestromovimientoController")
@ViewScoped
public class MaestromovimientoController implements Serializable {

    @EJB
    private Jpa.MaestromovimientoFacadeLocal ejbFacade;
    private List<Maestromovimiento> items = null;
    private Maestromovimiento selected;

    public MaestromovimientoController() {
    }

    public Maestromovimiento getSelected() {
        return selected;
    }

    public void setSelected(Maestromovimiento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MaestromovimientoFacadeLocal getFacade() {
        return ejbFacade;
    }

    public Maestromovimiento prepareCreate() {
        selected = new Maestromovimiento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlecontabilidad").getString("MaestromovimientoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlecontabilidad").getString("MaestromovimientoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlecontabilidad").getString("MaestromovimientoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Maestromovimiento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }
    
    public List<Maestromovimiento> getItemsordenados() {
        if (items == null) {
            items = getFacade().MovimientosOrdenadosFecha();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecontabilidad").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlecontabilidad").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Maestromovimiento getMaestromovimiento(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Maestromovimiento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Maestromovimiento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Maestromovimiento.class)
    public static class MaestromovimientoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MaestromovimientoController controller = (MaestromovimientoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "maestromovimientoController");
            return controller.getMaestromovimiento(getKey(value));
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
            if (object instanceof Maestromovimiento) {
                Maestromovimiento o = (Maestromovimiento) object;
                return getStringKey(o.getIdmaestro());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Maestromovimiento.class.getName()});
                return null;
            }
        }

    }

}
