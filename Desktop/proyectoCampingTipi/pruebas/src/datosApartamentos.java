import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class datosApartamentos {

    public static List<Apartamento> leerArchivo(String archivo) {
        List<Apartamento> apartamentos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                // Remove unwanted characters
                linea = linea.replaceAll("[{}]", "").trim();

                String[] datos = linea.split(",", 10);

                try {
                    Apartamento apt = new Apartamento(
                            Integer.parseInt(datos[0].trim()),
                            datos[1].trim(),
                            Integer.parseInt(datos[2].trim()),
                            Double.parseDouble(datos[3].trim()),
                            Double.parseDouble(datos[4].trim()),
                            Integer.parseInt(datos[5].trim()),
                            Double.parseDouble(datos[6].trim()),
                            Integer.parseInt(datos[7].trim()),
                            datos[8].trim(),
                            datos[9].trim()
                    );

                    apartamentos.add(apt);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error procesando línea: " + linea);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apartamentos;
    }

    public static class Apartamento {
        private int id;
        private String nombreApartamento;
        private int stock;
        private double espacio;
        private double altura;
        private int personas;
        private double precio;
        private int desayuno;
        private String descripcion;
        private String imagen; 

        public Apartamento(int id, String nombreApartamento, int stock,
                           double espacio, double altura, int personas, double precio,
                           int desayuno, String descripcion, String imagen) {
            this.id = id;
            this.nombreApartamento = nombreApartamento;
            this.stock = stock;
            this.espacio = espacio;
            this.altura = altura;
            this.personas = personas;
            this.precio = precio;
            this.desayuno = desayuno;
            this.descripcion = descripcion;
            this.imagen = imagen;
        }

        @Override
        public String toString() {
            return "Apartamento{" +
                    "id=" + id +
                    ", nombreApartamento='" + nombreApartamento + '\'' +
                    ", stock=" + stock +
                    ", espacio=" + espacio +
                    ", altura=" + altura +
                    ", personas=" + personas +
                    ", precio=" + precio +
                    ", desayuno=" + desayuno +
                    ", descripcion='" + descripcion + '\'' +
                    ", imagen='" + imagen + '\'' +
                    '}';
        }

        public int getPersonas() {
            return personas;
        }
        public double getPrecio() {
            return precio;
        }
        public double getEspacio() {
            return precio;
        }
        public double getAltura() {
            return altura;
        }
        public String getNombreApartamento() {
            return nombreApartamento;
        }
        public String getImange() {
            return imagen;
        }
        public String getdescripcion() {
            return descripcion;
        }
        //Aqui desayuno es true o false
        public int getDesayuno() {
            return desayuno;
        }
    }
}
