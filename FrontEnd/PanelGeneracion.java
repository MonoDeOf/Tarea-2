package frontend;

import backend.Sistema;
import javax.swing.*;
import java.awt.*;

public class PanelGeneracion extends JPanel {
    private VentanaPrincipal ventana;
    private Sistema sistema;

    private JSpinner spnCardio, spnFuerza, spnSemana;
    private JComboBox<String> cmbIntensidad;
    private JButton btnGenerar, btnVolver;

    public PanelGeneracion(VentanaPrincipal ventana, Sistema sistema) {
        this.ventana = ventana;
        this.sistema = sistema;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Parámetros de la Nueva Rutina", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        JPanel pnlFormulario = new JPanel(new GridLayout(4, 2, 10, 15));
        
        pnlFormulario.add(new JLabel("Cantidad Ejercicios Cardio:"));
        spnCardio = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
        pnlFormulario.add(spnCardio);

        pnlFormulario.add(new JLabel("Cantidad Ejercicios Fuerza:"));
        spnFuerza = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
        pnlFormulario.add(spnFuerza);

        pnlFormulario.add(new JLabel("Nivel de Intensidad Requerido:"));
        cmbIntensidad = new JComboBox<>(new String[]{"Básico", "Intermedio", "Avanzado", "Alto Rendimiento"});
        pnlFormulario.add(cmbIntensidad);

        pnlFormulario.add(new JLabel("Semana Actual de Trabajo:"));
        spnSemana = new JSpinner(new SpinnerNumberModel(2, 1, 52, 1));
        pnlFormulario.add(spnSemana);

        add(pnlFormulario, BorderLayout.CENTER);

        JPanel pnlBotones = new JPanel(new GridLayout(1, 2, 10, 10));
        btnVolver = new JButton("Volver");
        btnGenerar = new JButton("Generar Rutina");
        pnlBotones.add(btnVolver);
        pnlBotones.add(btnGenerar);
        add(pnlBotones, BorderLayout.SOUTH);

        btnVolver.addActionListener(e -> ventana.cambiarVista("CARGA"));
        btnGenerar.addActionListener(e -> {
            int cantCardio = (int) spnCardio.getValue();
            int cantFuerza = (int) spnFuerza.getValue();
            String nivel = (String) cmbIntensidad.getSelectedItem();
            int semana = (int) spnSemana.getValue();
            
            // Envío de la instrucción estructurada al backend
            sistema.generarRutina(cantCardio, cantFuerza, nivel, semana);
        });
    }
}