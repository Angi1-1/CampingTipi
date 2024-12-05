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

public class PaginaReserva{
    private List<datosApartamentos.Apartamento> Busqueda(String fechaLlegadaSeleccionada, String fechaSalidaSeleccionada, String numeroDePersonas) {
        int numeroPersonas = Integer.parseInt(numeroDePersonas.split(" ")[0]);
        List<datosApartamentos.Apartamento> apartamentos = datosApartamentos.leerArchivo();
    
        System.out.println("Los datos de resultados: " + apartamentos);
    
        // Filtrar y mostrar los apartamentos que cumplen con la condición
        List<datosApartamentos.Apartamento> resultados = apartamentos.stream()
                .filter(apt -> apt.getPersonas() >= numeroPersonas)
                .toList(); // Use .collect(Collectors.toList()) for Java < 16
    
        return resultados;
    }
    
    String Tinos = "file:src/fonts/Tinos/Tinos-Regular.ttf";
    String GideonRoman ="file:src/fonts/Gideon_Roman/GideonRoman-Regular.ttf";
    private TextArea notaArea;
    private Integer userId = 1;
    // Servicios que ofecemos
    private Map<String, String> createServicio(String nombre, String imagenUrl) {
        Map<String, String> servicio = new HashMap<>();
        servicio.put("nombre", nombre);
        servicio.put("imagenUrl", imagenUrl);
        return servicio;
    }
    //Datos de pasajeros de la reserva

    // Clase para almacenar los datos de pasajaros
    class DatosPasajero {
        TextField respuestanombre;
        TextField respuestadoc;
        DatePicker respuestafecha;
        ComboBox<String> respuestaSexo;
       
        public DatosPasajero(TextField respuestanombre, TextField respuestadoc, DatePicker respuestafecha, ComboBox<String> respuestaSexo) {
            this.respuestanombre = respuestanombre;
            this.respuestadoc = respuestadoc;
            this.respuestafecha = respuestafecha;
            this.respuestaSexo = respuestaSexo;
        }
    }

    List<String> matriculas = new ArrayList<>(); // Lista para guardar matrículas
    List<Reserva> reservaFinalizada = new ArrayList<>();
    String notasReservas = "";
    double precioTotalDeReserva; 
    
