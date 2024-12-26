import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
public class Reserva {
    private int id;
    private List<Pasajero> pasajeros;
    private List<String> matriculas;
    private int apartamento;
    private String nota;
    private String fechaLlegada;
    private String fechaSalida;
    private double precio;
    private String nombreApartamento;
    private static final String URL_WITH_DB = "jdbc:mysql://localhost:3306/CampingTipi?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    public static void insertDatosReservas(int idUsuario, int AlojamientoId, String fechaLlegada, String fechaSalida, double precio,List<Pasajero> pasajeros, List<String> matriculas, String nota) {
         try (Connection conn = DriverManager.getConnection(URL_WITH_DB, USER, PASSWORD)) {

            //List<Pasajero> pasajeros, List<String> matriculas sea una string
              // Convertir las listas en cadenas delimitadas
              String pasajerosStr = pasajeros.stream()
            .map(p -> p.getNombre() + "-" + p.getDocumento()) // Convertir cada Pasajero en un String
            .reduce((p1, p2) -> p1 + "," + p2) // Concatenar con coma
            .orElse(""); // Si la lista está vacía, usar cadena vacía
            String matriculasStr = String.join(",", matriculas); // Convierte la lista de matrículas a una cadena


            String query = "INSERT INTO reserva (UsuarioId, AlojamientoId, FechaLlegada, FechaSalida, Precio, Pasajeros, Matriculas, Nota) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, idUsuario);     
            pstmt.setInt(2, AlojamientoId);         
            pstmt.setString(3, fechaLlegada);    
            pstmt.setString(4, fechaSalida);     
            pstmt.setDouble(5, precio);      
            pstmt.setString(6, pasajerosStr);    
            pstmt.setString(7, matriculasStr);     
            pstmt.setString(8, nota); 
            // Ejecutar la consulta
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Reserva(int id, List<Pasajero> pasajeros, List<String> matriculas, int apartamento, String nota, String fechaLlegada, String fechaSalida, double precio, String nombreApartamento) {
        this.id = id;
        this.pasajeros = pasajeros;
        this.matriculas = matriculas;
        this.apartamento = apartamento;
        this.nota = nota;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.precio = precio;
        this.nombreApartamento = nombreApartamento;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<Pasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    public List<String> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<String> matriculas) {
        this.matriculas = matriculas;
    }

    public int getApartamento() {
        return apartamento;
    }

    public void setApartamento(int apartamento) {
        this.apartamento = apartamento;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
    public String getFechaLlegada() {
        return fechaLlegada;
    }
    public String getFechaSalida() {
        return fechaSalida;
    }
    public double getPrecio(){
        return precio;
    }
   
    public String imprimirReservas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Numero de reserva: ").append(id).append("\n");
        sb.append("Su fecha de llegada y salida: ").append(fechaLlegada).append(" - ").append(fechaSalida).append("\n");
        sb.append("Numero de pasajeros: ").append(pasajeros.size()).append("\n");
        sb.append("Precio de la reserva: ").append(precio).append("€").append("\n");
        sb.append("Datos de los pasajeros reservados: ").append("\n");
        sb.append("Apartamento: ").append(nombreApartamento).append("\n");
    
        // Recorre los pasajeros y añade su número
        for (int i = 0; i < pasajeros.size(); i++) {
            sb.append("Pasajero ").append(i + 1).append(":\n")
              .append(pasajeros.get(i).imprimirDatos()) // Usa el método imprimirDatos() de Pasajero
              .append("\n");
        }
    
        // Añade las matrículas con el formato "Vehículo X: matrícula"
        if (matriculas != null && !matriculas.isEmpty()) {
            sb.append("Vehículos reservados: \n");
            for (int i = 0; i < matriculas.size(); i++) {
                sb.append("Vehículo ").append(i + 1).append(": ").append(matriculas.get(i)).append("\n");
            }
        } else {
            sb.append("No se han reservado vehículos.\n");
        }
    
        sb.append("Nota: ").append(nota).append("\n");
        return sb.toString();
    }
    
    
    public static class Pasajero {
        private String nombre;
        private String documento;
        private String fechaNacimiento; // Cambiado a LocalDate
        private String sexo;

        public Pasajero(String nombre, String documento, String fechaNacimiento, String sexo) {
            this.nombre = nombre;
            this.documento = documento;
            this.fechaNacimiento = fechaNacimiento;
            this.sexo = sexo;
        }

        // Getters y setters
        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getFechaNacimiento() {
            return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
        }

        public String getSexo() {
            return sexo;
        }

        public void setSexo(String sexo) {
            this.sexo = sexo;
        }


        
        public String imprimirDatos() {
            return "Nombre: " + nombre + "\n" +
                   "Documento: " + documento + "\n" +
                   "Fecha de Nacimiento: " + fechaNacimiento + "\n" +
                   "Sexo: " + sexo;
        }
        

    }
}
