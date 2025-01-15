import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegistrarCuentas extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CampingTipi?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    String Tinos = "file:src/fonts/Tinos/Tinos-Regular.ttf";
    String GideonRoman = "file:src/fonts/Gideon_Roman/GideonRoman-Regular.ttf";

    @Override
    public void start(Stage primaryStage) {
        // Llama al método mostrarLogin para construir la interfaz
        mostrarRegistro(primaryStage);
    }

    public void mostrarRegistro(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));
            Parent header = loader.load();
            BorderPane root = new BorderPane();
            root.setTop(header);
            Label mensaje = new Label();
            mensaje.setFont(Font.loadFont(GideonRoman, 22));
            // Titulo principal
            Label lblTitle = new Label("MI CUENTA");
            lblTitle.setFont(Font.loadFont(Tinos, 64));
            lblTitle.setMaxWidth(Double.MAX_VALUE);
            lblTitle.setAlignment(Pos.TOP_LEFT);
            // Línea horizontal (Separator)
            Separator hr = new Separator();
            hr.setStyle("-fx-background-color: black;");
            hr.setPrefHeight(2); // Grosor de la línea
            hr.setMaxWidth(Double.MAX_VALUE);
            // Subtítulo
            Label lblSubtitulo = new Label(
                    "Al crear una cuenta, acepta los Términos y Condiciones Generales de Uso y da su consentimiento al tratamiento de sus datos, de conformidad con la Política de Confidencialidad de Camping Tipi.");
            lblSubtitulo.setFont(Font.loadFont(GideonRoman, 22));
            lblSubtitulo.setWrapText(true); // Habilita el ajuste del texto
            // cuenta
            Label lblEmail = new Label("Email");
            lblEmail.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaEmail = new TextField();
            respuestaEmail.setFont(Font.loadFont(GideonRoman, 22));
            respuestaEmail.setPrefWidth(530);
            respuestaEmail.setPrefHeight(53);
            // contraseña
            Label lblPassword = new Label("Contraseña");
            lblPassword.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaPassword = new TextField();
            respuestaPassword.setFont(Font.loadFont(GideonRoman, 22));
            respuestaPassword.setPrefWidth(530);
            respuestaPassword.setPrefHeight(53);
            // Datos personales
            Label lblDatosPersonales = new Label("DATOS PERSONALES");
            lblDatosPersonales.setFont(Font.loadFont(Tinos, 32));
            // Nombre y Apellido
            Label lblNombre = new Label("Nombre y Apellido");
            lblNombre.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaNombre = new TextField();
            respuestaNombre.setFont(Font.loadFont(GideonRoman, 22));
            respuestaNombre.setPrefWidth(530);
            respuestaNombre.setPrefHeight(53);
            // Telefono
            Label lblTelefono = new Label("Teléfono");
            lblTelefono.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaTelefono = new TextField();
            respuestaTelefono.setFont(Font.loadFont(GideonRoman, 22));
            respuestaTelefono.setPrefWidth(530);
            respuestaTelefono.setPrefHeight(53);
            // Fecha de Nacimiento
            Label lblFechaNacimiento = new Label("Fecha de Nacimiento");
            lblFechaNacimiento.setFont(Font.loadFont(GideonRoman, 22));
            DatePicker respuestaFechaNacimiento = new DatePicker();
            respuestaFechaNacimiento.setStyle("-fx-font-size: 22px;");
            respuestaFechaNacimiento.setPrefWidth(530);
            respuestaFechaNacimiento.setPrefHeight(53);
            // Documento de identidad
            Label lblDni = new Label("Documento de identidad");
            lblDni.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaDNI = new TextField();
            respuestaDNI.setFont(Font.loadFont(GideonRoman, 22));
            respuestaDNI.setPrefWidth(530);
            respuestaDNI.setPrefHeight(53);

            // Contenido de derecha
            // Datos personales
            Label lblFactura = new Label("DATOS PERSONALES");
            lblFactura.setFont(Font.loadFont(Tinos, 32));
            // Ubicación
            Label lblUbicacion = new Label("Ubicación");
            lblUbicacion.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaUbicacion = new TextField();
            respuestaUbicacion.setFont(Font.loadFont(GideonRoman, 22));
            respuestaUbicacion.setPrefWidth(530);
            respuestaUbicacion.setPrefHeight(53);
            // Ciudad
            Label lblCiudad = new Label("Ciudad");
            lblCiudad.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaCiudad = new TextField();
            respuestaCiudad.setFont(Font.loadFont(GideonRoman, 22));
            respuestaCiudad.setPrefWidth(530);
            respuestaCiudad.setPrefHeight(53);
            // Ciudad
            Label lblPostal = new Label("Código Postal");
            lblPostal.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaPostal = new TextField();
            respuestaPostal.setFont(Font.loadFont(GideonRoman, 22));
            respuestaPostal.setPrefWidth(530);
            respuestaPostal.setPrefHeight(53);

            VBox izquierda = new VBox(lblEmail, respuestaEmail, lblPassword, respuestaPassword, lblDatosPersonales,
                    lblNombre, respuestaNombre, lblTelefono, respuestaTelefono, lblFechaNacimiento,
                    respuestaFechaNacimiento, lblDni, respuestaDNI);
            VBox derecha = new VBox(lblFactura, lblUbicacion, respuestaUbicacion, lblCiudad, respuestaCiudad, lblPostal,
                    respuestaPostal);
            Region separador = new Region();
            HBox.setHgrow(separador, Priority.ALWAYS);

            // CheckBox
            CheckBox checkBox = new CheckBox(
                    "Acepto los Condiciones Generales de Venta y doy mi consentimiento al tratamiento de mis datos, de conformidad con la Política de Confidencialidad de Camping Tipi.");
            checkBox.setFont(Font.loadFont(GideonRoman, 16));
            checkBox.setSelected(false);

            Button login = new Button("ENTRAR");
            login.setFont(Font.loadFont(GideonRoman, 22));
            login.setPrefWidth(289);
            login.setPrefHeight(50);
            login.setStyle(
                    "-fx-background-color: black;" +
                            "-fx-text-fill: white;" +
                            "-fx-border-radius: 5;" +
                            "-fx-background-radius: 5;");

            login.setOnAction(event -> {
                String dni = respuestaDNI.getText().trim();
                String email = respuestaEmail.getText().trim();
                String password = respuestaPassword.getText().trim();
                String nombreApellido = respuestaNombre.getText().trim();
                String telefono = respuestaTelefono.getText().trim();
                String calle = respuestaUbicacion.getText().trim();
                String ciudad = respuestaCiudad.getText().trim();
                String codigoPostal = respuestaPostal.getText().trim();
                LocalDate fechaNacimiento = respuestaFechaNacimiento.getValue();
                
                // Regla de formato
                if (dni.isEmpty() || email.isEmpty() || password.isEmpty() || nombreApellido.isEmpty() ||
                        telefono.isEmpty() || fechaNacimiento == null || calle.isEmpty() || ciudad.isEmpty()
                        || codigoPostal.isEmpty()) {
                    // Mensaje de error
                    mensaje.setText("Debes completar todos los campos.");
                    mensaje.setStyle(" -fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }
          
                if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    mensaje.setText("Por favor, introduce un correo electrónico válido.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }
                
                if (!nombreApellido.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) {
                    mensaje.setText("El nombre y apellido no pueden contener números.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }
                if (!ciudad.matches("^[a-zA-ZÁÉÍÓÚáéíóúñÑ\\s]+$")) {
                    mensaje.setText("La ciudad no puede contener números.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }
                if (!telefono.matches("^[0-9]{9,}$")) {
                    mensaje.setText("El teléfono debe contener al menos 9 dígitos y no puede tener letras.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }
                if (!codigoPostal.matches("^[0-9]+$")) {
                    mensaje.setText("El código postal solo puede contener números.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }

                // Validación de fecha de nacimiento no nula
                if (fechaNacimiento == null) {
                    mensaje.setText("Por favor, selecciona una fecha de nacimiento.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }
                // Validación de edad mínima (18 años)
                if (ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now()) < 18) {
                    mensaje.setText("Debes ser mayor de 18 años para registrarte.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }

                if (!checkBox.isSelected()) {
                    mensaje.setText("Debes aceptar las condiciones para continuar.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }

                String fechaNacimientoString = fechaNacimiento.toString();
                // Cifrar la contraseña
                String hashedPassword = hashPassword(password);
                if (hashedPassword == null) {
                    // Mensaje de error
                    System.err.println("Error");
                    return;
                }
                // Guardar los datos a base de datos
                try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "INSERT INTO usuario (DNI, Usuario, Password, NombreApellido, Telefono, FechaNacimiento, Calle, Ciudad, CodigoPostal) "
                            +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setString(1, dni);
                    pstmt.setString(2, email);
                    pstmt.setString(3, hashedPassword); // Guardar la contraseña cifrada
                    pstmt.setString(4, nombreApellido);
                    pstmt.setString(5, telefono);
                    pstmt.setString(6, fechaNacimientoString); // Convertir LocalDate a java.sql.Date
                    pstmt.setString(7, calle);
                    pstmt.setString(8, ciudad);
                    pstmt.setString(9, codigoPostal);

                    // Ejecutar la consulta
                    int rowsInserted = pstmt.executeUpdate();
                    if (rowsInserted > 0) {
                        mensaje.setText("Registro exitoso. ¡Bienvenido a Camping Tipi!");
                        mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: green;");
                        // Limpiar los campos después de guardar
                        respuestaDNI.clear();
                        respuestaEmail.clear();
                        respuestaPassword.clear();
                        respuestaNombre.clear();
                        respuestaTelefono.clear();
                        respuestaFechaNacimiento.setValue(null);
                        respuestaUbicacion.clear();
                        respuestaCiudad.clear();
                        respuestaPostal.clear();
                        checkBox.setSelected(false);
                        // Volver a pagina login y cierra
                        Stage stage = new Stage();
                        Login loginEntrar = new Login();
                        loginEntrar.start(stage);
                        primaryStage.close();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    mensaje.setText("Error al guardar los datos. Por favor, inténtalo nuevamente.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                }

            });

            HBox botonLoginContainer = new HBox(login);
            botonLoginContainer.setAlignment(Pos.CENTER); // Centra el botón dentro del HBox
            HBox contenido = new HBox(izquierda, separador, derecha);
            VBox form = new VBox(15, lblSubtitulo, contenido, checkBox, mensaje, botonLoginContainer);
            form.setAlignment(Pos.CENTER_LEFT);
            form.setPadding(new Insets(20));
            VBox conteP = new VBox(32, lblTitle, hr, form);
            conteP.setAlignment(Pos.TOP_CENTER);
            conteP.setPadding(new Insets(20));
            conteP.setStyle("-fx-background-color: #d9e3c7;");
            ScrollPane scrollPane = new ScrollPane(conteP);
            scrollPane.setFitToWidth(true);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Barra de desplazamiento vertical solo si
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // No mostrar barra horizontal
            root.setCenter(scrollPane);
            Scene scene = new Scene(root, 1440, 800);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo utiliza el algoritmo SHA-256 para genera un cifrado unidireccional de
    // una cadena de texto para proteger contraseña
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}