package Jsf;

import Jpa.ArticuloFacadeLocal;
import Jpa.AuxiliarrequerimientoFacadeLocal;
import Jpa.CompraFacadeLocal;
import Jpa.DepartamentoFacadeLocal;
import Jpa.DetallecompraFacadeLocal;
import Jpa.EstatusfacturaFacadeLocal;
import Jpa.EstatusrequerimientoFacadeLocal;
import Jpa.ProveedorFacadeLocal;
import Jpa.RequerimientoFacadeLocal;
import Modelo.Articulo;
import Modelo.Auxiliarrequerimiento;
import Modelo.Detallecompra;
import Modelo.Compra;
import Modelo.Departamento;
import Modelo.Estatusfactura;
import Modelo.Estatusrequerimiento;
import Modelo.Proveedor;
import Modelo.Requerimiento;
import Modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@ManagedBean(name = "comprasController")
@SessionScoped

public class ComprasController implements Serializable {

    @EJB
    private AuxiliarrequerimientoFacadeLocal auxiliarrequerimientoEJB;
    @EJB
    private RequerimientoFacadeLocal requerimientoEJB;
    @EJB
    private ProveedorFacadeLocal proveedorEJB;
    @EJB
    private ArticuloFacadeLocal articuloEJB;
    @EJB
    private CompraFacadeLocal compraEJB;
    @EJB
    private DepartamentoFacadeLocal departamentoEJB;
    @EJB
    private DetallecompraFacadeLocal detallecompraEJB;
    @EJB
    private EstatusrequerimientoFacadeLocal estatusrequerimientoEJB;
    @EJB
    private EstatusfacturaFacadeLocal estatusfacturaEJB;

    private Auxiliarrequerimiento auxiliarrequerimiento;
    private Usuario usa;
    private Departamento dpto;
    private Compra codCompra;
    private Compra compraautorizada;

    @Inject
    private Auxiliarrequerimiento auxiliar;

    @Inject
    private Requerimiento requerimiento;

    @Inject
    private Compra compra;
    @Inject
    private Detallecompra detallecompra;

    public Compra getCompra() {
        return compra;
    }

    public Compra getCompraautorizada() {
        return compraautorizada;
    }

    public void setCompraautorizada(Compra compraautorizada) {
        this.compraautorizada = compraautorizada;
    }

    
    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Detallecompra getDetallecompra() {
        return detallecompra;
    }

    public void setDetallecompra(Detallecompra detallecompra) {
        this.detallecompra = detallecompra;
    }

    private List<Auxiliarrequerimiento> auxiliarrequerimientos;
    private List<Requerimiento> requerimientos;
    private List<Proveedor> proveedores;
    private List<Articulo> articulos;
    private int idAuxiliar = 0;
    private List<Requerimiento> requerimientosFiltrados;
    private List<Detallecompra> detallesCompras;
    private List<Detallecompra> detallesactuales;
    private List<Compra> comprasporautorizar = null;
    private List<Compra> comprasporpagar = null;
    private List<Compra> compraspagadas = null;

