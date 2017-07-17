/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jsf;

import Modelo.Movimientobancario;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author sofimar
 */
public class reporteArticulo {

    public void getReporte(String ruta) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getFactura(String ruta, int numerofact) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("numfactura", numerofact);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getOrdendeCompra(String ruta, int numeroorden) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("numordenc", numeroorden);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getOrdendePago(String ruta, int numeroorden) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("numeroordenpago", numeroorden);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getComprobanteRetIva(String ruta, int numerocomprobante) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("numerocomprobante", numerocomprobante);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getComprobanteIngreso(String ruta, int numeroorden) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("numeroingreso", numeroorden);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getOrdendeCobro(String ruta, int numeroorden) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("numeroordencobro", numeroorden);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void getMovimientoBancario(String ruta, int cta, Date fechaini, Date fechafinal) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("cuenta", cta);
            parameter.put("fechaini", fechaini);
            parameter.put("fechafin", fechafinal);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }       
    public void getMovimientoCompras(String ruta, int status, Date fechaini, Date fechafinal) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (ruta != null) {

            Connection conexion;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/inpeca", "root", "091095");

            //Se definen los parametros si es que el reporte necesita
            Map parameter = new HashMap();
            parameter.put("estatus", status);
            parameter.put("fechaini", fechaini);
            parameter.put("fechafin", fechafinal);

            try {
                File file = new File(ruta);

                HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

                httpServletResponse.setContentType("application/pdf");
                httpServletResponse.addHeader("Content-Type", "application/pdf");

                JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(file.getPath());

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, conexion);

                JRExporter jrExporter = null;
                jrExporter = new JRPdfExporter();
                jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, httpServletResponse.getOutputStream());

                if (jrExporter != null) {
                    try {
                        jrExporter.exportReport();
                    } catch (JRException e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (conexion != null) {
                    try {
                        conexion.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
