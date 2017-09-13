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
import Jpa.DetallelibrodiarioFacadeLocal;
import Jpa.DetalleretencionislrspFacadeLocal;
import Jpa.DetalleretencionivaefFacadeLocal;
import Jpa.DetalleretencionivaspFacadeLocal;
import Jpa.EmpresaFacadeLocal;
import Jpa.EstatuscontableFacadeLocal;
import Jpa.EstatusfacturaventaFacadeLocal;
import Jpa.FacturaFacadeLocal;
import Jpa.LibrodiarioFacadeLocal;
import Jpa.LibromayorFacadeLocal;
import Jpa.MaestromovimientoFacadeLocal;
import Jpa.MovimientobancarioFacadeLocal;
import Jpa.PlandecuentaFacadeLocal;
import Jpa.TipoconjuntoFacadeLocal;
import Jpa.TipopagoFacadeLocal;
import Jpa.TiporetencionislrFacadeLocal;
import Modelo.Articulo;
import Modelo.Banco;
import Modelo.Cobroventa;
import Modelo.Cuentabancaria;
import Modelo.Detallefactura;
import Modelo.Detallelibrodiario;
import Modelo.Detalleretencionislrsp;
import Modelo.Detalleretencionivasp;
import Modelo.Empresa;
import Modelo.Estatuscontable;
import Modelo.Estatusfacturaventa;
import Modelo.Factura;
import Modelo.Librodiario;
import Modelo.Libromayor;
import Modelo.Maestromovimiento;
import Modelo.Movimientobancario;
import Modelo.Pagocompra;
import Modelo.Plandecuenta;
import Modelo.Tipoconjunto;
import Modelo.Tipopago;
import Modelo.Tiporetencionislr;
import java.sql.SQLException;
import java.sql.SQLException;
import java.io.Serializable;
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
import javax.faces.context.FacesContext;import javax.servlet.ServletContext;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author sofimar
 */
@ManagedBean(name = "asientosventasController")
@SessionScoped

public class AsientosventasController implements Serializable {

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
    private DetalleretencionislrspFacadeLocal detalleretencionislrspEJB;
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
    @EJB
    private EmpresaFacadeLocal empresaEJB;

    private Factura factura;
    private int numeroFact = 0;
    private int visualizar = 0;
    private Cobroventa cobro = new Cobroventa();
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
    
    private int vercasilla = 0;
    private Maestromovimiento master;
    private double retiva =0;
    private double retislr=0;
    private String mensaje;
    private List<Detallelibrodiario> listadetalleslibrodiario = new ArrayList();
    private int id = 0;
    private double totaldebegeneral = 0;
    private double totalhabergeneral = 0;
    private Detallelibrodiario detalleamodificar;
    private Detallelibrodiario detalleaanexar= new Detallelibrodiario();
    private int cuentaseleccionada;
    private int indicearreglo = 0;
    private Librodiario codlibrodiario;

    
    
    private Date fechaactual = new Date();
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
    @Inject
    private Detallelibrodiario detallelibroventa;
    @Inject
    private Librodiario librodiario;
    @Inject
    private Empresa empresa;

    public double getTotaldebegeneral() {
        return totaldebegeneral;
    }

    public void setTotaldebegeneral(double totaldebegeneral) {
        this.totaldebegeneral = totaldebegeneral;
    }

    public Detallelibrodiario getDetalleaanexar() {
        return detalleaanexar;
    }

    public void setDetalleaanexar(Detallelibrodiario detalleaanexar) {
        this.detalleaanexar = detalleaanexar;
    }

    public double getTotalhabergeneral() {
        return totalhabergeneral;
    }

    public void setTotalhabergeneral(double totalhabergeneral) {
        this.totalhabergeneral = totalhabergeneral;
    }

    public Librodiario getLibrodiario() {
        return librodiario;
    }
    public int getCuentaseleccionada() {
        return cuentaseleccionada;
    }

    public void setCuentaseleccionada(int cuentaseleccionada) {
        this.cuentaseleccionada = cuentaseleccionada;
    }
    public Detallelibrodiario getDetalleamodificar() {
        return detalleamodificar;
    }

    public void setDetalleamodificar(Detallelibrodiario detalleamodificar) {
        this.detalleamodificar = detalleamodificar;
    }
    
