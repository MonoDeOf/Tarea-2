package backend;

public class EjercicioCardio extends Ejercicio {
    public EjercicioCardio(int id, String nombre, String nivel, int tiempo, String desc, int semana) {
        super(id, nombre, "Cardio", nivel, tiempo, desc, semana);
    }

    @Override
    public void mostrarInfo() {
        System.out.println("=== Ejercicio de CARDIO ===");
        System.out.println("Código: " + getCodigo());
        System.out.println("Nombre: " + getNombre());
        System.out.println("Nivel: " + getNivel());
        System.out.println("Tiempo: " + getTiempo() + " min");
        System.out.println("Descripción: " + getDescripcion());
        System.out.println("Últ. semana: " + getSemanaUso());
    }
}