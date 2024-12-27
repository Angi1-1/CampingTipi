
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;


public class Login extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CampingTipi?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    String Tinos = "file:src/fonts/Tinos/Tinos-Regular.ttf";
    String GideonRoman = "file:src/fonts/Gideon_Roman/GideonRoman-Regular.ttf";

    @Override
    public void start(Stage primaryStage) {
        // Llama al método mostrarLogin para construir la interfaz
        mostrarLogin(primaryStage);
    }

    public void mostrarLogin(Stage primaryStage) {
        try { 
            BorderPane root = new BorderPane();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));

            Parent header = loader.load();
            root.setTop(header); 
            // Título principal
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
            Label lblSubtitulo = new Label("Inicia sesión con tu correo electrónico y contraseña para acceder a tu cuenta.");
            lblSubtitulo.setFont(Font.loadFont(GideonRoman, 22));
            lblSubtitulo.setStyle("-fx-wrap-text: true;");
            lblSubtitulo.setPrefWidth(530); // Define un ancho preferido mayor
            
            // Botón de "¿Sin cuenta?"
            Button sinCuenta = new Button("¿Sin cuenta?");
            sinCuenta.setFont(Font.loadFont(GideonRoman, 22));
            sinCuenta.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
            sinCuenta.setPrefWidth(530);
            sinCuenta.setAlignment(Pos.CENTER_RIGHT);
            sinCuenta.setOnAction(event -> {
                Stage stage = new Stage(); 
                RegistrarCuentas registrarCuentas = new RegistrarCuentas();
                registrarCuentas.start(stage);
                primaryStage.close();
            });

            // Campo de Email
            Label lblEmail = new Label("Email");
            lblEmail.setFont(Font.loadFont(GideonRoman, 22));
            TextField respuestaEmail = new TextField();
            respuestaEmail.setFont(Font.loadFont(GideonRoman, 22));
            respuestaEmail.setPrefWidth(400);
            respuestaEmail.setPrefHeight(40);

            // Campo de Contraseña
            Label lblPassword = new Label("Contraseña");
            lblPassword.setFont(Font.loadFont(GideonRoman, 22));
            PasswordField passwordField = new PasswordField();
            passwordField.setFont(Font.loadFont(GideonRoman, 22));
            passwordField.setPrefWidth(400);
            passwordField.setPrefHeight(40);

            // Botón de "¿Olvidó su contraseña?"
            Button sinPassword = new Button("¿Olvidó su contraseña?");
            sinPassword.setFont(Font.loadFont(GideonRoman, 22));
            sinPassword.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
            sinPassword.setAlignment(Pos.CENTER_RIGHT); // Alineación derecha
            sinPassword.setPrefWidth(530); // Asegurar el mismo ancho que el VBox principal
            Label mensaje = new Label();
            mensaje.setFont(Font.loadFont(GideonRoman, 22));
            mensaje.setPrefWidth(530);
            // Botón de Login
            Button login = new Button("ENTRAR");
            login.setFont(Font.loadFont(GideonRoman, 22));
            login.setPrefWidth(289);
            login.setPrefHeight(50);
            login.setStyle(
                "-fx-background-color: black;" +
                "-fx-text-fill: white;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;"
            );
            login.setOnAction(event -> {
                String email = respuestaEmail.getText().trim();
                String password = passwordField.getText().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    mensaje.setText("Debes completar todos los campos.");
                    mensaje.setStyle(" -fx-wrap-text: true; -fx-text-fill: red;");
                    return;
                }

                if (VerificarLogin(email, password)) {
                    mensaje.setText("Inicio de sesión correcto.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: green;");
                    //Si es correcto lleva a pagina personal
                } else {
                    mensaje.setText("Credenciales inválidas.");
                    mensaje.setStyle("-fx-wrap-text: true; -fx-text-fill: red;");
                }

            });
            HBox ButonLogin = new HBox(login);
            ButonLogin.setAlignment(Pos.CENTER); 
            ButonLogin.setPrefWidth(530); 
            

            
            // Formulario de Entrada
            VBox form = new VBox(15, lblSubtitulo, sinCuenta, lblEmail, respuestaEmail, lblPassword, passwordField, sinPassword,mensaje, ButonLogin);
            form.setAlignment(Pos.CENTER_LEFT);
            form.setPadding(new Insets(20));
            form.setMinWidth(530); // Ancho mínimo
            form.setPrefWidth(530); // Ancho preferido
            form.setMaxWidth(530); // Ancho máximo
            // Contenedor Principal
            VBox conteP = new VBox(32, lblTitle, hr, form);
            conteP.setAlignment(Pos.TOP_CENTER);
            conteP.setPadding(new Insets(20));
            conteP.setStyle("-fx-background-color: #d9e3c7;");

            // Contenedor Principal
           
            root.setCenter(conteP);

            // Configuración de la Escena
            Scene scene = new Scene(root, 1440, 800);
            primaryStage.setTitle("Mi Cuenta");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean VerificarLogin (String email, String password){
        String hashedPassword = hashPassword(password); // Método para cifrar la contraseña
        String query = "SELECT * FROM Usuario WHERE Usuario = ? AND Password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, email);
            pstmt.setString(2, hashedPassword);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Si existe al menos un registro, las credenciales son válidas
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Credenciales inválidas
    }

    // Método para cifrar la contraseña (SHA-256)
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