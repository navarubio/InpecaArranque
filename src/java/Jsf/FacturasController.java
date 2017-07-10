/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jsf;

import Jpa.ArticuloFacadeLocal;
import Jpa.CajaFacadeLocal;
import Jpa.ClienteFacadeLocal;
import Jpa.DetallefacturaFacadeLocal;
import Jpa.EmpresaFacadeLocal;
import Jpa.EstatuscontableFacade;
import Jpa.EstatuscontableFacadeLocal;
import Jpa.EstatusfacturaventaFacadeLocal;
import Jpa.FacturaFacadeLocal;
import Jpa.MaestromovimientoFacadeLocal;
import Jpa.Numeroaletras;
import Jpa.RequerimientoFacadeLocal;
import Jpa.TipoconjuntoFacadeLocal;
import Modelo.Articulo;
import Modelo.Caja;
import Modelo.Cliente;
import Modelo.Detallefactura;
import Modelo.Empresa;
import Modelo.Estatuscontable;
import Modelo.Factura;
import Modelo.Maestromovimiento;
import Modelo.Requerimiento;
import Modelo.Tipoconjunto;
import Modelo.Usuario;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 * @author sofimar
 */
@ManagedBean(name = "facturasController")
@SessionScoped

public class FacturasController implements Serializable {

    @EJB
    private FacturaFacadeLocal facturaEJB;
    @EJB
    private DetallefacturaFacadeLocal detallefacturaEJB;
    @EJB
    private ClienteFacadeLocal clienteEJB;
    @EJB
    private ArticuloFacadeLocal articuloEJB;
    @EJB
    private CajaFacadeLocal cajaEJB;
    @EJB
    private EstatuscontableFacadeLocal estatuscontableEJB;
    @EJB
    private EstatusfacturaventaFacadeLocal estatusfacturaventaEJB;
    @EJB
    private MaestromovimientoFacadeLocal maestromovimientoEJB;
    @EJB
    private TipoconjuntoFacadeLocal tipoconjuntoEJB;
    @EJB
    private RequerimientoFacadeLocal requerimientoEJB;
    @EJB
    private EmpresaFacadeLocal empresaEJB;

    private Detallefactura detallefactura;
    //private RequerimientosController reque = new RequerimientosController();

    private List<Factura> facturas;
    private List<Detallefactura> detallesfacturas;
    private List<Cliente> clientes;
    private List<Articulo> articulos;
    private Factura codfactura;
    private int number;
    private Date fechaactual = new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");
    private String correo;
    private envioCorreo enviomail;
    private Tipoconjunto tipoconjunto = null;
    private double cantidad = 0;
    private double pcosto = 0;
    private double pventa = 0;
    private double subtotal = 0;
    private double totalgeneral = 0;
    private double totaliva = 0;
    private double totalsubtotal = 0;
    private List<Requerimiento> listarequerimiento = new ArrayList();
    private int id = 0;
    private List<Requerimiento> requerimientos = null;
    private String siguiente;
    private Empresa empresa;

    @Inject
    private Factura factura;
    @Inject
    private Cliente cliente;
    @Inject
    private Detallefactura detalle;
    @Inject
    private Maestromovimiento maestromovi;
    @Inject
    private Requerimiento requer;
    @Inject
    private Articulo articulo;

    Numeroaletras numletras = new Numeroaletras();

    String numero = "";
    String cantidadenletras = "";

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Detallefactura getDetallefactura() {
        return detallefactura;
    }

    public void setDetallefactura(Detallefactura detallefactura) {
        this.detallefactura = detallefactura;
    }

