package util;

// PruebaIText.java
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PruebaIText {
    public static void main(String[] args) {
        try {
            PdfWriter writer = new PdfWriter("prueba.pdf");
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            document.add(new Paragraph("iText 7 funciona correctamente"));
            document.close();
            System.out.println("✅ PDF generado con éxito");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}