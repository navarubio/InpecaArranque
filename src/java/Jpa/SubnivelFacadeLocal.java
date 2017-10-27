/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Itemmenu;
import Modelo.Subnivel;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Inpeca
 */
@Local
public interface SubnivelFacadeLocal {

    void create(Subnivel subnivel);

    void edit(Subnivel subnivel);

    void remove(Subnivel subnivel);

    Subnivel find(Object id);

    List<Subnivel> findAll();

    List<Subnivel> findRange(int[] range);

    int count();   
    
    List<Subnivel> subnivelesOrdenados (); 
    
    List<Subnivel> subnivelxSubmenu(int idsubmenu);
}
