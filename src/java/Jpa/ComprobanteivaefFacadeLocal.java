/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Comprobanteivaef;
import Modelo.Factura;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sofimar
 */
@Local
public interface ComprobanteivaefFacadeLocal {

    void create(Comprobanteivaef comprobanteivaef);

    void edit(Comprobanteivaef comprobanteivaef);

    void remove(Comprobanteivaef comprobanteivaef);

    Comprobanteivaef find(Object id);

    List<Comprobanteivaef> findAll();

    List<Comprobanteivaef> findRange(int[] range);

    int count();
    
    String  siguientecomprobanteformat(); 
    
    List<Comprobanteivaef> buscarcomprobantesFiltrados (Date fechaini, Date fechafinish);
    
    
}
