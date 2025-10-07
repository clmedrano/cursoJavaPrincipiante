package util;

import Compras.detalle.CompraItem;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteCompra {
    
    public static String generarPDFCompra(
            Integer idCompra,
            Integer nit,
            String proveedor,
            java.util.Date fecha,
            Double total,
            List<CompraItem> items) {
        
        // Nombre del archivo
        String nombreArchivo = "compra_" + idCompra + ".pdf";
        
        try {
            PdfWriter writer = new PdfWriter(nombreArchivo);
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
            return nombreArchivo;
            
        } catch (FileNotFoundException e) {
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