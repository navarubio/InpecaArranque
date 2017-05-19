/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Usomaquinariapicadora;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author sofimar
 */
@Stateless
public class UsomaquinariapicadoraFacade extends AbstractFacade<Usomaquinariapicadora> implements UsomaquinariapicadoraFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsomaquinariapicadoraFacade() {
        super(Usomaquinariapicadora.class);
    }
    
}
