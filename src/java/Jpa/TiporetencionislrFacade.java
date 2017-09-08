/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jpa;

import Modelo.Personalidadjuridica;
import Modelo.Residenciajuridica;
import Modelo.Tiporetencionislr;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author sofimar
 */
@Stateless
public class TiporetencionislrFacade extends AbstractFacade<Tiporetencionislr> implements TiporetencionislrFacadeLocal {

    @PersistenceContext(unitName = "InpecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TiporetencionislrFacade() {
        super(Tiporetencionislr.class);
    }

    @Override
    public Tiporetencionislr retencionislrFiltrada(int persona, int residen, int subg) {
        String consulta = null;
        Tiporetencionislr tiporetfiltrada = new Tiporetencionislr();
        int idpersonalidad = persona;
        int idresidencia = residen;
        int idsubgrupo = subg;
        if (idpersonalidad==3){
            idpersonalidad=1;
        }
        try {
            consulta = "From Tiporetencionislr t where t.idsubgrupo.idsubgrupo= ?1 and t.idpersonalidad.idpersonalidad= ?2 and t.idresidencia.idresidencia= ?3";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idsubgrupo);
            query.setParameter(2, idpersonalidad);
            query.setParameter(3, idresidencia);
            List<Tiporetencionislr> lista = query.getResultList();
            if (!lista.isEmpty()) {
                tiporetfiltrada = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        }
        return tiporetfiltrada;
    }

    @Override
    public List<Tiporetencionislr> tiporetfiltradaPJyD(Personalidadjuridica persona, Residenciajuridica residencia) {
        String consulta;
        int idpersonalidad = persona.getIdpersonalidad();
        int idresidencia= residencia.getIdresidencia();
        if (idpersonalidad==3){
            idpersonalidad=1;
        }
        List<Tiporetencionislr> lista = null;
        try {
            consulta = "From Tiporetencionislr t where t.idpersonalidad.idpersonalidad= ?1 and t.idresidencia.idresidencia= ?2 ";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idpersonalidad);
            query.setParameter(2, idresidencia);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }
    
    @Override
    public List<Tiporetencionislr> tiporetfiltradaPJD() {
        String consulta;
        int idpersonalidad = 1;
        int idresidencia= 3;
        List<Tiporetencionislr> lista = null;
        try {
            consulta = "From Tiporetencionislr t where t.idpersonalidad.idpersonalidad= ?1 and t.idresidencia.idresidencia= ?2 ";
            Query query = em.createQuery(consulta);
            query.setParameter(1, idpersonalidad);
            query.setParameter(2, idresidencia);
            lista = query.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return lista;
    }


}
