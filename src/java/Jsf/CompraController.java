package Jsf;

import Modelo.Compra;
import Jsf.util.JsfUtil;
import Jsf.util.JsfUtil.PersistAction;
import Jpa.CompraFacade;
import Jpa.CompraFacadeLocal;
import Jpa.DetallecompraFacadeLocal;
import Jpa.EstatusfacturaFacadeLocal;
import Jpa.PagocompraFacadeLocal;
import Modelo.Detallecompra;
import Modelo.Estatusfactura;
import Modelo.Factura;
import Modelo.Pagocompra;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.servlet.ServletContext;

@Named("compraController")
@ViewScoped
public class CompraController implements Serializable {

    @EJB
    private Jpa.CompraFacadeLocal ejbFacade;
    @EJB
    private EstatusfacturaFacadeLocal estatusfacturaEJB;
    @EJB
    private DetallecompraFacadeLocal detallecompraEJB;
    @EJB
    private PagocompraFacadeLocal pagocompraEJB;

    private List<Compra> items = null;
    private List<Compra> comprasactivas = null;
    private List<Compra> comprasporautorizar = null;
    private List<Compra> compraspagadas = null;
    private List <Compra> comprasfiltradas=null;
    private List<Estatusfactura> estatusfactxpagar = null;
    private List<Detallecompra> detallecompraFiltrados;
    private List<Pagocompra> pagosporidcompra;
    private Compra selected;
    private Compra compraseleccionada;
    private Estatusfactura estatusfact;
    private PagosController pagoscontroller;
    private Date fechadesde;
    private Date fechahasta;    

    public CompraController() {
    }

    @PostConstruct
    public void init() {
        comprasactivas = ejbFacade.buscarcomprasporPagar();
        comprasporautorizar = ejbFacade.buscarcomprasporAutorizar();
        compraspagadas = ejbFacade.buscarcomprasPagadas();
    }

    public void setSelected(Compra selected) {
        this.selected = selected;
        if (selected != null) {
            asignar();
        }
    }

    public Compra getSelected() {
        return selected;
    }

    public void setCompraseleccionada(Compra compraseleccionada) {
        if (compraseleccionada != null) {
            this.compraseleccionada = compraseleccionada;
            this.selected = compraseleccionada;
            asignar();
        }
    }
    public Date getFechadesde() {
        return fechadesde;
    }

    public void setFechadesde(Date fechadesde) {
        this.fechadesde = fechadesde;
    }

    public Date getFechahasta() {
        return fechahasta;
    }

    public void setFechahasta(Date fechahasta) {
        this.fechahasta = fechahasta;
    }
    public List<Compra> getComprasfiltradas() {
        return comprasfiltradas;
    }

    public void setComprasfiltradas(List<Compra> comprasfiltradas) {
        this.comprasfiltradas = comprasfiltradas;
    }

    public Estatusfactura getEstatusfact() {
        return estatusfact;
    }

    public void setEstatusfact(Estatusfactura estatusfact) {
        this.estatusfact = estatusfact;
    }

    public Compra getCompraseleccionada() {
        return compraseleccionada;
    }

