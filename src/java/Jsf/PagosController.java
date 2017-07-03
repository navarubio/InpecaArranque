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
import Jpa.DetalleretencionislrefFacade;
import Jpa.DetalleretencionislrefFacadeLocal;
import Jpa.DetalleretencionivaefFacade;
import Jpa.DetalleretencionivaefFacadeLocal;
import Jpa.EmpresaFacadeLocal;
import Jpa.EstatuscontableFacadeLocal;
import Jpa.EstatusfacturaFacadeLocal;
import Jpa.MaestromovimientoFacadeLocal;
import Jpa.MovimientobancarioFacadeLocal;
import Jpa.PagocompraFacadeLocal;
import Jpa.RequerimientoFacadeLocal;
import Jpa.TipoconjuntoFacadeLocal;
import Jpa.TipopagoFacadeLocal;
import Jpa.TiporetencionislrFacadeLocal;
import Modelo.Autorizacion;
import Modelo.Auxiliarrequerimiento;
import Modelo.Banco;
import Modelo.Compra;
import Modelo.Comprobanteivaef;
import Modelo.Cuentabancaria;
import Modelo.Departamento;
import Modelo.Detallecompra;
import Modelo.Detalleretencionislref;
import Modelo.Detalleretencionivaef;
import Modelo.Empresa;
import Modelo.Estatuscontable;
import Modelo.Estatusfactura;
import Modelo.Maestromovimiento;
import Modelo.Movimientobancario;
import Modelo.Pagocompra;
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

public class PagosController implements Serializable {

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

