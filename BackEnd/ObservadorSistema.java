package backend;

import java.util.List;

public interface ObservadorSistema {
    void onEjerciciosCargados(int total, int tiempoTotal, int cardio, int fuerza, int basico, int intermedio, int avanzado, int altoRend);
    void onRutinaGenerada(List<Ejercicio> rutina);
    void onError(String mensajeError);
}