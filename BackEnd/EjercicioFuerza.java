package backend;

public class EjercicioFuerza extends Ejercicio {
    public EjercicioFuerza(int id, String nombre, String nivel, int tiempo, String desc, int semana) {
        super(id, nombre, "Fuerza", nivel, tiempo, desc, semana);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("=== Ejercicio de FUERZA ===");
        System.out.println("Código: " + getCodigo());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Nivel: " + getNivel());
        System.out.println("Tiempo: " + getTiempo() + " min");
        System.out.println("Descripción: " + getDescripcion());
        System.out.println("Últ. semana: " + getSemanaUso());
    }
}