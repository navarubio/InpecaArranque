/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Movimientoinventariopicadora;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sofimar
 */
@Stateless
public class MovimientoinventariopicadoraFacade extends AbstractFacade<Movimientoinventariopicadora> implements MovimientoinventariopicadoraFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovimientoinventariopicadoraFacade() {
        super(Movimientoinventariopicadora.class);
    }
    
}
