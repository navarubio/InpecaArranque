/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Cuentabancaria;
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
public class SubnivelFacade extends AbstractFacade<Subnivel> implements SubnivelFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubnivelFacade() {
        super(Subnivel.class);
    }
    
    @Override
    public List<Subnivel> subnivelesOrdenados () {
        String consulta;
        List<Subnivel> lista = null;
        try {
            consulta = "From Subnivel s order by s.idsubnivel";
            Query query = em.createQuery(consulta);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<Subnivel> subnivelxSubmenu(int idsubmenu) {
        String consulta;
        List<Subnivel> lista = null; 
        try { 
            consulta = "From Subnivel s where s.idsubmenu.idsubmenu= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idsubmenu);            
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
}
