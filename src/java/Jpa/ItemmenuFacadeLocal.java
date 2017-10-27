/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Itemmenu;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Inpeca
 */
@Local
public interface ItemmenuFacadeLocal {

    void create(Itemmenu itemmenu);

    void edit(Itemmenu itemmenu);

    void remove(Itemmenu itemmenu);

    Itemmenu  find(Object id);

    List<Itemmenu> findAll();

    List<Itemmenu> findRange(int[] range);

    int count();   
    
    List<Itemmenu> itemsOrdenados ();
    
}
