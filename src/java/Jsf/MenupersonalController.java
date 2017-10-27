package Jsf;


import Jpa.MenurolFacadeLocal;
import Modelo.Itemmenu;
import Modelo.Menurol;
import Modelo.Submenu;
import Modelo.Subnivel;
import Modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author sofimar
 */
@ManagedBean(name = "menupersonalController")
@SessionScoped
public class MenupersonalController implements Serializable {

    @EJB
    private MenurolFacadeLocal menurolEJB;

    private List<Menurol> listamenurol;
    private List<Menurol> listamenuitems;
    private MenuModel menumodelo;
    private MenuModel menumodelonuevo;
    Submenu submenu1;
    Submenu submenu2;
    Subnivel subnivel=null;
    Itemmenu itemmenu = null;

    public MenuModel getMenumodelo() {
        return menumodelo;
    }

    public void setMenumodelo(MenuModel menumodelo) {
        this.menumodelo = menumodelo;
    }

    public MenuModel getMenumodelonuevo() {
        return menumodelonuevo;
    }

    public void setMenumodelonuevo(MenuModel menumodelonuevo) {
        this.menumodelonuevo = menumodelonuevo;
    }

    @PostConstruct
    public void init() {
        this.listadeMenunuevo();
        menumodelo = new DefaultMenuModel();
        menumodelonuevo = new DefaultMenuModel();
//        cargarMenu();
         cargarMenunuevo();

    }



    public void listadeMenunuevo() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

        try {
            listamenurol = menurolEJB.itemsxrol(us.getIdrol());
        } catch (Exception e) {
        }
    }
 
    public void cargarMenunuevo() {
        for (Menurol sm : listamenurol) {
            if (sm.getIditemmenu().getIdsubmenu().getIdsubmenu() != 1) {
                if (submenu1 != sm.getIditemmenu().getIdsubmenu()) {
                    DefaultSubMenu nivel1submenu = new DefaultSubMenu(sm.getIditemmenu().getIdsubmenu().getSubmenu());
                    if (sm.getIditemmenu().getIdsubmenu().getIcon() != null) {
                        nivel1submenu.setIcon(sm.getIditemmenu().getIdsubmenu().getIcon());
                    }
                    submenu1 = sm.getIditemmenu().getIdsubmenu();
                    for (Menurol sm2 : listamenurol) {
                        if (sm2.getIditemmenu().getIdsubmenu().getIdsubmenu().equals(sm.getIditemmenu().getIdsubmenu().getIdsubmenu())) {
                            
                            if (subnivel!=sm2.getIditemmenu().getIdsubnivel()){
                                DefaultSubMenu nivel2submenu = new DefaultSubMenu(sm2.getIditemmenu().getIdsubnivel().getSubnivel());
                                if (sm2.getIditemmenu().getIdsubnivel().getIcon() != null) {
                                    nivel2submenu.setIcon(sm2.getIditemmenu().getIdsubnivel().getIcon());
                                }
                                subnivel = sm2.getIditemmenu().getIdsubnivel();

                                for (Menurol it : listamenurol) {
                                    submenu2 = it.getIditemmenu().getIdsubmenu();
                                    if (submenu2 != null) {
                                        if (sm2.getIditemmenu().getIdsubmenu().equals(it.getIditemmenu().getIdsubmenu())) {
                                            if (it.getIditemmenu().getIdsubnivel().equals(sm2.getIditemmenu().getIdsubnivel())&&sm2.getIditemmenu().getEstado()==true) {
                                                DefaultMenuItem item = new DefaultMenuItem(it.getIditemmenu().getDescripcion());
                                                if (it.getIditemmenu().getUrl() != null) {
                                                    item.setUrl(it.getIditemmenu().getUrl());
                                                }
                                                nivel2submenu.addElement(item);
                                            }
                                        }
                                    }
                                }
                                nivel1submenu.addElement(nivel2submenu);                            
                            }    
                        }
                    }
                    menumodelonuevo.addElement(nivel1submenu);
                }
            } else {
                DefaultMenuItem item = new DefaultMenuItem(sm.getIditemmenu().getDescripcion());
                item.setIcon(sm.getIditemmenu().getIcon());
                if (sm.getIditemmenu().getUrl() != null) {
                    item.setUrl(sm.getIditemmenu().getUrl());
                }
                menumodelonuevo.addElement(item);
            }
        }
    }

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }

}