    public int getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(int idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    @Inject
    private Proveedor provee;

    @Inject
    private RequerimientosController requerimientosController;

    public Proveedor getProvee() {
        return provee;
    }

    public void setProvee(Proveedor provee) {
        this.provee = provee;
    }

    public List<Auxiliarrequerimiento> getAuxiliarrequerimientos() {
        return auxiliarrequerimientos;
    }

    public void setAuxiliarrequerimientos(List<Auxiliarrequerimiento> auxiliarrequerimientos) {
        this.auxiliarrequerimientos = auxiliarrequerimientos;
    }

    public List<Requerimiento> getRequerimientos() {
        return requerimientos;
    }

    public void setRequerimientos(List<Requerimiento> requerimientos) {
        this.requerimientos = requerimientos;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public Auxiliarrequerimiento getAuxiliarrequerimiento() {
        return auxiliarrequerimiento;
    }

    public void setAuxiliarrequerimiento(Auxiliarrequerimiento auxiliarrequerimiento) {
        this.auxiliarrequerimiento = auxiliarrequerimiento;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public List<Requerimiento> getRequerimientosFiltrados() {
        return requerimientosFiltrados;
    }

    public void setRequerimientosFiltrados(List<Requerimiento> requerimientosFiltrados) {
        this.requerimientosFiltrados = requerimientosFiltrados;
    }

    public List<Detallecompra> getDetallesCompras() {
        return detallesCompras;
    }

    public void setDetallesCompras(List<Detallecompra> detallesCompras) {
        this.detallesCompras = detallesCompras;
    }

    public List<Compra> getComprasporautorizar() {
        return comprasporautorizar;
    }

    public void setComprasporautorizar(List<Compra> comprasporautorizar) {
        this.comprasporautorizar = comprasporautorizar;
    }

    public List<Compra> getComprasporpagar() {
        return comprasporpagar;
    }

    public void setComprasporpagar(List<Compra> comprasporpagar) {
        this.comprasporpagar = comprasporpagar;
    }

    public List<Compra> getCompraspagadas() {
        return compraspagadas;
    }

    public void setCompraspagadas(List<Compra> compraspagadas) {
        this.compraspagadas = compraspagadas;
    }
    

    @PostConstruct
    public void init() {
        auxiliarrequerimientos = auxiliarrequerimientoEJB.findAll();
        requerimientos = requerimientoEJB.findAll();
        proveedores = proveedorEJB.findAll();
        articulos = articuloEJB.findAll();
        comprasporautorizar = compraEJB.buscarcomprasporAutorizar();
        comprasporpagar = compraEJB.buscarcomprasporPagar();
        compraspagadas = compraEJB.buscarcomprasPagadas();

//        this.auxiliarrequerimiento=requerimientosController.getAuxrequer();
    }
    
    public List<Proveedor> listarproveedores(){
        List<Proveedor> lista= null;
        lista=proveedorEJB.findAll();
        return lista;
    }
       
    public void asignar(Auxiliarrequerimiento aux) {
        this.auxiliarrequerimiento = aux;
        this.idAuxiliar = aux.getIdauxiliarrequerimiento();
        this.auxiliar = aux;
        requerimientosFiltrados = requerimientosAuxiliar();
        this.compra.setIdauxiliarrequerimiento(auxiliar);
    }

    public List<Requerimiento> buscarrequerimiento() {
        List<Requerimiento> listado = null;
        listado = requerimientoEJB.buscarrequerimientos(auxiliarrequerimiento);
        return listado;
    }

    /*    public List<Detallecompra> buscardetallecompra() {
        List<Detallecompra> listado = null;
        listado = detallecompraEJB.buscardetalle(compra);
        return listado;
    }*/
    public List<Requerimiento> requerimientosAuxiliar() {
        List<Requerimiento> listado = null;
        listado = requerimientoEJB.requerimientosAuxiliar(idAuxiliar);
        return listado;
    }

    public void asignarProveedor(Proveedor proveed) {
        provee = proveed;
    }

    public void modificar() {
        double subtotal = 0;
        double alicuota = 0;
        double iva = 0;
        double montotgeneral = 0;
        double montotiva = 0;
        double montotsubtotal = 0;
        double total = 0;
        List<Requerimiento> requerimientosactulizado;
        subtotal = requerimiento.getCantidad() * requerimiento.getPcosto();
        alicuota = requerimiento.getCodigo().getIdgravamen().getAlicuota();
        iva = (subtotal * alicuota) / 100;
        total = subtotal + iva;
        requerimiento.setSubtotal(subtotal);
        requerimiento.setTributoiva(iva);
        requerimiento.setTotal(total);

        requerimientoEJB.edit(requerimiento);

        requerimientosactulizado = buscarrequerimiento();
        for (Requerimiento requeri : requerimientosactulizado) {
            montotgeneral += requeri.getTotal();
            montotiva += requeri.getTributoiva();
            montotsubtotal += requeri.getSubtotal();
        }

        auxiliarrequerimiento.setSubtotal(montotsubtotal);
        auxiliarrequerimiento.setMontoiva(montotiva);
        auxiliarrequerimiento.setMontototal(montotgeneral);

        auxiliarrequerimientoEJB.edit(auxiliarrequerimiento);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Requerimiento fue Modificado"));
    }

    public void autorizar() {
        Estatusfactura statusfactu = null;
        int tipo = 2;
        statusfactu = estatusfacturaEJB.cambiarestatusFactura(tipo);
        compraautorizada.setIdestatusfactura(statusfactu);
        compraEJB.edit(compraautorizada);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Compra Autorizada por Gerencia"));
    }

    public void asignarRequerimiento(Requerimiento requeri) {
        requerimiento = requeri;
    }
    public void asignarCompra(Compra compraselec) {
        compra = compraselec;
    }
    
    public void asignarCompraAutorizada(Compra compraselec ){
        this.idAuxiliar = compraselec.getIdauxiliarrequerimiento().getIdauxiliarrequerimiento();
        this.auxiliar = compraselec.getIdauxiliarrequerimiento();
        requerimientosFiltrados = requerimientosAuxiliar();
        compraautorizada=compraselec;      
        
        
    }
    
    public List<Requerimiento> solicitarRequerimientosFiltro() {
        return requerimientosFiltrados;
    }

    public void actualizarRequerimiento() {
        double subtotal = 0;
        double alicuota = 0;
        double iva = 0;
        double total = 0;
        subtotal = requerimiento.getCantidad() * requerimiento.getPcosto();
        alicuota = requerimiento.getCodigo().getIdgravamen().getAlicuota();
        iva = subtotal * alicuota;
        total = subtotal + iva;
        requerimiento.setSubtotal(subtotal);
        requerimiento.setTributoiva(iva);
        requerimiento.setTotal(total);
    }

    public void registrar() {
        try {
            compra.setRifproveedor(provee);
            compra.setSubtotal(auxiliar.getSubtotal());
            compra.setIva(auxiliar.getMontoiva());
            compra.setTotal(auxiliar.getMontototal());
            compra.setMontopendiente(auxiliar.getMontototal());
            Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            compra.setIdusuario(us);
            Estatusfactura statusfactu = null;
            int tipo = 0;
            if (compra.getTotal() <= 50000) {
                tipo = 0;
            } else if (compra.getTotal() > 50000) {
                tipo = 1;
            }
            statusfactu = estatusfacturaEJB.cambiarestatusFactura(tipo);
            compra.setIdestatusfactura(statusfactu);
            compraEJB.create(compra);

            Estatusrequerimiento statusreque = null;
            statusreque = estatusrequerimientoEJB.cambiarestatusaProcesado();
            auxiliarrequerimiento.setIdestatusrequerimiento(statusreque);
            auxiliarrequerimientoEJB.edit(auxiliarrequerimiento);

            codCompra = compraEJB.ultimacompraInsertada();
            int numerocompra = codCompra.getIdcompra();
            for (Requerimiento rq : requerimientosFiltrados) {
                Articulo arti = rq.getCodigo();
                detallecompra.setIdcompra(codCompra);
                detallecompra.setCodigo(arti);
                detallecompra.setCantidad(rq.getCantidad());
                detallecompra.setPcosto(rq.getPcosto());
                detallecompra.setSubtotal(rq.getSubtotal());
                detallecompra.setTributoiva(rq.getTributoiva());
                detallecompra.setTotalapagar(rq.getTotal());
                detallecompraEJB.create(detallecompra);
            }
            if (tipo==1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Requerimiento fue enviado para Autorizacion con el Nro " + numerocompra ));                
            }else{    
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Requerimiento fue Almacenado con el Nro " + numerocompra ));
            }
            } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error al Grabar Requerimiento"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public Usuario getUsuario() {
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        usa = us;
        return us;
    }

    public Departamento buscarDepartamento() {
        Usuario usua = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        dpto = departamentoEJB.buscarDepartamento(usua);
//        statusreq.setIdestatusrequerimiento(statu);
        return dpto;
    }

    public List<Requerimiento> buscarRequerimiento(Auxiliarrequerimiento auxi) {
        requerimientosFiltrados = requerimientoEJB.buscarrequerimientos(auxiliar);
        return requerimientosFiltrados;
    }

    public List<Requerimiento> requerimientosAuxiliar(int idaux) {
        requerimientosFiltrados = requerimientoEJB.requerimientosAuxiliar(idAuxiliar);
        return requerimientosFiltrados;
    }

    public List<Compra> buscarComprasporAutorizar() {
        comprasporautorizar = compraEJB.buscarcomprasporAutorizar();
        return comprasporautorizar;
    }

    public List<Compra> buscarComprasporPagar() {
        comprasporpagar = compraEJB.buscarcomprasporPagar();
        return comprasporpagar;
    }
        public List<Compra> buscarComprasPagadas() {
        compraspagadas = compraEJB.buscarcomprasPagadas();
        return comprasporpagar;
    }
}
