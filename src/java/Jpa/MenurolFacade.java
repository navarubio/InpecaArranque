/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Itemmenu;
import Modelo.Menurol;
import Modelo.Rol;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sofimar
 */
@Stateless
public class MenurolFacade extends AbstractFacade<Menurol> implements MenurolFacadeLocal {

    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MenurolFacade() {
        super(Menurol.class);
    }

    @Override
    public List<Menurol> itemsxrol(Rol rolusuario) {
        String consulta;
        List<Menurol> lista = null;
        try {
            consulta = "From Menurol m where m.idrol.idrol= ?1 order by m.iditemmenu.idsubmenu.idsubmenu,m.iditemmenu.idsubnivel.idsubnivel ";
            Query query = em.createQuery(consulta);
            query.setParameter(1, rolusuario.getIdrol());
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

    @Override
    public List<Menurol> itemsxsubmenu(Itemmenu item) {
        String consulta;
        List<Menurol> lista = null;
        try {
            consulta = "From Menurol m where m.iditemmenu.idsubmenu.idsubmenu= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, item.getIdsubmenu().getIdsubmenu());
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

}
