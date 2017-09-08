/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Maestromovimiento;
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
public class MaestromovimientoFacade extends AbstractFacade<Maestromovimiento> implements MaestromovimientoFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MaestromovimientoFacade() {
        super(Maestromovimiento.class);
    }
    
    public List<Maestromovimiento> MovimientosOrdenadosFecha() {
        String consulta;
        List<Maestromovimiento> lista = null;
        try {
            consulta = "From Maestromovimiento m order by m.fechamovimiento";
            Query query = em.createQuery(consulta);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

}
