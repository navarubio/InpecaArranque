/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Compra;
import Modelo.Pagocompra;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Inpeca
 */
@Local
public interface PagocompraFacadeLocal {

    void create(Pagocompra pagocompra);

    void edit(Pagocompra pagocompra);

    void remove(Pagocompra pagocompra);

    Pagocompra find(Object id);

    List<Pagocompra> findAll();

    List<Pagocompra> findRange(int[] range);

    int count();
    
    List<Pagocompra> buscarPagosefectuados();
    
    public List<Pagocompra> buscarpago(Compra compr);
    
    public Pagocompra buscarpagototal(Compra compr);
    
    int ultimopago();
    
    List<Pagocompra> buscarpagoscompraFiltrados (Date fechaini, Date fechafinish);
    
}
