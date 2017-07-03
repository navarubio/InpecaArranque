/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Factura;
import java.text.DecimalFormat;
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
public class FacturaFacade extends AbstractFacade<Factura> implements FacturaFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacturaFacade() {
        super(Factura.class);
    }
    
    @Override
    public Factura ultimaInsertada() {
        String consulta = null;
        Factura ultima = new Factura();
        try {
            consulta = "Select f From Factura f Order By f.numerofact Desc";
            Query query = em.createQuery(consulta);
            List<Factura> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return ultima;
    }
    
    @Override
    public int siguientefactura() {
        String consulta = null;
        Factura ultima = new Factura();
        int numeracion;
        try {
            consulta = "Select f From Factura f Order By f.numerofact Desc";
            Query query = em.createQuery(consulta);
            List<Factura> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        numeracion = ultima.getNumerofact()+1;
        return numeracion;
    }
    
    @Override
    public String  siguientefacturaformat() {
        String consulta = null;
        Factura ultima = new Factura();
        int numeracion;
        DecimalFormat myFormatter = new DecimalFormat("00000"); 
        //formatear la cantidad 
        try {
            consulta = "Select f From Factura f Order By f.numerofact Desc";
            Query query = em.createQuery(consulta);
            List<Factura> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        if (ultima.getNumerofact()!=null){
            numeracion = ultima.getNumerofact()+1;
        }else{
            numeracion=1;
        }
        String output = myFormatter.format(numeracion); 

        return output;
    }
    @Override
    public String  ultimafacturaformat() {
        String consulta = null;
        Factura ultima = new Factura();
        int numeracion;
        DecimalFormat myFormatter = new DecimalFormat("00000"); 
        //formatear la cantidad 
        try {
            consulta = "Select f From Factura f Order By f.numerofact Desc";
            Query query = em.createQuery(consulta);
            List<Factura> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        numeracion = ultima.getNumerofact();
        String output = myFormatter.format(numeracion); 

        return output;
    }

    @Override
    public List<Factura> buscarfacturasporCobrar() {
        String consulta;
        int idstatus = 2;
        int idstatus2 =3;
        List<Factura> lista = null;
        try {
            consulta = "From Factura f where f.idestatusfacturaventa.idestatusfacturaventa= ?1 or f.idestatusfacturaventa.idestatusfacturaventa= ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idstatus);
            query.setParameter(2, idstatus2);            
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<Factura> buscarfacturasCobradas() {
        String consulta;
        int idstatus = 1;
        List<Factura> lista = null;
        try {
            consulta = "From Factura f where f.idestatusfacturaventa.idestatusfacturaventa= ?1";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idstatus);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
}