    public void setLibrodiario(Librodiario librodiario) {
        this.librodiario = librodiario;
    }

    public Detallelibrodiario getDetallelibroventa() {
        return detallelibroventa;
    }

    public void setDetallelibroventa(Detallelibrodiario detallelibroventa) {
        this.detallelibroventa = detallelibroventa;
    }

    public List<Detallelibrodiario> getListadetalleslibrodiario() {
        return listadetalleslibrodiario;
    }

    public void setListadetalleslibrodiario(List<Detallelibrodiario> listadetalleslibrodiario) {
        this.listadetalleslibrodiario = listadetalleslibrodiario;
    }

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

    public int getVercasilla() {
        return vercasilla;
    }

    public void setVercasilla(int vercasilla) {
        this.vercasilla = vercasilla;
    }

    public String getMensaje() {
        return mensaje;
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

    public void setRetislr(double retislr) {
        this.retislr = retislr;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
    

    @PostConstruct
    public void init() {
        detallesfactura = detallefacturaEJB.findAll();
        cuentasbancarias = cuentabancariaEJB.findAll();
        tipopagos = tipopagoEJB.findAll();
        cobrosefectuados = cobroventaEJB.findAll();
        bancos = bancoEJB.findAll();
//        cobro = new Cobroventa();
        cobro.setFechacobro(fechaactual);
        visualizar = 0;
        vercasilla=0;
        listadetalleslibrodiario.clear();
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

    public void asignarfactura (Factura factu, Maestromovimiento maestro){
        this.factura = factu;
        this.numeroFact = factura.getNumerofact();
        detallesfacturafiltrados = detallesenfacturaEJB.buscardetallefactura(factura);
        this.master = maestro;
        this.vercasilla = 1;
        this.retiva=0;
        this.retislr=0;
        listadetalleslibrodiario = detallesasiento();
        librodiario.setFecha(factura.getFecha());
        Detallefactura detal = detallesfacturafiltrados.get(0);
        Articulo artic = detal.getCodigo();
        librodiario.setDescripcionasiento("P/R EMISION DE FACTURA N° "+factura.getNumerofact()+" CORRESPONDIENTE A "+artic.getDescripcion());
    }
    
    public void asignarpagofactura (Cobroventa cobro, Maestromovimiento maestro){
        this.cobro=cobro; 
        this.factura = cobro.getNumerofact();
        detallesfacturafiltrados = detallesenfacturaEJB.buscardetallefactura(factura);
        this.master = maestro;
        this.vercasilla = 2;
        this.retiva=0;
        this.retislr=0;
        librodiario.setFecha(factura.getFecha());
        
        if (cobro.getMontoretenido()>0){
            this.retiva=detalleretencionivaspEJB.retencionivaencobro(cobro.getNumerofact().getNumerofact());
            this.retislr=detalleretencionislrspEJB.retencionislrencobro(cobro.getNumerofact().getNumerofact());
        }
        listadetalleslibrodiario = detallesasientocobro();
        librodiario.setDescripcionasiento("P/R COBRO N° "+  cobro.getIdcobroventa()+" CORRESPONDIENTE A LA FACTURA N° "+factura.getNumerofact());

    }
    
    public List<Detallelibrodiario> detallesasiento() {
        List<Detallelibrodiario> detallesasiento = null;
        anexar();
        detallesasiento = listadetalleslibrodiario;
        return detallesasiento;
    }
    
    public List<Detallelibrodiario> detallesasientocobro() {
        List<Detallelibrodiario> detallesasiento = null;
        anexarcobro();
        detallesasiento = listadetalleslibrodiario;
        return detallesasiento;
    }

    public void anexar() {
        listadetalleslibrodiario.clear();
        id = 0;
        visualizar = 0;
        empresa=empresaEJB.devolverEmpresabase();

        Detallelibrodiario detallelibro = new Detallelibrodiario();

        int codctadebe = empresa.getCtasxcobrar();
        Plandecuenta cuentaporcobrar = plandecuentaEJB.buscarcuenta(codctadebe);
        detallelibro.setIdplandecuenta(cuentaporcobrar);
        detallelibro.setDebe(factura.getTotalgeneral());
        detallelibro.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelibro);
        id++;
        
        Detallelibrodiario detallelibr = new Detallelibrodiario();
        
        int codcta = empresa.getDebfiscal();
        Plandecuenta cuentadebfiscal = plandecuentaEJB.buscarcuenta(codcta);
        detallelibr.setIdplandecuenta(cuentadebfiscal);
        detallelibr.setHaber(factura.getIvafact());
        detallelibr.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelibr);
        id++;      
        
        Detallefactura detalle1 = detallesfacturafiltrados.get(0);
        
        Articulo arti = detalle1.getCodigo();
        Detallelibrodiario detallelib = new Detallelibrodiario();
        if (arti.getIdplandecuenta() != null) {
            detallelib.setIdplandecuenta(arti.getIdplandecuenta());
            detallelibroventa.setIdplandecuenta(arti.getIdplandecuenta());
        } else {
            codcta = 41115;
            Plandecuenta cuentaprovicional = plandecuentaEJB.buscarcuenta(codcta);
            detallelib.setIdplandecuenta(cuentaprovicional);
            detallelibroventa.setIdplandecuenta(detallelib.getIdplandecuenta());
        }
        detallelib.setHaber(factura.getBimponiblefact());
        detallelib.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelib);
        id++;

    }
    
