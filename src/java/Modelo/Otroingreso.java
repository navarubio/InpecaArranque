/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sofimar
 */
@Entity
@Table(name = "otroingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Otroingreso.findAll", query = "SELECT o FROM Otroingreso o"),
    @NamedQuery(name = "Otroingreso.findByIdotroingreso", query = "SELECT o FROM Otroingreso o WHERE o.idotroingreso = :idotroingreso"),
    @NamedQuery(name = "Otroingreso.findByMontoingresado", query = "SELECT o FROM Otroingreso o WHERE o.montoingresado = :montoingresado"),
    @NamedQuery(name = "Otroingreso.findByFechaingreso", query = "SELECT o FROM Otroingreso o WHERE o.fechaingreso = :fechaingreso"),
    @NamedQuery(name = "Otroingreso.findByObservaciones", query = "SELECT o FROM Otroingreso o WHERE o.observaciones = :observaciones")})
public class Otroingreso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idotroingreso")
    private Integer idotroingreso;
    @OneToMany(mappedBy = "idotroingreso")
    private Collection<Maestromovimiento> maestromovimientoCollection;    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montoingresado")
    private Double montoingresado;
    @Column(name = "fechaingreso")
    @Temporal(TemporalType.DATE)
    private Date fechaingreso;
    @Size(max = 255)
    @Column(name = "observaciones")
    private String observaciones;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;
    @JoinColumn(name = "idtipopago", referencedColumnName = "idtipopago")
    @ManyToOne
    private Tipopago idtipopago;
    @JoinColumn(name = "idcuentabancaria", referencedColumnName = "idcuentabancaria")
    @ManyToOne
    private Cuentabancaria idcuentabancaria;
    @JoinColumn(name = "idtipoingreso", referencedColumnName = "idtipoingreso")
    @ManyToOne
    private Tipoingreso idtipoingreso;

    public Otroingreso() {
    }

    public Otroingreso(Integer idotroingreso) {
        this.idotroingreso = idotroingreso;
    }

    public Integer getIdotroingreso() {
        return idotroingreso;
    }

    public void setIdotroingreso(Integer idotroingreso) {
        this.idotroingreso = idotroingreso;
    }

    public Double getMontoingresado() {
        return montoingresado;
    }

    public void setMontoingresado(Double montoingresado) {
        this.montoingresado = montoingresado;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public Tipopago getIdtipopago() {
        return idtipopago;
    }

    public void setIdtipopago(Tipopago idtipopago) {
        this.idtipopago = idtipopago;
    }

    public Cuentabancaria getIdcuentabancaria() {
        return idcuentabancaria;
    }

    public void setIdcuentabancaria(Cuentabancaria idcuentabancaria) {
        this.idcuentabancaria = idcuentabancaria;
    }

    public Tipoingreso getIdtipoingreso() {
        return idtipoingreso;
    }

    public void setIdtipoingreso(Tipoingreso idtipoingreso) {
        this.idtipoingreso = idtipoingreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idotroingreso != null ? idotroingreso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Otroingreso)) {
            return false;
        }
        Otroingreso other = (Otroingreso) object;
        if ((this.idotroingreso == null && other.idotroingreso != null) || (this.idotroingreso != null && !this.idotroingreso.equals(other.idotroingreso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Otroingreso[ idotroingreso=" + idotroingreso + " ]";
    }
    
    @XmlTransient
    public Collection<Maestromovimiento> getMaestromovimientoCollection() {
        return maestromovimientoCollection;
    }

    public void setMaestromovimientoCollection(Collection<Maestromovimiento> maestromovimientoCollection) {
        this.maestromovimientoCollection = maestromovimientoCollection;
    }
    
}
