import java.io.IOException;
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
        conexionJava.insertarUsuario("12345678A", "admin@gmail.com", "1234", "admin", "600600600", "12/07/2001", "calle tajo", "Madrid", "28019");
        launch(args);
    }}
