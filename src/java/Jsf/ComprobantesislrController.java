package Jsf;

import Jpa.ComprobanteislrefFacadeLocal;
import Jpa.ComprobanteivaefFacadeLocal;
import Jpa.DetalleretencionislrefFacadeLocal;
import Jpa.DetalleretencionivaefFacadeLocal;
import Modelo.Comprobanteislref;
import Modelo.Comprobanteivaef;
import Modelo.Detalleretencionislref;
import Modelo.Detalleretencionivaef;
import Modelo.Estatuscomprobanteretencion;
import Modelo.Factura;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.jboss.weld.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author sofimar
 */
@ManagedBean(name = "comprobantesislrController")
@SessionScoped

public class ComprobantesislrController implements Serializable {

    @EJB
    private ComprobanteislrefFacadeLocal comprobanteislrefEJB;
    @EJB
    private DetalleretencionislrefFacadeLocal detalleretencionislrefEJB;
            
    private Detalleretencionislref detalleretencionislref;
    private String correlativo="";
    private int anio;
    private int mes;
    private String mesfiscal;
    private String serialcomprobante;
    private Date fechacomprobante;
    private List<Detalleretencionislref> detalleretislrfiltrados = new ArrayList();
    private double totalgeneral;
    private double totalbaseimponible;
    private double totaliva;
    private double totalislrretenido;
    private int id=0;
    private int idcomprobanteretislr;
    @Inject
    private Estatuscomprobanteretencion estatuscomprobanteretencion;
    @Inject
    private Comprobanteislref comprobanteislref;

    ///////////////////////////////////////////

    
    private Date fechaactual = new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");


    public Detalleretencionislref getDetalleretencionislref() {
        return detalleretencionislref;
    }

    public void setDetalleretencionislref(Detalleretencionislref detalleretencionislref) {
        this.detalleretencionislref = detalleretencionislref;
    }

    public Comprobanteislref getComprobanteislref() {
        return comprobanteislref;
    }

    public void setComprobanteislref(Comprobanteislref comprobanteislref) {
        this.comprobanteislref = comprobanteislref;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public String getMesfiscal() {
        return mesfiscal;
    }

    public void setMesfiscal(String mesfiscal) {
        this.mesfiscal = mesfiscal;
    }
    
    public String getSerialcomprobante() {
        return serialcomprobante;
    }

    public void setSerialcomprobante(String serialcomprobante) {
        this.serialcomprobante = serialcomprobante;
    }

    public Date getFechacomprobante() {
        return fechacomprobante;
    }

    public void setFechacomprobante(Date fechacomprobante) {
        this.fechacomprobante = fechacomprobante;
    }

    public List<Detalleretencionislref> getDetalleretislrfiltrados() {
        return detalleretislrfiltrados;
    }

    public void setDetalleretislrfiltrados(List<Detalleretencionislref> detalleretislrfiltrados) {
        this.detalleretislrfiltrados = detalleretislrfiltrados;
    }

    public double getTotalgeneral() {
        return totalgeneral;
    }

    public void setTotalgeneral(double totalgeneral) {
        this.totalgeneral = totalgeneral;
    }

    public double getTotalbaseimponible() {
        return totalbaseimponible;
    }

    public void setTotalbaseimponible(double totalbaseimponible) {
        this.totalbaseimponible = totalbaseimponible;
    }

    public double getTotaliva() {
        return totaliva;
    }

    public void setTotaliva(double totaliva) {
        this.totaliva = totaliva;
    }

    public double getTotalislrretenido() {
        return totalislrretenido;
    }

    public void setTotalislrretenido(double totalislrretenido) {
        this.totalislrretenido = totalislrretenido;
    }
    
    public String devolversiguientecomprobante() {
        String siguiente;
        siguiente = comprobanteislrefEJB.siguientecomprobanteformat();
        correlativo=siguiente;
        return siguiente;
    }
    
    public void separarperiodofiscal(){
        //fechacomprobante=comprobanteivaef.getFecha();
        mes=0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechacomprobante);
        anio = cal.get(Calendar.YEAR);
        mes = cal.get(Calendar.MONTH)+1;
        String year= Integer.toString(anio);
        String month= String.format("%02d",mes);
        mesfiscal=month;        
        serialcomprobante=year + month + correlativo;
    }    

    public void onDateSelect(SelectEvent event) {    
        separarperiodofiscal();
    }
    
    public void obtenertotaltotales() {
        double montotgeneral = 0;
        double montotsubtotal = 0;
        double montoretenido=0;
        for (Detalleretencionislref detalleretislr : detalleretislrfiltrados) {
            montotgeneral += detalleretislr.getTotalcompra();
            montotsubtotal += detalleretislr.getBimponible();
            montoretenido  += detalleretislr.getTotalislrretenido();
        }
        totalgeneral = montotgeneral;
        totalbaseimponible = montotsubtotal;
        totalislrretenido = montoretenido;
    }
    
    public void asignar(Detalleretencionislref detalleretislref) {
        this.detalleretencionislref = detalleretislref;
        this.detalleretislrfiltrados=detalleretencionislrefEJB.buscarretencionesporPreveedor(detalleretislref.getIdcompra().getRifproveedor().getRifproveedor()); 
        obtenertotaltotales();
    }
    public void eliminar(Detalleretencionislref detalleaeliminar) {
        int indc= detalleretislrfiltrados.indexOf(detalleaeliminar);
        detalleretislrfiltrados.remove(indc);
        obtenertotaltotales();
    }
    public void registrar() {
/*        try {
            estatuscomprobanteretencion.setIdestatuscomprobante(1);
            comprobanteivaef.setComprobante(serialcomprobante);
            comprobanteivaef.setFecha(fechacomprobante);
            comprobanteivaef.setAnio(anio);
            comprobanteivaef.setMes(mes);
            comprobanteivaef.setRifproveedor(detalleretencionivaef.getIdcompra().getRifproveedor());
            
            comprobanteivaef.setTotalgeneral(totalgeneral);
            comprobanteivaef.setTotalbimponible(totalbaseimponible);
            comprobanteivaef.setTotaliva(totaliva);
            comprobanteivaef.setTotalivaretenido(totalivaretenido);
            comprobanteivaef.setIdestatuscomprobante(estatuscomprobanteretencion);
            comprobanteivaefEJB.create(comprobanteivaef);
            

            idcomprobanteretiva = Integer.parseInt(comprobanteivaefEJB.siguientecomprobanteformat());
            comprobanteivaef.setIdcomprobanteivaef(idcomprobanteretiva-1);
            for (Detalleretencionivaef detalle : detalleretivafiltrados) {
                detalle.setIdcomprobanteivaef(comprobanteivaef);
                detalleretencionivaefEJB.edit(detalle);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Fue generado el Comprobante de Retencion de Iva "));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Aviso", "Error al Generar el Comprobante de Retencion de IVA"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }*/
    }
    
    @PostConstruct
    public void init() {
        detalleretislrfiltrados.clear();
    }
    
    public void verComprobanteretiva(Comprobanteivaef item) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        int codigocomprobante = item.getIdcomprobanteivaef();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/comprobanteretiva.jasper");

        rArticulo.getComprobanteRetIva(ruta, codigocomprobante);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
///////////////////////////////////    
    
    
    
    
}
