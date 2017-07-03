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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sofimar
 */
@Entity
@Table(name = "factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByNumerofact", query = "SELECT f FROM Factura f WHERE f.numerofact = :numerofact"),
    @NamedQuery(name = "Factura.findByNumerocontrol", query = "SELECT f FROM Factura f WHERE f.numerocontrol = :numerocontrol"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByHora", query = "SELECT f FROM Factura f WHERE f.hora = :hora"),
    @NamedQuery(name = "Factura.findByObservacionesfact", query = "SELECT f FROM Factura f WHERE f.observacionesfact = :observacionesfact"),
    @NamedQuery(name = "Factura.findByBimponiblefact", query = "SELECT f FROM Factura f WHERE f.bimponiblefact = :bimponiblefact"),
    @NamedQuery(name = "Factura.findByIvafact", query = "SELECT f FROM Factura f WHERE f.ivafact = :ivafact"),
    @NamedQuery(name = "Factura.findByTotalgeneral", query = "SELECT f FROM Factura f WHERE f.totalgeneral = :totalgeneral"),
    @NamedQuery(name = "Factura.findBySaldopendiente", query = "SELECT f FROM Factura f WHERE f.saldopendiente= :saldopendiente"),
    @NamedQuery(name = "Factura.findByCantidadenletras", query = "SELECT f FROM Factura f WHERE f.cantidadenletras = :cantidadenletras")})
