package Jsf;

import Jpa.ArticuloFacadeLocal;
import Jpa.CamionFacadeLocal;
import Jpa.ChoferFacadeLocal;
import Jpa.ClienteFacadeLocal;
import Jpa.DespachadorFacadeLocal;
import Jpa.DespachopicadoraFacadeLocal;
import Jpa.DetallenotacargaFacadeLocal;
import Jpa.EmpresaFacadeLocal;
import Jpa.InventariopicadoraFacadeLocal;
import Jpa.MovimientoinventariopicadoraFacadeLocal;
import Modelo.Notacarga;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.NotacargaFacade;
import Jpa.NotacargaFacadeLocal;
import Modelo.Articulo;
import Modelo.Camion;
import Modelo.Chofer;
import Modelo.Cliente;
import Modelo.Despachador;
import Modelo.Despachopicadora;
import Modelo.Detallenotacarga;
import Modelo.Empresa;
import Modelo.Inventariopicadora;
import Modelo.Movimientoinventariopicadora;
import Modelo.Produccionpicadora;
import Modelo.Requerimiento;
import Modelo.Usuario;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@Named("notacargaController")
@SessionScoped
public class NotacargaController implements Serializable {

    @EJB
    private Jpa.NotacargaFacadeLocal ejbFacade;
    @EJB
    private ArticuloFacadeLocal articuloEJB;
    @EJB
    private DetallenotacargaFacadeLocal detallenotacargaEJB;
    @EJB
    private ClienteFacadeLocal clienteEJB;
    @EJB
    private DespachopicadoraFacadeLocal despachopicadoraEJB;
    @EJB
    private DespachadorFacadeLocal despachorEJB;
    @EJB
    private CamionFacadeLocal camionEJB;
    @EJB
    private ChoferFacadeLocal choferEJB;
    @EJB
    private NotacargaFacadeLocal notacargaEJB;
    @EJB
    private MovimientoinventariopicadoraFacadeLocal movimientoinventariopicadoraEJB;
    @EJB
    private InventariopicadoraFacadeLocal inventariopicadoraEJB;
    @EJB
    private EmpresaFacadeLocal empresaEJB;

    private List<Notacarga> items = null;
    private Notacarga selected;
    private List<Articulo> articulos = null;
    private List<Detallenotacarga> listadetallenota = new ArrayList();
    private List<Detallenotacarga> detalles = null;
    private Detallenotacarga detallenota;
    private double cantidad = 0;
    private double totalcantidad = 0;
    private double pventa = 0;
    private double subtotal = 0;
    private int id = 0;
    private double totalgeneral = 0;
    private double totaliva = 0;
    private double totalsubtotal = 0;
    private Camion camion;
    private envioCorreo enviomail;
    private String correo;
    private String correodespacho;
    private double mt3 = 0;
    @Inject
    private Notacarga nota;
    @Inject
    private Articulo articulo;
    @Inject
    private Detallenotacarga detalle;
    @Inject
    private Cliente cliente;
    @Inject
    Chofer chofer;
    private FacturasController factu = new FacturasController();
    private Notacarga codnota;
    private int number;
    private List<Cliente> clientes;
    int idnota = 0;
    private Notacarga notacargadialog;
    private List<Detallenotacarga> detallesnotafiltrados;
    private List<Despachopicadora> despachosfiltrados;
    private List<Despachador> despachadores;
    private List<Camion> camiones;
    private List<Chofer> choferes;
    private Empresa empresa;
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");
    @Inject
    private Despachopicadora despacho;
    @Inject
    private Movimientoinventariopicadora moviinventariopro;
    @Inject
    private Inventariopicadora inventariopro;

    public NotacargaController() {
    }

    @PostConstruct
    public void init() {
        clientes = clienteEJB.findAll();
        selected = new Notacarga();
        articulos = articuloEJB.listadoAgregadospicadora();
        despachadores = despachorEJB.findAll();
        camiones = camionEJB.findAll();
        choferes = choferEJB.findAll();
        listadetallenota.clear();
        totalsubtotal = 0;
        totaliva = 0;
        totalgeneral = 0;
        totalcantidad = 0;
        articulo = null;
    }

