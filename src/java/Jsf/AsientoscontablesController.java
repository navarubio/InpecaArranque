/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jsf;

import Jpa.AutorizacionFacadeLocal;
import Jpa.AuxiliarrequerimientoFacadeLocal;
import Jpa.BancoFacadeLocal;
import Jpa.CompraFacadeLocal;
import Jpa.CuentabancariaFacadeLocal;
import Jpa.DepartamentoFacadeLocal;
import Jpa.DetallecompraFacadeLocal;
import Jpa.DetallelibrodiarioFacadeLocal;
import Jpa.DetalleretencionislrefFacade;
import Jpa.DetalleretencionislrefFacadeLocal;
import Jpa.DetalleretencionivaefFacade;
import Jpa.DetalleretencionivaefFacadeLocal;
import Jpa.EmpresaFacadeLocal;
import Jpa.EstatuscontableFacadeLocal;
import Jpa.EstatusfacturaFacadeLocal;
import Jpa.LibrodiarioFacadeLocal;
import Jpa.LibromayorFacadeLocal;
import Jpa.MaestromovimientoFacadeLocal;
import Jpa.MovimientobancarioFacadeLocal;
import Jpa.PagocompraFacadeLocal;
import Jpa.PlandecuentaFacadeLocal;
import Jpa.RequerimientoFacadeLocal;
import Jpa.TipoconjuntoFacadeLocal;
import Jpa.TipopagoFacadeLocal;
import Jpa.TiporetencionislrFacadeLocal;
import Modelo.Articulo;
import Modelo.Autorizacion;
import Modelo.Auxiliarrequerimiento;
import Modelo.Banco;
import Modelo.Compra;
import Modelo.Comprobanteivaef;
import Modelo.Cuentabancaria;
import Modelo.Departamento;
import Modelo.Detallecompra;
import Modelo.Detallelibrodiario;
import Modelo.Detalleretencionislref;
import Modelo.Detalleretencionivaef;
import Modelo.Empresa;
import Modelo.Estatuscontable;
import Modelo.Estatusfactura;
import Modelo.Librodiario;
import Modelo.Libromayor;
import Modelo.Maestromovimiento;
import Modelo.Movimientobancario;
import Modelo.Pagocompra;
import Modelo.Plandecuenta;
import Modelo.Requerimiento;
import Modelo.Tipoconjunto;
import Modelo.Tipopago;
import Modelo.Tiporetencionislr;
import Modelo.Usuario;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Inpeca
 */
@Named
@SessionScoped

public class AsientoscontablesController implements Serializable {

    @EJB
    private CuentabancariaFacadeLocal cuentabancariaEJB;
    @EJB
    private PagocompraFacadeLocal pagocompraEJB;
    @EJB
    private EstatusfacturaFacadeLocal estatusfacturaEJB;
    @EJB
    private EstatuscontableFacadeLocal estatuscontableEJB;
    @EJB
    private AutorizacionFacadeLocal autorizacionEJB;
    @EJB
    private AuxiliarrequerimientoFacadeLocal auxiliarrequerimientoEJB;
    @EJB
    private CompraFacadeLocal compraEJB;
    @EJB
    private DetallecompraFacadeLocal detallecompraEJB;
    @EJB
    private DepartamentoFacadeLocal departamentoEJB;
    @EJB
    private TipopagoFacadeLocal tipopagoEJB;
    @EJB
    private BancoFacadeLocal bancoEJB;
    @EJB
    private TiporetencionislrFacadeLocal tiporetencionislrEJB;
    @EJB
    private DetalleretencionivaefFacadeLocal detalleretencionivaefEJB;
    @EJB
    private DetalleretencionislrefFacadeLocal detalleretencionislrefEJB;
    @EJB
    private EmpresaFacadeLocal empresaEJB;
    @EJB
    private MaestromovimientoFacadeLocal maestromovimientoEJB;
    @EJB
    private TipoconjuntoFacadeLocal tipoconjuntoEJB;
    @EJB
    private MovimientobancarioFacadeLocal movimientoBancarioEJB;
    @EJB
    private PlandecuentaFacadeLocal plandecuentaEJB;
    @EJB
    private LibrodiarioFacadeLocal librodiarioEJB;
    @EJB
    private LibromayorFacadeLocal libromayorEJB;
    @EJB
    private DetallelibrodiarioFacadeLocal detallelibrodiarioEJB;

    private Auxiliarrequerimiento auxiliarrequerimiento;
    private Compra compra;
    private Maestromovimiento master;
    private Autorizacion autoriza;
    private Detallecompra detallecompras;
    private Detallelibrodiario detalleaanexar = new Detallelibrodiario();
    private Pagocompra pagocompra = new Pagocompra();
    private Pagocompra pagocompraver;
    private Estatusfactura statusfactu = null;
    private int formapago = 0;
    private Estatuscontable estatuscontab = null;
    private Banco banco;
    private Cuentabancaria cuentabanco;
    private Pagocompra pago;
    private Usuario usa;
    private Departamento dpto;
    private Compra codCompra;
    
