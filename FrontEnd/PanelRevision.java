package frontend;

import backend.Ejercicio;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class PanelRevision extends JPanel {
    private VentanaPrincipal ventana;
    private List<Ejercicio> rutinaActual = new ArrayList<>();
    private int indiceActual = 0;

    private JLabel lblNombre, lblTipo, lblNivel, lblTiempo;
    private JTextArea txtDescripcion;
    private JButton btnAnterior, btnSiguiente;

    public PanelRevision(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Revisión de Ejercicios Asignados", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        // Panel de visualización de datos estructurados del ejercicio
        JPanel pnlDetalles = new JPanel(new GridLayout(4, 2, 5, 10));
        pnlDetalles.add(new JLabel("Nombre:"));
        lblNombre = new JLabel("-");
        pnlDetalles.add(lblNombre);

        pnlDetalles.add(new JLabel("Tipo:"));
        lblTipo = new JLabel("-");
        pnlDetalles.add(lblTipo);

        pnlDetalles.add(new JLabel("Intensidad:"));
        lblNivel = new JLabel("-");
        pnlDetalles.add(lblNivel);

        pnlDetalles.add(new JLabel("Tiempo Estimado:"));
        lblTiempo = new JLabel("-");
        pnlDetalles.add(lblTiempo);

        txtDescripcion = new JTextArea();
        txtDescripcion.setEditable(false);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBorder(BorderFactory.createTitledBorder("Descripción de Ejecución"));

        JPanel pnlCentral = new JPanel(new BorderLayout(5, 10));
        pnlCentral.add(pnlDetalles, BorderLayout.NORTH);
        pnlCentral.add(scrollDesc, BorderLayout.CENTER);
        add(pnlCentral, BorderLayout.CENTER);

        // Controles de navegación
        JPanel pnlNavegacion = new JPanel(new GridLayout(1, 2, 10, 10));
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        pnlNavegacion.add(btnAnterior);
        pnlNavegacion.add(btnSiguiente);
        add(pnlNavegacion, BorderLayout.SOUTH);

        btnAnterior.addActionListener(e -> {
            if (indiceActual > 0) {
                indiceActual--;
                presentarEjercicioActual();
            }
        });

        btnSiguiente.addActionListener(e -> {
            if (indiceActual == rutinaActual.size() - 1) {
                // Transición al resumen final con los datos calculados de la rutina creada
                ventana.getPanelResumen().calcularYMostrarResumen(rutinaActual);
                ventana.cambiarVista("RESUMEN");
            } else {
                indiceActual++;
                presentarEjercicioActual();
            }
        });
    }

    public void cargarRutina(List<Ejercicio> nuevaRutina) {
        this.rutinaActual = nuevaRutina;
        this.indiceActual = 0;
        presentarEjercicioActual();
    }

    private void presentarEjercicioActual() {
        if (rutinaActual == null || rutinaActual.isEmpty()) return;

        Ejercicio ej = rutinaActual.get(indiceActual);
        lblNombre.setText(ej.getNombre());
        lblTipo.setText(ej.getTipo());
        lblNivel.setText(ej.getNivel());
        lblTiempo.setText(ej.getTiempo() + " minutos");
        txtDescripcion.setText(ej.getDescripcion());

        // Control dinámico de las restricciones de la interfaz gráfica
        btnAnterior.setEnabled(indiceActual > 0); 

        if (indiceActual == rutinaActual.size() - 1) {
            btnSiguiente.setText("Resumen de la rutina"); 
        } else {
            btnSiguiente.setText("Siguiente");
        }
    }
}