    public Notacarga getSelected() {
        return selected;
    }

    public void setSelected(Notacarga selected) {
        if (selected != null) {
            this.selected = selected;
            detallesnotafiltrados = detallenotacargaEJB.detallesfiltrados(selected);
            despachosfiltrados = despachopicadoraEJB.despachosfiltrados(selected);
        }
    }

    public List<Despachopicadora> getDespachosfiltrados() {
        return despachosfiltrados;
    }

    public void setDespachosfiltrados(List<Despachopicadora> despachosfiltrados) {
        this.despachosfiltrados = despachosfiltrados;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Camion getCamion() {
        return camion;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Despachopicadora getDespacho() {
        return despacho;
    }

    public void setDespacho(Despachopicadora despacho) {
        this.despacho = despacho;
    }

    public double getPventa() {
        return pventa;
    }

    public void setPventa(double pventa) {
        this.pventa = pventa;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public Notacarga getNotacargadialog() {
        return notacargadialog;
    }

    public void setNotacargadialog(Notacarga notacargadialog) {
        this.notacargadialog = notacargadialog;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public Detallenotacarga getDetalle() {
        return detalle;
    }

    public void setDetalle(Detallenotacarga detalle) {
        this.detalle = detalle;
    }

    public List<Detallenotacarga> getListadetallenota() {
        return listadetallenota;
    }

    public void setListadetallenota(List<Detallenotacarga> listadetallenota) {
        this.listadetallenota = listadetallenota;
    }

    private NotacargaFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Articulo> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<Articulo> articulos) {
        this.articulos = articulos;
    }

    public double getTotalgeneral() {
        return totalgeneral;
    }

    public void setTotalgeneral(double totalgeneral) {
        this.totalgeneral = totalgeneral;
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

    public List<Despachador> getDespachadores() {
        return despachadores;
    }

    public void setDespachadores(List<Despachador> despachadores) {
        this.despachadores = despachadores;
    }

    public List<Camion> getCamiones() {
        return camiones;
    }

    public void setCamiones(List<Camion> camiones) {
        this.camiones = camiones;
    }

    public List<Chofer> getChoferes() {
        return choferes;
    }

    public void setChoferes(List<Chofer> choferes) {
        this.choferes = choferes;
    }

    public double getMt3() {
        return mt3;
    }

    public void setMt3(double mt3) {
        this.mt3 = mt3;
    }

    public void setTotalsubtotal(double totalsubtotal) {
        this.totalsubtotal = totalsubtotal;
    }

    public List<Detallenotacarga> getDeallesnotafiltrados() {
        return detallesnotafiltrados;
    }

    public void setDeallesnotafiltrados(List<Detallenotacarga> deallesnotafiltrados) {
        this.detallesnotafiltrados = deallesnotafiltrados;
    }

    public List<Notacarga> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public Notacarga getNotacarga(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Notacarga> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Notacarga> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public Notacarga prepareCreate() {
        selected = new Notacarga();
        initializeEmbeddableKey();
        return selected;
    }

    public void anexaraNota() {
        if (cantidad != 0) {
            double alicuota = 0;
            double iva = 0;
            double total = 0;
            Detallenotacarga detal = new Detallenotacarga();
            detal.setCodigo(detalle.getCodigo());
            detal.setCantidad(cantidad);
//            pcosto = reque.getCodigo().getPcosto();
            detal.setPrecioventa(pventa);
            subtotal = cantidad * pventa;
            detal.setSubtotal(subtotal);
            alicuota = detal.getCodigo().getIdgravamen().getAlicuota();
            iva = (subtotal * alicuota) / 100;
            total = subtotal + iva;
            detal.setSubtotal(subtotal);
            detal.setIva(iva);
            detal.setTotalnota(total);
            detal.setIddetallenotacarga(id);
            this.listadetallenota.add(detal);
            id++;
            detalles = detallenotacargaEJB.findAll();
            pventa = 0;
            cantidad = 0;
            detalle.setCodigo(null);
            totaltotal();
//            requer.setCodigo(null);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "No puede dejar el campo Cantidad en 0.0"));
        }

    }

    public void registrarnota() {
        Articulo art = new Articulo();
        Date fecha = new Date();
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        String fechaCadena = hourFormat.format(fecha);
        DecimalFormat numformat = new DecimalFormat("#######.##");
        try {
            Usuario usua = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            selected.setIdusuario(usua);
            selected.setRifcliente(cliente);
            selected.setCantidad(totalcantidad);
            selected.setBimponible(totalsubtotal);
            selected.setIva(totaliva);
            selected.setTotalgeneral(totalgeneral);
            selected.setPendiente(totalcantidad);
            ejbFacade.create(selected);

            codnota = ejbFacade.ultimaInsertada();
            number = codnota.getIdnotacarga();
            String material = " ";
            for (Detallenotacarga rq : listadetallenota) {
                Articulo arti = rq.getCodigo();
                detalle.setIdnotacarga(codnota);
                detalle.setCodigo(arti);
                detalle.setCantidad(rq.getCantidad());
                detalle.setPrecioventa(rq.getPrecioventa());
                detalle.setSubtotal(rq.getSubtotal());
                detalle.setIva(rq.getIva());
                detalle.setTotalnota(rq.getTotalnota());
                detalle.setPordespachar(rq.getCantidad());
                material = material + detalle.getCodigo().getDescripcion() + " "
                        + detalle.getCantidad()+ "Mt3 ";
                detallenotacargaEJB.create(detalle);

            }
            String subject;
            String ultimanota = "" + codnota.getIdnotacarga();
            String fechafactu = formateador.format(selected.getFecha());
            correo = "NOTA CARGA NRO: " + ultimanota
                    + "  USUARIO: " + selected.getIdusuario().getNombre()
                    + "  FECHA: " + fechafactu
                    + "  CLIENTE: " + selected.getRifcliente().getRazonsocial()
                    + "  RIF: " + selected.getRifcliente().getRifcliente()
                    + "  AGREGADO: " + material
                    + "  SUBTOTAL: " + formatearnumero.format(selected.getBimponible())
                    + "  IVA: " + formatearnumero.format(selected.getIva())
                    + "  TOTAL: " + formatearnumero.format(selected.getTotalgeneral())
                    + "  OBSERVACIONES: " + selected.getObservacionesnotacarga();
            empresa= empresaEJB.devolverEmpresabase();
            subject = empresa.getNombrecomercial() + " Nota Carga N° " + ultimanota;
            enviomail = new envioCorreo(correo, subject);
            enviomail.start();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La nota de carga se registro exitosamente", "Aviso"));
            limpiarListaArreglo();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar esta Factura", "Aviso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }

    public void registrardespacho() {
        Articulo art = new Articulo();
        art = articulo;
        double pendient = 0;
        double cantidamax = detalle.getPordespachar();
        double cubicajemax = despacho.getIdcamion().getCubicaje();
        Inventariopicadora inventa = new Inventariopicadora();

        if (mt3 <= cantidamax) {
            if (mt3 <= cubicajemax) {
                try {
                    despacho.setIdnotacarga(notacargadialog);
                    despacho.setCodigo(articulo);
                    despacho.setCantidad(mt3);
                    pendient = detalle.getPordespachar() - mt3;
                    despacho.setPendiente(pendient);
                    despachopicadoraEJB.create(despacho);

                    inventariopro = inventariopicadoraEJB.buscarAgregado(articulo.getCodigo());

                    detalle.setPordespachar(pendient);
                    detallenotacargaEJB.edit(detalle);

                    notacargadialog.setPendiente(notacargadialog.getPendiente() - mt3);
                    notacargaEJB.edit(notacargadialog);

                    moviinventariopro.setCodigo(art);
                    moviinventariopro.setDisminucion(mt3);
                    moviinventariopro.setIddespachopicadora(despacho);
                    movimientoinventariopicadoraEJB.create(moviinventariopro);

                    if (inventa == null) {
                        inventariopro.setCodigo(articulo);
                        inventariopro.setCantidad(0 - mt3);
                        inventariopicadoraEJB.create(inventariopro);
                    } else {
                        double cant = inventariopro.getCantidad();
                        double saldo = cant - mt3;
                        inventariopro.setCantidad(saldo);
                        inventariopicadoraEJB.edit(inventariopro);
                    }

//                    String subject;
//                    String ultimafactura = ejbFacade.u();
//                    String fechafactu = formateador.format(factura.getFecha());
//                    correo = "FACTURA NRO: " + ultimafactura
//                            + "  CONTROL: " + factura.getNumerocontrol()
//                            + "  USUARIO: " + factura.getIdusuario().getNombre()
//                            + "  DEPARTAMENTO: " + factura.getIdusuario().getIddepartamento().getDepartamento()
//                            + "  FECHA: " + fechafactu
//                            + "  CLIENTE: " + factura.getRifcliente().getRazonsocial()
//                            + "  RIF: " + factura.getRifcliente().getRifcliente()
//                            + "  SUBTOTAL: " + formatearnumero.format(factura.getBimponiblefact())
//                            + "  IVA: " + formatearnumero.format(factura.getIvafact())
//                            + "  TOTAL: " + formatearnumero.format(factura.getTotalgeneral())
//                            + "  OBSERVACIONES: " + factura.getObservacionesfact();
//
//                    subject = "Emisión de Factura N° " + ultimafactura;
//                    enviomail = new envioCorreo(correo, subject);
//                    enviomail.start();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "La nota de despacho se registro exitosamente", "Aviso"));
                    limpiarListaArreglo();
                } catch (Exception e) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Grabar nota de Despacho", "Aviso"));
                } finally {
                    FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cantidad no puede exceder cubicaje del camión", "Aviso"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Cantidad no puede exceder saldo pendiente del agregado", "Aviso"));
        }
    }

    public void buscarArticulo() {
        articulo = detalle.getCodigo();
//        pcosto = articulo.getPcosto();
        pventa = articulo.getPventa();
    }

    public void buscarCamion() {
        mt3 = despacho.getIdcamion().getCubicaje();
    }

    public void buscarChofer() {
        chofer = despacho.getIdchofer();
    }

    public int devolversiguientenotacarga() {
        int siguiente;
        siguiente = ejbFacade.siguientenotacarga();
        return siguiente;
    }

    public double totaltotal() {
        double montotgeneral = 0;
        double montotiva = 0;
        double montotsubtotal = 0;
        double cantidadmt3 = 0;

        for (Detallenotacarga requeri : listadetallenota) {
            montotgeneral += requeri.getTotalnota();
            montotiva += requeri.getIva();
            montotsubtotal += requeri.getSubtotal();
            cantidadmt3 += requeri.getCantidad();
        }
        totalgeneral = montotgeneral;
        totaliva = montotiva;
        totalsubtotal = montotsubtotal;
        totalcantidad = cantidadmt3;

        return montotgeneral;
    }

    public void eliminar(Detallenotacarga detalleelim) {
        listadetallenota.remove(detalleelim.hashCode());
        int indice = 0;
        for (Detallenotacarga requeri : listadetallenota) {
            requeri.setIddetallenotacarga(indice);
            indice++;
            id = indice;
        }
        if (detalleelim.hashCode() == 0) {
            id = 0;
        }
        totaltotal();
    }

    public void limpiarListaArreglo() {
        listadetallenota.clear();
        totaltotal();
    }

    public void asignarNotacarga(Notacarga notaselec) {
        this.idnota = notaselec.getIdnotacarga();
        this.selected = notaselec;
        this.notacargadialog = notaselec;
        detallesnotafiltrados = detallenotacargaEJB.detallesfiltrados(notaselec);
        detalle = detallesnotafiltrados.get(0);
        articulo = detalle.getCodigo();
//        compraautorizada = compraselec;
    }

    public void asignarDetallenota(Detallenotacarga detallenotaselec) {
        this.detalle = detallenotaselec;
        articulo = detalle.getCodigo();
//        compraautorizada = compraselec;
    }

}
