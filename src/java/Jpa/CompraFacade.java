/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Compra;
import Modelo.Cuentabancaria;
import Modelo.Estatusfactura;
import Modelo.Movimientobancario;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sofimarye
 */
@Stateless
public class CompraFacade extends AbstractFacade<Compra> implements CompraFacadeLocal{

    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompraFacade() {
        super(Compra.class);
    }
    
    @Override
    public Compra ultimacompraInsertada() {
        String consulta = null;
        Compra ultimo = new Compra();
        try {
            consulta = "Select c From Compra c Order By c.idcompra Desc";
            Query query = em.createQuery(consulta);
            List<Compra> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultimo = lista.get(0);
}
        } catch (Exception e) {
            throw e;
        }
        return ultimo;
    }
    @Override
    public List<Compra> buscarcomprasporAutorizar() {
        String consulta;
        int idstatus = 1;
        List<Compra> lista = null;
        try {
            consulta = "From Compra c where c.idestatusfactura.idestatusfactura= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idstatus);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    @Override
    public List<Compra> buscarcomprasporPagar() {
        String consulta;
        int idstatus = 0;
        int idstatus2 =2;
        int idstatus3 =4;
        List<Compra> lista = null;
        try {
            consulta = "From Compra c where c.idestatusfactura.idestatusfactura= ?1 or c.idestatusfactura.idestatusfactura= ?2 or c.idestatusfactura.idestatusfactura= ?3";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idstatus);
            query.setParameter(2, idstatus2);
            query.setParameter(3, idstatus3);            
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    @Override
    public List<Compra> buscarcomprasPagadas() {
        String consulta;
        int idstatus = 3;
        List<Compra> lista = null;
        try {
            consulta = "From Compra c where c.idestatusfactura.idestatusfactura= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idstatus);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }  
    
    @Override
    public List<Compra> buscarcomprasFiltradas (Estatusfactura status, Date fechaini, Date fechafinish) {
        String consulta;
        List<Compra> lista = null;
        try {
            consulta = "From Compra c where c.idestatusfactura.idestatusfactura= ?1 and c.fechaorden between ?2 and ?3 order by c.fechaorden";
            Query query = em.createQuery(consulta);
            query.setParameter(1, status.getIdestatusfactura());
            query.setParameter(2, fechaini);
            query.setParameter(3, fechafinish);

            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    

}
