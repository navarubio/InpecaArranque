package Jsf;

import Jpa.ItemmenuFacadeLocal;
import Jpa.MenurolFacadeLocal;
import Modelo.Rol;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.RolFacade;
import Jpa.RolFacadeLocal;
import Modelo.Articulo;
import Modelo.Itemmenu;
import Modelo.Menurol;
import Modelo.Requerimiento;

import java.io.Serializable;
import java.util.ArrayList;
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

@Named("rolController")
@SessionScoped
public class RolController implements Serializable {

    @EJB
    private Jpa.RolFacadeLocal ejbFacade;
    @EJB
    private ItemmenuFacadeLocal EJBitemmenu;
    @EJB
    private MenurolFacadeLocal EJBmenurol;

    private List<Rol> items = null;
    private Rol selected;
    private int visualizar = 0;
    private List<Itemmenu> listaitemsmenu = new ArrayList();
    private List<Itemmenu> listadooriginal;

    public RolController() {
    }

    @PostConstruct
    public void init() {
        visualizar = 0;
        listaitemsmenu.clear();
        listadooriginal = EJBitemmenu.itemsOrdenados();
        listaitemsmenu = listadooriginal;
    }

    public Rol getSelected() {
        return selected;
    }

    public void setSelected(Rol selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RolFacadeLocal getFacade() {
        return ejbFacade;
    }

    public int getVisualizar() {
        return visualizar;
    }

    public void setVisualizar(int visualizar) {
        this.visualizar = visualizar;
    }

    public List<Itemmenu> getListaitemsmenu() {
        return listaitemsmenu;
    }

    public void setListaitemsmenu(List<Itemmenu> listaitemsmenu) {
        this.listaitemsmenu = listaitemsmenu;
    }

    public Rol prepareCreate() {
        selected = new Rol();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundlemenu").getString("RolCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundlemenu").getString("RolUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundlemenu").getString("RolDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Rol> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public void asignar(Rol rl) {
        this.selected = rl;
        visualizar = 1;
    }

    public void limpiarvisualizar() {
        visualizar = 0;
        listaitemsmenu.clear();
    }

    public void registrar() {

        try {
            for (Itemmenu itm : listaitemsmenu) {
                Menurol menuRol = new Menurol();
                if (itm.getEstado() == true) {
                    menuRol.setIditemmenu(itm);
                    menuRol.setIdrol(selected);
                    EJBmenurol.create(menuRol);
                    selected.setEstadomenu(Boolean.TRUE);
                    ejbFacade.edit(selected);
                }
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sus Almacenaron las Opciones de Menu para el Rol " + selected.getRol(), ""));
            listaitemsmenu.clear();
            
            visualizar=0;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Opciones de Menu", "Aviso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    public void cambiarEstado (Itemmenu item){
        listaitemsmenu.set(item.hashCode(), item);
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
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlemenu").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundlemenu").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Rol getRol(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Rol> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Rol> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Rol.class)
    public static class RolControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RolController controller = (RolController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolController");
            return controller.getRol(getKey(value));
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
            if (object instanceof Rol) {
                Rol o = (Rol) object;
                return getStringKey(o.getIdrol());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Rol.class.getName()});
                return null;
            }
        }

    }

}
