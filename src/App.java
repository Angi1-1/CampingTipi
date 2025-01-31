import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar MainScene.fxml como encabezado
            Parent header = FXMLLoader.load(getClass().getResource("/MainScene.fxml"));

            // Cargar Home.fxml como contenido principal con ScrollPane
            Parent content = FXMLLoader.load(getClass().getResource("/Home.fxml"));

            // Crear un BorderPane y configurar las secciones
            BorderPane root = new BorderPane();
            root.setTop(header);    // Encabezado
            root.setCenter(content); // Contenido principal
            // Configurar la escena y mostrarla
            Scene scene = new Scene(root, 800, 600); // Cambia el tamaño según lo que necesites
            primaryStage.setTitle("Ejecución Header con Home");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Imprime el error
        }
    }


    public static void main(String[] args) {
        conexionJava.inicializarBaseDeDatos();
        datosApartamentos.datosApartamentosInsertar();
        //insertar datos de prueba
        conexionJava.insertarUsuario("12345678A", "admin@gmail.com", "03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4", "admin", "600600600", "12/07/2001", "calle tajo", "Madrid", "28019");
        
        List<Reserva.Pasajero> pasajeros =  List.of( // Lista de pasajeros
        new Reserva.Pasajero("Guoan Qi", "X0000000A", "1990-01-01", "Mujer"),
        new Reserva.Pasajero("Juan Perez", "Y1234561A", "1985-05-05", "Hombre")
        );
        List<String> matriculas = List.of("2425GMH");
        Reserva reserva = new Reserva(1, pasajeros, matriculas, 1, "Nota de prueba", "2025-08-12", "2025-08-16", 150.0, "Apartamento Bell");
        byte[] archivoPDF = DescargaPDF.generarPDFMysql("reserva.pdf",reserva);
        Reserva.insertDatosReservas(1, 1, "15/07/2025", "20/07/2025", 150.0, pasajeros, matriculas, "Nota de prueba", archivoPDF);

        launch(args);
    }}