    public List<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Factura> facturas) {
        this.facturas = facturas;
    }

    public List<Detallefactura> getDetallesfacturas() {
        return detallesfacturas;
    }

    public void setDetallesfacturas(List<Detallefactura> detallesfacturas) {
        this.detallesfacturas = detallesfacturas;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente clientt) {
        this.cliente = clientt;
    }

    public Detallefactura getDetalle() {
        return detalle;
    }

    public void setDetalle(Detallefactura detalle) {
        this.detalle = detalle;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Requerimiento getRequer() {
        return requer;
    }

    public void setRequer(Requerimiento requer) {
        this.requer = requer;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public double getPventa() {
        return pventa;
    }

    public void setPventa(double pventa) {
        this.pventa = pventa;
    }

    public List<Requerimiento> getListarequerimiento() {
        return listarequerimiento;
    }

    public void setListarequerimiento(List<Requerimiento> listarequerimiento) {
        this.listarequerimiento = listarequerimiento;
    }

    public List<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }
    
    

    @PostConstruct
    public void init() {
        clientes = clienteEJB.findAll();
        articulos = articuloEJB.findAll();
        factura.setFecha(fechaactual);
        listarequerimiento.clear();
    }

    public void registrarventa() {
        Articulo art = new Articulo();
        Date fecha = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String fechaCadena = hourFormat.format(fecha);
        DecimalFormat numformat = new DecimalFormat("#######.##");

        try {
            Usuario usua = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            factura.setIdusuario(usua);
            factura.setRifcliente(cliente);
            factura.setBimponiblefact(totalbaseimponible());
            factura.setIvafact(totaliva());
            factura.setTotalgeneral(totaltotal());
            numero = numformat.format(factura.getTotalgeneral());
            cantidadenletras = numletras.Convertir(numero, true);
            factura.setSerialfactura(siguiente);
            factura.setCantidadenletras(cantidadenletras);
            factura.setSaldopendiente(totaltotal());
            factura.setHora(fechaCadena);
            factura.setIdcaja(cajaEJB.ubicarCaja());
            factura.setIdestatuscontable(estatuscontableEJB.estatusContablePorRegistrar());
            factura.setIdestatusfacturaventa(estatusfacturaventaEJB.estatusFacturaPorCobrar());
            facturaEJB.create(factura);

            codfactura = facturaEJB.ultimaInsertada();
            number = codfactura.getNumerofact();

            int tipoconj = 1;
            tipoconjunto = tipoconjuntoEJB.cambiartipoConjunto(tipoconj);
            maestromovi.setNumerofact(codfactura);
            maestromovi.setFechamovimiento(factura.getFecha());
            maestromovi.setIdtipoconjunto(tipoconjunto);
            maestromovi.setIdestatuscontable(estatuscontableEJB.estatusContablePorRegistrar());
            maestromovimientoEJB.create(maestromovi);
            String material = " ";
            for (Requerimiento rq : listarequerimiento) {
                Articulo arti = rq.getCodigo();
                detalle.setNumerofact(codfactura);
                detalle.setCodigo(arti);
                detalle.setUnidades(rq.getCantidad());
                detalle.setPrecioventa(rq.getPcosto());
                detalle.setSubtotal(rq.getSubtotal());
                detalle.setTributoiva(rq.getTributoiva());
                detalle.setTotal(rq.getTotal());
                material = material + detalle.getCodigo().getDescripcion() + 
                         " CANTIDAD: " + detalle.getUnidades()+ " "+detalle.getCodigo().getIdmedida().getMedida()+" PRECIO: "+ detalle.getPrecioventa()+"  ";
                detallefacturaEJB.create(detalle);
            }
            String subject;
            String ultimafactura = facturaEJB.ultimafacturaformat();
            String fechafactu = formateador.format(factura.getFecha());
            empresa= empresaEJB.devolverEmpresabase();
            correo = "FACTURA NRO: " + ultimafactura
                    + "  CONTROL: " + factura.getNumerocontrol()
                    + "  USUARIO: " + factura.getIdusuario().getNombre()
                    + "  DEPARTAMENTO: " + factura.getIdusuario().getIddepartamento().getDepartamento()
                    + "  FECHA: " + fechafactu
                    + "  CLIENTE: " + factura.getRifcliente().getRazonsocial()
                    + "  RIF: " + factura.getRifcliente().getRifcliente()
                    + "  MATERIAL: " + material
                    + "  SUBTOTAL: " + formatearnumero.format(factura.getBimponiblefact())
                    + "  IVA: " + formatearnumero.format(factura.getIvafact())
                    + "  TOTAL: " + formatearnumero.format(factura.getTotalgeneral())
                    + "  OBSERVACIONES: " + factura.getObservacionesfact();

            subject = empresa.getNombrecomercial()+" Emisión de Factura N° " + ultimafactura;
            enviomail = new envioCorreo(correo, subject);
            enviomail.start();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "La Factura se registro exitosamente con el numero " + facturaEJB.ultimafacturaformat()));
            limpiarListaArreglo();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error al Grabar esta Factura"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void buscarArticulo() {
        articulo = requer.getCodigo();
        pcosto = articulo.getPcosto();
        pventa = articulo.getPventa();
    }

    public String devolversiguientefactura() {

        siguiente = facturaEJB.siguientefacturaformat();
        
        return siguiente;
    }

    public void anexarafactura() {
        if (cantidad != 0) {
            double alicuota = 0;
            double iva = 0;
            double total = 0;
            Requerimiento reque1 = new Requerimiento();
            reque1.setCodigo(requer.getCodigo());
            reque1.setCantidad(cantidad);
//            pcosto = reque.getCodigo().getPcosto();
            reque1.setPcosto(pventa);
            subtotal = cantidad * pventa;
            reque1.setSubtotal(subtotal);
            alicuota = reque1.getCodigo().getIdgravamen().getAlicuota();
            iva = (subtotal * alicuota) / 100;
            total = subtotal + iva;
            reque1.setTributoiva(iva);
            reque1.setTotal(total);
            reque1.setIdrequerimiento(id);
            this.listarequerimiento.add(reque1);
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

    public void limpiarListaArreglo() {
        listarequerimiento.clear();

    }
}
