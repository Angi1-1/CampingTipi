import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;

public class conexionJava {
    private static final String URL = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
    private static final String URL_WITH_DB = "jdbc:mysql://localhost:3306/CampingTipi?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    

    //Metoo para inicializar la base de datos 
    public static void inicializarBaseDeDatos(){
        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar al servidor MySQL para crear la base de datos
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a MySQL con XAMPP");

            // Crear la base de datos si no existe
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE DATABASE IF NOT EXISTS CampingTipi");
            System.out.println("Base de datos 'CampingTipi' creada o ya existe.");
            conn.close();

            // Conectar a la base de datos `CampingTipi`
            conn = DriverManager.getConnection(URL_WITH_DB, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos 'CampingTipi'.");

            // Crear la tabla `Usuario` si no existe
            stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Usuario (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "DNI VARCHAR(100) NOT NULL , " + 
                    "Usuario VARCHAR(100) NOT NULL, " +
                    "Password VARCHAR(100) NOT NULL, " +
                    "NombreApellido VARCHAR(100) NOT NULL," +
                    "Telefono VARCHAR(100) NOT NULL, " + 
                    "FechaNacimiento VARCHAR(200) NOT NULL, "+
                    "Calle VARCHAR(200) NOT NULL ," +
                    "Ciudad VARCHAR(200) NOT NULL , "+
                    "CodigoPostal VARCHAR(200) NOT NULL" 
                    +")");
            
            stmt.execute("CREATE TABLE IF NOT EXISTS Alojamiento (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre VARCHAR(100) NOT NULL , " + 
                    "Stock INT NOT NULL, " +
                    "Espacio DOUBLE NOT NULL, " +
                    "Altura DOUBLE NOT NULL," +
                    "Persona INT NOT NULL, " + 
                    "Precio DOUBLE NOT NULL, "+
                    "Desayuno int NOT NULL ," +
                    "Descripcion VARCHAR(500) NOT NULL , "+
                    "imagen VARCHAR(500) NOT NULL" 
                    +")");

            stmt.execute("CREATE TABLE IF NOT EXISTS Reserva (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "UsuarioId int not null , " + 
                    "AlojamientoId INT NOT NULL, " +
                    "FechaLlegada VARCHAR(200) NOT NULL, " +
                    "FechaSalida VARCHAR(200) NOT NULL," +
                    "Precio DECIMAL(10,2) NOT NULL, "+
                    "Pasajeros VARCHAR(500) NOT NULL , "+
                    "Matriculas VARCHAR(500) NOT NULL, " +
                    "Nota VARCHAR(500) NOT NULL, " +
                    "PDF LONGBLOB, " +
                    "FOREIGN KEY (UsuarioId) REFERENCES usuario(id) ON DELETE CASCADE, " +
                    "FOREIGN KEY (AlojamientoId) REFERENCES alojamiento(id) ON DELETE CASCADE" +
                    ")");
            // Cerrar la conexión
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    public static void insertartDatosApartamentos(String nombre, int stock, double espacio, double altura, int personas, double precio, int desayuno, String descripcion, String imagen){
        try (Connection conn = DriverManager.getConnection(URL_WITH_DB, USER, PASSWORD)) {
            String checkQuerry = "SELECT COUNT(*) FROM Alojamiento WHERE Nombre = ? AND Desayuno = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuerry);
            checkStatement.setString(1, nombre);
            checkStatement.setInt(2, desayuno);
            ResultSet rs = checkStatement.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return;
            }
            String query = "INSERT INTO Alojamiento (Nombre, Stock, Espacio, Altura, Persona, Precio, Desayuno, Descripcion, Imagen) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, nombre);       // Nombre
            pstmt.setInt(2, stock);          // Stock
            pstmt.setDouble(3, espacio);     // Espacio
            pstmt.setDouble(4, altura);      // Altura
            pstmt.setInt(5, personas);       // Personas
            pstmt.setDouble(6, precio);      // Precio
            pstmt.setInt(7, desayuno);       // Desayuno
            pstmt.setString(8, descripcion); // Descripción
            pstmt.setString(9, imagen);      // Imagen
            // Ejecutar la consulta
            pstmt.executeUpdate();
            System.out.println("Alojamiento insertado correctamente: " + nombre);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertarUsuario (String dni, String usuario, String password, String NombreApellido, String Telefono, String fechaNacimiento, String calle, String ciudad, String CodigoPostal) {
        try(Connection conn = DriverManager.getConnection(URL_WITH_DB, USER, PASSWORD)){
            String checkQuerry = "SELECT COUNT(*) FROM usuario WHERE Usuario = ? AND DNI = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuerry);
            checkStatement.setString(1, usuario);
            checkStatement.setString(2, dni);

            ResultSet rs = checkStatement.executeQuery();
            if(rs.next() && rs.getInt(1)>0){
                return;
            }
            else {
                String query = "INSERT INTO usuario (DNI, Usuario, Password, NombreApellido, Telefono, FechaNacimiento, Calle, Ciudad, CodigoPostal) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(query);
                pstmt.setString(1, dni);    
                pstmt.setString(2, usuario);     
                pstmt.setString(3, password); 
                pstmt.setString(4, NombreApellido);    
                pstmt.setString(5, Telefono);       
                pstmt.setString(6, fechaNacimiento);
                pstmt.setString(7, calle);      
                pstmt.setString(8, ciudad); 
                pstmt.setString(9, CodigoPostal);      
                // Ejecutar la consulta
                pstmt.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
