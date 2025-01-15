import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PerfilUser extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CampingTipi?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private int idUsuario;
    private String correo;
    private String nombreApellido;
    private String telefono;
    private String fechaNacimiento;
    private String passwordUser;

    String Tinos = "file:src/fonts/Tinos/Tinos-Regular.ttf";
    String GideonRoman = "file:src/fonts/Gideon_Roman/GideonRoman-Regular.ttf";

    private Label lblBienvenido;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Mi Cuenta");
        BorderPane root = new BorderPane(); // Layout principal

        // Encabezado
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        String imageUrl = getClass().getResource("/imagenes/perfilFondo.png").toExternalForm();
        header.setStyle("-fx-background-image: url('" + imageUrl + "'); " +
                "-fx-background-size: cover;");
        header.setPrefHeight(300);

        header.setPadding(new Insets(20));

        lblBienvenido = new Label("Bienvenido " + nombreApellido);
        lblBienvenido.setFont(Font.loadFont(Tinos, 64));
        lblBienvenido.setStyle("-fx-text-fill: white;" + "-fx-font-weight:700");
        Label lblMiCuenta = new Label("Mi Cuenta");
        lblMiCuenta.setFont(Font.loadFont(Tinos, 120));

        lblMiCuenta.setStyle("-fx-text-fill: white; -fx-font-weight:700; ");

        header.getChildren().addAll(lblBienvenido, lblMiCuenta);

        Label lblTitulo = new Label("Datos Personales");
        lblTitulo.setFont(Font.loadFont(Tinos, 44));

        // datos de usuarios
        Label lblNombre = new Label("Nombre y Apellido:");
        lblNombre.setFont(Font.loadFont(Tinos, 22));
        lblNombre.setPrefWidth(260);
        TextField respuestaNombre = new TextField(nombreApellido);
        respuestaNombre.setFont(Font.loadFont(Tinos, 22));
        respuestaNombre.setEditable(false);
        HBox nombre = new HBox(10, lblNombre, respuestaNombre);

        Label lblFechaNacimiento = new Label("Fecha de nacimiento:");
        lblFechaNacimiento.setFont(Font.loadFont(Tinos, 22));
        lblFechaNacimiento.setPrefWidth(260);
        TextField respuestaFecha = new TextField(fechaNacimiento);
        respuestaFecha.setFont(Font.loadFont(Tinos, 22));
        respuestaFecha.setEditable(false);
        HBox fechaUsuario = new HBox(10, lblFechaNacimiento, respuestaFecha);

        Label lblCorreo = new Label("Correo eletronico:");
        lblCorreo.setFont(Font.loadFont(Tinos, 22));
        lblCorreo.setPrefWidth(260);
        TextField respuestaCorreo = new TextField(correo);
        respuestaCorreo.setFont(Font.loadFont(Tinos, 22));
        respuestaCorreo.setEditable(false);
        HBox correoUsuario = new HBox(10, lblCorreo, respuestaCorreo);

        Label lblTelefono = new Label("Telefono:");
        lblTelefono.setFont(Font.loadFont(Tinos, 22));
        lblTelefono.setPrefWidth(260);
        TextField respuestaTelefono = new TextField(telefono);
        respuestaTelefono.setFont(Font.loadFont(Tinos, 22));
        respuestaTelefono.setEditable(false);
        HBox telefonoUsuario = new HBox(10, lblTelefono, respuestaTelefono);

        VBox datosUsuario = new VBox(10, nombre, fechaUsuario, correoUsuario, telefonoUsuario);

        // botones
        Button modificarDatos = new Button("Modificar Datos");
        modificarDatos.setFont(Font.loadFont(Tinos, 18));
        modificarDatos.setPrefWidth(314);
        modificarDatos.setPrefHeight(41);
        modificarDatos.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;");
        Button confirmarCambios = new Button("Confirmar");
        confirmarCambios.setStyle(
            "-fx-background-color: black;" +
                    "-fx-text-fill: white;" +
                    "-fx-border-radius: 5;" +
                    "-fx-background-radius: 5;");

        confirmarCambios.setVisible(false);
        confirmarCambios.setFont(Font.loadFont(Tinos, 18));
        confirmarCambios.setPrefWidth(314);
        confirmarCambios.setPrefHeight(41);
        modificarDatos.setOnAction(e -> {
            habilitarEdicion(true, 
                respuestaNombre, 
                respuestaFecha, 
                respuestaCorreo, 
                respuestaTelefono);
            modificarDatos.setVisible(false);
            confirmarCambios.setVisible(true);
        });
          
        confirmarCambios.setOnAction(e -> {
            // Guardar los valores editados
            nombreApellido = respuestaNombre.getText();
            fechaNacimiento = respuestaFecha.getText();
            correo = respuestaCorreo.getText();
            telefono = respuestaTelefono.getText();
    
            // Actualizar mensaje de bienvenida
            lblBienvenido.setText("Bienvenido " + nombreApellido);
    
            // Deshabilitar edición
            habilitarEdicion(false, respuestaNombre, respuestaFecha, respuestaCorreo, respuestaTelefono);
            confirmarCambios.setVisible(false);
            modificarDatos.setVisible(true);
        });
        
        Button cambioPassword = new Button("Cambiar Contraseña");
        cambioPassword.setFont(Font.loadFont(Tinos, 18));
        cambioPassword.setPrefWidth(314);
        cambioPassword.setPrefHeight(41);
        cambioPassword.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;");

        VBox izquierda = new VBox(20, lblTitulo, datosUsuario, modificarDatos,confirmarCambios, cambioPassword);

        Label lblReserva = new Label("Tu Reservas");
        lblReserva.setFont(Font.loadFont(Tinos, 44));

        // cuadro de reserva

        ImageView image = new ImageView("/iconos/reservaIconCmap.png");
        image.setFitWidth(100);
        image.setFitHeight(100);

        Label fechaReserva = new Label("00/00/0000 - 00/00/2000");
        fechaReserva.setFont(Font.loadFont(Tinos, 22));
        Label idReserva = new Label("Nº" + " 00000");
        idReserva.setFont(Font.loadFont(Tinos, 22));
        Label tipoCamping = new Label("Bell");
        tipoCamping.setFont(Font.loadFont(Tinos, 22));
        VBox datosReserva = new VBox(5, fechaReserva, idReserva, tipoCamping);

        Button pdf = new Button("Ver Detalle");
        pdf.setFont(Font.loadFont(Tinos, 16));
        pdf.setPrefWidth(100);
        pdf.setPrefHeight(41);
        pdf.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;");

        HBox reserva = new HBox(10, image, datosReserva, pdf);
        reserva.setStyle(
                "-fx-border-radius: 5;" +
                        "-fx-padding: 16px 32px" +
                        "-fx-border: 1px solid black");
        reserva.setAlignment(Pos.CENTER);

        Label comentario = new Label("*Si desea cancelar la reserva, pon contacto con nosotros.");
        comentario.setFont(Font.loadFont(Tinos, 16));
        VBox derecha = new VBox(34, lblReserva, reserva, comentario);
        HBox contenidoTexto = new HBox(64, izquierda, derecha);
        contenidoTexto.setAlignment(Pos.CENTER);
        VBox contenidoPagina = new VBox(16, header, contenidoTexto);
        contenidoPagina.setAlignment(Pos.CENTER);
        root.setCenter(contenidoPagina);
        Scene scene = new Scene(root, 1440, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void habilitarEdicion(boolean habilitar, TextField respuestaNombre, TextField respuestaFecha, TextField respuestaCorreo, TextField respuestaTelefono) {
        respuestaNombre.setEditable(habilitar);
        respuestaFecha.setEditable(habilitar);
        respuestaCorreo.setEditable(habilitar);
        respuestaTelefono.setEditable(habilitar);
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setidUsuario(int id) {
        mostrarInformacionUser(id);
        this.idUsuario = id;

    }

    private void mostrarInformacionUser(int id) {
        String query = "SELECT Usuario, NombreApellido, Telefono, FechaNacimiento, Password FROM Usuario WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    correo = rs.getString("Usuario");
                    nombreApellido = rs.getString("NombreApellido");
                    telefono = rs.getString("Telefono");
                    fechaNacimiento = rs.getString("FechaNacimiento");
                    passwordUser = rs.getString("Password");

                    // // // Actualizar campos en la interfaz
                    // txtNombre.setText(nombreApellido);
                    // txtFecha.setText(fechaNacimiento);
                    // txtCorreo.setText(correo);
                    // txtTelefono.setText(telefono);
                } else {
                    System.out.println("No se encontró información para el usuario con ID: " + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args); // Iniciar aplicación JavaFX
    }
}
