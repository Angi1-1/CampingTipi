import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
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
    public static byte[] generarPDFMysql(String filename, Reserva reserva) {
        System.out.println("Recibido: " + filename);
        Document document = new Document();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            
            reserva.getApartamento();
            // Información básica
            document.add(new Paragraph("Detalles de la Reserva"));
            document.add(new Paragraph(reserva.imprimirReservas()));
            System.out.println("Documento creado con éxito.");
            document.close(); 
            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void guardarReserva(String archivo, Reserva reserva) {
        System.out.println("Realiza la reserva" + archivo + reserva.imprimirReservas());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            // Escribe los datos generales de la reserva
            writer.write("{" + reserva.getId() + "," 
                             + reserva.getFechaLlegada() + ","
                             + reserva.getFechaSalida()+","
                             + reserva.getApartamento()+","
                             + reserva.getPrecio()+ "}");
            writer.newLine();
           
            System.out.println("Datos de la reserva guardados correctamente en " + archivo);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos de la reserva: " + e.getMessage());
        }
    }
    

    
}
