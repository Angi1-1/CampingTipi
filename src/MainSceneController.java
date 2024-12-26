import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainSceneController {

    @FXML
    private Label tfTitle;

    @FXML
    private Button bHeadReserva; // Anotar con @FXML para que sea inicializado automáticamente
    @FXML
    private Button bHeadMcuenta; 
    @FXML
    void btnOKClicked(ActionEvent event) {
        Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);
    }

    @FXML
    public void initialize() {
        // Vincular el botón "Reserva" con la acción
        if (bHeadReserva != null) { // Verificar que no sea null
            bHeadReserva.setOnAction(event -> abrirPaginaReserva());
        } else {
            System.out.println("bHeadReserva no fue inicializado. Verifica el fx:id en el FXML.");
        }
         // Vincular el botón "Inicio" con la acción
        if (bHeadMcuenta != null) { // Verificar que no sea null
            bHeadMcuenta.setOnAction(event -> abrirLogin());
        } else {
            System.out.println("bHeadCuenta no fue inicializado. Verifica el fx:id en el FXML.");
        }
        
    }

    // Método para abrir la página de reserva
    private void abrirPaginaReserva() {
        Stage stage = new Stage(); 
        PaginaReserva paginaReserva = new PaginaReserva();
        paginaReserva.mostrarPaginaReserva(stage);
    }

    private void abrirLogin () {
        Stage stage = new Stage();
        Login pLogin = new Login();
        pLogin.mostrarLogin(stage);
    }
}
