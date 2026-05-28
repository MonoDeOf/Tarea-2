package frontend;

import backend.Ejercicio;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelResumen extends JPanel {
    private VentanaPrincipal ventana;
    private JTextArea txtResumenConsolidado;
    private JButton btnFinalizar;

    public PanelResumen(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Resumen Estadístico de la Rutina", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        txtResumenConsolidado = new JTextArea();
        txtResumenConsolidado.setEditable(false);
        txtResumenConsolidado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        add(new JScrollPane(txtResumenConsolidado), BorderLayout.CENTER);

        btnFinalizar = new JButton("Crear Nueva Rutina / Salir");
        add(btnFinalizar, BorderLayout.SOUTH);

        btnFinalizar.addActionListener(e -> ventana.cambiarVista("GENERACION"));
    }

    public void calcularYMostrarResumen(List<Ejercicio> rutina) {
        int tiempoTotal = 0;
        int cardio = 0, fuerza = 0;
        int basico = 0, intermedio = 0, avanzado = 0, altoRend = 0;

        for (Ejercicio ej : rutina) {
            tiempoTotal += ej.getTiempo();
            
            if (ej.getTipo().equalsIgnoreCase("Cardio")) cardio++;
            else if (ej.getTipo().equalsIgnoreCase("Fuerza")) fuerza++;

            switch (ej.getNivel().toLowerCase()) {
                case "básico": basico++; break;
                case "intermedio": intermedio++; break;
                case "avanzado": avanzado++; break;
                case "alto rendimiento": altoRend++; break;
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("      MÉTRICAS DE LA RUTINA GENERADA     \n");
        sb.append("=========================================\n\n");
        sb.append("• Tiempo Total Estimado : ").append(tiempoTotal).append(" min\n\n");
        sb.append("Distribución por Tipo de Ejercicio:\n");
        sb.append("  - Cardiovascular     : ").append(cardio).append("\n");
        sb.append("  - Fuerza             : ").append(fuerza).append("\n\n");
        sb.append("Distribución por Nivel de Intensidad:\n");
        sb.append("  - Básico             : ").append(basico).append("\n");
        sb.append("  - Intermedio         : ").append(intermedio).append("\n");
        sb.append("  - Avanzado           : ").append(avanzado).append("\n");
        sb.append("  - Alto Rendimiento   : ").append(altoRend).append("\n");
        sb.append("=========================================");

        txtResumenConsolidado.setText(sb.toString());
    }
}