import java.awt.*;
import javax.swing.*;

import javafx.stage.Stage;

public class UserProfile {

    private JFrame frame;
    private JTextField txtNombre, txtFecha, txtCorreo, txtTelefono;
    private JPasswordField txtContraseñaActual, txtNuevaContraseña, txtConfirmarContraseña;
    private JComboBox<String> comboSexo;
    private JButton btnModificar, btnConfirmar, btnCambiarContraseña, btnVerDetalle;

    public UserProfile() {
        frame = new JFrame("Mi Cuenta");
        frame.setSize(1440, 800); // Tamaño fijo
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Barra de navegación superior
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        navBar.setBackground(Color.WHITE);
        navBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        String[] opcionesNav = {"Alojamientos", "Reservas", "Testimonios", "Mi Cuenta"};
        for (String opcion : opcionesNav) {
            JButton btnNav = new JButton(opcion);
            btnNav.setFocusPainted(false);
            btnNav.setBackground(Color.WHITE);
            navBar.add(btnNav);
        }
        frame.add(navBar, BorderLayout.NORTH);

        // Panel principal con scroll vertical
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Header con imagen de fondo y texto
        JLabel lblBackground = new JLabel(new ImageIcon("parte1.png"));
        lblBackground.setLayout(new BorderLayout());

        JLabel lblBienvenido = new JLabel("Bienvenido @username", SwingConstants.CENTER);
        lblBienvenido.setFont(new Font("Serif", Font.BOLD, 24));
        lblBienvenido.setForeground(Color.WHITE);
        lblBackground.add(lblBienvenido, BorderLayout.NORTH);

        JLabel lblMiCuenta = new JLabel("Mi Cuenta", SwingConstants.CENTER);
        lblMiCuenta.setFont(new Font("Serif", Font.BOLD, 36));
        lblMiCuenta.setForeground(Color.WHITE);
        lblBackground.add(lblMiCuenta, BorderLayout.CENTER);

        mainPanel.add(lblBackground, BorderLayout.NORTH);

        // Panel de datos personales y reservas
        JPanel panelMain = new JPanel(new GridLayout(1, 2, 20, 0));

        // Panel de Datos Personales
        JPanel panelDatos = new JPanel(new GridLayout(7, 2, 5, 5));
        panelDatos.setBorder(BorderFactory.createTitledBorder("Datos Personales"));

        txtNombre = new JTextField("Juana Gomez");
        txtNombre.setEditable(false);

        txtFecha = new JTextField("00/00/2024");
        txtFecha.setEditable(false);

        comboSexo = new JComboBox<>(new String[]{"Hombre", "Mujer"});
        comboSexo.setSelectedItem("Hombre");
        comboSexo.setEnabled(false);

        txtCorreo = new JTextField("aaaa@gmail.com");
        txtCorreo.setEditable(false);

        txtTelefono = new JTextField("600 000 000");
        txtTelefono.setEditable(false);

        panelDatos.add(new JLabel("Nombre y Apellido:"));
        panelDatos.add(txtNombre);
        panelDatos.add(new JLabel("Fecha de nacimiento:"));
        panelDatos.add(txtFecha);
        panelDatos.add(new JLabel("Sexo:"));
        panelDatos.add(comboSexo);
        panelDatos.add(new JLabel("Correo electrónico:"));
        panelDatos.add(txtCorreo);
        panelDatos.add(new JLabel("Teléfono:"));
        panelDatos.add(txtTelefono);

        btnModificar = new JButton("Modificar Datos");
        styleButton(btnModificar);
        btnModificar.addActionListener(e -> habilitarEdicion(true));

        btnConfirmar = new JButton("Confirmar");
        styleButton(btnConfirmar);
        btnConfirmar.setVisible(false);
        btnConfirmar.addActionListener(e -> habilitarEdicion(false));

        btnCambiarContraseña = new JButton("Cambiar Contraseña");
        styleButton(btnCambiarContraseña);
        btnCambiarContraseña.addActionListener(e -> mostrarDialogoCambioContraseña());

        panelDatos.add(btnModificar);
        panelDatos.add(btnConfirmar);
        panelDatos.add(btnCambiarContraseña);

        panelMain.add(panelDatos);

        // Panel de Reservas
        JPanel panelReserva = new JPanel();
        panelReserva.setLayout(new BoxLayout(panelReserva, BoxLayout.Y_AXIS));
        panelReserva.setBorder(BorderFactory.createTitledBorder("Tu Reservas"));

        JLabel iconCamping = new JLabel(new ImageIcon("campingIcon.png"));
        iconCamping.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblFecha = new JLabel("00/00/0000 - 00/00/2000");
        lblFecha.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblReservaID = new JLabel("N° 00000000");
        lblReservaID.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTipo = new JLabel("Bell");
        lblTipo.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnVerDetalle = new JButton("Ver Detalle");
        styleButton(btnVerDetalle);
        btnVerDetalle.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVerDetalle.addActionListener(e -> mostrarDetallesReserva());

        JLabel lblNota = new JLabel("*Si desea cancelar la reserva, pon contacto con nosotros.");
        lblNota.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelReserva.add(iconCamping);
        panelReserva.add(lblFecha);
        panelReserva.add(lblReservaID);
        panelReserva.add(lblTipo);
        panelReserva.add(btnVerDetalle);
        panelReserva.add(lblNota);

        panelMain.add(panelReserva);
        mainPanel.add(panelMain, BorderLayout.CENTER);

        // Footer
        JPanel panelFooter = new JPanel(new GridLayout(1, 3));
        panelFooter.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelFooter.setBackground(Color.BLACK);

        JLabel lblSoporte = new JLabel("<html><div style='color:white;'>DEPARTAMENTO DE SERVICIO AL CLIENTE<br>De lunes a domingo: 10 de la mañana - 19 de la tarde:<br>+34 900 000 000<br>Poner contacto</div></html>");
        JLabel lblOtrosServicios = new JLabel("<html><div style='color:white;'>Otros Servicios<br>Sobre Nosotros<br>Contáctanos<br>Aviso Legal<br>Preguntas Frecuentes</div></html>");
        JLabel lblRedes = new JLabel("<html><div style='color:white;'>Follow Us<br>[Instagram] [Facebook]</div></html>");

        panelFooter.add(lblSoporte);
        panelFooter.add(lblOtrosServicios);
        panelFooter.add(lblRedes);
        mainPanel.add(panelFooter, BorderLayout.SOUTH);

        frame.add(scrollPane);
        frame.setVisible(true);
    }

