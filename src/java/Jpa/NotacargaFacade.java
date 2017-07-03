/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Factura;
import Modelo.Notacarga;
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
public class NotacargaFacade extends AbstractFacade<Notacarga> implements NotacargaFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotacargaFacade() {
        super(Notacarga.class);
    }
    
    @Override
    public Notacarga ultimaInsertada() {
        String consulta = null;
        Notacarga ultima = new Notacarga();
        try {
            consulta = "Select n From Notacarga n Order By n.idnotacarga Desc";
            Query query = em.createQuery(consulta);
            List<Notacarga> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return ultima;
    }
    
    @Override
    public int siguientenotacarga() {
        String consulta = null;
        Notacarga ultima = new Notacarga();
        int numeracion;
        try {
            consulta = "Select n From Notacarga n Order By n.idnotacarga Desc";
            Query query = em.createQuery(consulta);
            List<Notacarga> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultima = lista.get(0);
            }else{
                ultima.setIdnotacarga(0);
            }
        } catch (Exception e) {
            throw e;
        }
        numeracion = ultima.getIdnotacarga()+1;
        return numeracion;
    }
    
}
