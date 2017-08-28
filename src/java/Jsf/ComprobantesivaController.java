package Jsf;

import Jpa.ComprobanteivaefFacadeLocal;
import Jpa.DetalleretencionivaefFacadeLocal;
import Modelo.Comprobanteivaef;
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
@ManagedBean(name = "comprobantesivaController")
@SessionScoped

public class ComprobantesivaController implements Serializable {

    @EJB
    private ComprobanteivaefFacadeLocal comprobanteivaefEJB;
    @EJB
    private DetalleretencionivaefFacadeLocal detalleretencionivaefEJB;
            
    private Detalleretencionivaef detalleretencionivaef;
    private String correlativo="";
    private int serialcomprob=0;
    private int anio;
    private int mes;
    private String mesfiscal;
    private String serialcomprobante;
    private Date fechacomprobante;
    private List<Detalleretencionivaef> detalleretivafiltrados = new ArrayList();
    private double totalgeneral;
    private double totalbaseimponible;
    private double totaliva;
    private double totalivaretenido;
    private int id=0;
    private int idcomprobanteretiva;
    @Inject
    private Estatuscomprobanteretencion estatuscomprobanteretencion;
    @Inject
    private Comprobanteivaef comprobanteivaef;

    ///////////////////////////////////////////

    
    private Date fechaactual = new Date();
    SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    DecimalFormat formatearnumero = new DecimalFormat("###,###.##");


    public Detalleretencionivaef getDetalleretencionivaef() {
        return detalleretencionivaef;
    }

    public void setDetalleretencionivaef(Detalleretencionivaef detalleretencionivaef) {
        this.detalleretencionivaef = detalleretencionivaef;
    }

    public Comprobanteivaef getComprobanteivaef() {
        return comprobanteivaef;
    }

    public void setComprobanteivaef(Comprobanteivaef comprobanteivaef) {
        this.comprobanteivaef = comprobanteivaef;
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

    public List<Detalleretencionivaef> getDetalleretivafiltrados() {
        return detalleretivafiltrados;
    }

    public void setDetalleretivafiltrados(List<Detalleretencionivaef> detalleretivafiltrados) {
        this.detalleretivafiltrados = detalleretivafiltrados;
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

    public double getTotalivaretenido() {
        return totalivaretenido;
    }

    public void setTotalivaretenido(double totalivaretenido) {
        this.totalivaretenido = totalivaretenido;
    }
    
    public String devolversiguientecomprobante() {
        String siguiente;
        siguiente = comprobanteivaefEJB.siguientecomprobanteformat();
        serialcomprob= Integer.parseInt (siguiente);
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
        double montotiva = 0;
        double montotsubtotal = 0;
        double montoretenido=0;
        for (Detalleretencionivaef detalleretiva : detalleretivafiltrados) {
            montotgeneral += detalleretiva.getTotalcompra();
            montotiva += detalleretiva.getTotalivacompra();
            montotsubtotal += detalleretiva.getBimponible();
            montoretenido  += detalleretiva.getTotalivaretenido();
        }
        totalgeneral = montotgeneral;
        totaliva = montotiva;
        totalbaseimponible = montotsubtotal;
        totalivaretenido = montoretenido;
    }
    
    public void asignar(Detalleretencionivaef detalleretivaef) {
        this.detalleretencionivaef = detalleretivaef;
        this.detalleretivafiltrados=detalleretencionivaefEJB.buscarretencionesporPreveedor(detalleretivaef.getIdcompra().getRifproveedor().getRifproveedor()); 
        obtenertotaltotales();
    }
    public void eliminar(Detalleretencionivaef detalleaeliminar) {
        int indc= detalleretivafiltrados.indexOf(detalleaeliminar);
        detalleretivafiltrados.remove(indc);
        obtenertotaltotales();
    }
    public void registrar() {
        try {
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
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fue generado el Comprobante de Retencion de Iva ", "Aviso "));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al Generar el Comprobante de Retencion de IVA", "Aviso"));
        } finally {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        }
    }
    
    @PostConstruct
    public void init() {
        detalleretivafiltrados.clear();
        
       
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
