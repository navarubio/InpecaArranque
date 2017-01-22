/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Autorizacion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sofimar
 */

@Local
public interface AutorizacionFacadeLocal {
    
    void create(Autorizacion autorizacion);

    void edit(Autorizacion autorizacion);

    void remove(Autorizacion autorizacion);

    Autorizacion find(Object id);

    List<Autorizacion> findAll();

    List<Autorizacion> findRange(int[] range);
    
    int count();
   

}    

