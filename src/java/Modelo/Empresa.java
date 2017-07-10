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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sofimar
 */
@Entity
@Table(name = "empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e"),
    @NamedQuery(name = "Empresa.findByRif", query = "SELECT e FROM Empresa e WHERE e.rif = :rif"),
    @NamedQuery(name = "Empresa.findByRazonsocial", query = "SELECT e FROM Empresa e WHERE e.razonsocial = :razonsocial"),
    @NamedQuery(name = "Empresa.findByNombrecomercial", query = "SELECT e FROM Empresa e WHERE e.nombrecomercial = :nombrecomercial"),
    @NamedQuery(name = "Empresa.findByDireccionfiscal", query = "SELECT e FROM Empresa e WHERE e.direccionfiscal = :direccionfiscal"),
    @NamedQuery(name = "Empresa.findByTelefonos", query = "SELECT e FROM Empresa e WHERE e.telefonos = :telefonos"),
    @NamedQuery(name = "Empresa.findByPresidente", query = "SELECT e FROM Empresa e WHERE e.presidente = :presidente"),
    @NamedQuery(name = "Empresa.findByAdministrador", query = "SELECT e FROM Empresa e WHERE e.administrador = :administrador"),
    @NamedQuery(name = "Empresa.findByContador", query = "SELECT e FROM Empresa e WHERE e.contador = :contador")})
public class Empresa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "rif")
    private String rif;
    @Size(max = 100)
    @Column(name = "razonsocial")
    private String razonsocial;
    @Size(max = 50)
    @Column(name = "nombrecomercial")
    private String nombrecomercial;
    @Size(max = 255)
    @Column(name = "direccionfiscal")
    private String direccionfiscal;
    @Size(max = 50)
    @Column(name = "telefonos")
    private String telefonos;
    @Size(max = 150)
    @Column(name = "presidente")
    private String presidente;
    @Size(max = 150)
    @Column(name = "administrador")
    private String administrador;
    @Size(max = 150)
    @Column(name = "contador")
    private String contador;
    @JoinColumn(name = "idcontribuyente", referencedColumnName = "idcontribuyente")
    @ManyToOne
    private Contribuyente idcontribuyente;
    @JoinColumn(name = "idpersonalidad", referencedColumnName = "idpersonalidad")
    @ManyToOne
    private Personalidadjuridica idpersonalidad;
  
    
    public Empresa() {
    }

    public Empresa(String rif) {
        this.rif = rif;
    }

    public String getRif() {
        return rif;
    }

    public void setRif(String rif) {
        this.rif = rif;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getNombrecomercial() {
        return nombrecomercial;
    }

    public void setNombrecomercial(String nombrecomercial) {
        this.nombrecomercial = nombrecomercial;
    }

    public String getDireccionfiscal() {
        return direccionfiscal;
    }

    public void setDireccionfiscal(String direccionfiscal) {
        this.direccionfiscal = direccionfiscal;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public Contribuyente getIdcontribuyente() {
        return idcontribuyente;
    }

    public void setIdcontribuyente(Contribuyente idcontribuyente) {
        this.idcontribuyente = idcontribuyente;
    }

    public Personalidadjuridica getIdpersonalidad() {
        return idpersonalidad;
    }

    public void setIdpersonalidad(Personalidadjuridica idpersonalidad) {
        this.idpersonalidad = idpersonalidad;
    }

  
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rif != null ? rif.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.rif == null && other.rif != null) || (this.rif != null && !this.rif.equals(other.rif))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Empresa[ rif=" + rif + " ]";
    }
    
}
