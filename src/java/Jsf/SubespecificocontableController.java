package Jsf;

import Modelo.Subespecificocontable;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.SubespecificocontableFacade;

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

@Named("subespecificocontableController")
@SessionScoped
public class SubespecificocontableController implements Serializable {

    @EJB
    private Jpa.SubespecificocontableFacade ejbFacade;
    private List<Subespecificocontable> items = null;
    private Subespecificocontable selected;

    public SubespecificocontableController() {
    }

    public Subespecificocontable getSelected() {
        return selected;
    }

    public void setSelected(Subespecificocontable selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SubespecificocontableFacade getFacade() {
        return ejbFacade;
    }

    public Subespecificocontable prepareCreate() {
        selected = new Subespecificocontable();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bundle.actual").getString("SubespecificocontableCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bundle.actual").getString("SubespecificocontableUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bundle.actual").getString("SubespecificocontableDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Subespecificocontable> getItems() {
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle.actual").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle.actual").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Subespecificocontable getSubespecificocontable(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Subespecificocontable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Subespecificocontable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Subespecificocontable.class)
    public static class SubespecificocontableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            SubespecificocontableController controller = (SubespecificocontableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "subespecificocontableController");
            return controller.getSubespecificocontable(getKey(value));
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
            if (object instanceof Subespecificocontable) {
                Subespecificocontable o = (Subespecificocontable) object;
                return getStringKey(o.getIdsubespecificocontable());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Subespecificocontable.class.getName()});
                return null;
            }
        }

    }

}
