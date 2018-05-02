/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jsf;

import Jpa.BancoFacadeLocal;
import Jpa.CobroventaFacadeLocal;
import Jpa.CuentabancariaFacadeLocal;
import Jpa.DetallefacturaFacadeLocal;
import Jpa.DetalleretencionislrspFacadeLocal;
import Jpa.DetalleretencionivaefFacadeLocal;
import Jpa.DetalleretencionivaspFacadeLocal;
import Jpa.EstatuscontableFacadeLocal;
import Jpa.EstatusfacturaventaFacadeLocal;
import Jpa.FacturaFacadeLocal;
import Jpa.MaestromovimientoFacadeLocal;
import Jpa.MovimientobancarioFacadeLocal;
import Jpa.TipoconjuntoFacadeLocal;
import Jpa.TipopagoFacadeLocal;
import Jpa.TiporetencionislrFacadeLocal;
import Modelo.Banco;
import Modelo.Cobroventa;
import Modelo.Cuentabancaria;
import Modelo.Detallefactura;
import Modelo.Detalleretencionislrsp;
import Modelo.Detalleretencionivasp;
import Modelo.Estatuscontable;
import Modelo.Estatusfacturaventa;
import Modelo.Factura;
import Modelo.Maestromovimiento;
import Modelo.Movimientobancario;
import Modelo.Pagocompra;
import Modelo.Tipoconjunto;
import Modelo.Tipopago;
import Modelo.Tiporetencionislr;
import java.sql.SQLException;
import java.sql.SQLException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;import javax.servlet.ServletContext;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author sofimar
 */
@ManagedBean(name = "cobroventasController")
@SessionScoped

public class CobroventasController implements Serializable {

    @EJB
    private DetallefacturaFacadeLocal detallefacturaEJB;
    @EJB
    private DetallefacturaFacadeLocal detallesenfacturaEJB;
    @EJB
    private CuentabancariaFacadeLocal cuentabancariaEJB;
    @EJB
    private TipopagoFacadeLocal tipopagoEJB;
    @EJB
    private CobroventaFacadeLocal cobroventaEJB;
    @EJB
    private EstatusfacturaventaFacadeLocal estatusfacturaventaEJB;
    @EJB
    private FacturaFacadeLocal facturaEJB;
    @EJB
    private BancoFacadeLocal bancoEJB;
    @EJB
    private EstatuscontableFacadeLocal estatuscontableEJB;
    @EJB
    private TipoconjuntoFacadeLocal tipoconjuntoEJB;
    @EJB
    private MaestromovimientoFacadeLocal maestromovimientoEJB;
    @EJB
    private TiporetencionislrFacadeLocal tiporetencionislrEJB;
    @EJB
    private DetalleretencionivaspFacadeLocal detalleretencionivaspEJB;
    @EJB
    private DetalleretencionislrspFacadeLocal detalleretencionislrefEJB;
    @EJB
    private MovimientobancarioFacadeLocal movimientoBancarioEJB;

    private Factura factura;
    private int numeroFact = 0;
    private int visualizar = 0;
    static Cobroventa cobro;
    private Cobroventa cobroventa = new Cobroventa();
    static Cuentabancaria cuentabancaria;
    private Detallefactura detallefact;
    private Estatusfacturaventa statusfactu = null;
    private Estatuscontable estatuscontab = null;
    private int formacobro = 0;
    static Banco banco;
    private int idbanco;
    private List<Detallefactura> detallesfactura;
    private List<Detallefactura> detallesfacturafiltrados;
    private List<Cuentabancaria> cuentasbancarias;
    private double saldocuenta;
    private List<Tipopago> tipopagos;
    private List<Cobroventa> cobrosefectuados;
    private List<Cuentabancaria> lstCuentasSelecc;
    private List<Banco> bancos;
    private int edad = 0;
    private String mensaje;
    private java.util.GregorianCalendar fecha1= new GregorianCalendar();
    private Calendar cal = Calendar.getInstance();
    private Date fechaactual = cal.getTime();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");
    private String correo;
    private envioCorreo enviomail;
    private Tipoconjunto tipoconjunto = null;
    private int tipofactura = 1;
    private double montoUT = 0;
    private double montopisoretiva = 0;
    private double montopisoretislr = 0;
    private double ivaretenido;
    private double islrretenido;
    private double montoacobrar;
    private double totalretenido;
    private double montocobrado;
    private int tipocompra = 1;
    private List<Tiporetencionislr> tiporetencionesfiltradasPD = null;

