import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class WindowBuilder extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My Awesome Window");

        Pane pane = new Pane();
        pane.setPrefSize(1208, 617);
        pane.setStyle("-fx-background-color: #eeeeee;");

        Label element2 = new Label("Tiendas y Fechas Disponibles");
        element2.setLayoutX(299);
        element2.setLayoutY(67.03125);
        element2.setPrefWidth(610);
        element2.setPrefHeight(63);
        element2.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 44.00));
        element2.setStyle("-fx-text-fill: #1b1b1b;");
        pane.getChildren().add(element2);

        Label element3 = new Label("Selecciona Fecha:");
        element3.setLayoutX(57.078125);
        element3.setLayoutY(142.03125);
        element3.setPrefWidth(229);
        element3.setPrefHeight(25);
        element3.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 22.00));
        element3.setStyle("-fx-text-fill: #1b1b1b;");
        pane.getChildren().add(element3);

        TextField element4 = new TextField("");
        element4.setLayoutX(59.08);
        element4.setLayoutY(179.78);
        element4.setPrefWidth(215.00);
        element4.setPrefHeight(24.00);
        element4.setPromptText("Your Input!");
        element4.setFont(Font.loadFont(getClass().getResourceAsStream("/resources/fonts/Lexend.ttf"), 14.00));
        element4.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #1b1b1b; -fx-border-color: #626262; -fx-border-width: 1px; -fx-border-radius: 2px; -fx-prompt-text-fill: #737674;");
        pane.getChildren().add(element4);

        Scene scene = new Scene(pane, 1208, 617);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}