    private void mostrarDetallesReserva() {
        String detalles = """
                Fecha: 00/00/0000 - 00/00/2000
                Pasajero: Juana Gomez
                Número de personas: 2
                Vehículos registrados: 2
                Precio: €€€
                """;

        JOptionPane.showMessageDialog(frame, detalles, "Detalle de Reserva", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarDialogoCambioContraseña() {
        JDialog dialog = new JDialog(frame, "Cambiar Contraseña", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));

        txtContraseñaActual = new JPasswordField();
        txtNuevaContraseña = new JPasswordField();
        txtConfirmarContraseña = new JPasswordField();

        dialog.add(new JLabel("Contraseña Actual:"));
        dialog.add(txtContraseñaActual);
        dialog.add(new JLabel("Nueva Contraseña:"));
        dialog.add(txtNuevaContraseña);
        dialog.add(new JLabel("Confirmar Contraseña:"));
        dialog.add(txtConfirmarContraseña);

        JButton btnGuardar = new JButton("Guardar");
        styleButton(btnGuardar);
        btnGuardar.addActionListener(e -> {
            JOptionPane.showMessageDialog(dialog, "Contraseña cambiada con éxito.");
            dialog.dispose();
        });

        JButton btnCancelar = new JButton("Cancelar");
        styleButton(btnCancelar);
        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.add(btnGuardar);
        dialog.add(btnCancelar);

        dialog.setVisible(true);
    }

    private void habilitarEdicion(boolean habilitar) {
        txtNombre.setEditable(habilitar);
        txtFecha.setEditable(habilitar);
        comboSexo.setEnabled(habilitar);
        txtCorreo.setEditable(habilitar);
        txtTelefono.setEditable(habilitar);
        btnConfirmar.setVisible(habilitar);
        btnModificar.setVisible(!habilitar);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        button.setPreferredSize(new Dimension(200, 40));
    }

    public static void main(String[] args) {
        new UserProfile();
    }

    public static void UserProfile(Stage stage) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'UserProfile'");
    }
}