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
            Parent header = FXMLLoader.load(getClass().getResource("/MainScene.fxml")); //¡Es el header!

            Parent content = FXMLLoader.load(getClass().getResource("/Home.fxml"));//¡Es la home!

            BorderPane root = new BorderPane();
            root.setTop(header);
            root.setCenter(content);
            Scene scene = new Scene(root, 900, 900); 
            primaryStage.setTitle("Ejecución Header con Home");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        conexionJava.inicializarBaseDeDatos();
        datosApartamentos.datosApartamentosInsertar();
        //insertar datos de prueba
        conexionJava.insertarUsuario("12345678A", "admin@gmail.com", "1234", "admin", "600600600", "12/07/2001", "calle tajo", "Madrid", "28019");
        launch(args);
    }}
