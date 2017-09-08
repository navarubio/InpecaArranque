/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Maestromovimiento;
import Modelo.Medida;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Inpeca
 */
@Local
public interface MaestromovimientoFacadeLocal {

    void create(Maestromovimiento maestromovimiento);

    void edit(Maestromovimiento maestromovimiento);

    void remove(Maestromovimiento maestromovimiento);

    Maestromovimiento find(Object id);

    List<Maestromovimiento> findAll();

    List<Maestromovimiento> findRange(int[] range);

    int count();
    
    List<Maestromovimiento> MovimientosOrdenadosFecha();
    
}
