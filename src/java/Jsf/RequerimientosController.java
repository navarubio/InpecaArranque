package Jsf;

import Jpa.ArticuloFacadeLocal;
import Jpa.AuxiliarrequerimientoFacadeLocal;
import Jpa.DepartamentoFacadeLocal;
import Jpa.EmpresaFacadeLocal;
import Jpa.RequerimientoFacadeLocal;
import Modelo.Articulo;
import Modelo.Auxiliarrequerimiento;
import Modelo.Departamento;
import Modelo.Empresa;
import Modelo.Estatusrequerimiento;
import Modelo.Proveedor;
import Modelo.Requerimiento;
import Modelo.Usuario;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ViewScoped

public class RequerimientosController implements Serializable {

    @EJB
    private ArticuloFacadeLocal articuloEJB;
    @EJB
    private RequerimientoFacadeLocal requerimientoEJB;
    @EJB
    private AuxiliarrequerimientoFacadeLocal auxiliarrequerimientoEJB;
    @EJB
    private DepartamentoFacadeLocal departamentoEJB;
    @EJB
    private EmpresaFacadeLocal empresaEJB;

    private List<Articulo> articulos = null;
    private List<Requerimiento> requerimientos = null;
    private List<Requerimiento> requerimientosfiltrados;
    private List<Requerimiento> listarequerimiento = new ArrayList();
    private String codigo = null;
    private String descripcion = null;
    private double cantidad = 0;
    private double pcosto = 0;
    private double pventa = 0;
    private double subtotal = 0;
    private double totalgeneral = 0;
    private double totaliva = 0;
    private double totalsubtotal = 0;
    private int id = 0;
    private envioCorreo enviomail;
    private String correo;
    private Empresa empresa;
    private Date fechaactual = new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");

    private Auxiliarrequerimiento codAux;
    private Auxiliarrequerimiento auxiliar;

    public Auxiliarrequerimiento getAuxiliar() {
        return auxiliar;
    }

    public void setAuxiliar(Auxiliarrequerimiento auxiliar) {
        this.auxiliar = auxiliar;
    }

    public List<Requerimiento> getRequerimientosfiltrados() {
        return requerimientosfiltrados;
    }

