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
            System.out.println("Intentando escribir en el archivo...");
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph("Detalles de la Reserva"));
            document.add(new Paragraph("Numero de la reserva: " + reserva.getId()));
            //Listado de pasajeros 
            // Añadir pasajeros
            document.add(new Paragraph("Pasajeros:"));
            // for (App.DatosPasajero pasajero : reserva.getPasajeros()) {
            //     document.add(new Paragraph("Nombre: " + pasajero.getNombre() + ", Documento: " + pasajero.getDocumento()));
            // }

            // Añadir matrículas
            document.add(new Paragraph("Vehículos:"));
            for (String matricula : reserva.getMatriculas()) {
                document.add(new Paragraph(matricula));
            }

            document.add(new Paragraph("Nota:" + reserva.getNota()));

            
            document.add(new Paragraph("Apartamento: " + reserva.getApartamento()));
            document.add(new Paragraph("Nota: "+ reserva.getNota()));
            System.out.println("Documento creado con éxito.");
        } catch (DocumentException | IOException e) {
            System.out.println("Error al crear el documento.");
            e.printStackTrace();
        } finally {
            System.out.println("Documento cerrado.");
        }
    }
    
}
