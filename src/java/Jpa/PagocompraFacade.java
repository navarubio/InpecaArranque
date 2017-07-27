/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Compra;
import Modelo.Comprobanteivaef;
import Modelo.Pagocompra;
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
public class PagocompraFacade extends AbstractFacade<Pagocompra> implements PagocompraFacadeLocal {

    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PagocompraFacade() {
        super(Pagocompra.class);
    }

    @Override
    public List<Pagocompra> buscarPagosefectuados() {
        String consulta;
        List<Pagocompra> lista = null;
        try {
            consulta = "SELECT p FROM Pagocompra p";
            Query query = em.createQuery(consulta);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

    @Override
    public List<Pagocompra> buscarpago(Compra compr) {
        String consulta;
        List<Pagocompra> lista = null;
        try {
            consulta = "From Pagocompra p where p.idcompra.idcompra= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, compr.getIdcompra());
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }

    @Override
    public Pagocompra buscarpagototal(Compra compr) {
        String consulta;
        Pagocompra pago=null;
        List<Pagocompra> lista = null;
        try {
            consulta = "From Pagocompra p where p.idcompra.idcompra= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, compr.getIdcompra());
            lista = query.getResultList();
            if (!lista.isEmpty()) {
                pago = lista.get(0);
}
        } catch (Exception e) {
            throw e;
                    
        }
        return pago;
    }
    @Override
    public int  ultimopago() {
        String consulta = null;
        Pagocompra ultima = new Pagocompra();
        int numeracion;
//        DecimalFormat myFormatter = new DecimalFormat("00000000"); 
        //formatear la cantidad 
        try {
            consulta = "Select p From Pagocompra p Order By p.idpagocompra Desc";
            Query query = em.createQuery(consulta);
            List<Pagocompra> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        numeracion = ultima.getIdpagocompra();
        return numeracion;
    }
    
    @Override
    public List<Pagocompra> buscarpagoscompraFiltrados (Date fechaini, Date fechafinish) {
        String consulta;
        List<Pagocompra> lista = null;
        try {
            consulta = "From Pagocompra p where p.fechapago between ?1 and ?2 order by p.fechapago";
            Query query = em.createQuery(consulta);
            query.setParameter(1, fechaini);
            query.setParameter(2, fechafinish);

            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
}