        public void anexarcobro() {
        listadetalleslibrodiario.clear();
        id = 0;
        visualizar = 0;
        empresa=empresaEJB.devolverEmpresabase();

        Detallelibrodiario detallelibr = new Detallelibrodiario();
        detallelibr.setIdplandecuenta(cobro.getIdcuentabancaria().getIdplandecuenta());
        detallelibr.setDebe(cobro.getMontocobrado());
        detallelibr.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelibr);
        id++;
        
        if (cobro.getMontoretenido() > 0) {
            
            if (cobro.getMontoretenido()==retiva){
                Detallelibrodiario detallelib = new Detallelibrodiario();
                int codcta = empresa.getRetivacliente();
                Plandecuenta cuentaretencioniva = plandecuentaEJB.buscarcuenta(codcta);
                detallelib.setIdplandecuenta(cuentaretencioniva);
                detallelib.setDebe(cobro.getMontoretenido());
                detallelib.setIddetallelibrodiario(id);
                this.listadetalleslibrodiario.add(detallelib);
                id++;                
            }else if (cobro.getMontoretenido()>retiva){
                Detallelibrodiario detallelib = new Detallelibrodiario();
                int codcta = empresa.getRetivacliente();
                Plandecuenta cuentaretencioniva = plandecuentaEJB.buscarcuenta(codcta);
                detallelib.setIdplandecuenta(cuentaretencioniva);
                detallelib.setDebe(retiva);
                detallelib.setIddetallelibrodiario(id);
                this.listadetalleslibrodiario.add(detallelib);
                id++;

                Detallelibrodiario detallelibr1 = new Detallelibrodiario();
                int codcta1 = empresa.getRetislrcliente();
                Plandecuenta cuentaretencionislr = plandecuentaEJB.buscarcuenta(codcta1);
                detallelibr1.setIdplandecuenta(cuentaretencionislr);
                detallelibr1.setDebe(retislr);
                detallelibr1.setIddetallelibrodiario(id);
                this.listadetalleslibrodiario.add(detallelibr1);
                id++;
            }
        }
        
        Detallelibrodiario detallelibro = new Detallelibrodiario();

        int codctahaber = empresa.getCtasxcobrar();
        Plandecuenta cuentaporcobrar = plandecuentaEJB.buscarcuenta(codctahaber);
        detallelibro.setIdplandecuenta(cuentaporcobrar);
        if (cobro.getMontopendiente()>0){
            if (cobro.getMontoretenido()>0){
                detallelibro.setHaber(cobro.getMontocobrado()+retiva+retislr);
            }else{  
                detallelibro.setHaber(cobro.getMontocobrado());
            }
        }else {
            detallelibro.setHaber(cobro.getMontocobrado()+retiva+retislr);
        }
        detallelibro.setIddetallelibrodiario(id);
        this.listadetalleslibrodiario.add(detallelibro);
        id++;
    }


    public List<Detallefactura> detallefacturaAuxiliar() {
        List<Detallefactura> listado = null;
        listado = detallesenfacturaEJB.buscardetallefactura(factura);
        return listado;
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
/**    public void registrar() {
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
    }**/
    
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
