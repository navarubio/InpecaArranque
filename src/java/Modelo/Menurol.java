/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sofimar
 */
@Entity
@Table(name = "menurol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Menurol.findAll", query = "SELECT m FROM Menurol m"),
    @NamedQuery(name = "Menurol.findByIdmenurol", query = "SELECT m FROM Menurol m WHERE m.idmenurol = :idmenurol")})
public class Menurol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmenurol")
    private Integer idmenurol;
    @JoinColumn(name = "iditemmenu", referencedColumnName = "iditemmenu")
    @ManyToOne
    private Itemmenu iditemmenu;
    @JoinColumn(name = "idrol", referencedColumnName = "idrol")
    @ManyToOne
    private Rol idrol;

    public Menurol() {
    }

    public Menurol(Integer idmenurol) {
        this.idmenurol = idmenurol;
    }

    public Integer getIdmenurol() {
        return idmenurol;
    }

    public void setIdmenurol(Integer idmenurol) {
        this.idmenurol = idmenurol;
    }

    public Itemmenu getIditemmenu() {
        return iditemmenu;
    }

    public void setIditemmenu(Itemmenu iditemmenu) {
        this.iditemmenu = iditemmenu;
    }

    public Rol getIdrol() {
        return idrol;
    }

    public void setIdrol(Rol idrol) {
        this.idrol = idrol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmenurol != null ? idmenurol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menurol)) {
            return false;
        }
        Menurol other = (Menurol) object;
        if ((this.idmenurol == null && other.idmenurol != null) || (this.idmenurol != null && !this.idmenurol.equals(other.idmenurol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Menurol[ idmenurol=" + idmenurol + " ]";
    }
    
}
