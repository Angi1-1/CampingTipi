import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegistrarCuentas extends Application {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CampingTipi?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    String Tinos = "file:src/fonts/Tinos/Tinos-Regular.ttf";
    String GideonRoman = "file:src/fonts/Gideon_Roman/GideonRoman-Regular.ttf";

    @Override
    public void start(Stage primaryStage) {
        // Llama al método mostrarLogin para construir la interfaz
        registrar(primaryStage);
    }
    

    public void registrar(Stage primaryStage) {
        try{
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}