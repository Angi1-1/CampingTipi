import java.util.List;

public class Reserva {
    private int id;
    private List<App.DatosPasajero> pasajeros;
    private List<String> matriculas;
    private int apartamento;
    private String nota;

    public Reserva(int id, List<App.DatosPasajero> pasajeros, List<String> matriculas, int apartamento,String nota ){
        this.id = id;
        this.pasajeros = pasajeros;
        this.matriculas = matriculas;
        this.apartamento = apartamento;
        this.nota = nota;
    }

      // Getters y setters
      public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<App.DatosPasajero> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<App.DatosPasajero> pasajeros) {
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

    //  public static class Pasajero {
    //     private String nombre;
    //     private String documento;
    //     private String fechaNacimiento;
    //     private String sexo;

    //     public Pasajero(String nombre, String documento, String fechaNacimiento, String sexo) {
    //         this.nombre = nombre;
    //         this.documento = documento;
    //         this.fechaNacimiento = fechaNacimiento;
    //         this.sexo = sexo;
    //     }

    //     // Getters y setters
    //     public String getNombre() {
    //         return nombre;
    //     }

    //     public void setNombre(String nombre) {
    //         this.nombre = nombre;
    //     }

    //     public String getDocumento() {
    //         return documento;
    //     }

    //     public void setDocumento(String documento) {
    //         this.documento = documento;
    //     }

    //     public String getFechaNacimiento() {
    //         return fechaNacimiento;
    //     }

    //     public void setFechaNacimiento(String fechaNacimiento) {
    //         this.fechaNacimiento = fechaNacimiento;
    //     }

    //     public String getSexo() {
    //         return sexo;
    //     }

    //     public void setSexo(String sexo) {
    //         this.sexo = sexo;
    //     }
    // }
}
