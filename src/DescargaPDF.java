import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

public class DescargaPDF {
    public void generarPDF(String filename, Reserva reserva) {
        System.out.println("Recibido: " + filename);
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            
            reserva.getApartamento();
            // Información básica
            document.add(new Paragraph("Detalles de la Reserva"));
            document.add(new Paragraph(reserva.imprimirReservas()));
            System.out.println("Documento creado con éxito.");
        } catch (DocumentException | IOException e) {
            System.out.println("Error al crear el documento.");
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close(); // Asegurar que el documento se cierre
            }
            System.out.println("Documento cerrado.");
        }
    }
}
