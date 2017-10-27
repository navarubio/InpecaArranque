/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Itemmenu;
import Modelo.Subnivel;
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
public class ItemmenuFacade extends AbstractFacade<Itemmenu> implements ItemmenuFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemmenuFacade() {
        super(Itemmenu.class);
    }
    
    @Override
    public List<Itemmenu> itemsOrdenados () {
        String consulta;
        List<Itemmenu> lista = null; 
        try { 
            consulta = "From Itemmenu i order by i.idsubmenu.idsubmenu, i.idsubnivel.idsubnivel";
            Query query = em.createQuery(consulta);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
}
