package Jsf;

import Modelo.Itemmenu;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.ItemmenuFacade;
import Jpa.ItemmenuFacadeLocal;
import Jpa.SubnivelFacadeLocal;
import Modelo.Cuentabancaria;
import Modelo.Submenu;
import Modelo.Subnivel;

import java.io.Serializable;
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
import javax.inject.Inject;

@Named("itemmenuController")
@SessionScoped
public class ItemmenuController implements Serializable {

    @EJB
    private Jpa.ItemmenuFacadeLocal ejbFacade;
    @EJB
    private SubnivelFacadeLocal ejbSubnivel;
    private List<Itemmenu> items = null;
    private Itemmenu selected;

    
    private List<Subnivel> lstSubnivelSelecc;

    @Inject
    private Itemmenu itemmen;

    public ItemmenuController() {
    }

    public Itemmenu getSelected() {
        return selected;
    }

    public void setSelected(Itemmenu selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ItemmenuFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Subnivel> getLstSubnivelSelecc() {
        return lstSubnivelSelecc;
    }

    public void setLstSubnivelSelecc(List<Subnivel> lstSubnivelSelecc) {
        this.lstSubnivelSelecc = lstSubnivelSelecc;
    }
    

    public Itemmenu prepareCreate() {
        selected = new Itemmenu();
        initializeEmbeddableKey();
        return selected;
    }
    
    public List<Subnivel> refrescarSubniveles() {
        try {
            lstSubnivelSelecc = ejbSubnivel.subnivelxSubmenu(selected.getIdsubmenu().getIdsubmenu());
        } catch (Exception e) {
        }
        return lstSubnivelSelecc;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlemenurol").getString("ItemmenuCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlemenurol").getString("ItemmenuUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlemenurol").getString("ItemmenuDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Itemmenu> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

        public List<Itemmenu> getItemsordenados() {
        if (items == null) {
            items = getFacade().itemsOrdenados();
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlemenurol").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlemenurol").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Itemmenu getItemmenu(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Itemmenu> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Itemmenu> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Itemmenu.class)
    public static class ItemmenuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItemmenuController controller = (ItemmenuController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itemmenuController");
            return controller.getItemmenu(getKey(value));
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
            if (object instanceof Itemmenu) {
                Itemmenu o = (Itemmenu) object;
                return getStringKey(o.getIditemmenu());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Itemmenu.class.getName()});
                return null;
            }
        }

    }

}