    private int visualizar = 0;
    private int tipocompra = 1;
    private double montoUT = 0;
    private double montopisoretiva = 0;
    private double montopisoretislr = 0;
    private int idAuxiliar = 0;
    private int idCompra = 0;
    private int id = 0;
    private int indicearreglo = 0;
    private double totaldebegeneral = 0;
    private double totalhabergeneral = 0;
    private int vercasilla = 0;
    private double retiva = 0;
    private double retislr = 0;
    private List<Auxiliarrequerimiento> auxiliarrequerimientos;
    private List<Tiporetencionislr> tiporetencionesfiltradasPD = null;
    private List<Cuentabancaria> cuentasbancarias;
    private List<Tipopago> tipopagos;
    private List<Detallecompra> detallecompraFiltrados;
    private List<Banco> bancos;
    private List<Cuentabancaria> lstCuentasSelecc;
    private List<Pagocompra> pagosefectuados;
    private List<Pagocompra> pagoespecifico;
    private String mensaje;
    private Date fechaactual = new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");
    private String correo;
    private envioCorreo enviomail;
    @Inject
    private Detalleretencionivaef detalleretencionivaef;
    @Inject
    private Detalleretencionislref detalleretencionislref;
    @Inject
    private Movimientobancario movimientobancario;
    private double ivaretenido;
    private double islrretenido;
    private double montoapagar;
    private double totalretenido;
    ArrayList<Detallecompra> lista = new ArrayList();
    private Tipoconjunto tipoconjunto = null;
    private List<Detallelibrodiario> listadetalleslibrodiario = new ArrayList();
    private Detallelibrodiario detalleamodificar;
    private int cuentaseleccionada;
    private Librodiario codlibrodiario;

    @Inject
    private Auxiliarrequerimiento auxiliar;
    private Requerimiento requerimiento;
    @Inject
    private Compra compras;
    @Inject
    private Detallecompra detallecompra;
    @Inject
    private Tipopago tipopago;
    @Inject
    private Cuentabancaria cuentabancaria;
    @Inject
    private Maestromovimiento maestromovi;
    @Inject
    private Librodiario librodiario;
    @Inject
    private Detallelibrodiario detallelibroventa;
    @Inject
    private Libromayor libromayor;
    @Inject
    private Empresa empresa;
    
    public List<Detallelibrodiario> getListadetalleslibrodiario() {
        return listadetalleslibrodiario;
    }

    public void setListadetalleslibrodiario(List<Detallelibrodiario> listadetalleslibrodiario) {
        this.listadetalleslibrodiario = listadetalleslibrodiario;
    }

    public int getCuentaseleccionada() {
        return cuentaseleccionada;
    }

    public void setCuentaseleccionada(int cuentaseleccionada) {
        this.cuentaseleccionada = cuentaseleccionada;
    }

    public Librodiario getLibrodiario() {
        return librodiario;
    }

    public Detallelibrodiario getDetalleamodificar() {
        return detalleamodificar;
    }

    public void setDetalleamodificar(Detallelibrodiario detalleamodificar) {
        this.detalleamodificar = detalleamodificar;
    }

    public int getVercasilla() {
        return vercasilla;
    }

    public void setVercasilla(int vercasilla) {
        this.vercasilla = vercasilla;
    }

    public void setLibrodiario(Librodiario librodiario) {
        this.librodiario = librodiario;
    }

    public Detallelibrodiario getDetallelibroventa() {
        return detallelibroventa;
    }

    public double getRetiva() {
        return retiva;
    }

    public void setRetiva(double retiva) {
        this.retiva = retiva;
    }

    public double getRetislr() {
        return retislr;
    }

    public Detallelibrodiario getDetalleaanexar() {
        return detalleaanexar;
    }

    public void setDetalleaanexar(Detallelibrodiario detalleaanexar) {
        this.detalleaanexar = detalleaanexar;
    }

    public void setRetislr(double retislr) {
        this.retislr = retislr;
    }

    public double getTotaldebegeneral() {
        return totaldebegeneral;
    }

    public void setTotaldebegeneral(double totaldebegeneral) {
        this.totaldebegeneral = totaldebegeneral;
    }

    public double getTotalhabergeneral() {
        return totalhabergeneral;
    }

    public void setTotalhabergeneral(double totalhabergeneral) {
        this.totalhabergeneral = totalhabergeneral;
    }

    public void setDetallelibroventa(Detallelibrodiario detallelibroventa) {
        this.detallelibroventa = detallelibroventa;
    }

    public Libromayor getLibromayor() {
        return libromayor;
    }

    public void setLibromayor(Libromayor libromayor) {
        this.libromayor = libromayor;
    }

    public List<Cuentabancaria> getLstCuentasSelecc() {
        return lstCuentasSelecc;
    }