    private Auxiliarrequerimiento auxiliarrequerimiento;
    private Compra compra;
    private Autorizacion autoriza;
    private Detallecompra detallecompras;
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
    private Empresa empresa;
    private int visualizar = 0;
    private int tipocompra = 1;
    private double montoUT = 0;
    private double montopisoretiva = 0;
    private double montopisoretislr = 0;
    private int idAuxiliar = 0;
    private int idCompra = 0;
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
        auxiliarrequerimientos = auxiliarrequerimientoEJB.findAll();
        cuentasbancarias = cuentabancariaEJB.findAll();
        tipopagos = tipopagoEJB.findAll();
        bancos = bancoEJB.findAll();
        pagosefectuados = pagocompraEJB.findAll();
        pago = new Pagocompra();
        //articulos = articuloEJB.findAll();
        //comprasporautorizar=compraEJB.buscarcomprasporAutorizar();

//        this.auxiliarrequerimiento=requerimientosController.getAuxrequer();
    }

    public void asignar(Compra compr) {
        this.tipocompra = 1;
        this.visualizar = 0;
        this.totalretenido=0;
        this.ivaretenido=0;
        this.islrretenido=0;
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
                }else if (tipocompra == 1) {
                    if (montoiva > 0) {
                        visualizar = 1;                    
                    }else {
                        visualizar=5;
                    }
                }else if ((tipocompra == 2) && (personaj == 2) && (residencia == 1)) {
                    if (montocompra >= montopisoretislr) {
                        if (montoiva > 0) {
                            visualizar=2;
                        }else{
                            visualizar=3;
                        }
                    }else {
                        if (montoiva > 0) {
                            visualizar=1;
                        }else{
                            visualizar=5;
                        }
                    }
                }else{
                    if (montoiva > 0) {
                        visualizar=2;
                    }else{
                        visualizar=3;
                    }
                }
            }else {
                if (tipocompra == 2) {
                    if ((personaj == 2) && (residencia == 1)) {
                        visualizar=5;
                    }else{
                        visualizar = 3;                    
                    }
                } else if (tipocompra == 3) {
                    visualizar = 5;
                } else if (tipocompra ==1){
                    visualizar=5;
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
                }else{
                    visualizar=4;
                }
            }else if (tipocompra == 3) {
                visualizar = 5;
            } else if (tipocompra ==1){
                visualizar=5;
            }
        }
        calcularMontoapagar();
        if (compra.getTotal() > (compra.getMontopendiente())) {
            visualizar = 6;
        }
    }

    public void asignarCompra(Compra compr) {
        this.compra = compr;
        this.idCompra = compr.getIdcompra();
        this.pagoespecifico = pagocompraEJB.buscarpago(compr);
        this.pagocompra = pagocompraEJB.buscarpagototal(compr);
        this.auxiliarrequerimiento = compr.getIdauxiliarrequerimiento();
//        this.auxiliar = aux;
        detallecompraFiltrados = detallecompraAuxiliar();
//        this.compra.setIdauxiliarrequerimiento(auxiliar);
    }

    public List<Detallecompra> detallecompraAuxiliar() {
        List<Detallecompra> listado = null;
        listado = detallecompraEJB.buscardetallecompra(compra);
        return listado;
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
        if (visualizar == 7 || visualizar == 6 || visualizar == 5) {
            try {
                /**
                 * compra.setRifproveedor(provee);
                 * compra.setSubtotal(auxiliar.getSubtotal());
                 * compra.setIva(auxiliar.getMontoiva());
                 * compra.setTotal(auxiliar.getMontototal()); Usuario us =
                 * (Usuario)
                 * FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
                 * compra.setIdusuario(us);*
                 */
                //estatuscontab = estatuscontableEJB.estatusContablePorRegistrar();

                if (formapago == 1) {
                    double saldo = 0;
                    if (montoapagar == compra.getTotal()) {
                        pagocompra.setTotalpago(montoapagar);
                        compra.setMontopendiente(saldo);
                        pagocompra.setMontoretenido(saldo);
                        int tipo = 3;
                        statusfactu = estatusfacturaEJB.cambiarestatusFactura(tipo);
                    } else {
                        pagocompra.setTotalpago(montoapagar);
                        compra.setMontopendiente(saldo);
                        pagocompra.setMontoretenido(compra.getTotal() - montoapagar);
                        int tipo = 3;
                        statusfactu = estatusfacturaEJB.cambiarestatusFactura(tipo);
                    }
                } else {
                    int tipo = 0;
                    double saldop = 0;
                    if (visualizar == 6 || visualizar == 5) {
                        saldop = compra.getMontopendiente() - pagocompra.getTotalpago();
                    } else if (visualizar == 7) {
                        saldop = ((compra.getMontopendiente() - pagocompra.getTotalpago()) - (compra.getMontopendiente() - montoapagar));
                    }
                    if (saldop <= 0) {
                        tipo = 3;
                    } else {
                        tipo = 4; 
                    }
                    compra.setMontopendiente(saldop);
                    statusfactu = estatusfacturaEJB.cambiarestatusFactura(tipo);
                }
                pagocompra.setSaldopendiente(compra.getMontopendiente());
                compra.setIdestatusfactura(statusfactu);
                compraEJB.edit(compra);
                pagocompra.setIdcompra(compra);
                pagocompra.setIdbanco(banco);

                if (visualizar == 6) {
                    pagocompra.setMontoretenido(0.0);
                } else if (visualizar == 7) {
                    pagocompra.setMontoretenido((compra.getTotal() - montoapagar));
                } else if (visualizar == 5) {
                    pagocompra.setMontoretenido(0.0);
                }
                cuentabanco = pagocompra.getIdcuentabancaria();
//              pagocompra.setTotalpago(compra.getTotal());
//              pagocompra.setSaldopendiente(compra.getMontopendiente());
                pagocompraEJB.create(pagocompra);
                
                int tipoconj = 2;
                tipoconjunto = tipoconjuntoEJB.cambiartipoConjunto(tipoconj);
                pagocompra.setIdpagocompra(pagocompraEJB.ultimopago());
                maestromovi.setIdpagocompra(pagocompra);
                maestromovi.setFechamovimiento(pagocompra.getFechapago());
                maestromovi.setIdtipoconjunto(tipoconjunto);
                maestromovi.setIdestatuscontable(estatuscontableEJB.estatusContablePorRegistrar());
                maestromovimientoEJB.create(maestromovi);

                if (visualizar == 7) {
                    pagocompra.setIdpagocompra(pagocompraEJB.ultimopago());
                    detalleretencionivaef.setIdpagocompra(pagocompra);
                    detalleretencionivaefEJB.edit(detalleretencionivaef);
                    visualizar = 0;
                }

                double saldoactualbanco = 0;
                double saldoanteriorbanco = 0;
                saldoanteriorbanco = pagocompra.getIdcuentabancaria().getSaldo();
                saldoactualbanco = (pagocompra.getIdcuentabancaria().getSaldo() - pagocompra.getTotalpago());
                cuentabanco.setSaldo(saldoactualbanco);
                cuentabancariaEJB.edit(cuentabanco);
                
                movimientobancario.setFecha(pagocompra.getFechapago());
                movimientobancario.setIdcuentabancaria(cuentabanco);
                movimientobancario.setSaldoanterior(saldoanteriorbanco);
                movimientobancario.setDebito(pagocompra.getTotalpago());
                movimientobancario.setSaldoactual(saldoactualbanco);
                movimientobancario.setIdpagocompra(pagocompra);
                movimientoBancarioEJB.create(movimientobancario);
                
                String subject;
                String fechapag = formateador.format(pagocompra.getFechapago());
                correo = "COMPRA NRO: " + compra.getIdcompra()
                        + "  FECHA: " + fechapag
                        + "  PROVEEDOR: " + compra.getRifproveedor().getRazonsocial()
                        + "  RIF: " + compra.getRifproveedor().getRifproveedor()
                        + "  TIPO PAGO: " + pagocompra.getIdtipopago().getTipopago()
                        + "  BANCO: " + pagocompra.getIdcuentabancaria().getIdbanco().getNombrebanco()
                        + "  TOTAL: " + formatearnumero.format(pagocompra.getTotalpago())
                        + "  OBSERVACIONES: " + pagocompra.getObservacionespago();

                subject = "Emisión de Pago N° " + pagocompra.getIdpagocompra();
                enviomail = new envioCorreo(correo, subject);
                enviomail.start();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Pago fue Almacenado"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error al Grabar Pago"));
            } finally {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Debe efectuar la retención sobre el pago", "Aviso"));
        }
    }

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
            totalretenido=ivaretenido+islrretenido;
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
}