    public void setRequerimientosfiltrados(List<Requerimiento> requerimientosfiltrados) {
        this.requerimientosfiltrados = requerimientosfiltrados;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getPventa() {
        return pventa;
    }

    public void setPventa(double pventa) {
        this.pventa = pventa;
    }

    public double getTotalgeneral() {
        return totalgeneral;
    }

    public void setTotalgeneral(double totalgeneral) {
        this.totalgeneral = totalgeneral;
    }

    public List<Requerimiento> getListarequerimiento() {
        return listarequerimiento;
    }

    public double getTotaliva() {
        return totaliva;
    }

    public void setTotaliva(double totaliva) {
        this.totaliva = totaliva;
    }

    public double getTotalsubtotal() {
        return totalsubtotal;
    }

    public void setTotalsubtotal(double totalsubtotal) {
        this.totalsubtotal = totalsubtotal;
    }

    public void setListarequerimiento(List<Requerimiento> listarequerimiento) {
        this.listarequerimiento = listarequerimiento;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPcosto() {
        return pcosto;
    }

    public void setPcosto(double pcosto) {
        this.pcosto = pcosto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public List<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }

    @Inject
    private Articulo articulo;
    @Inject
    private Requerimiento requer;
    @Inject
    private Proveedor provee;

    @PostConstruct
    public void init() {
        articulos = articuloEJB.findAll();
        auxrequer.setFecharequerimiento(fechaactual);
        listarequerimiento.clear();
    }

    @Inject
    private Auxiliarrequerimiento auxrequer;
    @Inject
    private Usuario usa;
    @Inject
    private Departamento dpto;
    @Inject
    private Estatusrequerimiento statusreq;
    private int statu = 1;

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Requerimiento getRequer() {
        return requer;
    }

    public void setRequer(Requerimiento requer) {
        this.requer = requer;
    }

    public Auxiliarrequerimiento getAuxrequer() {
        return auxrequer;
    }

    public void setAuxrequer(Auxiliarrequerimiento auxrequer) {
        this.auxrequer = auxrequer;
    }

    public Usuario getUsuario() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        usa = us;
        return us;
    }

    public Departamento buscarDepartamento() {
        Usuario usua = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        dpto = departamentoEJB.buscarDepartamento(usua);
        statusreq.setIdestatusrequerimiento(statu);
        return dpto;
    }

    public void buscarArticulo() {
        articulo = requer.getCodigo();
        pcosto = articulo.getPcosto();
        pventa = articulo.getPventa();
    }

    public void buscarProveedor() {
    }

    public void anexar() {
        if (cantidad != 0) {
            double alicuota = 0;
            double iva = 0;
            double total = 0;
            Requerimiento reque = new Requerimiento();
            reque.setCodigo(requer.getCodigo());
            reque.setCantidad(cantidad);
//            pcosto = reque.getCodigo().getPcosto();
            reque.setPcosto(pcosto);
            subtotal = cantidad * pcosto;
            reque.setSubtotal(subtotal);
            alicuota = reque.getCodigo().getIdgravamen().getAlicuota();
            iva = (subtotal * alicuota) / 100;
            total = subtotal + iva;
            reque.setTributoiva(iva);
            reque.setTotal(total);
            reque.setIdrequerimiento(id);
            this.listarequerimiento.add(reque);
            id++;
            requerimientos = requerimientoEJB.findAll();
            pcosto = 0;
            pventa = 0;
            cantidad = 0;
            requer.setCodigo(null);
//            requer.setCodigo(null);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "No puede dejar el campo Cantidad en 0.0"));
        }
    }

    public void anexarafactura() {
        if (cantidad != 0) {
            double alicuota = 0;
            double iva = 0;
            double total = 0;
            Requerimiento reque = new Requerimiento();
            reque.setCodigo(requer.getCodigo());
            reque.setCantidad(cantidad);
//            pcosto = reque.getCodigo().getPcosto();
            reque.setPcosto(pventa);
            subtotal = cantidad * pventa;
            reque.setSubtotal(subtotal);
            alicuota = reque.getCodigo().getIdgravamen().getAlicuota();
            iva = (subtotal * alicuota) / 100;
            total = subtotal + iva;
            reque.setTributoiva(iva);
            reque.setTotal(total);
            reque.setIdrequerimiento(id);
            this.listarequerimiento.add(reque);
            id++;
            requerimientos = requerimientoEJB.findAll();
            pcosto = 0;
            pventa = 0;
            cantidad = 0;
            requer.setCodigo(null);
//            requer.setCodigo(null);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "No puede dejar el campo Cantidad en 0.0"));
        }
    }

    public void eliminar(Requerimiento requerim) {
        listarequerimiento.remove(requerim.hashCode());
        int indice = 0;
        for (Requerimiento requeri : listarequerimiento) {
            requeri.setIdrequerimiento(indice);
            indice++;
            id = indice;
        }
        if (requerim.hashCode() == 0) {
            id = 0;
        }

    }

    public double totaltotal() {
        double montotgeneral = 0;
        double montotiva = 0;
        double montotsubtotal = 0;

        for (Requerimiento requeri : listarequerimiento) {
            montotgeneral += requeri.getTotal();
            montotiva += requeri.getTributoiva();
            montotsubtotal += requeri.getSubtotal();
        }
        totalgeneral = montotgeneral;
        totaliva = montotiva;
        totalsubtotal = montotsubtotal;

        return montotgeneral;
    }

    public double totaliva() {
        double montotgeneral = 0;
        double montotiva = 0;
        double montotsubtotal = 0;

        for (Requerimiento requeri : listarequerimiento) {
            montotgeneral += requeri.getTotal();
            montotiva += requeri.getTributoiva();
            montotsubtotal += requeri.getSubtotal();
        }
        totalgeneral = montotgeneral;
        totaliva = montotiva;
        totalsubtotal = montotsubtotal;

        return montotiva;
    }

    public double totalbaseimponible() {
        double montotgeneral = 0;
        double montotiva = 0;
        double montotsubtotal = 0;

        for (Requerimiento requeri : listarequerimiento) {
            montotgeneral += requeri.getTotal();
            montotiva += requeri.getTributoiva();
            montotsubtotal += requeri.getSubtotal();
        }
        totalgeneral = montotgeneral;
        totaliva = montotiva;
        totalsubtotal = montotsubtotal;

        return montotsubtotal;
    }

    public void registrar() {
        Articulo art = new Articulo();
        try {
            auxrequer.setIddepartamento(dpto);
            auxrequer.setIdusuario(usa);
            auxrequer.setIdestatusrequerimiento(statusreq);
            auxrequer.setSubtotal(totalsubtotal);
            auxrequer.setMontoiva(totaliva);
            auxrequer.setMontototal(totalgeneral);

            auxiliarrequerimientoEJB.create(auxrequer);
            totalsubtotal = 0;
            totaliva = 0;
            totalgeneral = 0;

            codAux = requerimientoEJB.ultimoInsertado();
            String subject;
            String material = " ";
            
            for (Requerimiento rq : listarequerimiento) {
                Articulo arti = rq.getCodigo();
                requer.setIdauxiliarrequerimiento(codAux);
                requer.setCodigo(arti);
                requer.setCantidad(rq.getCantidad());
                requer.setPcosto(rq.getPcosto());
                requer.setSubtotal(rq.getSubtotal());
                requer.setTributoiva(rq.getTributoiva());
                requer.setTotal(rq.getTotal());
                material = material + requer.getCodigo().getDescripcion() + 
                       " CANTIDAD: " + requer.getCantidad()+ " PRECIO: "+requer.getPcosto() +"  ";
                requerimientoEJB.create(requer);
            }
            String fechareque = formateador.format(auxrequer.getFecharequerimiento());
            empresa= empresaEJB.devolverEmpresabase();
            correo = "CODIGO: REQ-" + auxrequer.getIdauxiliarrequerimiento()
                    + "  SOLICITANTE: " + auxrequer.getIdusuario().getNombre()
                    + "  DEPARTAMENTO: " + auxrequer.getIddepartamento()
                    + "  MATERIAL O SERVICIO: "+material
                    + "  FECHA: " + fechareque
                    + "  SOLICITUD: " + auxrequer.getDescripcion()
                    + "  MONTO ESTIMADO: "+ formatearnumero.format (auxrequer.getMontototal());
            subject = empresa.getNombrecomercial()+ " Requerimiento REQ-" + requer.getIdauxiliarrequerimiento().getIdauxiliarrequerimiento();
            enviomail = new envioCorreo(correo, subject);
            enviomail.start();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Requerimiento fue Almacenado Codigo " + requer.getIdauxiliarrequerimiento().getIdauxiliarrequerimiento(), ""));
            listarequerimiento.clear();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Requerimiento", "Aviso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void modificar(Requerimiento requerim) {
        requerimientoEJB.edit(requerim);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Requerimiento fue Modificado"));
    }

    public void cancelModificar() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Se cancelo moficiacion"));
    }

    public void asignarSeleccion(Requerimiento requ) {
        requer = requ;
    }

    public List<Requerimiento> buscando() {
        List<Requerimiento> listado = null;
        listado = requerimientoEJB.buscarrequerimientos(auxrequer);
        return listado;
    }

    public void asignar(Auxiliarrequerimiento aux) {
        this.auxiliar = aux;
        this.auxrequer = aux;
//        this.requerimientosfiltrados = requerimientoEJB.buscarrequerimientos(aux);
    }

    public List<Requerimiento> buscarRequerimiento(Auxiliarrequerimiento auxi) {
        requerimientosfiltrados = requerimientoEJB.buscarrequerimientos(auxi);
        auxiliar = auxi;
        auxrequer = auxiliar;
        return requerimientosfiltrados;
    }

    public List<Requerimiento> buscarRequerimientosGenerales() {

        return requerimientosfiltrados;
    }

    public void limpiarListaArreglo() {
        listarequerimiento.clear();

    }

}
