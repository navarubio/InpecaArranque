/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sofimar
 */
@Entity
@Table(name = "submenu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Submenu.findAll", query = "SELECT s FROM Submenu s"),
    @NamedQuery(name = "Submenu.findByIdsubmenu", query = "SELECT s FROM Submenu s WHERE s.idsubmenu = :idsubmenu"),
    @NamedQuery(name = "Submenu.findBySubmenu", query = "SELECT s FROM Submenu s WHERE s.submenu = :submenu")})
public class Submenu implements Serializable {

    @OneToMany(mappedBy = "idsubmenu")
    private Collection<Itemmenu> itemmenuCollection;
    @OneToMany(mappedBy = "idsubmenu")
    private Collection<Subnivel> subnivelCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsubmenu")
    private Integer idsubmenu;
    @Size(max = 50)
    @Column(name = "submenu")
    private String submenu;
    @Size(max = 50)
    @Column(name = "icon")
    private String icon;


    public Submenu() {
    }

    public Submenu(Integer idsubmenu) {
        this.idsubmenu = idsubmenu;
    }

    public Integer getIdsubmenu() {
        return idsubmenu;
    }

    public void setIdsubmenu(Integer idsubmenu) {
        this.idsubmenu = idsubmenu;
    }

    public String getSubmenu() {
        return submenu;
    }

    public void setSubmenu(String submenu) {
        this.submenu = submenu;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsubmenu != null ? idsubmenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Submenu)) {
            return false;
        }
        Submenu other = (Submenu) object;
        if ((this.idsubmenu == null && other.idsubmenu != null) || (this.idsubmenu != null && !this.idsubmenu.equals(other.idsubmenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return submenu;
    }

    @XmlTransient
    public Collection<Itemmenu> getItemmenuCollection() {
        return itemmenuCollection;
    }

    public void setItemmenuCollection(Collection<Itemmenu> itemmenuCollection) {
        this.itemmenuCollection = itemmenuCollection;
    }

    @XmlTransient
    public Collection<Subnivel> getSubnivelCollection() {
        return subnivelCollection;
    }

    public void setSubnivelCollection(Collection<Subnivel> subnivelCollection) {
        this.subnivelCollection = subnivelCollection;
    }

}
