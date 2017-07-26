/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Comprobanteislref;
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
public interface ComprobanteislrefFacadeLocal {

    void create(Comprobanteislref comprobanteislref);

    void edit(Comprobanteislref comprobanteislref);

    void remove(Comprobanteislref comprobanteislref);

    Comprobanteislref find(Object id);

    List<Comprobanteislref> findAll();

    List<Comprobanteislref> findRange(int[] range);

    int count();
    
    String  siguientecomprobanteformat(); 
    
    List<Comprobanteislref> buscarcomprobantesFiltrados (Date fechaini, Date fechafinish);
    
}