public class Factura implements Serializable {
    @JoinColumn(name = "idmaestro", referencedColumnName = "idmaestro")
    @ManyToOne
    private Maestromovimiento idmaestro;
    @OneToMany(mappedBy = "numerofact")
    private Collection<Maestromovimiento> maestromovimientoCollection;
    @OneToMany(mappedBy = "numerofact")
    private Collection<Detalleretencionivasp> detalleretencionivaspCollection;
    @OneToMany(mappedBy = "numerofact")
    private Collection<Detalleretencionislrsp> detalleretencionislrspCollection;
    @OneToMany(mappedBy = "numerofact")
    private Collection<Detallefactura> detallefacturaCollection;
    @OneToMany(mappedBy = "numerofact")
    private Collection<Cobroventa> cobroventaCollection;
    @OneToMany(mappedBy = "numerofact")
    private Collection<Despachopicadora> despachopicadoraCollection;
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "numerofact")
    private Integer numerofact;
    @Size(max = 11)
    @Column(name = "serialfactura")
    private String serialfactura;
    @Size(max = 11)
    @Column(name = "numerocontrol")
    private String numerocontrol;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Size(max = 12)
    @Column(name = "hora")
    private String hora;
    @Size(max = 255)
    @Column(name = "observacionesfact")
    private String observacionesfact;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "bimponiblefact")
    private Double bimponiblefact;
    @Column(name = "ivafact")
    private Double ivafact;
    @Column(name = "totalgeneral")
    private Double totalgeneral;
    @Column(name = "saldopendiente")
    private Double saldopendiente;
    @Size(max = 250)
    @Column(name = "cantidadenletras")
    private String cantidadenletras;
    @JoinColumn(name = "idcaja", referencedColumnName = "idcaja")
    @ManyToOne
    private Caja idcaja;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;
    @JoinColumn(name = "rifcliente", referencedColumnName = "rifcliente")
    @ManyToOne
    private Cliente rifcliente;
    @JoinColumn(name = "idestatusfacturaventa", referencedColumnName = "idestatusfacturaventa")
    @ManyToOne
    private Estatusfacturaventa idestatusfacturaventa;
    @JoinColumn(name = "idestatuscontable", referencedColumnName = "idestatuscontable")
    @ManyToOne
    private Estatuscontable idestatuscontable;

    public Factura() {
    }

    public Factura(Integer numerofact) {
        this.numerofact = numerofact;
    }

    public Integer getNumerofact() {
        return numerofact;
    }

    public void setNumerofact(Integer numerofact) {
        this.numerofact = numerofact;
    }

    public String getSerialfactura() {
        return serialfactura;
    }

    public void setSerialfactura(String serialfactura) {
        this.serialfactura = serialfactura;
    }
    
    public String getNumerocontrol() {
        return numerocontrol;
    }

    public void setNumerocontrol(String numerocontrol) {
        this.numerocontrol = numerocontrol;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getObservacionesfact() {
        return observacionesfact;
    }

    public void setObservacionesfact(String observacionesfact) {
        this.observacionesfact = observacionesfact;
    }

    public Double getBimponiblefact() {
        return bimponiblefact;
    }

    public void setBimponiblefact(Double bimponiblefact) {
        this.bimponiblefact = bimponiblefact;
    }

    public Double getIvafact() {
        return ivafact;
    }

    public void setIvafact(Double ivafact) {
        this.ivafact = ivafact;
    }

    public Double getTotalgeneral() {
        return totalgeneral;
    }

    public void setTotalgeneral(Double totalgeneral) {
        this.totalgeneral = totalgeneral;
    }

    public Double getSaldopendiente() {
        return saldopendiente;
    }

    public void setSaldopendiente(Double saldopendiente) {
        this.saldopendiente = saldopendiente;
    }
    
    public String getCantidadenletras() {
        return cantidadenletras;
    }

    public void setCantidadenletras(String cantidadenletras) {
        this.cantidadenletras = cantidadenletras;
    }

    public Caja getIdcaja() {
        return idcaja;
    }

    public void setIdcaja(Caja idcaja) {
        this.idcaja = idcaja;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    public Cliente getRifcliente() {
        return rifcliente;
    }

    public void setRifcliente(Cliente rifcliente) {
        this.rifcliente = rifcliente;
    }

    public Estatusfacturaventa getIdestatusfacturaventa() {
        return idestatusfacturaventa;
    }

    public void setIdestatusfacturaventa(Estatusfacturaventa idestatusfacturaventa) {
        this.idestatusfacturaventa = idestatusfacturaventa;
    }

    public Estatuscontable getIdestatuscontable() {
        return idestatuscontable;
    }

    public void setIdestatuscontable(Estatuscontable idestatuscontable) {
        this.idestatuscontable = idestatuscontable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numerofact != null ? numerofact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.numerofact == null && other.numerofact != null) || (this.numerofact != null && !this.numerofact.equals(other.numerofact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Factura[ numerofact=" + numerofact + " ]";
    }

    @XmlTransient
    public Collection<Detallefactura> getDetallefacturaCollection() {
        return detallefacturaCollection;
    }

    public void setDetallefacturaCollection(Collection<Detallefactura> detallefacturaCollection) {
        this.detallefacturaCollection = detallefacturaCollection;
    }
    
    @XmlTransient
    public Collection<Cobroventa> getCobroventaCollection() {
        return cobroventaCollection;
    }

    public void setCobroventaCollection(Collection<Cobroventa> CobroventaCollection) {
        this.cobroventaCollection = CobroventaCollection;
    }

    @XmlTransient
    public Collection<Despachopicadora> getDespachopicadoraCollection() {
        return despachopicadoraCollection;
    }

    public void setDespachopicadoraCollection(Collection<Despachopicadora> despachopicadoraCollection) {
        this.despachopicadoraCollection = despachopicadoraCollection;
    }
    
    @XmlTransient
    public Collection<Detalleretencionivasp> getDetalleretencionivaspCollection() {
        return detalleretencionivaspCollection;
    }

    public void setDetalleretencionivaspCollection(Collection<Detalleretencionivasp> detalleretencionivaspCollection) {
        this.detalleretencionivaspCollection = detalleretencionivaspCollection;
    }

    @XmlTransient
    public Collection<Detalleretencionislrsp> getDetalleretencionislrspCollection() {
        return detalleretencionislrspCollection;
    }

    public void setDetalleretencionislrspCollection(Collection<Detalleretencionislrsp> detalleretencionislrspCollection) {
        this.detalleretencionislrspCollection = detalleretencionislrspCollection;
    }

    public Maestromovimiento getIdmaestro() {
        return idmaestro;
    }

    public void setIdmaestro(Maestromovimiento idmaestro) {
        this.idmaestro = idmaestro;
    }

    @XmlTransient
    public Collection<Maestromovimiento> getMaestromovimientoCollection() {
        return maestromovimientoCollection;
    }

    public void setMaestromovimientoCollection(Collection<Maestromovimiento> maestromovimientoCollection) {
        this.maestromovimientoCollection = maestromovimientoCollection;
    }
    
}
