package frontend;

import backend.Sistema;
import backend.ObservadorSistema;
import backend.Ejercicio;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaPrincipal extends JFrame implements ObservadorSistema {
    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    private Sistema sistema;

    private PanelCarga panelCarga;
    private PanelGeneracion panelGeneracion;
    private PanelRevision panelRevision;
    private PanelResumen panelResumen;

    public VentanaPrincipal(Sistema sistema) {
        this.sistema = sistema;
        this.sistema.suscribir(this);

        setTitle("Sistema de Gestión de Rutinas de Entrenamiento");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        contenedorPrincipal = new JPanel(cardLayout);

        panelCarga = new PanelCarga(this, sistema);
        panelGeneracion = new PanelGeneracion(this, sistema);
        panelRevision = new PanelRevision(this);
        panelResumen = new PanelResumen(this);

        contenedorPrincipal.add(panelCarga, "CARGA");
        contenedorPrincipal.add(panelGeneracion, "GENERACION");
        contenedorPrincipal.add(panelRevision, "REVISION");
        contenedorPrincipal.add(panelResumen, "RESUMEN");

        add(contenedorPrincipal);
        cambiarVista("CARGA");
    }

    public void cambiarVista(String nombreVista) {
        cardLayout.show(contenedorPrincipal, nombreVista);
    }

    public PanelResumen getPanelResumen() {
        return this.panelResumen;
    }

    @Override
    public void onEjerciciosCargados(int total, int tiempoTotal, int cardio, int fuerza, int basico, int intermedio, int avanzado, int altoRend) {
        panelCarga.actualizarEstadisticasInterfaz(total, tiempoTotal, cardio, fuerza, basico, intermedio, avanzado, altoRend);
    }

    @Override
    public void onRutinaGenerada(List<Ejercicio> rutina) {
        if (rutina.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron ejercicios disponibles que cumplan los criterios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        panelRevision.cargarRutina(rutina);
        cambiarVista("REVISION");
    }

    @Override
    public void onError(String mensajeError) {
        JOptionPane.showMessageDialog(this, mensajeError, "Error del Sistema", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Sistema sistemaObj = new Sistema();
            new VentanaPrincipal(sistemaObj).setVisible(true);
        });
    }
}