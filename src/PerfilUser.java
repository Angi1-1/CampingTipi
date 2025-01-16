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
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Desktop;

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
    private String fechaEntrada;
    private String fechaSalida;
    private int numeroReserva;
    private String nombreAlojamiento;
    private byte[] reservaPdf;

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
        cambioPassword.setOnAction(e -> mostrarVentanaCambioPassword());
        VBox izquierda = new VBox(20, lblTitulo, datosUsuario, modificarDatos, confirmarCambios, cambioPassword);

        Label lblReserva = new Label("Tu Reservas");
        lblReserva.setFont(Font.loadFont(Tinos, 44));

        // cuadro de reserva

        ImageView image = new ImageView("/iconos/reservaIconCmap.png");
        image.setFitWidth(100);
        image.setFitHeight(100);

        Label fechaReserva = new Label(fechaEntrada + " - " + fechaSalida);
        fechaReserva.setFont(Font.loadFont(Tinos, 22));
        Label idReserva = new Label("Nº" + numeroReserva);
        idReserva.setFont(Font.loadFont(Tinos, 22));
        Label tipoCamping = new Label(nombreAlojamiento);
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
        pdf.setOnAction(e -> {
            if (reservaPdf != null) {
                mostrarPDF(reservaPdf); // Llama al método con el byte[] del PDF
            } else {
                System.out.println("No hay datos PDF disponibles.");
            }
        });
        HBox reserva = new HBox(10, image, datosReserva, pdf);
        reserva.setStyle(
                "-fx-border-radius: 5;" +
                        "-fx-padding: 16px 32px" +
                        "-fx-border: 1px solid black");
        reserva.setAlignment(Pos.CENTER);

        Label comentario = new Label("*Si desea cancelar la reserva, pon contacto con nosotros.");
        comentario.setFont(Font.loadFont(Tinos, 16));
        VBox derecha = new VBox(34, lblReserva, reserva, comentario);
        izquierda.setAlignment(Pos.CENTER);
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
        // Habilitar o deshabilitar la edición en los campos
        respuestaNombre.setEditable(habilitar);
        respuestaFecha.setEditable(habilitar);
        respuestaCorreo.setEditable(habilitar);
        respuestaTelefono.setEditable(habilitar);

        // Solo actualiza en la base de datos si la edición está deshabilitada
        if (!habilitar) {
            String query = "UPDATE usuario SET NombreApellido = ?, FechaNacimiento = ?, Usuario = ?, Telefono = ? WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                    PreparedStatement pstmt = conn.prepareStatement(query)) {
                // Configurar los valores en el PreparedStatement
                pstmt.setString(1, respuestaNombre.getText());
                pstmt.setString(2, respuestaFecha.getText());
                pstmt.setString(3, respuestaCorreo.getText());
                pstmt.setString(4, respuestaTelefono.getText());
                pstmt.setInt(5, idUsuario); // ID del usuario actual
                // Ejecutar la actualización
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Datos actualizados correctamente.");
                } else {
                    System.out.println("No se pudo actualizar los datos.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Error al actualizar los datos en la base de datos.");
            }
        }
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setidUsuario(int id) {
        mostrarInformacionUser(id);
        this.idUsuario = id;

    }

    private void mostrarInformacionUser(int id) {
        String query = "SELECT reserva.*, usuario.*, alojamiento.Nombre AS AlojamientoNombre \n" + //
                "FROM reserva \n" + //
                "JOIN usuario ON reserva.UsuarioId = usuario.id \n" + //
                "JOIN alojamiento ON reserva.AlojamientoId = alojamiento.id \n" + //
                "WHERE reserva.id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    correo = rs.getString("Usuario");
                    nombreApellido = rs.getString("NombreApellido");
                    telefono = rs.getString("Telefono");
                    fechaNacimiento = rs.getString("FechaNacimiento");

                    fechaEntrada = rs.getString("FechaLlegada");
                    fechaSalida = rs.getString("FechaSalida");
                    numeroReserva = rs.getInt("id");
                    nombreAlojamiento = rs.getString("AlojamientoNombre");
                    reservaPdf = rs.getBytes("PDF");

                } else {
                    System.out.println("No se encontró información para el usuario con ID: " + id);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarPDF(byte[] pdfBytes) {
        if (pdfBytes == null || pdfBytes.length == 0) {
            System.out.println("No se encontró ningún PDF en la base de datos.");
            return;
        }

        try {
            // Crear un archivo temporal para almacenar el PDF
            File tempFile = File.createTempFile("reserva_", ".pdf");

            // Escribir los bytes en el archivo
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(pdfBytes);
            }

            // Abrir el PDF con el visor predeterminado del sistema
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(tempFile);
            } else {
                System.out.println("No se puede abrir el PDF automáticamente en este sistema.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error al mostrar el PDF.");
        }
    }

    private void mostrarVentanaCambioPassword() {
        // Crear el nuevo Stage
        Stage cambioPasswordStage = new Stage();
        // Layout principal
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Titulo
        Label titulo = new Label("Cambiar Contraseña");
        titulo.setFont(Font.loadFont(Tinos, 44));

        Label titulosub = new Label("Introduce los datos para realizar cambio:");
        titulosub.setFont(Font.loadFont(Tinos, 22));
        // Etiquetas y campos de texto

        // Label lblPasswordActual = new Label("Contraseña actual:");
        // lblPasswordActual.setFont(Font.loadFont(Tinos, 22));
        // lblPasswordActual.setPrefWidth(240);
        // lblPasswordActual.setPrefHeight(45);
        // PasswordField txtPasswordActual = new PasswordField();
        // txtPasswordActual.setPromptText("Ingresa tu contraseña actual");
        // txtPasswordActual.setFont(Font.loadFont(Tinos, 22));

        // HBox antiguo = new HBox(lblPasswordActual, txtPasswordActual);

        Label lblPasswordNueva = new Label("Nueva contraseña :");
        lblPasswordNueva.setFont(Font.loadFont(Tinos, 22));
        lblPasswordNueva.setPrefWidth(240);
        lblPasswordNueva.setPrefHeight(55);
        PasswordField txtPasswordNueva = new PasswordField();
        txtPasswordNueva.setPromptText("Ingresa tu nueva contraseña");
        txtPasswordNueva.setFont(Font.loadFont(Tinos, 22));

        HBox nueva = new HBox(lblPasswordNueva, txtPasswordNueva);

        Label lblConfirmarPassword = new Label("Confirmar Contraseña:");
        lblConfirmarPassword.setFont(Font.loadFont(Tinos, 22));
        lblConfirmarPassword.setPrefWidth(240);
        lblConfirmarPassword.setPrefHeight(55);
        PasswordField txtConfirmarPassword = new PasswordField();
        txtConfirmarPassword.setPromptText("Confirma tu nueva contraseña");
        txtConfirmarPassword.setFont(Font.loadFont(Tinos, 22));

        HBox nuevaConfimar = new HBox(lblConfirmarPassword, txtConfirmarPassword);

        Label mensajerErrro = new Label();
        mensajerErrro.setFont(Font.loadFont(Tinos, 16));
        // Botón para guardar los cambios
        Button btnGuardar = new Button("Guardar");
        btnGuardar.setFont(Font.loadFont(Tinos, 22));
        btnGuardar.setPrefHeight(57);
        btnGuardar.setPrefWidth(250);
        btnGuardar.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;");
        Button btncerrar = new Button("Cerrar");
        btncerrar.setFont(Font.loadFont(Tinos, 22));
        btncerrar.setPrefHeight(57);
        btncerrar.setPrefWidth(250);
        btncerrar.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;");
        btnGuardar.setOnAction(e -> {
            // String actualPassword = txtPasswordActual.getText();
            String nuevaPassword = txtPasswordNueva.getText();
            String confirmarPassword = txtConfirmarPassword.getText();

            // Validar las contraseñas
            if (!nuevaPassword.equals(confirmarPassword)) {
                mensajerErrro.setText("Error, La nueva contraseña y su confirmación no coinciden.");
                mensajerErrro.setStyle(" -fx-wrap-text: true; -fx-text-fill: red;");
            } else if (nuevaPassword.isEmpty()) {
                mensajerErrro.setText("Error, La nueva contraseña no puede estar vacía.");
                mensajerErrro.setStyle(" -fx-wrap-text: true; -fx-text-fill: red;");
            } else {
                // Actualizar la contraseña (puedes llamar a un método para actualizar en la
                // base de datos)
                actualizarPassword(nuevaPassword);
                mensajerErrro.setText("Éxito, La contraseña se ha actualizado correctamente.");
                mensajerErrro.setStyle(" -fx-wrap-text: true; -fx-text-fill: green;");
            }
        });
        btnGuardar.setOnAction(e -> {
            cambioPasswordStage.close();
        });

        HBox botones = new HBox(5, btnGuardar, btncerrar);
        // Agregar los controles al layout
        layout.getChildren().addAll(titulo, titulosub, nueva, nuevaConfimar, mensajerErrro, botones);

        // Configurar y mostrar la escena
        Scene scene = new Scene(layout, 500, 350);
        cambioPasswordStage.setScene(scene);
        cambioPasswordStage.show();
    }

    private void actualizarPassword(String nuevaPassword) {
        String hashedPassword = hashPassword(nuevaPassword); // Usa tu método para cifrar
        String query = "UPDATE usuario SET Password = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, hashedPassword);
            pstmt.setInt(2, idUsuario); // ID del usuario actual
            pstmt.executeUpdate();
            passwordUser = hashedPassword; // Actualiza la variable local
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
