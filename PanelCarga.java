package frontend;

import backend.Sistema;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class PanelCarga extends JPanel {
    private VentanaPrincipal ventana;
    private Sistema sistema;
    
    private JTextField txtRuta;
    private JButton btnBuscar, btnSiguiente;
    private JTextArea txtEstadisticas;

    public PanelCarga(VentanaPrincipal ventana, Sistema sistema) {
        this.ventana = ventana;
        this.sistema = sistema;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Título de la sección
        JLabel lblTitulo = new JLabel("Carga del Catálogo de Ejercicios", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de selección de archivo
        JPanel pnlSelector = new JPanel(new BorderLayout(5, 5));
        txtRuta = new JTextField();
        txtRuta.setEditable(false);
        btnBuscar = new JButton("Buscar Archivo...");
        pnlSelector.add(txtRuta, BorderLayout.CENTER);
        pnlSelector.add(btnBuscar, BorderLayout.EAST);

        // Área central de visualización de métricas
        txtEstadisticas = new JTextArea("No se han cargado datos aún.");
        txtEstadisticas.setEditable(false);
        txtEstadisticas.setBorder(BorderFactory.createTitledBorder("Estadísticas del Catálogo"));

        JPanel pnlCentro = new JPanel(new BorderLayout(10, 10));
        pnlCentro.add(pnlSelector, BorderLayout.NORTH);
        pnlCentro.add(new JScrollPane(txtEstadisticas), BorderLayout.CENTER);
        add(pnlCentro, BorderLayout.CENTER);

        // Botón de navegación inferior
        btnSiguiente = new JButton("Iniciar Generación de Rutina");
        btnSiguiente.setEnabled(false);
        add(btnSiguiente, BorderLayout.SOUTH);

        // Eventos controlados mediante listeners
        btnBuscar.addActionListener(e -> ejecutarBuscador());
        btnSiguiente.addActionListener(e -> ventana.cambiarVista("GENERACION"));
    }

    private void ejecutarBuscador() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("."));
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            String ruta = fileChooser.getSelectedFile().getAbsolutePath();
            txtRuta.setText(ruta);
            // Comunicación Evento Frontend -> Backend
            sistema.cargarEjerciciosDesdeArchivo(ruta);
        }
    }

    public void actualizarEstadisticasInterfaz(int total, int tiempoTotal, int cardio, int fuerza, int basico, int intermedio, int avanzado, int altoRend) {
        StringBuilder sb = new StringBuilder();
        sb.append("Cantidad total de ejercicios: ").append(total).append("\n");
        sb.append("Tiempo estimado disponible: ").append(tiempoTotal).append(" minutos\n\n");
        sb.append("--- Clasificación por Tipo ---\n");
        sb.append("• Cardiovascular: ").append(cardio).append("\n");
        sb.append("• Fuerza: ").append(fuerza).append("\n\n");
        sb.append("--- Clasificación por Intensidad ---\n");
        sb.append("• Básico: ").append(basico).append("\n");
        sb.append("• Intermedio: ").append(intermedio).append("\n");
        sb.append("• Avanzado: ").append(avanzado).append("\n");
        sb.append("• Alto Rendimiento: ").append(altoRend).append("\n");

        txtEstadisticas.setText(sb.toString());
        btnSiguiente.setEnabled(true);
    }
}