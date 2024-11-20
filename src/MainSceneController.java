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
    }

    // Método para abrir la página de reserva
    private void abrirPaginaReserva() {
        Stage stage = new Stage(); 
        PaginaReserva paginaReserva = new PaginaReserva();
        paginaReserva.mostrarPaginaReserva(stage);
    }
}
