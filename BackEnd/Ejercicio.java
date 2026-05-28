package backend;

public abstract class Ejercicio {
    private int codigoIdentificador;
    private String nombreEjercicio; 
    private String tipoEjercicio; 
    private String nivelIntensidad; 
    private int tiempoMinutos; 
    private String descripcion;
    private int semanaUltimoUso;

    public Ejercicio(int codigoIdentificador, String nombreEjercicio, String tipoEjercicio, 
                     String nivelIntensidad, int tiempoMinutos, String descripcion, int semanaUltimoUso) {
        this.codigoIdentificador = codigoIdentificador;
        this.nombreEjercicio = nombreEjercicio;
        this.tipoEjercicio = tipoEjercicio;
        this.nivelIntensidad = nivelIntensidad;
        this.tiempoMinutos = tiempoMinutos;
        this.descripcion = descripcion;
        this.semanaUltimoUso = semanaUltimoUso;
    }
    
    public int getCodigo() { return this.codigoIdentificador; }
    public String getNombre() { return this.nombreEjercicio; }
    public String getTipo() { return this.tipoEjercicio; }
    public String getNivel() { return this.nivelIntensidad; }
    public int getTiempo() { return this.tiempoMinutos; }
    public String getDescripcion() { return this.descripcion; }
    public int getSemanaUso() { return this.semanaUltimoUso; }
    
    public void setSemanaUso(int semana) { this.semanaUltimoUso = semana; }
    
    public void actualizar(String nombre, String nivel, int tiempo, String desc) {
        this.nombreEjercicio = nombre;
        this.nivelIntensidad = nivel;
        this.tiempoMinutos = tiempo;
        this.descripcion = desc;
    }
    
    public abstract void mostrarInfo();
}