     public void mostrarPaginaReserva(Stage primaryStage) {
        try {
        //Cargar el header desde el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScene.fxml"));

        Parent header = loader.load();
        BorderPane root = new BorderPane();
        root.setTop(header); 
        primaryStage.setTitle("Tiendas y Fechas Disponibles");
        // Crear título
        Label mensajeError = new Label("");
        mensajeError.setFont(Font.loadFont(Tinos, 16));
        mensajeError.setStyle("-fx-text-fill: red;");
        mensajeError.setVisible(false);
        Label titulo = new Label("Tiendas y Fechas Disponible");
        titulo.setStyle("-fx-font-size: 44px; -fx-font-weight: bold; -fx-text-fill: black;");
        // Crear etiquetas y campos de entrada
        Label fechaLabel = new Label("Selecciona fecha:");
        fechaLabel.setFont(Font.loadFont(Tinos, 22));
        DatePicker  fechaLlegada = new DatePicker();
        fechaLlegada.setPromptText("Fecha de llegada");
        fechaLlegada.setStyle("-fx-font-size:22; -fx-height:22px; -fx-width:202px");
        DatePicker  fechaSalida = new DatePicker();
        fechaSalida.setPromptText("Fecha de Salida");
        fechaSalida.setStyle("-fx-font-size: 22px; -fx-height: 22px; -fx-width: 202px;");
        // Separador entre fechas
        Label separador = new Label(" - ");
        separador.setFont(Font.font(Tinos, 22));
        // Crear sección de personas
        Label personasLabel = new Label("Selecciona número de pasajero:");
        personasLabel.setFont(Font.loadFont(Tinos, 22));
        ComboBox<String> comboPersonas = new ComboBox<>();
        comboPersonas.getItems().addAll("1 Persona", "2 Personas", "3 Personas", "4 Personas", "5 Personas");
        comboPersonas.setPromptText("Persona");
        comboPersonas.setStyle("-fx-font-size:22px;-fx-pref-height: 22px; -fx-pref-width: 317px; -fx-background-color:#ffff");
        // Crear botón
        Button buscarButton = new Button("Buscar");
        buscarButton.setStyle(
            "-fx-background-color: black;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10 20;" +
            "-fx-border-color: black;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 4px;" +
            "-fx-pref-width: 289px;" +
            "-fx-pref-height: 57px;" +
            "-fx-alignment: center;" +
            "-fx-font-size:22;"
        );
        buscarButton.setFont(Font.font(Tinos, 22));
        VBox resultadosContainer = new VBox(10);
        resultadosContainer.setPadding(new Insets(10));
        resultadosContainer.setAlignment(Pos.TOP_CENTER);
        buscarButton.setOnAction(event -> {
            // Obtener las fechas seleccionadas y la persona seleccionada
            LocalDate fechaLlegadaSeleccionada = fechaLlegada.getValue();
            LocalDate fechaSalidaSeleccionada = fechaSalida.getValue();
            String personaSeleccionada = comboPersonas.getValue();
            if (fechaLlegadaSeleccionada == null || fechaSalidaSeleccionada == null || personaSeleccionada == null) {
                mensajeError.setText("No se puede dejar campos sin rellenar");
                mensajeError.setVisible(true);
            } else if (!fechaLlegadaSeleccionada.isBefore(fechaSalidaSeleccionada)) {
                mensajeError.setText("La fecha de llegada debe ser antes de la fecha de salida");
                mensajeError.setVisible(true);
            } else {
                mensajeError.setVisible(false);
                resultadosContainer.getChildren().clear();

                // Perform search
                List<datosApartamentos.Apartamento> resultados = Busqueda(
                    fechaLlegadaSeleccionada.toString(),
                    fechaSalidaSeleccionada.toString(),
                    personaSeleccionada
                );

                if (resultados.isEmpty()) {
                    HBox busquedaBox = new HBox();
                    busquedaBox.setPadding(new Insets(10));
                    busquedaBox.setSpacing(10);
                    busquedaBox.setAlignment(Pos.CENTER);
                    busquedaBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-background-color: white; -fx-width:1254px; -fx-height:184px;-fx-border-radius:8px;");
                    busquedaBox.setPadding(new Insets(16, 23, 16, 23));
                    VBox detailsBox = new VBox();
                    detailsBox.setSpacing(5);
                    detailsBox.setAlignment(Pos.CENTER);
                    Label titleLabel = new Label("¡Llámanos!");
                    titleLabel.setFont(Font.loadFont(Tinos, 44));
                    Label noResultsLabel = new Label("Parece que no tenemos disponibilidad para las fechas que buscas. Por favor, llámanos y te ayudaremos a encontrar tu tienda ideal.");
                    noResultsLabel.setFont(Font.loadFont(Tinos, 22));
                    Label telefono = new Label("900 900 900");
                    telefono.setFont(Font.loadFont(Tinos, 22));
                    detailsBox.getChildren().addAll(titleLabel,noResultsLabel,telefono);
                    busquedaBox.getChildren().addAll(detailsBox);
                    resultadosContainer.getChildren().add(busquedaBox);
                } else {
                    for (datosApartamentos.Apartamento apt : resultados) {
                        // Apartment card
                        Button busquedaBox = new Button();
                        busquedaBox.setStyle(
                            "-fx-border-color: black; " +
                            "-fx-border-width: 1; " +
                            "-fx-background-color: white; " +
                            "-fx-border-radius: 8px; " +
                            "-fx-padding: 16 23 16 23; " +
                            "-fx-pref-width: 1254px; " +
                            "-fx-pref-height: 184px;"
                        );
                        
                        HBox contentBox = new HBox();
                        contentBox.setSpacing(10);
                        contentBox.setPadding(new Insets(10));
                        ImageView imageView = new ImageView(new Image("imagenes/bell.jpg")); // Replace with a valid image path
                        imageView.setFitWidth(180);
                        imageView.setFitHeight(150);
                        imageView.setPreserveRatio(true);
                        // Crear un rectángulo con esquinas redondeadas como clip
                        Rectangle clip = new Rectangle(180, 150); 
                        clip.setArcWidth(16); 
                        clip.setArcHeight(16); 
                        imageView.setClip(clip);
                        VBox detailsBox = new VBox();
                        detailsBox.setSpacing(5);
                        Label titleLabel = new Label(apt.getNombreApartamento());
                        titleLabel.setFont(Font.loadFont(Tinos, 44));
                        Label breakfastLabel = new Label(apt.getDesayuno() == 1 ? "Con Desayuno" : "Sin Desayuno");
                        breakfastLabel.setFont(Font.loadFont(Tinos, 22));

                        HBox featuresBox = new HBox(10);
                        Label capacityLabel = new Label(apt.getPersonas() + " Personas");
                        capacityLabel.setFont(Font.loadFont(Tinos, 16));
                        capacityLabel.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-border-radius: 8;");
                        Label spaceLabel = new Label(apt.getEspacio() + " m²");
                        spaceLabel.setFont(Font.loadFont(Tinos, 16));
                        spaceLabel.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-border-radius: 8;");
                        featuresBox.getChildren().addAll(capacityLabel, spaceLabel);

                        detailsBox.getChildren().addAll(titleLabel, breakfastLabel, featuresBox);
                        VBox priceBox = new VBox();
                        priceBox.setAlignment(Pos.CENTER);
                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS); // Permitir que el espaciador crezca
                        Label priceLabel = new Label("Desde");
                        priceLabel.setFont(Font.loadFont(Tinos, 22));
                        Label priceValueLabel = new Label("€" + apt.getPrecio());
                        priceValueLabel.setFont(Font.loadFont(Tinos,44));
                        priceBox.getChildren().addAll(priceLabel, priceValueLabel);

                        contentBox.getChildren().addAll(imageView, detailsBox,spacer, priceBox);
                        busquedaBox.setGraphic(contentBox);
                        busquedaBox.setOnAction(item -> {
                            primaryStage.close();
                            informacion(new Stage(), apt, fechaLlegadaSeleccionada, fechaSalidaSeleccionada, personaSeleccionada);
                        });
                        resultadosContainer.getChildren().add(busquedaBox);
                    }
                }
            }
        });

        // Organizar campos de fecha en un HBox
        HBox fechasBox = new HBox(10, fechaLlegada, separador, fechaSalida);
        fechasBox.setAlignment(Pos.CENTER);
        // Organizar la sección de pasajeros en un HBox
        HBox personasBox = new HBox(10, comboPersonas);
        personasBox.setAlignment(Pos.CENTER);
        // Organizar los campos en un GridPane
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);
        grid.add(fechaLabel, 0, 0);
        grid.add(fechasBox, 0, 1);
        grid.add(personasLabel,1,0);
        grid.add(comboPersonas, 1,1);
        grid.add(buscarButton, 2,1);
        grid.add(mensajeError, 2, 2);
        // Contenedor principal
        VBox contenidoPrincipal = new VBox(20, titulo, grid, resultadosContainer);
        contenidoPrincipal.setPadding(new Insets(20));
        contenidoPrincipal.setAlignment(Pos.CENTER);
        ScrollPane scrollPane = new ScrollPane(contenidoPrincipal);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Barra de desplazamiento vertical solo si es necesario
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // No mostrar barra horizontal
        root.setCenter(scrollPane);
        Scene scene = new Scene(root, 1440, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }catch(IOException e){
        e.printStackTrace();
        System.err.println("Error al abrir la página de reserva: " + e.getMessage());
    }
    }
     public void informacion(Stage segundoStage, datosApartamentos.Apartamento apt, LocalDate fechaLlegadaSeleccionada, LocalDate fechaSalidaSeleccionada, String personaSeleccionada) {
        List<DatosPasajero> datosPasajeros = new ArrayList<>();
        //Calcular el precio y dia de quedada
        int diasDiferencia = (int) ChronoUnit.DAYS.between(fechaLlegadaSeleccionada, fechaSalidaSeleccionada);
        precioTotalDeReserva = diasDiferencia * apt.getPrecio();

        System.out.println("Se seleccionó el apartamento: " + apt.getNombreApartamento());
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));
        Button back = new Button("<= Back");
        back.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-font-size:22");
        back.setFont(Font.loadFont(Tinos, 22));
        back.setOnAction(e -> {
            segundoStage.close();
            mostrarPaginaReserva(new Stage());
        }
        );
        // Parte izquierda - datos de imagen y descripción
        VBox leftSection = new VBox(20);
        leftSection.setPadding(new Insets(20));
        leftSection.setAlignment(Pos.TOP_CENTER);
        
        // Contenidos principales
        ImageView imageView = new ImageView(new Image("imagenes/bell.jpg"));
        imageView.setFitWidth(617);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(true);
    
        Label titulo = new Label(apt.getNombreApartamento());
        titulo.setFont(Font.loadFont(Tinos, 44));
        titulo.setStyle("-fx-font-weight:bold;");
    
        HBox caracteristicas = new HBox(10);
        caracteristicas.setAlignment(Pos.CENTER);
        Label personas = new Label(apt.getPersonas() + " Personas");
        personas.setFont(Font.loadFont(Tinos, 16));
        personas.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-border-radius: 5;");
        Label espacio = new Label(apt.getEspacio() + " m²");
        espacio.setFont(Font.loadFont(Tinos, 16));
        espacio.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-border-radius: 5;");
        Label altura = new Label(apt.getAltura() + " Personas");
        altura.setFont(Font.loadFont(Tinos, 16));
        altura.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-border-radius: 5;");
    
        caracteristicas.getChildren().addAll(personas, espacio, altura);
    
        Label descripcion = new Label(apt.getdescripcion());
        descripcion.setFont(Font.loadFont(Tinos, 22));
        descripcion.setWrapText(true);
        descripcion.setMaxHeight(250);
        descripcion.setMaxWidth(600);
        HBox serviciosBox = new HBox(10);
        serviciosBox.setAlignment(Pos.CENTER);
        List<Map<String, String>> servicios = new ArrayList<>();
        servicios.add(createServicio("Mesa", "iconos/silla.png"));
        servicios.add(createServicio("Toallas de baño", "iconos/toalla.png"));
        servicios.add(createServicio("Electricidad", "iconos/enchufe.png"));
        servicios.add(createServicio("Cama de matrimonio", "iconos/cama.png"));
        servicios.add(createServicio("Cierre con candado", "iconos/candado.png"));
    
        // Crear servicios
        for (Map<String, String> servicio : servicios) {
            VBox servicioItem = new VBox(10);
            servicioItem.setAlignment(Pos.CENTER);
            servicioItem.setPrefWidth(100);
            servicioItem.setPrefHeight(101);
            servicioItem.setStyle("-fx-border-color: black; -fx-padding: 5; -fx-border-radius: 5;");
            
            // Crear nodos únicos para cada servicio
            Label servicioNombre = new Label(servicio.get("nombre")); // Usar clave "nombre"
            servicioNombre.setFont(Font.loadFont(Tinos, 16));
            servicioNombre.setStyle("-fx-font-weight:700;-fx-text-alignment: center;");
            servicioNombre.setWrapText(true);
            servicioNombre.setMaxWidth(100);
            ImageView servicioView = new ImageView(new Image(servicio.get("imagenUrl"))); // Crear nueva imagen
            servicioView.setFitWidth(30);
            servicioView.setFitHeight(30);
            // Añadir elementos únicos a cada HBox
            servicioItem.getChildren().addAll(servicioView, servicioNombre);
    
            // Añadir servicioItem al VBox
            serviciosBox.getChildren().add(servicioItem);
        }
    
        // Añadir todo a leftSection
        leftSection.getChildren().clear(); // Asegurarse de no duplicar
        leftSection.getChildren().addAll(imageView, titulo, caracteristicas, descripcion, serviciosBox);
    
        // Parte derecha - datos de usuario
        VBox rightSection = new VBox(20);
        rightSection.setPadding(new Insets(20));
        rightSection.setAlignment(Pos.TOP_CENTER);
    
        Label reservaTitulo = new Label("Rellenar Tu Reserva");
        reservaTitulo.setFont(Font.loadFont(Tinos,44));
        reservaTitulo.setStyle("-fx-font-weigh:700");
        reservaTitulo.setAlignment(Pos.CENTER);

        HBox divInputFecha = new HBox(20);
        Label fecha = new Label("Su fecha de llegada y salida");
        fecha.setFont(Font.loadFont(Tinos, 22));
        Label respuestaFecha = new Label(fechaLlegadaSeleccionada + " - " + fechaSalidaSeleccionada);
        respuestaFecha.setFont(Font.loadFont(Tinos, 22));
        respuestaFecha.setPrefWidth(260);
        divInputFecha.getChildren().addAll(fecha,respuestaFecha);
        HBox divInputPasajero = new HBox(20);
        Label pasajero = new Label("Pasajeros");
        pasajero.setFont(Font.loadFont(Tinos, 22));
        pasajero.setPrefWidth(260);
        Label respuestaPasajero = new Label(personaSeleccionada +"");
        respuestaPasajero.setFont(Font.loadFont(Tinos, 22));
        divInputPasajero.getChildren().addAll(pasajero,respuestaPasajero);
        HBox divInputPrecio = new HBox(20);
        Label precio = new Label("Precio de la Reserva");
        precio.setPrefWidth(260); 
        precio.setFont(Font.loadFont(Tinos, 22));
        Label respuestaprecio = new Label(precioTotalDeReserva + "€");
        respuestaprecio.setFont(Font.loadFont(Tinos, 32));
        divInputPrecio.getChildren().addAll(precio,respuestaprecio);

        String[] partes = personaSeleccionada.split("");
        
        VBox contenidosDatosUsuario = new VBox(20);
        contenidosDatosUsuario.getChildren().clear();
        //Lista de los datos de los pasajeros
        Button Siguiente = new Button("Siguiente");
        Button anterior = new Button("Anterior");
        Button finalizar = new Button("Finalizar");
        Button pagar = new Button("Pagar");
        
        String botoString = (
            "-fx-background-color: black;" +
            "-fx-text-fill: white;" +
            "-fx-padding: 10 20;" +
            "-fx-border-color: black;" +
            "-fx-border-width: 1px;" +
            "-fx-border-radius: 4px;" +
            "-fx-pref-width: 289px;" +
            "-fx-pref-height: 57px;" +
            "-fx-alignment: center;" +
            "-fx-font-size:22;"
        );
    
        Siguiente.setStyle(botoString);
        anterior.setStyle(botoString);
        finalizar.setStyle(botoString);
        pagar.setStyle(botoString);
        Siguiente.setFont(Font.font(Tinos, 22));
        for (int i = 1; i <= Integer.parseInt(partes[0]); i++) { 
            VBox contenedorPasajero = new VBox(20);
            // Título del pasajero
            Label tituloDatos = new Label("Datos de Pasajero " + i);
            tituloDatos.setFont(Font.loadFont(GideonRoman, 32));
        
            // Input Nombre y Apellido
            HBox divInputNombre = new HBox(20);
            Label nombre = new Label("Nombre y Apellido");
            nombre.setPrefWidth(254);
            nombre.setFont(Font.loadFont(Tinos, 22));
            TextField respuestanombre = new TextField();
            respuestanombre.setFont(Font.loadFont(Tinos, 22));
            respuestanombre.setPrefWidth(333);
            respuestanombre.setPrefHeight(22);
            divInputNombre.getChildren().addAll(nombre, respuestanombre);
        
            // Input Documento de Identidad
            HBox divInputDocumentos = new HBox(20);
            Label documentos = new Label("Documento de Identidad");
            documentos.setPrefWidth(254);
            documentos.setFont(Font.loadFont(Tinos, 22));
            TextField respuestadoc = new TextField();
            respuestadoc.setFont(Font.loadFont(Tinos, 22));
            respuestadoc.setPrefWidth(333);
            respuestadoc.setPrefHeight(22);
            divInputDocumentos.getChildren().addAll(documentos, respuestadoc);
        
            // Input Fecha de Nacimiento
            HBox divInputFechaNacimiento = new HBox(20);
            Label fechaNac = new Label("Fecha de Nacimiento");
            fechaNac.setPrefWidth(254); // Cambié `fecha` a `fechaNac` para evitar error
            fechaNac.setFont(Font.loadFont(Tinos, 22));
            DatePicker respuestafecha = new DatePicker();
            respuestafecha.setPrefWidth(333);
            respuestafecha.setPrefHeight(22);
            respuestafecha.setStyle("-fx-font-size: 22px; -fx-font-family: Tinos;");
            divInputFechaNacimiento.getChildren().addAll(fechaNac, respuestafecha);
        
            // Input Sexo
            HBox divInputSexo = new HBox(20);
            Label sexo = new Label("Sexo");
            sexo.setPrefWidth(254);
            sexo.setFont(Font.loadFont(Tinos, 22));
            ComboBox<String> respuestaSexo = new ComboBox<>();
            respuestaSexo.getItems().addAll("Hombre", "Mujer");
            respuestaSexo.setPromptText("Selecionar sexo");
            respuestaSexo.setStyle("-fx-font-size:22px;-fx-pref-height: 18px; -fx-pref-width: 333px; -fx-background-color:#ffff");
            // Añadir todos los inputs al contenedor del pasajero
            divInputSexo.getChildren().addAll(sexo, respuestaSexo);
            contenedorPasajero.getChildren().addAll(tituloDatos, divInputNombre, divInputDocumentos, divInputFechaNacimiento, divInputSexo);
            contenidosDatosUsuario.getChildren().add(contenedorPasajero);
            //guardar los datos 
            datosPasajeros.add(new DatosPasajero(respuestanombre, respuestadoc, respuestafecha, respuestaSexo));
        }        
        contenidosDatosUsuario.getChildren().add(Siguiente);
        //Eventos de pagina 
        List <Reserva.Pasajero> datosUsuarios = new ArrayList<>();
        //Cuando elegir siguiente
        Siguiente.setOnAction(event -> {
            contenidosDatosUsuario.getChildren().clear();
            // Siguiente.setVisible(false);
            // anterior.setVisible(true);
            // finalizar.setVisible(true);
            System.out.println("Datos ingresados por el usuario:");
            
            for (int i = 0; i < Integer.parseInt(partes[0]); i++) {
                // Obtener los datos de cada pasajero desde la lista de campos
                DatosPasajero datos = datosPasajeros.get(i);
                
                String nombrePasajeroString = datos.respuestanombre.getText();
                String documentoPasajerotring = datos.respuestadoc.getText();
                String fechaNacimientoPasajerotring = datos.respuestafecha.getValue().toString();
                String sexoPasajerotring = datos.respuestaSexo.getValue();
                Reserva.Pasajero datoUsuarioIntroduciodo = new Reserva.Pasajero(nombrePasajeroString, documentoPasajerotring, fechaNacimientoPasajerotring, sexoPasajerotring);
                datosUsuarios.add(datoUsuarioIntroduciodo);
                // Imprimir los datos del pasajero
                System.out.println("Pasajero " + (i + 1) + ":");
                System.out.println("  Nombre: " + datos.respuestanombre.getText());
                System.out.println("  Documento: " + datos.respuestadoc.getText());
                System.out.println("  Fecha de Nacimiento: " + datos.respuestafecha.getValue().toString());
                System.out.println("  Sexo: " + datos.respuestaSexo.getValue());
                VBox contenedorPasajero = new VBox(20);
                // Título del pasajero
                Label tituloDatos = new Label("Datos de Pasajero " + (i + 1));
                tituloDatos.setFont(Font.loadFont(GideonRoman, 32));
            
                // Input Nombre y Apellido
                HBox divInputNombre = new HBox(20);
                Label nombre = new Label("Nombre y Apellido");
                nombre.setPrefWidth(254);
                nombre.setFont(Font.loadFont(Tinos, 22));
                Label respuestanombre = new Label(datos.respuestanombre.getText());
                respuestanombre.setFont(Font.loadFont(Tinos, 22));
                divInputNombre.getChildren().addAll(nombre, respuestanombre);
            
                // Input Documento de Identidad
                HBox divInputDocumentos = new HBox(20);
                Label documentos = new Label("Documento de Identidad");
                documentos.setPrefWidth(254);
                documentos.setFont(Font.loadFont(Tinos, 22));
                Label respuestadoc = new Label(datos.respuestafecha.getValue().toString());
                respuestadoc.setFont(Font.loadFont(Tinos, 22));
                divInputDocumentos.getChildren().addAll(documentos, respuestadoc);
            
                // Input Fecha de Nacimiento
                HBox divInputFechaNacimiento = new HBox(20);
                Label fechaNac = new Label("Fecha de Nacimiento");
                fechaNac.setPrefWidth(254); // Cambié `fecha` a `fechaNac` para evitar error
                fechaNac.setFont(Font.loadFont(Tinos, 22));
                Label respuestafecha = new Label(datos.respuestafecha.getValue().toString());
                respuestafecha.setPrefWidth(333);
                respuestafecha.setPrefHeight(22);
                respuestafecha.setStyle("-fx-font-size: 22px; -fx-font-family: Tinos;");
                divInputFechaNacimiento.getChildren().addAll(fechaNac, respuestafecha);
            
                // Input Sexo
                HBox divInputSexo = new HBox(20);
                Label sexo = new Label("Sexo");
                sexo.setPrefWidth(254);
                sexo.setFont(Font.loadFont(Tinos, 22));
                Label respuestaSexo = new Label(datos.respuestaSexo.getValue());
                respuestaSexo.setFont(Font.loadFont(Tinos, 22));
                divInputSexo.getChildren().addAll(sexo, respuestaSexo);
                contenedorPasajero.getChildren().addAll(tituloDatos, divInputNombre, divInputDocumentos, divInputFechaNacimiento, divInputSexo);
                contenidosDatosUsuario.getChildren().addAll(contenedorPasajero);
            }


            System.out.println("Los datos insertados:");
            for (Reserva.Pasajero usuario : datosUsuarios) {
                System.out.println(usuario.imprimirDatos());
            }
            Label opcionesTitulo = new Label("Otras Opciones");
            opcionesTitulo.setFont(Font.loadFont(GideonRoman, 32));

            HBox divInputVehiculo = new HBox(20);
            Label vehiculoLabel = new Label("Vehículo para aparcar:");
            vehiculoLabel.setFont(Font.loadFont(Tinos, 22));
            vehiculoLabel.setPrefWidth(254);
            vehiculoLabel.setPrefHeight(22);
            ComboBox<String> vehiculComboBox = new ComboBox<>();
            vehiculComboBox.getItems().addAll("1 vehículos", "2 vehículos", "3 vehículos", "4 vehículos", "Sin vehículo");
            vehiculComboBox.setPromptText("Seleccionar");
            vehiculComboBox.setStyle("-fx-font-size:22px;-fx-pref-height: 18px; -fx-pref-width: 333px; -fx-background-color:#ffff");
            divInputVehiculo.getChildren().addAll(vehiculoLabel, vehiculComboBox); 
            VBox divInputMatricula = new VBox(10);
            //lista para guardar matricula

            vehiculComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                // Limpiar los campos de matrícula existentes y la lista de matrículas
                divInputMatricula.getChildren().clear();
                matriculas.clear();
            
                // Verificar que el nuevo valor no sea nulo ni "Sin vehículo"
                if (newValue != null && !newValue.equals("Sin vehículo")) {
                    // Dividir el valor para obtener el número de vehículos
                    String[] matriculasNumero = newValue.split(" ");
                    int numVehiculos = Integer.parseInt(matriculasNumero[0]);
            
                    // Crear los campos de matrícula en función del número de vehículos
                    for (int i = 0; i < numVehiculos; i++) { // Cambiar índice para que empiece desde 0
                        HBox matriculaBox = new HBox(20);
                        int index = i; 
                        Label matriculaLabel = new Label("Matrícula de vehículo " + (i + 1) + ":");
                        matriculaLabel.setPrefWidth(254);
                        matriculaLabel.setPrefHeight(22);
                        matriculaLabel.setFont(Font.loadFont(Tinos, 22));
            
                        TextField matriculaField = new TextField();
                        matriculaField.setFont(Font.loadFont(Tinos, 22));
                        matriculaField.setPrefWidth(333);
                        matriculaField.setPrefHeight(20);
            
                        // Listener para actualizar la lista de matrículas
                        matriculaField.textProperty().addListener((fieldObservable, oldText, newText) -> {
                            // Asegurar que la lista tenga espacio suficiente para el índice actual
                            if (matriculas.size() <= index) {
                                matriculas.add(""); // Añadir entradas vacías si es necesario
                            }
                            matriculas.set(index, newText); // Actualizar la matrícula en el índice correspondiente
                        });
            
                        matriculaBox.getChildren().addAll(matriculaLabel, matriculaField);
                        divInputMatricula.getChildren().add(matriculaBox);
                    }
                }
            });
            
           
            VBox divInputNota = new VBox(10);
            Label notaLabel = new Label("Nota:");
            notaLabel.setFont(Font.loadFont(Tinos, 22));
            notaArea = new TextArea();
            notaArea.setPromptText("¿Alergias? ¿Medicamentos?...");
            notaArea.setFont(Font.loadFont(Tinos, 22));
            notaArea.setPrefRowCount(5);

            divInputNota.getChildren().addAll(notaLabel, notaArea);
         
            HBox botonesBox = new HBox(10);
            botonesBox.getChildren().addAll(finalizar);
            // Agregar todo al VBox principal
            contenidosDatosUsuario.getChildren().addAll(
                opcionesTitulo,
                divInputVehiculo,
                divInputMatricula,
                divInputNota, 
                botonesBox
            );
        });
        
        //Cuando termina la consulta
        finalizar.setOnAction(event -> {
            notasReservas = notaArea.getText();
         
            // Limpiar los datos visuales previos
            contenidosDatosUsuario.getChildren().clear();
            VBox contenidoParte1 = new VBox(10);
            VBox contenidoParte2 = new VBox(10);
            // Imprimir los datos de los pasajeros
            System.out.println("Datos ingresados por el usuario:");
            
            for (int i = 0; i < datosUsuarios.size(); i++) {
                Reserva.Pasajero datos = datosUsuarios.get(i);
        
                // Mostrar los datos del pasajero en la consola
                System.out.println("Pasajero " + (i + 1) + ":");
                System.out.println("  Nombre: " + datos.getNombre());
                System.out.println("  Documento: " + datos.getDocumento());
                System.out.println("  Fecha de Nacimiento: " + 
                    (datos.getFechaNacimiento() != null ? datos.getFechaNacimiento() : "No seleccionada"));
                System.out.println("  Sexo: " + 
                    (datos.getSexo() != null ? datos.getSexo() : "No seleccionado"));
        
                // Mostrar los datos visualmente en el contenedor
                VBox contenedorPasajero = new VBox(20);
                Label tituloDatos = new Label("Datos de Pasajero " + (i + 1));
                tituloDatos.setFont(Font.loadFont(GideonRoman, 32));
        
                // Crear etiquetas con los datos del pasajero
                HBox divInputNombre = new HBox(20);
                Label nombre = new Label("Nombre y Apellido");
                nombre.setPrefWidth(254);
                nombre.setFont(Font.loadFont(Tinos, 22));
                Label respuestanombre = new Label(datos.getNombre());
                respuestanombre.setFont(Font.loadFont(Tinos, 22));
                divInputNombre.getChildren().addAll(nombre, respuestanombre);
        
                HBox divInputDocumentos = new HBox(20);
                Label documentos = new Label("Documento de Identidad");
                documentos.setPrefWidth(254);
                documentos.setFont(Font.loadFont(Tinos, 22));
                Label respuestadoc = new Label(datos.getDocumento());
                respuestadoc.setFont(Font.loadFont(Tinos, 22));
                divInputDocumentos.getChildren().addAll(documentos, respuestadoc);
        
                HBox divInputFechaNacimiento = new HBox(20);
                Label fechaNac = new Label("Fecha de Nacimiento");
                fechaNac.setPrefWidth(254);
                fechaNac.setFont(Font.loadFont(Tinos, 22));
                Label respuestafecha = new Label(
                    datos.getFechaNacimiento() != null ? datos.getFechaNacimiento(): "No seleccionada"
                );
                respuestafecha.setFont(Font.loadFont(Tinos, 22));
                divInputFechaNacimiento.getChildren().addAll(fechaNac, respuestafecha);
        
                HBox divInputSexo = new HBox(20);
                Label sexo = new Label("Sexo");
                sexo.setPrefWidth(254);
                sexo.setFont(Font.loadFont(Tinos, 22));
                Label respuestaSexo = new Label(datos.getSexo() != null ? datos.getSexo() : "No seleccionado");
                respuestaSexo.setFont(Font.loadFont(Tinos, 22));
                divInputSexo.getChildren().addAll(sexo, respuestaSexo);
        
                contenedorPasajero.getChildren().addAll(tituloDatos, divInputNombre, divInputDocumentos, divInputFechaNacimiento, divInputSexo);
                contenidoParte1.getChildren().addAll(contenedorPasajero);
            }
        
            // Imprimir y mostrar las matrículas
            System.out.println("Matrículas ingresadas:" + matriculas);
            for (int i = 0; i < matriculas.size(); i++) {
                String matricula = matriculas.get(i);
                System.out.println("Matrícula de vehículo " + (i + 1) + ": " + matricula);
        
                // // Mostrar las matrículas visualmente en el contenedor
                HBox matriculaBox = new HBox(20);
                Label matriculaLabel = new Label("Matrícula de vehículo " + (i + 1) + ":");
                matriculaLabel.setPrefWidth(254);
                matriculaLabel.setFont(Font.loadFont(Tinos, 22));
                Label matriculaValue = new Label(matricula != null && !matricula.isEmpty() ? matricula : "No ingresada");
                matriculaValue.setFont(Font.loadFont(Tinos, 22));
                matriculaBox.getChildren().addAll(matriculaLabel, matriculaValue);
                contenidoParte2.getChildren().addAll(matriculaBox);
            }
            VBox divInputNota = new VBox(10);
            Label notaLabel = new Label("Nota:");
            notaLabel.setFont(Font.loadFont(Tinos, 22));
            Label notaArea = new Label(notasReservas);
            notaArea.setFont(Font.loadFont(Tinos, 22));
            divInputNota.getChildren().addAll(notaLabel, notaArea);
            
            //Volver atras 
            contenidosDatosUsuario.getChildren().addAll(contenidoParte1,contenidoParte2,divInputNota,pagar);
        });
        
        pagar.setOnAction(event -> {
            System.out.println("Finalizado");
            reservaTitulo.setText("¡ Reserva Realizado !");
            contenidosDatosUsuario.getChildren().clear();
            Button downloandPDFButton = new Button("DESCARGAR PDF");
            downloandPDFButton.setStyle(botoString);
            downloandPDFButton.setAlignment(Pos.CENTER);
            contenidosDatosUsuario.getChildren().addAll(downloandPDFButton);
            Reserva reservaFinalizada = new Reserva(userId, datosUsuarios, matriculas, apt.getIdapartamento(), notasReservas, fechaLlegadaSeleccionada.toString(), fechaSalidaSeleccionada.toString(), precioTotalDeReserva, apt.getNombreApartamento());
            Reserva.insertDatosReservas(userId, apt.getIdapartamento(), fechaLlegadaSeleccionada.toString(), fechaSalidaSeleccionada.toString(), precioTotalDeReserva,datosUsuarios, matriculas, notasReservas);
            System.out.println("Reserva creada con todos los datos: " + reservaFinalizada);
            DescargaPDF pdfDescargaPDF = new DescargaPDF();
             downloandPDFButton.setOnAction(e -> {
                pdfDescargaPDF.generarPDF("reserva.pdf", reservaFinalizada);
            });
            pdfDescargaPDF.guardarReserva("reservaDatos.txt",reservaFinalizada);
        });
        rightSection.getChildren().addAll(reservaTitulo,divInputFecha,divInputPasajero,divInputPrecio, contenidosDatosUsuario); 
        HBox contenidoRellenar = new HBox(10);
        contenidoRellenar.getChildren().addAll(leftSection,rightSection);    
        
        ScrollPane scrollPane = new ScrollPane(contenidoRellenar);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Barra de desplazamiento vertical solo si es necesario
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // No mostrar barra horizontal
        // root.setLeft(leftSection);
        // root.setRight(rightSection);
        root.setTop(back);
        root.setCenter(scrollPane);
       
        Scene informacioScene = new Scene(root, 1440, 800);
        segundoStage.setScene(informacioScene);
        segundoStage.show();
    }
    
}