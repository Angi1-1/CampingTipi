import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainSceneController {

    @FXML
    private Label tfTitle;

    @FXML
    private Button bHeadReserva;
    @FXML
    private Button bHeadMcuenta;

    @FXML
    private ScrollBar scrollbarhome; // ScrollBar vinculado al FXML

    @FXML
    private Pane mainPane; // Pane principal que contiene el contenido desplazable

    @FXML
    public void initialize() {
        // Vincular el botón "Reserva" con la acción
        if (bHeadReserva != null) {
            bHeadReserva.setOnAction(event -> abrirPaginaReserva());
        } else {
            System.out.println("La página de reserva no fue inicializada. Contactar con el servicio técnico de la aplicación.");
        }

        // Configurar el ScrollBar
        if (scrollbarhome != null && mainPane != null) {
            configurarScrollBar();
        } else {
            System.out.println("scrollbarhome o mainPane no fueron inicializados. Contactar con el servicio técnico de la aplicación.");
        }

        if (bHeadMcuenta != null) {
            bHeadMcuenta.setOnAction(event -> abrirUserProfile());
        } else {
            System.out.println("La página de cuenta personal no fue inicializada. Contactar con el servicio técnico de la aplicación.");
        }


    }

    // Método para configurar el desplazamiento del ScrollBar
    private void configurarScrollBar() {
        scrollbarhome.setMin(0);
        scrollbarhome.setMax(1); // Valores iniciales, ajustables según el contenido
        scrollbarhome.setValue(0);

        // Listener para manejar el desplazamiento del ScrollBar
        scrollbarhome.valueProperty().addListener((observable, oldValue, newValue) -> {
            double contentHeight = mainPane.getPrefHeight(); // Altura del contenido
            double viewportHeight = mainPane.getParent().getBoundsInLocal().getHeight(); // Altura visible
            double maxTranslate = contentHeight - viewportHeight; // Máximo desplazamiento
            double translateY = -newValue.doubleValue() * maxTranslate; // Calcular desplazamiento
            mainPane.setTranslateY(translateY); // Aplicar desplazamiento
        });

        // Ajustar el rango del ScrollBar según el tamaño del contenido y el viewport
        mainPane.heightProperty().addListener((observable, oldValue, newHeight) -> {
            double visibleHeight = mainPane.getParent().getBoundsInLocal().getHeight();
            if (newHeight.doubleValue() > visibleHeight) {
                scrollbarhome.setDisable(false);
                scrollbarhome.setMax(1); // Activar ScrollBar
            } else {
                scrollbarhome.setDisable(true); // Deshabilitar si el contenido cabe en el viewport
            }
        });
    }

    // Método para abrir la página de reserva
    private void abrirPaginaReserva() {
        Stage stage = new Stage();
        PaginaReserva paginaReserva = new PaginaReserva();
        paginaReserva.mostrarPaginaReserva(stage);
    }

    private void abrirUserProfile() {
        Stage stage = new Stage();
        UserProfile userProfile = new UserProfile();
        UserProfile.UserProfile(stage);
    }


    // Método para actualizar el título de la ventana
    @FXML
    void btnOKClicked(ActionEvent event) {
        Stage mainWindow = (Stage) tfTitle.getScene().getWindow();
        String title = tfTitle.getText();
        mainWindow.setTitle(title);
    }

    @FXML
    private GridPane GridPanelDos;

    @FXML
    private GridPane GridPanelTres;

    @FXML
    private GridPane GridPanelUno;
    public void slider() {
        new Thread(){
            public void run(){
                int count = 0;
                while (true) {
                    switch (count) {
                        case 0:
                            Thread.sleep(5000);

                                TranslateTransition slider1 = new TranslateTransition();
                                slider1.setNode(GridPanelUno);
                                slider1.setDuration(Duration.seconds(3));
                                slider1.setToX(0);
                                slider1.play();

                                TranslateTransition slider2 = new TranslateTransition();
                                slider2.setNode(GridPanelDos);
                                slider2.setDuration(Duration.seconds(3));
                                slider2.setToX(0);
                                slider2.play();


                                TranslateTransition slider3 = new TranslateTransition();
                                slider3.setNode(GridPanelTres);
                                slider3.setDuration(Duration.seconds(3));
                                slider3.setToX(-600);
                                slider3.play();
                                
                                count = 1;
                                break;
                            case 1:
                            Thread.sleep(5000)
                    }
                    
                }
            } catch(Exception e) {e.printStackTrace();}
        }
    }


}