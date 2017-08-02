/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Articulo;
import Modelo.Cuentabancaria;
import Modelo.Detalleproduccionpicadora;
import Modelo.Movimientobancario;
import java.util.Date;
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
public class DetalleproduccionpicadoraFacade extends AbstractFacade<Detalleproduccionpicadora> implements DetalleproduccionpicadoraFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetalleproduccionpicadoraFacade() {
        super(Detalleproduccionpicadora.class);
    }

    
    @Override
    public List<Detalleproduccionpicadora> buscarmovimientoporfecha (Date fechaini, Date fechafinish) {
        String consulta;
        List<Detalleproduccionpicadora> lista = null;
        try {
            consulta = "SELECT p FROM Produccionpicadora p JOIN Detalleproduccionpicadora d where p.idproduccionpicadora=d.idproduccionpicadora and p.fecha between ?1 and ?2";
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
