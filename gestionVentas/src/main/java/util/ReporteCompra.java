package util;

import BaseDatos.ConexionMySQL;
import Compras.Item.CompraItem;
import Ventas.Item.VentaItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class ReporteCompra {
    private ConexionMySQL conexion;
    
    public ReporteCompra() {
        this.conexion = new ConexionMySQL();
    }
    
    public static String generarPDFCompra(Integer idCompra, Integer nit, String proveedor, java.util.Date fecha, Double total, List<CompraItem> items) {
        // ✅ Ruta completa del archivo
        String nombreArchivo = "compra_" + idCompra + ".pdf";
        
        // C:\Users\hp\Documentos\Reportes_UBI\compra_1.pdf
        // C:/Users/hp/Documentos/Reportes_UBI\compra_1.pdf
        String carpetaReportes = System.getProperty("user.home") + File.separator + "Documentos" + File.separator + "Reportes_UBI";
        File carpeta = new File(carpetaReportes);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        String rutaCompleta = carpetaReportes + File.separator + nombreArchivo;
        System.out.println("Ruta completa del PDF: " + rutaCompleta);
        
        try {
            PdfWriter writer = new PdfWriter(rutaCompleta);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Título
            Paragraph titulo = new Paragraph("COMPRA Nº " + idCompra)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();
            document.add(titulo);
            document.add(new Paragraph(" "));
            
            // Datos de la cabecera
            Table cabecera = new Table(3);
//            cabecera.addCell("NIT:");
//            cabecera.addCell(nit.toString());
//            cabecera.addCell("Proveedor:");
//            cabecera.addCell(proveedor);
//            cabecera.addCell("Fecha:");
//            cabecera.addCell(fecha.toString());
            
            // Añadir celdas SIN bordes
            cabecera.addCell(crearCeldaSinBorde("NIT"));
            cabecera.addCell(crearCeldaSinBorde(":"));
            cabecera.addCell(crearCeldaSinBorde(nit.toString()));
            
            cabecera.addCell(crearCeldaSinBorde("Proveedor"));
            cabecera.addCell(crearCeldaSinBorde(":"));
            cabecera.addCell(crearCeldaSinBorde(proveedor));
            
            cabecera.addCell(crearCeldaSinBorde("Fecha"));
            cabecera.addCell(crearCeldaSinBorde(":"));
            cabecera.addCell(crearCeldaSinBorde(fecha.toString()));
            
            document.add(cabecera);
            document.add(new Paragraph(" "));
            
            // Tabla de ítems
            Table tablaItems = new Table(4);
//            tablaItems.addHeaderCell("ID");
            tablaItems.addHeaderCell("Producto");
            tablaItems.addHeaderCell("Cantidad");
            tablaItems.addHeaderCell("Precio");
            tablaItems.addHeaderCell("SubTotal");
            
            for (CompraItem item : items) {
//                tablaItems.addCell(item.getIdProducto().toString());
                tablaItems.addCell(item.getNombreProducto());
                tablaItems.addCell(item.getCantidad().toString());
                tablaItems.addCell(String.format("%.2f", item.getPrecio()));
                tablaItems.addCell(String.format("%.2f", item.getSubTotal()));
            }
            
            document.add(tablaItems);
            document.add(new Paragraph(" "));
            
            // Total final
            Paragraph totalFinal = new Paragraph("TOTAL (Bs): " + String.format("%.2f", total))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold()
                    .setFontSize(14);
            document.add(totalFinal);
            
            document.close();
            return rutaCompleta;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static String generarPDFVenta(Integer idVenta, Integer nit, String cliente, java.util.Date fecha, Double total, List<VentaItem> items) {
        // ✅ Ruta completa del archivo
        String nombreArchivo = "venta_" + idVenta + ".pdf";
        
        // C:\Users\hp\Documentos\Reportes_UBI\compra_1.pdf
        // C:/Users/hp/Documentos/Reportes_UBI\compra_1.pdf
        String carpetaReportes = System.getProperty("user.home") + File.separator + "Documentos" + File.separator + "Reportes_UBI";
        File carpeta = new File(carpetaReportes);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        String rutaCompleta = carpetaReportes + File.separator + nombreArchivo;
        System.out.println("Ruta completa del PDF: " + rutaCompleta);
        
        try {
            PdfWriter writer = new PdfWriter(rutaCompleta);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Título
            Paragraph titulo = new Paragraph("VENTA Nº " + idVenta)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();
            document.add(titulo);
            document.add(new Paragraph(" "));
            
            // Datos de la cabecera
            Table cabecera = new Table(3);
//            cabecera.addCell("NIT:");
//            cabecera.addCell(nit.toString());
//            cabecera.addCell("Cliente:");
//            cabecera.addCell(cliente);
//            cabecera.addCell("Fecha:");
//            cabecera.addCell(fecha.toString());
            
            // Añadir celdas SIN bordes
            cabecera.addCell(crearCeldaSinBorde("NIT"));
            cabecera.addCell(crearCeldaSinBorde(":"));
            cabecera.addCell(crearCeldaSinBorde(nit.toString()));
            
            cabecera.addCell(crearCeldaSinBorde("Cliente"));
            cabecera.addCell(crearCeldaSinBorde(":"));
            cabecera.addCell(crearCeldaSinBorde(cliente));
            
            cabecera.addCell(crearCeldaSinBorde("Fecha"));
            cabecera.addCell(crearCeldaSinBorde(":"));
            cabecera.addCell(crearCeldaSinBorde(fecha.toString()));
            
            document.add(cabecera);
            document.add(new Paragraph(" "));
            
            // Tabla de ítems
            Table tablaItems = new Table(4);
//            tablaItems.addHeaderCell("ID");
            tablaItems.addHeaderCell("Producto");
            tablaItems.addHeaderCell("Cantidad");
            tablaItems.addHeaderCell("Precio");
            tablaItems.addHeaderCell("SubTotal");
            
            for (VentaItem item : items) {
//                tablaItems.addCell(item.getIdProducto().toString());
                tablaItems.addCell(item.getNombreProducto());
                tablaItems.addCell(item.getCantidad().toString());
                tablaItems.addCell(String.format("%.2f", item.getPrecio()));
                tablaItems.addCell(String.format("%.2f", item.getSubTotal()));
            }
            
            document.add(tablaItems);
            document.add(new Paragraph(" "));
            
            // Total final
            Paragraph totalFinal = new Paragraph("TOTAL (Bs): " + String.format("%.2f", total))
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setBold()
                    .setFontSize(14);
            document.add(totalFinal);
            
            document.close();
            return rutaCompleta;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    
     /**
     * Genera un PDF con el historial de movimientos de un producto
     * @param idproducto ID del producto a reportar
     * @return Ruta completa del archivo PDF generado, o null si falla
     */
    public String generarPDFMovimiento(Integer idproducto) {
        if (idproducto == null) {
            System.err.println("❌ ID de producto no puede ser nulo");
            return null;
        }
        
        // ✅ Ruta completa del archivo
        String nombreArchivo = "Movimiento_" + idproducto + ".pdf";
        
        String carpetaReportes = System.getProperty("user.home") + File.separator + "Documentos" + File.separator + "Reportes_UBI";
        File carpeta = new File(carpetaReportes);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }
        String rutaCompleta = carpetaReportes + File.separator + nombreArchivo;
        System.out.println("Ruta completa del PDF: " + rutaCompleta);
        
         try {
            PdfWriter writer = new PdfWriter(rutaCompleta);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            // Título
            Paragraph titulo = new Paragraph("MOVIMIENTOS DEL PRODUCTO " + idproducto)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(20)
                    .setBold();
            document.add(titulo);
            document.add(new Paragraph(" "));
            
            // Consulta de datos
            String sql = """
                SELECT fecha, hora, descripcion, ingreso, salida, saldo
                FROM movimientos
                WHERE idproducto = ?
                ORDER BY fecha DESC, hora DESC
                """;
            /* non-static variable conexion cannot be referenced from static contexto */
            try (Connection conn = conexion.getConexion();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                
                ps.setInt(1, idproducto);
                
                try (ResultSet rs = ps.executeQuery()) {
                    // Tabla de ítems
                    Table tablaItems = new Table(6);
                    
                    // Encabezados sin bordes
                    tablaItems.addHeaderCell(crearCeldaSinBorde("Fecha").setBold());
                    tablaItems.addHeaderCell(crearCeldaSinBorde("Hora").setBold());
                    tablaItems.addHeaderCell(crearCeldaSinBorde("Descripción").setBold());
                    tablaItems.addHeaderCell(crearCeldaSinBorde("Ingreso").setBold());
                    tablaItems.addHeaderCell(crearCeldaSinBorde("Salida").setBold());
                    tablaItems.addHeaderCell(crearCeldaSinBorde("Saldo").setBold());
                    
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    
                    while (rs.next()) {
                        // Formatear fecha
                        Date fecha = rs.getDate("fecha");
                        String fechaFormateada = fecha != null ? 
                            LocalDate.parse(fecha.toString()).format(formatter) : "";
                        
                        // Añadir filas
                        tablaItems.addCell(crearCeldaSinBorde(fechaFormateada));
//                        tablaItems.addCell(crearCeldaSinBorde(rs.getString("hora")));
//                        tablaItems.addCell(crearCeldaSinBorde(rs.getString("descripcion")));
//                        tablaItems.addCell(crearCeldaSinBorde(rs.getString("ingreso")));
//                        tablaItems.addCell(crearCeldaSinBorde(rs.getString("salida")));
//                        tablaItems.addCell(crearCeldaSinBorde(rs.getString("saldo")));
                    }
                    
                    document.add(tablaItems);
                }
            }
            
            document.add(new Paragraph(" "));
            
//            // Pie de página
//            Paragraph pie = new Paragraph("Generado el: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
//                    .setTextAlignment(TextAlignment.RIGHT)
//                    .setFontSize(10);
//            document.add(pie);
            
            document.close();
            return rutaCompleta;
            
        } catch (FileNotFoundException e) {
            System.err.println("❌ Error al crear el PDF: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ Error al consultar la base de datos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    // Método auxiliar para crear celdas sin bordes
    private static Cell crearCeldaSinBorde(String contenido) {
        Cell celda = new Cell();
        celda.setBorder(null); // ¡Esto quita el borde!
        celda.add(new Paragraph(contenido));
        return celda;
    }
}