    @Inject
    private Maestromovimiento maestromovi;
    @Inject
    private Movimientobancario movimientobancario;
    @Inject
    private Detallefactura detallefactu;
    @Inject
    private Detalleretencionivasp detalleretencionivasp;
    @Inject
    private Detalleretencionislrsp detalleretencionislrsp;

    public double getSaldocuenta() {
        return saldocuenta;
    }

    public void setSaldocuenta(double saldocuenta) {
        this.saldocuenta = saldocuenta;
    }

    public Factura getFactura() {
        return factura;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public int getVisualizar() {
        return visualizar;
    }

    public void setVisualizar(int visualizar) {
        this.visualizar = visualizar;
    }

    public List<Tipopago> getTipopagos() {
        return tipopagos;
    }

    public void setTipopagos(List<Tipopago> tipopagos) {
        this.tipopagos = tipopagos;
    }

    public List<Tiporetencionislr> getTiporetencionesfiltradasPD() {
        return tiporetencionesfiltradasPD;
    }

    public void setTiporetencionesfiltradasPD(List<Tiporetencionislr> tiporetencionesfiltradasPD) {
        this.tiporetencionesfiltradasPD = tiporetencionesfiltradasPD;
    }

    public List<Cobroventa> getCobrosefectuados() {
        return cobrosefectuados;
    }

    public void setCobrosefectuados(List<Cobroventa> cobrosefectuados) {
        this.cobrosefectuados = cobrosefectuados;
    }

    public List<Detallefactura> getDetallesfacturafiltrados() {
        return detallesfacturafiltrados;
    }

    public void setDetallesfacturafiltrados(List<Detallefactura> detallesfacturafiltrados) {
        this.detallesfacturafiltrados = detallesfacturafiltrados;
    }

    public Cobroventa getCobro() {
        return cobro;
    }

    public void setCobro(Cobroventa cobro) {
        this.cobro = cobro;
    }

    public void asignarDetalleFactura(Detallefactura detallefactura) {
        this.detallefactu = detallefactura;

    }

    public Detallefactura getDetallefactu() {
        return detallefactu;
    }

    public void setDetallefactu(Detallefactura detallefactu) {
        this.detallefactu = detallefactu;
    }

    public List<Cuentabancaria> getLstCuentasSelecc() {
        return lstCuentasSelecc;
    }

    public void setLstCuentasSelecc(List<Cuentabancaria> lstCuentasSelecc) {
        this.lstCuentasSelecc = lstCuentasSelecc;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public int getIdbanco() {
        return idbanco;
    }

    public void setIdbanco(int idbanco) {
        this.idbanco = idbanco;
    }

    public List<Banco> getBancos() {
        return bancos;
    }

    public void setBancos(List<Banco> bancos) {
        this.bancos = bancos;
    }

    public int getFormacobro() {
        return formacobro;
    }

    public void setFormacobro(int formacobro) {
        this.formacobro = formacobro;
    }

    public int getTipocompra() {
        return tipocompra;
    }

    public void setTipocompra(int tipocompra) {
        this.tipocompra = tipocompra;
    }

    public static Cuentabancaria getCuentabancaria() {
        return cuentabancaria;
    }

    public void setCuentabancaria(Cuentabancaria cuentabancaria) {
        this.cuentabancaria = cuentabancaria;
    }

    public Detalleretencionivasp getDetalleretencionivasp() {
        return detalleretencionivasp;
    }

    public void setDetalleretencionivasp(Detalleretencionivasp detalleretencionivasp) {
        this.detalleretencionivasp = detalleretencionivasp;
    }

    public Detalleretencionislrsp getDetalleretencionislrsp() {
        return detalleretencionislrsp;
    }

    public void setDetalleretencionislrsp(Detalleretencionislrsp detalleretencionislrsp) {
        this.detalleretencionislrsp = detalleretencionislrsp;
    }

    public double getMontocobrado() {
        return montocobrado;
    }

    public void setMontocobrado(double montocobrado) {
        this.montocobrado = montocobrado;
    }
    

    @PostConstruct
    public void init() {
        detallesfactura = detallefacturaEJB.findAll();
        cuentasbancarias = cuentabancariaEJB.findAll();
        tipopagos = tipopagoEJB.findAll();
        cobrosefectuados = cobroventaEJB.findAll();
        bancos = bancoEJB.findAll();
        cobro = new Cobroventa();
        cobro.setFechacobro(fechaactual);
        visualizar = 0;
    }

    public List<Cuentabancaria> refrescarCuentasBancarias() {
        try {
            lstCuentasSelecc = cuentabancariaEJB.espxBanco(banco.getIdbanco());
        } catch (Exception e) {
        }
        cobro.setIdcuentabancaria(lstCuentasSelecc.get(0));
        return lstCuentasSelecc;
    }

    public void saldoactual() {
        saldocuenta = cuentabancariaEJB.saldoencuenta(lstCuentasSelecc);
    }

    public void asignar(Factura factura) {
        this.tipocompra = 1;
        visualizar = 0;
        this.factura = factura;
        this.numeroFact = factura.getNumerofact();
        detallesfacturafiltrados = detallesenfacturaEJB.buscardetallefactura(factura);
        double montoiva = factura.getIvafact();
        this.tipofactura = 1;
        this.totalretenido = 0;
        this.montocobrado =factura.getSaldopendiente();
        this.ivaretenido = 0;
        this.islrretenido = 0;
        double totalfactu = factura.getTotalgeneral();
        montopisoretiva = (20 * 300);
        montopisoretislr = 0;
        detalleretencionivasp.setTotalivaretenido(0.0);
        detalleretencionislrsp.setTotalislrretenido(0.0);
        detalleretencionislrsp.setSustraendo(0.0);
        detalleretencionislrsp.setProcentajeretencion(0.0);
        detalleretencionivasp.setIdtiporetencioniva(null);
        detalleretencionislrsp.setIdtiporetencionislr(null);
        tiporetencionesfiltradasPD = tiporetencionislrEJB.tiporetfiltradaPJD();

        int tipo1;
        for (Detallefactura tipoc : detallesfacturafiltrados) {
            tipo1 = tipoc.getCodigo().getIdgrupo().getIdgrupo();
            if (tipo1 == 1 && tipocompra == 1) {
                tipocompra = 1;
            } else if (tipo1 == 2) {
                tipocompra = 2;
            } else if (tipo1 == 3) {
                tipocompra = 3;
            }
        }

        if (factura.getRifcliente().getIdcontribuyente().getIdcontribuyente() == 2) {
            if (totalfactu >= montopisoretiva) {
                if (tipocompra == 3) {
                    visualizar = 5;
                } else if (tipocompra == 1) {
                    if (montoiva > 0) {
                        visualizar = 1;
                    } else {
                        visualizar = 5;
                    }
                } else if (tipocompra == 2) {
                    if (totalfactu >= montopisoretislr) {
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
                }
            } else {
                if (tipocompra == 2) {
                    visualizar = 3;
                } else if (tipocompra == 3) {
                    visualizar = 5;
                } else if (tipocompra == 1) {
                    visualizar = 5;
                }
            }
        } else if (factura.getRifcliente().getIdcontribuyente().getIdcontribuyente() == 1 || factura.getRifcliente().getIdcontribuyente().getIdcontribuyente() == 3) {
            if (tipocompra == 2) {
                if (totalfactu >= montopisoretislr) {
                    visualizar = 4;
                } else {
                    visualizar = 5;
                }
            } else if (tipocompra == 3) {
                visualizar = 5;
            } else if (tipocompra == 1) {
                visualizar = 5;
            }
        }
        calcularMontoacobrar();
        if (factura.getTotalgeneral() > (factura.getSaldopendiente())) {
            visualizar = 6;
        }
    }

    public List<Detallefactura> detallefacturaAuxiliar() {
        List<Detallefactura> listado = null;
        listado = detallesenfacturaEJB.buscardetallefactura(factura);
        return listado;
    }

    public void registrar() {
        if (visualizar == 7 || visualizar == 6 || visualizar == 5) {
            try {
                estatuscontab = estatuscontableEJB.estatusContablePorRegistrar();
                if (formacobro == 1) {
                    double saldo = 0;
                    cobro.setMontocobrado(montoacobrar);
                    factura.setSaldopendiente(saldo);
                    int tipo = 1;
                    statusfactu = estatusfacturaventaEJB.estatusfacturaPagada(tipo);
                } else {
                    int tipo = 0;
                    double saldop = 0;
                    if (totalretenido>0) {
                        saldop = ((factura.getSaldopendiente()-totalretenido) - montocobrado);
                    }else{
                        saldop = factura.getSaldopendiente() - montocobrado;
                    }
                    if (saldop < 1) {
                        tipo = 1;
                    } else {
                        tipo = 3;
                    }

                    factura.setSaldopendiente(saldop);
                    statusfactu = estatusfacturaventaEJB.estatusfacturaAbonada(tipo);
                }
                factura.setIdestatusfacturaventa(statusfactu);
                facturaEJB.edit(factura);
                //codCompra = compraEJB.ultimacompraInsertada();

                if (visualizar == 6) {
                    cobro.setMontoretenido(0.0);
                    cobro.setMontocobrado(montocobrado);
                }else if (visualizar == 7) {
                    cobro.setMontoretenido((detalleretencionivasp.getTotalivaretenido() + detalleretencionislrsp.getTotalislrretenido()));
                    if (formacobro == 1) {
                        cobro.setMontocobrado(montoacobrar);
                    }else if (formacobro == 2){
                        cobro.setMontocobrado(montocobrado);                        
                    }
                } else if (visualizar == 5) {
                    cobro.setMontoretenido(0.0);
                }

                cobro.setNumerofact(factura);
                cobro.setIdestatuscontable(estatuscontab);
                cuentabancaria = cobro.getIdcuentabancaria();
                cobro.setMontopendiente(factura.getSaldopendiente());
                cobro.setMontocobrado(montocobrado);
                cobroventaEJB.create(cobro);

                int tipoconj = 1;
                tipoconjunto = tipoconjuntoEJB.cambiartipoConjunto(tipoconj);
                maestromovi.setIdcobroventa(cobroventaEJB.ultimocobroInsertado());
                maestromovi.setFechamovimiento(cobro.getFechacobro());
                maestromovi.setIdtipoconjunto(tipoconjunto);
                maestromovi.setIdestatuscontable(estatuscontableEJB.estatusContablePorRegistrar());
                maestromovimientoEJB.create(maestromovi);

                double saldoactualbanco = 0;
                double saldoanteriorbanco = 0;
                saldoanteriorbanco = cobro.getIdcuentabancaria().getSaldo();
                saldoactualbanco = montocobrado + cobro.getIdcuentabancaria().getSaldo();
                cuentabancaria.setSaldo(saldoactualbanco);
                cuentabancariaEJB.edit(cuentabancaria);
                
                movimientobancario.setFecha(cobro.getFechacobro());
                movimientobancario.setIdcuentabancaria(cuentabancaria);
                movimientobancario.setSaldoanterior(saldoanteriorbanco);
                movimientobancario.setCredito(cobro.getMontocobrado());
                movimientobancario.setSaldoactual(saldoactualbanco);
                movimientoBancarioEJB.create(movimientobancario);

                if (factura.getSaldopendiente() < 1) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Su Cobro fue Almacenado"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "El Abono recibido fue Almacenado"));
                }
                String subject;
                String ultimafactura = "" + factura.getNumerofact();
                String fechafactu = formateador.format(cobro.getFechacobro());
                correo = "FACTURA NRO: " + ultimafactura
                        + "  CONTROL: " + factura.getNumerocontrol()
                        + "  FECHA COBRO: " + fechafactu
                        + "  CLIENTE: " + factura.getRifcliente().getRazonsocial()
                        + "  RIF: " + factura.getRifcliente().getRifcliente()
                        + "  FORMA PAGO: " + cobro.getIdtipopago().getTipopago()
                        + "  BANCO: " + cobro.getIdcuentabancaria().getIdbanco().getNombrebanco()
                        + "  MONTO COBRADO: " + formatearnumero.format(cobro.getMontocobrado())
                        + "  MONTO PENDIENTE: " + formatearnumero.format(cobro.getMontopendiente())
                        + "  OBSERVACIONES: " + cobro.getObservacionescobro();

                subject = "Cobro N° " + cobro.getIdcobroventa();
                enviomail = new envioCorreo(correo, subject);
                enviomail.start();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Cobro", "Aviso"));
            } finally {
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Debe efectuar la retención sobre el pago", "Aviso"));
        }
    }

    public void seleccionpagofraccionado() {
        if (mensaje.equals("total")) {
            formacobro = 1;
        } else if (mensaje.equals("parcial")) {
            formacobro = 2;
        } else {
            formacobro = 0;
        }

    }

    public void calcularMontoacobrar() {

        if (factura.getTotalgeneral() > (factura.getSaldopendiente())) {
            montoacobrar = factura.getSaldopendiente();
            cobro.setMontocobrado(montoacobrar);
        } else {
            montoacobrar = (factura.getTotalgeneral() - detalleretencionivasp.getTotalivaretenido() - detalleretencionislrsp.getTotalislrretenido());
            cobro.setMontocobrado(montoacobrar);
        }
    }

    public void calcularivaretenido() {
        double ivatotal = factura.getIvafact();
        double porcent = detalleretencionivasp.getIdtiporetencioniva().getPorcentajeiva();
        ivaretenido = (ivatotal * porcent) / 100;
        detalleretencionivasp.setTotalivaretenido(ivaretenido);
        ivatotal = 0;
        porcent = 0;
    }

    public void calcularislrretenido() {
        int personal = 1;
        int residenc = 3;
        int tiposervicio = detalleretencionislrsp.getIdtiporetencionislr().getIdsubgrupo().getIdsubgrupo();
        Tiporetencionislr tiporetencion = tiporetencionislrEJB.retencionislrFiltrada(personal, residenc, tiposervicio);
        double bimponibletotal = factura.getBimponiblefact();
        double porcentbimponible = tiporetencion.getPorcentajebimponible();
        double porcentislr = tiporetencion.getPorcentajeretencion();
        double sustraendo = tiporetencion.getSustraendo();
        islrretenido = (((((porcentbimponible * bimponibletotal) / 100) * porcentislr) / 100) - sustraendo);
        detalleretencionislrsp.setProcentajeretencion(porcentislr);
        detalleretencionislrsp.setTotalislrretenido(islrretenido);
        detalleretencionislrsp.setSustraendo(sustraendo);
    }

    public void grabarRetencion() {
        try {
            if (detalleretencionivasp.getTotalivaretenido() >= 1) {
                detalleretencionivasp.setNumerofact(factura);
                detalleretencionivasp.setBimponible(factura.getBimponiblefact());
                detalleretencionivasp.setTotalventa(factura.getTotalgeneral());
                detalleretencionivasp.setTotalivaretenido(ivaretenido);
                detalleretencionivaspEJB.create(detalleretencionivasp);
            }
            if (detallefactu.getCodigo().getIdgrupo().getIdgrupo() == 2) {
                detalleretencionislrsp.setNumerofact(factura);
                detalleretencionislrsp.setTotalventa(factura.getTotalgeneral());
                detalleretencionislrsp.setBimponible(factura.getBimponiblefact());
                detalleretencionislrefEJB.create(detalleretencionislrsp);
            }
            calcularMontoacobrar();
            totalretenido = ivaretenido + islrretenido;
            montocobrado = montoacobrar;
            visualizar = 7;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Su Retencion fue Almacenada", "Su Retencion fue Almacenada"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar Retencion", "Error al Grabar Retencion"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    public void verOrdendeCobro(Cobroventa item) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        int codigocobroventa = item.getIdcobroventa();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/ordendecobro.jasper");

        rArticulo.getOrdendeCobro(ruta, codigocobroventa);
        FacesContext.getCurrentInstance().responseComplete();
    }
}
