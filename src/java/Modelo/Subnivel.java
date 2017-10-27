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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "subnivel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subnivel.findAll", query = "SELECT s FROM Subnivel s"),
    @NamedQuery(name = "Subnivel.findByIdsubnivel", query = "SELECT s FROM Subnivel s WHERE s.idsubnivel = :idsubnivel"),
    @NamedQuery(name = "Subnivel.findBySubnivel", query = "SELECT s FROM Subnivel s WHERE s.subnivel = :subnivel")})
public class Subnivel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsubnivel")
    private Integer idsubnivel;
    @Size(max = 50)
    @Column(name = "subnivel")
    private String subnivel;
    @Size(max = 50)
    @Column(name = "icon")
    private String icon;
    @OneToMany(mappedBy = "idsubnivel")
    private Collection<Itemmenu> itemmenuCollection;
    @JoinColumn(name = "idsubmenu", referencedColumnName = "idsubmenu")
    @ManyToOne
    private Submenu idsubmenu;

    public Subnivel() {
    }

    public Subnivel(Integer idsubnivel) {
        this.idsubnivel = idsubnivel;
    }

    public Integer getIdsubnivel() {
        return idsubnivel;
    }

    public void setIdsubnivel(Integer idsubnivel) {
        this.idsubnivel = idsubnivel;
    }

    public String getSubnivel() {
        return subnivel;
    }

    public void setSubnivel(String subnivel) {
        this.subnivel = subnivel;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @XmlTransient
    public Collection<Itemmenu> getItemmenuCollection() {
        return itemmenuCollection;
    }

    public void setItemmenuCollection(Collection<Itemmenu> itemmenuCollection) {
        this.itemmenuCollection = itemmenuCollection;
    }

    public Submenu getIdsubmenu() {
        return idsubmenu;
    }

    public void setIdsubmenu(Submenu idsubmenu) {
        this.idsubmenu = idsubmenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsubnivel != null ? idsubnivel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subnivel)) {
            return false;
        }
        Subnivel other = (Subnivel) object;
        if ((this.idsubnivel == null && other.idsubnivel != null) || (this.idsubnivel != null && !this.idsubnivel.equals(other.idsubnivel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return subnivel;
    }

}