    public void asignar() {
        detallecompraFiltrados = detallecompraAuxiliar();
        pagosporidcompra = pagocompraEJB.buscarpago(selected);
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CompraFacadeLocal getFacade() {
        return ejbFacade;
    }

    public List<Compra> getComprasactivas() {
        return comprasactivas;
    }

    public List<Compra> getComprasporAutorizar() {
        return comprasporautorizar;
    }

    public List<Detallecompra> getDetallecompraFiltrados() {
        return detallecompraFiltrados;
    }

    public void setDetallecompraFiltrados(List<Detallecompra> detallecompraFiltrados) {
        this.detallecompraFiltrados = detallecompraFiltrados;
    }

    public List<Compra> getComprasPagadas() {
        return compraspagadas;
    }

    public List<Estatusfactura> getEstatusfactxpagar() {
        return estatusfactxpagar;
    }

    public List<Pagocompra> getPagosporidcompra() {
        return pagosporidcompra;
    }

    public void setPagosporidcompra(List<Pagocompra> pagosporidcompra) {
        this.pagosporidcompra = pagosporidcompra;
    }

    public void setEstatusfactxpagar(List<Estatusfactura> estatusfactxpagar) {
        this.estatusfactxpagar = estatusfactxpagar;
    }

    public void setRequerimientosactivos(List<Compra> comprasactivas) {
        this.comprasactivas = comprasactivas;
    }

    public List<Compra> buscarComprasActivas() {
        comprasactivas = ejbFacade.buscarcomprasporPagar();
        return comprasactivas;
    }

    public List<Compra> buscarComprasporAutorizar() {
        comprasporautorizar = ejbFacade.buscarcomprasporAutorizar();
        return comprasporautorizar;
    }

    public List<Compra> buscarComprasPagadas() {
        comprasporautorizar = ejbFacade.buscarcomprasPagadas();
        return compraspagadas;
    }

    public List<Estatusfactura> getStatusFactporPagar() {
        estatusfactxpagar = estatusfacturaEJB.ListarEstatusporPagar();
        return estatusfactxpagar;
    }

    public List<Detallecompra> detallecompraAuxiliar() {
        List<Detallecompra> listado = null;
        listado = detallecompraEJB.buscardetallecompra(selected);
        return listado;
    }

    public List<Pagocompra> pagosFiltrados(Compra compra) {
        pagosporidcompra = pagocompraEJB.buscarpago(selected);
        return pagosporidcompra;
    }

    public Compra prepareCreate() {
        selected = new Compra();
        initializeEmbeddableKey();
        return selected;
    }

    public void actualizar(){
        comprasfiltradas= ejbFacade.buscarcomprasFiltradas(estatusfact, fechadesde, fechahasta);
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CompraCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CompraUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CompraDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Compra> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    public String getSubtotalGeneral() {
        double total = 0;
        
        if (comprasfiltradas!=null){
            for(Compra  inventa : getComprasfiltradas()) {
                total += inventa.getSubtotal();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    public String getIvaGeneral() {
        double total = 0;
        
        if (comprasfiltradas!=null){
            for(Compra inventa : getComprasfiltradas()) {
                total += inventa.getIva();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    
    public String getTotalGeneral() {
        double total = 0;
        
        if (comprasfiltradas!=null){
            for(Compra inventa : getComprasfiltradas()) {
                total += inventa.getTotal();
            }
        }
        return new DecimalFormat("###,###.##").format(total);

    }
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Compra getCompra(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Compra> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Compra> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Compra.class)
    public static class CompraControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CompraController controller = (CompraController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "compraController");
            return controller.getCompra(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Compra) {
                Compra o = (Compra) object;
                return getStringKey(o.getIdcompra());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Compra.class.getName()});
                return null;
            }
        }

    }

    public void verReporte() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/porautorizar.jasper");

        rArticulo.getReporte(ruta);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void verOrdendeCompra() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        int codigocompra = selected.getIdcompra();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/ordendecompra.jasper");

        rArticulo.getOrdendeCompra(ruta, codigocompra);
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public void verOrdendeCompralista(Compra compra) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        int codigocompra = compra.getIdcompra();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/ordendecompra.jasper");

        rArticulo.getOrdendeCompra(ruta, codigocompra);
        FacesContext.getCurrentInstance().responseComplete();
    }

        public void verMovimientoCompras() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        //Instancia hacia la clase reporteClientes        
        reporteArticulo rArticulo = new reporteArticulo();

        int codigoestatus = estatusfact.getIdestatusfactura() ;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/resources/reportes/comprashechas.jasper");

        rArticulo.getMovimientoCompras(ruta, codigoestatus,fechadesde,fechahasta);
        FacesContext.getCurrentInstance().responseComplete();
    }

}
