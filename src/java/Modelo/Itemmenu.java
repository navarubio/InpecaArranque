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
@Table(name = "itemmenu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Itemmenu.findAll", query = "SELECT i FROM Itemmenu i"),
    @NamedQuery(name = "Itemmenu.findByIditemmenu", query = "SELECT i FROM Itemmenu i WHERE i.iditemmenu = :iditemmenu"),
    @NamedQuery(name = "Itemmenu.findByDescripcion", query = "SELECT i FROM Itemmenu i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Itemmenu.findByUrl", query = "SELECT i FROM Itemmenu i WHERE i.url = :url"),
    @NamedQuery(name = "Itemmenu.findByIcon", query = "SELECT i FROM Itemmenu i WHERE i.icon = :icon"),
    @NamedQuery(name = "Itemmenu.findByEstado", query = "SELECT i FROM Itemmenu i WHERE i.estado = :estado")})
public class Itemmenu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iditemmenu")
    private Integer iditemmenu;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "url")
    private String url;
    @Size(max = 50)
    @Column(name = "icon")
    private String icon;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "idsubmenu", referencedColumnName = "idsubmenu")
    @ManyToOne
    private Submenu idsubmenu;
    @JoinColumn(name = "idsubnivel", referencedColumnName = "idsubnivel")
    @ManyToOne
    private Subnivel idsubnivel;

    public Itemmenu() {
    }

    public Itemmenu(Integer iditemmenu) {
        this.iditemmenu = iditemmenu;
    }

    public Integer getIditemmenu() {
        return iditemmenu;
    }

    public void setIditemmenu(Integer iditemmenu) {
        this.iditemmenu = iditemmenu;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


    public Submenu getIdsubmenu() {
        return idsubmenu;
    }

    public void setIdsubmenu(Submenu idsubmenu) {
        this.idsubmenu = idsubmenu;
    }

    public Subnivel getIdsubnivel() {
        return idsubnivel;
    }

    public void setIdsubnivel(Subnivel idsubnivel) {
        this.idsubnivel = idsubnivel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iditemmenu != null ? iditemmenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Itemmenu)) {
            return false;
        }
        Itemmenu other = (Itemmenu) object;
        if ((this.iditemmenu == null && other.iditemmenu != null) || (this.iditemmenu != null && !this.iditemmenu.equals(other.iditemmenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return descripcion;
    }
    
}
