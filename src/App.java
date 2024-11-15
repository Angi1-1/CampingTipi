import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public void start (Stage primaryStage){
        PaginaReserva paginaReserva = new PaginaReserva();
        paginaReserva.mostrarPaginaReserva(primaryStage);
    }
    public static void main(String[] args) {
        launch(args);
    }


}