    public double getIvaretenido() {
        return ivaretenido;
    }

    public void setIvaretenido(double ivaretenido) {
        this.ivaretenido = ivaretenido;
    }

    public void setLstCuentasSelecc(List<Cuentabancaria> lstCuentasSelecc) {
        this.lstCuentasSelecc = lstCuentasSelecc;
    }

    public Pagocompra getPagocompra() {
        return pagocompra;
    }

    public Detalleretencionivaef getDetalleretencionivaef() {
        return detalleretencionivaef;
    }

    public void setDetalleretencionivaef(Detalleretencionivaef detalleretencionivaef) {
        this.detalleretencionivaef = detalleretencionivaef;
    }

    public Detalleretencionislref getDetalleretencionislref() {
        return detalleretencionislref;
    }

    public void setDetalleretencionislref(Detalleretencionislref detalleretencionislref) {
        this.detalleretencionislref = detalleretencionislref;
    }

    public Requerimiento getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(Requerimiento requerimiento) {
        this.requerimiento = requerimiento;
    }

    public int getFormapago() {
        return formapago;
    }

    public List<Tiporetencionislr> getTiporetencionesfiltradasPD() {
        return tiporetencionesfiltradasPD;
    }

    public void setTiporetencionesfiltradasPD(List<Tiporetencionislr> tiporetencionesfiltradasPD) {
        this.tiporetencionesfiltradasPD = tiporetencionesfiltradasPD;
    }

