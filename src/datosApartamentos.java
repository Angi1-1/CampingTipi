import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class datosApartamentos {
    private static final String URL_WITH_DB = "jdbc:mysql://localhost:3306/CampingTipi?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    //Datos de apartamento para insertar a mysql
    public static void datosApartamentosInsertar () {
        conexionJava.insertartDatosApartamentos("Bell", 6, 12, 2.5, 2, 55, 1, "Algunos la llaman \"la pequeña\", pero ¡de pequeña no tiene nada! Con sus 12 m², es el espacio perfecto para una pareja. Si sois dos, esta es, sin duda, la mejor elección.(*) No incluye refrigerador eléctrico.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("Bell", 6, 12, 2.5, 2, 50, 0, "Algunos la llaman \"la pequeña\", pero ¡de pequeña no tiene nada! Con sus 12 m², es el espacio perfecto para una pareja. Si sois dos, esta es, sin duda, la mejor elección.(*) No incluye refrigerador eléctrico.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("BellXL", 6, 20, 3, 4, 55, 1, "Una tienda muy polivalente que se adapta a las necesidades de cada grupo y que puede alojar hasta 4 personas en 2 camas dobles. Una opción a tener en cuenta por familias con niños, pequeños grupos de amigos o por parejas que necesitan un extra de espacio.(*) No incluye frigorífico eléctrico.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("BellXL", 6, 20, 3, 4, 50, 0, "Una tienda muy polivalente que se adapta a las necesidades de cada grupo y que puede alojar hasta 4 personas en 2 camas dobles. Una opción a tener en cuenta por familias con niños, pequeños grupos de amigos o por parejas que necesitan un extra de espacio.(*) No incluye frigorífico eléctrico.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("Familia Anza", 6, 21, 5, 4, 65, 1, "Si buscas una tienda con baño privado, esta es tu tienda. Anza aúna el diseño de la tienda Lamu con el extra de confort que ofrece su módulo de baño integrado compuesto por ducha, lavabo e inodoro.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("Familia Anza", 6, 21, 5, 4, 60, 0, "Si buscas una tienda con baño privado, esta es tu tienda. Anza aúna el diseño de la tienda Lamu con el extra de confort que ofrece su módulo de baño integrado compuesto por ducha, lavabo e inodoro.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("Bali", 6, 35, 4, 5, 75, 1, "La Bali es una tienda espaciosa y muy luminosa, con capacidad para 5 personas repartidas en una litera doble (con camas de 135cm) y sofá cama modular.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("Bali", 6, 35, 4, 5, 70, 0, "La Bali es una tienda espaciosa y muy luminosa, con capacidad para 5 personas repartidas en una litera doble (con camas de 135cm) y sofá cama modular.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("Java", 6, 17.5, 5, 4, 65, 1, "La Java es una tienda espaciosa y muy luminosa, con capacidad para 4 personas repartidas en una litera doble (con camas de 135cm) y sofá cama modular.", "file:src/imagenes/bell.jpg");
        conexionJava.insertartDatosApartamentos("Java", 6, 17.5, 5, 4, 60, 0, "La Java es una tienda espaciosa y muy luminosa, con capacidad para 4 personas repartidas en una litera doble (con camas de 135cm) y sofá cama modular.", "file:src/imagenes/bell.jpg");
    }
    
    //Leer datos de apartamentos 
    public static List<Apartamento> leerArchivo() {
        List<Apartamento> apartamentos = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(URL_WITH_DB, USER, PASSWORD)) {
            String query = "SELECT * FROM Alojamiento";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Apartamento apt = new Apartamento(
                        rs.getInt("id"),                   // id
                        rs.getString("Nombre"),            // nombreApartamento
                        rs.getInt("Stock"),                // stock
                        rs.getDouble("Espacio"),           // espacio
                        rs.getDouble("Altura"),            // altura
                        rs.getInt("Persona"),              // personas
                        rs.getDouble("Precio"),            // precio
                        rs.getInt("Desayuno"),             // desayuno
                        rs.getString("Descripcion"),       // descripcion
                        rs.getString("Imagen")             // imagen
                );
                apartamentos.add(apt);
            }
            
        } catch (Exception e) {
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
        public int getIdapartamento () {
            return id;
        }
        public int getPersonas() {
            return personas;
        }
        public double getPrecio() {
            return precio;
        }
        public double getEspacio() {
            return espacio;
        }
        public int getStock() {
            return stock;
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
