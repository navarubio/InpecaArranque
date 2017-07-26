/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Jsf.reporteArticulo;
import Modelo.Comprobanteivaef;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;

/**
 *
 * @author sofimar
 */
@Stateless
public class ComprobanteivaefFacade extends AbstractFacade<Comprobanteivaef> implements ComprobanteivaefFacadeLocal{
    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComprobanteivaefFacade() {
        super(Comprobanteivaef.class);
    }
    
    @Override
    public String  siguientecomprobanteformat() {
        String consulta = null;
        Comprobanteivaef ultimo = new Comprobanteivaef();
        int numeracion=0;
        DecimalFormat myFormatter = new DecimalFormat("00000000"); 
        //formatear la cantidad 
        try {
            consulta = "Select c From Comprobanteivaef c Order By c.idcomprobanteivaef Desc";
            Query query = em.createQuery(consulta);
            List<Comprobanteivaef> lista = query.getResultList();
            if (!lista.isEmpty()) {
                ultimo = lista.get(0);
                numeracion = ultimo.getIdcomprobanteivaef()+1; 
            }else{
                numeracion=numeracion+1;
            }
                
        } catch (Exception e) {
            throw e;
        }
        
        String output = myFormatter.format(numeracion);
        return output;
    }
    
    @Override
    public List<Comprobanteivaef> buscarcomprobantesFiltrados (Date fechaini, Date fechafinish) {
        String consulta;
        List<Comprobanteivaef> lista = null;
        try {
            consulta = "From Comprobanteivaef c where c.fecha between ?1 and ?2 order by c.fecha";
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