    public void setFormapago(int formapago) {
        this.formapago = formapago;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public Cuentabancaria getCuentabanco() {
        return cuentabanco;
    }

    public void setCuentabanco(Cuentabancaria cuentabanco) {
        this.cuentabanco = cuentabanco;
    }

    public void setPagocompra(Pagocompra pagocompra) {
        this.pagocompra = pagocompra;
    }

    public Pagocompra getPagocompraver() {
        return pagocompraver;
    }

    public void setPagocompraver(Pagocompra pagocompraver) {
        this.pagocompraver = pagocompraver;
    }

    public Date getFechaactual() {
        return fechaactual;
    }

    public void setFechaactual(Date fechaactual) {
        this.fechaactual = fechaactual;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

    public Tipopago getTipopago() {
        return tipopago;
    }

    public void setTipopago(Tipopago tipopago) {
        this.tipopago = tipopago;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Pagocompra getPago() {
        return pago;
    }

    public double getTotalretenido() {
        return totalretenido;
    }

    public void setTotalretenido(double totalretenido) {
        this.totalretenido = totalretenido;
    }

    public void setPago(Pagocompra pago) {
        this.pago = pago;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<Detallecompra> getDetallecompraFiltrados() {
        return detallecompraFiltrados;
    }

    public void asignarDetalleCompra(Detallecompra detallecompra) {
        this.detallecompra = detallecompra;

    }

    public void setDetallecompraFiltrados(List<Detallecompra> detallecompraFiltrados) {
        this.detallecompraFiltrados = detallecompraFiltrados;
    }

    public Auxiliarrequerimiento getAuxiliarrequerimiento() {
        return auxiliarrequerimiento;
    }

    public void setAuxiliarrequerimiento(Auxiliarrequerimiento auxiliarrequerimiento) {
        this.auxiliarrequerimiento = auxiliarrequerimiento;
    }

    public int getIdAuxiliar() {
        return idAuxiliar;
    }

    public void setIdAuxiliar(int idAuxiliar) {
        this.idAuxiliar = idAuxiliar;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Autorizacion getAutoriza() {
        return autoriza;
    }

    public void setAutoriza(Autorizacion autoriza) {
        this.autoriza = autoriza;
    }

    public Detallecompra getDetallecompra() {
        return detallecompra;
    }

    public void setDetallecompra(Detallecompra detallecompra) {
        this.detallecompra = detallecompra;
    }

    public List<Tipopago> getTipopagos() {
        return tipopagos;
    }

    public void setTipopagos(List<Tipopago> tipopagos) {
        this.tipopagos = tipopagos;
    }

    public List<Pagocompra> getPagosefectuados() {
        return pagosefectuados;
    }

    public void setPagosefectuados(List<Pagocompra> pagosefectuados) {
        this.pagosefectuados = pagosefectuados;
    }

    public List<Pagocompra> getPagoespecifico() {
        return pagoespecifico;
    }

    public void setPagoespecifico(List<Pagocompra> pagoespecifico) {
        this.pagoespecifico = pagoespecifico;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getVisualizar() {
        return visualizar;
    }

    public void setVisualizar(int visualizar) {
        this.visualizar = visualizar;
    }

    public int getTipocompra() {
        return tipocompra;
    }

    public void setTipocompra(int tipocompra) {
        this.tipocompra = tipocompra;
    }

    @PostConstruct
    public void init() {
        visualizar = 0;
        vercasilla = 0;
//        librodiario.setFecha(fechaactual);
        auxiliarrequerimientos = auxiliarrequerimientoEJB.findAll();
        cuentasbancarias = cuentabancariaEJB.findAll();
        tipopagos = tipopagoEJB.findAll();
        bancos = bancoEJB.findAll();
        pagosefectuados = pagocompraEJB.findAll();
        pago = new Pagocompra();
        listadetalleslibrodiario.clear();
        retiva = 0;
        retislr = 0;
        //articulos = articuloEJB.findAll();
        //comprasporautorizar=compraEJB.buscarcomprasporAutorizar();

//        this.auxiliarrequerimiento=requerimientosController.getAuxrequer();
    }

    public void asignar(Compra compr) {
        this.tipocompra = 1;
        this.visualizar = 0;
        this.totalretenido = 0;
        this.ivaretenido = 0;
        this.islrretenido = 0;
        this.compra = compr;
        this.pagocompra.setTotalpago(compra.getTotal());
        this.montoapagar = compra.getTotal();
        this.idCompra = compr.getIdcompra();
        this.autoriza = autorizacionEJB.buscarAutorizacion(idCompra);
        this.auxiliarrequerimiento = compr.getIdauxiliarrequerimiento();
        detalleretencionivaef.setTotalivaretenido(0.0);
        detalleretencionislref.setTotalislrretenido(0.0);
        detalleretencionislref.setSustraendo(0.0);
        detalleretencionislref.setProcentajeretencion(0.0);
        detalleretencionivaef.setIdtiporetencioniva(null);
        detalleretencionislref.setIdtiporetencionislr(null);
//        this.auxiliar = aux;
        detallecompraFiltrados = detallecompraAuxiliar();
        tiporetencionesfiltradasPD = tiporetencionislrEJB.tiporetfiltradaPJyD(compra.getRifproveedor().getIdpersonalidad(), compra.getRifproveedor().getIdresidencia());
        empresa = empresaEJB.devolverEmpresabase();
        double montocompra = compra.getTotal();
        double montoiva = compra.getIva();

        // OJO CON ESTAS VARIABLES PARA CUANDO CAMBIE LA UNIDAD TRIBUTARIA CAMBIARLAS
        montopisoretiva = (20 * 300);
        montopisoretislr = 25000;
        //////////////////////////////////////////////////////////////////////////////

        int personaj = compra.getRifproveedor().getIdpersonalidad().getIdpersonalidad();
        int residencia = compra.getRifproveedor().getIdresidencia().getIdresidencia();
        int tipo1;
        for (Detallecompra tipoc : detallecompraFiltrados) {
            tipo1 = tipoc.getCodigo().getIdgrupo().getIdgrupo();
            if (tipo1 == 1 && tipocompra == 1) {
                tipocompra = 1;
            } else if (tipo1 == 2) {
                tipocompra = 2;
            } else if (tipo1 == 3) {
                tipocompra = 3;
            }
        }
        if (empresa.getIdcontribuyente().getIdcontribuyente() == 2) {
            if (montocompra >= montopisoretiva) {
                if (tipocompra == 3) {
                    visualizar = 5;
                } else if (tipocompra == 1) {
                    if (montoiva > 0) {
                        visualizar = 1;
                    } else {
                        visualizar = 5;
                    }
                } else if ((tipocompra == 2) && (personaj == 2) && (residencia == 1)) {
                    if (montocompra >= montopisoretislr) {
                        if (montoiva > 0) {
                            visualizar = 2;
                        } else {
                            visualizar = 3;
                        }
                    } else {
                        if (montoiva > 0) {
                            visualizar = 1;
                        } else {
                            visualizar = 5;
                        }
                    }
                } else {
                    if (montoiva > 0) {
                        visualizar = 2;
                    } else {
                        visualizar = 3;
                    }
                }
            } else {
                if (tipocompra == 2) {
                    if ((personaj == 2) && (residencia == 1)) {
                        visualizar = 5;
                    } else {
                        visualizar = 3;
                    }
                } else if (tipocompra == 3) {
                    visualizar = 5;
                } else if (tipocompra == 1) {
                    visualizar = 5;
                }
            }
        } else if (empresa.getIdcontribuyente().getIdcontribuyente() == 1 || empresa.getIdcontribuyente().getIdcontribuyente() == 3) {
            if (tipocompra == 2) {
                if ((personaj == 2) && (residencia == 1)) {
                    if (montocompra >= montopisoretislr) {
                        visualizar = 4;
                    } else {
                        visualizar = 5;
                    }
                } else {
                    visualizar = 4;
                }
            } else if (tipocompra == 3) {
                visualizar = 5;
            } else if (tipocompra == 1) {
                visualizar = 5;
            }
        }
        calcularMontoapagar();
        if (compra.getTotal() > (compra.getMontopendiente())) {
            visualizar = 6;
        }
    }

    public void asignarCompra(Compra compr, Maestromovimiento maestro) {
        this.retiva = 0;
        this.retislr = 0;
        this.vercasilla = 1;
        this.compra = compr;
        this.master = maestro;
        this.idCompra = compr.getIdcompra();
        this.pagoespecifico = pagocompraEJB.buscarpago(compr);
        this.pagocompra = pagocompraEJB.buscarpagototal(compr);
        this.auxiliarrequerimiento = compr.getIdauxiliarrequerimiento();
//        this.auxiliar = aux;
        detallecompraFiltrados = detallecompraAuxiliar();
        listadetalleslibrodiario = detallesasiento();
        librodiario.setFecha(compra.getFechaorden());
        Detallecompra detal = detallecompraFiltrados.get(0);
        Articulo artic = detal.getCodigo();
        librodiario.setDescripcionasiento("P/R COMPRA CMP-" + compra.getIdcompra() + " " + artic.getDescripcion()+ ", "+compra.getIdauxiliarrequerimiento().getDescripcion());
//        this.compra.setIdauxiliarrequerimiento(auxiliar);
    }

    public void asignarPagoCompra(Pagocompra pagocompr, Maestromovimiento maestro) {
        this.retiva = 0;
        this.retislr = 0;
        this.vercasilla = 2;
        this.compra = pagocompr.getIdcompra();
        this.master = maestro;
        this.idCompra = pagocompr.getIdcompra().getIdcompra();
        this.pagoespecifico = pagocompraEJB.buscarpago(compra);
        this.pagocompra = pagocompr;
        this.auxiliarrequerimiento = compra.getIdauxiliarrequerimiento();
        if (pagocompra.getMontoretenido() > 0) {
            this.retiva = detalleretencionivaefEJB.retencionivaporpago(pagocompra.getIdpagocompra());
            this.retislr = detalleretencionislrefEJB.retencionislrporpago(compra.getIdcompra());
        }
//        this.auxiliar = aux;
        detallecompraFiltrados = detallecompraAuxiliar();
        listadetalleslibrodiario = detallesasientopago();
        librodiario.setFecha(compra.getFechaorden());
        librodiario.setDescripcionasiento("P/R PAGO DE COMPRA CMP-" + compra.getIdcompra()+", "+pagocompra.getObservacionespago());
//        this.compra.setIdauxiliarrequerimiento(auxiliar);
    }

    public List<Detallecompra> detallecompraAuxiliar() {
        List<Detallecompra> listado = null;
        listado = detallecompraEJB.buscardetallecompra(compra);
        return listado;
    }

    public List<Detallelibrodiario> detallesasiento() {
        List<Detallelibrodiario> detallesasiento = null;
        anexar();
        detallesasiento = listadetalleslibrodiario;
        return detallesasiento;
    }

    public List<Detallelibrodiario> detallesasientopago() {
        List<Detallelibrodiario> detallesasiento = null;
        anexarpagocompra();
        detallesasiento = listadetalleslibrodiario;
        return detallesasiento;
    }

    public List<Requerimiento> requerimientosAuxiliar() {
        List<Requerimiento> listado = null;
        //**** listado = requerimientoEJB.requerimientosAuxiliar(idAuxiliar);
        return listado;
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

    public List<Pagocompra> buscarTodoslosPagos() {
        pagosefectuados = pagocompraEJB.findAll();
        return pagosefectuados;
    }

    public List<Cuentabancaria> refrescarCuentasBancarias() {
        try {
            lstCuentasSelecc = cuentabancariaEJB.espxBanco(banco.getIdbanco());
        } catch (Exception e) {
        }
        pagocompra.setIdcuentabancaria(lstCuentasSelecc.get(0));
        return lstCuentasSelecc;
    }

    public void calcularivaretenido() {
        double ivatotal = compra.getIva();
        double porcent = detalleretencionivaef.getIdtiporetencioniva().getPorcentajeiva();
        ivaretenido = (ivatotal * porcent) / 100;
        detalleretencionivaef.setTotalivaretenido(ivaretenido);
        ivatotal = 0;
        porcent = 0;
    }

    public void calcularislrretenido() {
        int personal = compra.getRifproveedor().getIdpersonalidad().getIdpersonalidad();
        int residenc = compra.getRifproveedor().getIdresidencia().getIdresidencia();
        int tiposervicio = detalleretencionislref.getIdtiporetencionislr().getIdsubgrupo().getIdsubgrupo();
        Tiporetencionislr tiporetencion = tiporetencionislrEJB.retencionislrFiltrada(personal, residenc, tiposervicio);
        double bimponibletotal = compra.getSubtotal();
        double porcentbimponible = tiporetencion.getPorcentajebimponible();
        double porcentislr = tiporetencion.getPorcentajeretencion();
        double sustraendo = tiporetencion.getSustraendo();
        islrretenido = (((((porcentbimponible * bimponibletotal) / 100) * porcentislr) / 100) - sustraendo);
        detalleretencionislref.setProcentajeretencion(porcentislr);
        detalleretencionislref.setTotalislrretenido(islrretenido);
        detalleretencionislref.setSustraendo(sustraendo);
    }

    public void registrar() {
        try {
            double debe = 0;
            double haber = 0;
            double saldototaltotal = 0;
            librodiarioEJB.create(librodiario);
            codlibrodiario = librodiarioEJB.ultimoInsertado();

            Detallelibrodiario detalleld = new Detallelibrodiario();
            Libromayor libromy = new Libromayor();
            Plandecuenta cuentacontable = new Plandecuenta();

            for (Detallelibrodiario dld : listadetalleslibrodiario) {
                detalleld.setIdlibrodiario(codlibrodiario);
                libromy.setIdlibrodiario(codlibrodiario);
                cuentacontable = plandecuentaEJB.buscarcuenta(dld.getIdplandecuenta().getIdplandecuenta());

                detalleld.setIdplandecuenta(dld.getIdplandecuenta());
                libromy.setIdplandecuenta(dld.getIdplandecuenta());

                if (dld.getDebe() != null) {
                    detalleld.setDebe(dld.getDebe());
                    detalleld.setHaber(0.0);
                    debe = dld.getDebe();
                    libromy.setHaber(0.0);
                    libromy.setDebe(dld.getDebe());
                }
                if (dld.getHaber() != null) {
                    detalleld.setHaber(dld.getHaber());
                    detalleld.setDebe(0.0);
                    haber = dld.getHaber();
                    libromy.setDebe(0.0);
                    libromy.setHaber(dld.getHaber());
                }
                double saldoant = plandecuentaEJB.buscarsaldoanterior(dld.getIdplandecuenta().getIdplandecuenta());
                libromy.setSaldoanterior(saldoant);
                if (dld.getIdplandecuenta().getIdtiposaldocontable().getIdtiposaldocontable() == 1) {
                    saldototaltotal = (((saldoant) + debe) - haber);
                } else if (dld.getIdplandecuenta().getIdtiposaldocontable().getIdtiposaldocontable() == 2) {
                    saldototaltotal = (((saldoant) - haber) + debe);
                }
                libromy.setSaldoposterior(saldototaltotal);
                cuentacontable.setSaldogeneral(saldototaltotal);
                detallelibrodiarioEJB.create(detalleld);
                libromayorEJB.create(libromy);
                plandecuentaEJB.edit(cuentacontable);
                master.setIdestatuscontable(estatuscontableEJB.estatusContableRegistrada());
                master.setIdlibrodiario(codlibrodiario);
                maestromovimientoEJB.edit(master);

                debe = 0;
                haber = 0;
                saldototaltotal = 0;
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Asiento fue Almacenado Codigo" + codlibrodiario.getIdlibrodiario(), ""));
            listadetalleslibrodiario.clear();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Asiento Contable", "Aviso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    /*public saldosdecuenta (int codcta){
     Plandecuenta ctaaactualizar= plandecuentaEJB.buscarcuenta(codcta);
     List<Libromayor> listamayorcta= libromayorEJB.listacuentaespecifica(codcta);
     double montotdebe = 0;
     double montothaber = 0;
     double totaldebetotal=0;
     double totalhabertotal=0;
     double saldototaltotal=0;
     double debe=0.0;
     double haber=0.0;

     if (listamayorcta!=null){            
     for (Libromayor mayoranalitico : listamayorcta) {
     if (mayoranalitico.getDebe()!=null){
     montotdebe += mayoranalitico.getDebe();
     }
     if (mayoranalitico.getHaber()!=null){
     montothaber += mayoranalitico.getHaber();            
     }
     }
     totaldebetotal= montotdebe;        
     totalhabertotal=montothaber;
     }else{
     totaldebetotal= debe;
     totalhabertotal=haber;
     }      
        
     if (ctaaactualizar.getIdtipocuentacontable().getIdtipocuentacontable()==1){
     saldototaltotal=(((totaldebetotal - totalhabertotal) + debe) - haber);
     }
        
        
        
     For s = 1 To Adodc2.Recordset.RecordCount
     saldoActDebe = Adodc2.Recordset.Fields("debe")
     saldoTotalDebe = saldoAntDebe + saldoActDebe
     saldoAntDebe = saldoTotalDebe
     saldoActDebe = 0
     saldoActHaber = Adodc2.Recordset.Fields("haber")
     saldoTotalHaber = saldoAntHaber + saldoActHaber
     saldoAntHaber = saldoTotalHaber
     saldoActHaber = 0
     Adodc2.Recordset.MoveNext
     Next s
     If Adodc2.Recordset.RecordCount = 0 Then
     If debe = "" Then
     debe = 0
     ElseIf haber = "" Then
     haber = 0
     End If
     saldoTotalDebe = CDbl(debe)
     saldoTotalHaber = CDbl(haber)
     End If
            
     If tipoSaldo = True Then
     If Adodc2.Recordset.RecordCount <> 0 Then
     SaldoTotalTotal = (((saldoTotalDebe - saldoTotalHaber) + debe) - haber)
     Else
     SaldoTotalTotal = (saldoTotalDebe - saldoTotalHaber)
     End If
     Else
     If Adodc2.Recordset.RecordCount <> 0 Then
     SaldoTotalTotal = ((saldoTotalDebe - saldoTotalHaber) - haber) + debe
     Else
     SaldoTotalTotal = (saldoTotalDebe - saldoTotalHaber)
     End If
     End If
     saldoAntDebe = 0
     saldoAntHaber = 0
     }*/

    public void grabarRetencion() {
        try {
            if (detalleretencionivaef.getTotalivaretenido() >= 1) {
                detalleretencionivaef.setIdcompra(compra);
                detalleretencionivaef.setBimponible(compra.getSubtotal());
                detalleretencionivaef.setTotalcompra(compra.getTotal());
                detalleretencionivaef.setTotalivaretenido(ivaretenido);
                detalleretencionivaef.setTotalivacompra(compra.getIva());
                detalleretencionivaefEJB.create(detalleretencionivaef);
            }
            if (detallecompra.getCodigo().getIdgrupo().getIdgrupo() == 2) {
                detalleretencionislref.setIdcompra(compra);
                detalleretencionislref.setTotalcompra(compra.getTotal());
                detalleretencionislref.setBimponible(compra.getSubtotal());
                detalleretencionislrefEJB.create(detalleretencionislref);
            }
            calcularMontoapagar();
            totalretenido = ivaretenido + islrretenido;
            visualizar = 7;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Retencion fue Almacenada", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Retencion", ""));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void calcularMontoapagar() {
        if (compra.getTotal() > (compra.getMontopendiente())) {
            montoapagar = compra.getMontopendiente();
            pagocompra.setTotalpago(montoapagar);
        } else {
            montoapagar = (compra.getTotal() - detalleretencionivaef.getTotalivaretenido() - detalleretencionislref.getTotalislrretenido());
            pagocompra.setTotalpago(montoapagar);
        }
    }

    public void seleccionpagofraccionado() {
        if (mensaje.equals("total")) {
            formapago = 1;
        } else if (mensaje.equals("parcial")) {
            formapago = 2;
        } else {
            formapago = 0;
        }
    }

    public Date fechaactual() {
        Date fecha = new Date();
        return fecha;
    }

    public void anexar() {
        listadetalleslibrodiario.clear();
        id = 0;
        visualizar = 0;
        Detallecompra detalle1 = detallecompraFiltrados.get(0);
        Articulo arti = detalle1.getCodigo();
        Detallelibrodiario detallelib = new Detallelibrodiario();
        if (arti.getIdplandecuenta() != null) {
            detallelib.setIdplandecuenta(arti.getIdplandecuenta());
            detallelibroventa.setIdplandecuenta(arti.getIdplandecuenta());
        } else {
            int codcta = 6121100;
            Plandecuenta cuentaprovisional = plandecuentaEJB.buscarcuenta(codcta);
            detallelib.setIdplandecuenta(cuentaprovisional);
            detallelibroventa.setIdplandecuenta(detallelib.getIdplandecuenta());
        }
        detallelib.setDebe(compra.getSubtotal());
        detallelib.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelib);
        id++;

        if (compra.getIva() > 0.0) {
            Detallelibrodiario detallelibr = new Detallelibrodiario();

            int codcta = 12425;
            Plandecuenta cuentacredfiscal = plandecuentaEJB.buscarcuenta(codcta);
            detallelibr.setIdplandecuenta(cuentacredfiscal);
            detallelibr.setDebe(compra.getIva());
            detallelibr.setIddetallelibrodiario(id);
            this.listadetalleslibrodiario.add(detallelibr);
            id++;
        }

        Detallelibrodiario detallelibro = new Detallelibrodiario();
        int codctahaber = 0;
        if (arti.getIdgrupo().getIdgrupo()<3){
            codctahaber = 21115;
        }else if (arti.getIdgrupo().getIdgrupo()==3){        
            codctahaber = 211110;    
        }
        Plandecuenta cuentaporpagar = plandecuentaEJB.buscarcuenta(codctahaber);
        detallelibro.setIdplandecuenta(cuentaporpagar);
        detallelibro.setHaber(compra.getTotal());
        detallelibro.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelibro);
        id++;
    }

    public void anexarpagocompra() {
        listadetalleslibrodiario.clear();
        id = 0;
        visualizar = 0;

        Detallelibrodiario detallelibro = new Detallelibrodiario();
        empresa=empresaEJB.devolverEmpresabase();
        int codctadebe = empresa.getCtaxpagarproved();
        Plandecuenta cuentaporpagar = plandecuentaEJB.buscarcuenta(codctadebe);
        detallelibro.setIdplandecuenta(cuentaporpagar);
        double saldop = pagocompra.getSaldopendiente();
        double montor = pagocompra.getMontoretenido();
        double totalpg = pagocompra.getTotalpago();
        double totalcp = compra.getTotal();
        if (saldop > 0) {
            if (montor > 0) {
                detallelibro.setDebe(pagocompra.getTotalpago() + retiva + retislr);
            } else {
                detallelibro.setDebe(pagocompra.getTotalpago());
            }
        } else {
            if (totalpg < totalcp) {
                detallelibro.setDebe(pagocompra.getTotalpago() + retiva + retislr);
            } else {
                detallelibro.setDebe(compra.getTotal());
            }
        }
        detallelibro.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelibro);
        id++;

        if (pagocompra.getMontoretenido() > 0) {

            if (pagocompra.getMontoretenido() == retiva) {
                Detallelibrodiario detallelib = new Detallelibrodiario();
                int codcta = 21235;
                Plandecuenta cuentaretencioniva = plandecuentaEJB.buscarcuenta(codcta);
                detallelib.setIdplandecuenta(cuentaretencioniva);
                detallelib.setHaber(pagocompra.getMontoretenido());
                detallelib.setIddetallelibrodiario(id);
                this.listadetalleslibrodiario.add(detallelib);
                id++;
            } else if (pagocompra.getMontoretenido() > retiva) {
                Detallelibrodiario detallelib = new Detallelibrodiario();
                int codcta = 21235;
                Plandecuenta cuentaretencioniva = plandecuentaEJB.buscarcuenta(codcta);
                detallelib.setIdplandecuenta(cuentaretencioniva);
                detallelib.setHaber(retiva);
                detallelib.setIddetallelibrodiario(id);
                this.listadetalleslibrodiario.add(detallelib);
                id++;
                
                Detallelibrodiario detallelibr1 = new Detallelibrodiario();
                int codcta1 = 212310;
                Plandecuenta cuentaretencionislr = plandecuentaEJB.buscarcuenta(codcta1);
                detallelibr1.setIdplandecuenta(cuentaretencionislr);
                detallelibr1.setHaber(retislr);
                detallelibr1.setIddetallelibrodiario(id);
                this.listadetalleslibrodiario.add(detallelibr1);
                id++;
            }
            
        }

        Detallelibrodiario detallelibr = new Detallelibrodiario();
        detallelibr.setIdplandecuenta(pagocompra.getIdcuentabancaria().getIdplandecuenta());
        detallelibr.setHaber(pagocompra.getTotalpago());
        detallelibr.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelibr);
        id++;

    }

    public double totaldebe() {
        double montotdebe = 0;

        for (Detallelibrodiario detall : listadetalleslibrodiario) {
            if (detall.getDebe() != null) {
                montotdebe += detall.getDebe();
            }
        }
        totaldebegeneral = montotdebe;
        return montotdebe;
    }

    public double totalhaber() {
        double montothaber = 0;

        for (Detallelibrodiario detall : listadetalleslibrodiario) {
            if (detall.getHaber() != null) {
                montothaber += detall.getHaber();
            }
        }
        totalhabergeneral = montothaber;
        return montothaber;
    }

    public void eliminar(Detallelibrodiario detalleld) {
        listadetalleslibrodiario.remove(detalleld.hashCode());
        int indice = 0;
        for (Detallelibrodiario detallazo : listadetalleslibrodiario) {
            detallazo.setIddetallelibrodiario(indice);
            indice++;
            id = indice;
        }
        if (detalleld.hashCode() == 0) {
            id = 0;
        }
    }

    public void asignarDetallelibrodiario(Detallelibrodiario detallelbr) {
        detalleamodificar = detallelbr;
        cuentaseleccionada = detallelbr.getIdplandecuenta().getIdplandecuenta();
        indicearreglo = detallelbr.hashCode();
    }

    public void modificar() {
        detalleamodificar.setIdplandecuenta(plandecuentaEJB.buscarcuenta(cuentaseleccionada));
        listadetalleslibrodiario.set(indicearreglo, detalleamodificar);

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Cuenta fue modificada"));
    }

    public void anexarregistro() {
        Detallelibrodiario detalleanexo = new Detallelibrodiario();
        detalleaanexar.setIdplandecuenta(plandecuentaEJB.buscarcuenta(cuentaseleccionada));
        int indic = listadetalleslibrodiario.size();
        detalleaanexar.setIddetallelibrodiario(indic);
        detalleanexo.setIddetallelibrodiario(indic);
        detalleanexo.setIdplandecuenta(detalleaanexar.getIdplandecuenta());
        detalleanexo.setDebe(detalleaanexar.getDebe());
        detalleanexo.setHaber(detalleaanexar.getHaber());
        this.listadetalleslibrodiario.add(detalleanexo);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Cuenta fue modificada"));
    }

}
