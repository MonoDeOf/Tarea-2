package backend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Ejercicio> catalogo = new ArrayList<>();
    private List<ObservadorSistema> observadores = new ArrayList<>();

    public void suscribir(ObservadorSistema obs) {
        observadores.add(obs);
    }

    public void cargarEjerciciosDesdeArchivo(String rutaArchivo) {
        catalogo.clear();
        int tiempoTotal = 0, cardio = 0, fuerza = 0;
        int basico = 0, intermedio = 0, avanzado = 0, altoRend = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 7) throw new IllegalArgumentException("Información incompleta en línea.");

                int id = Integer.parseInt(datos[0].trim());
                String nombre = datos[1].trim();
                String tipo = datos[2].trim();
                String nivel = datos[3].trim();
                int tiempo = Integer.parseInt(datos[4].trim());
                String desc = datos[5].trim();
                int semana = Integer.parseInt(datos[6].trim());

                Ejercicio ej;
                if (tipo.equalsIgnoreCase("Fuerza")) {
                    ej = new EjercicioFuerza(id, nombre, nivel, tiempo, desc, semana);
                    fuerza++;
                } else if (tipo.equalsIgnoreCase("Cardio")) {
                    ej = new EjercicioCardio(id, nombre, nivel, tiempo, desc, semana);
                    cardio++;
                } else {
                    continue; 
                }

                tiempoTotal += tiempo;
                switch (nivel.toLowerCase()) {
                    case "básico": basico++; break;
                    case "intermedio": intermedio++; break;
                    case "avanzado": avanzado++; break;
                    case "alto rendimiento": altoRend++; break;
                }
                catalogo.add(ej);
            }
            
            for (ObservadorSistema obs : observadores) {
                obs.onEjerciciosCargados(catalogo.size(), tiempoTotal, cardio, fuerza, basico, intermedio, avanzado, altoRend);
            }

        } catch (IOException | IllegalArgumentException e) {
            for (ObservadorSistema obs : observadores) {
                obs.onError("Error al cargar archivo: " + e.getMessage());
            }
        }
    }

    public void generarRutina(int cantCardio, int cantFuerza, String nivelReq, int semanaActual) {
        List<Ejercicio> rutinaGenerada = new ArrayList<>();
        int cardioAgregados = 0;
        int fuerzaAgregados = 0;

        for (Ejercicio ej : catalogo) {
            boolean cumpleSemana = (semanaActual - ej.getSemanaUso()) > 1 || ej.getSemanaUso() == 0;
            boolean cumpleNivel = ej.getNivel().equalsIgnoreCase(nivelReq);

            if (cumpleSemana && cumpleNivel) {
                if (ej.getTipo().equalsIgnoreCase("Cardio") && cardioAgregados < cantCardio) {
                    rutinaGenerada.add(ej);
                    cardioAgregados++;
                } else if (ej.getTipo().equalsIgnoreCase("Fuerza") && fuerzaAgregados < cantFuerza) {
                    rutinaGenerada.add(ej);
                    fuerzaAgregados++;
                }
            }
            if (cardioAgregados == cantCardio && fuerzaAgregados == cantFuerza) break;
        }

        for (Ejercicio ej : rutinaGenerada) {
            ej.setSemanaUso(semanaActual);
        }

        for (ObservadorSistema obs : observadores) {
            obs.onRutinaGenerada(rutinaGenerada);
        }
    }
}