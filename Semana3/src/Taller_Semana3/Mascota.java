package Taller_Semana3;

import java.util.ArrayList;

/**
 * Gestiona una mascota y su historial de consultas.
 * Usa ArrayList para colecciones y try-catch para manejo de errores.
 */
public class Mascota {
    private String nombre;
    private String especie;
    private int    edadAnios;
    private ArrayList<String> historial;

    public Mascota(String nombre, String especie, int edadAnios) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la mascota no puede estar vacio.");
        if (edadAnios < 0)
            throw new IllegalArgumentException("La edad no puede ser negativa.");

        this.nombre    = nombre;
        this.especie   = especie;
        this.edadAnios = edadAnios;
        this.historial = new ArrayList<>();
    }

    public void agregarConsulta(String registro) {
        try {
            if (registro == null || registro.isBlank())
                throw new IllegalArgumentException("El registro de consulta no puede estar vacio.");
            historial.add(registro);
            System.out.println("  >> Consulta registrada para " + nombre);
        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    public void mostrarHistorial() {
        System.out.println("Historial de " + nombre + " (" + especie + ", " + edadAnios + " anios):");
        if (historial.isEmpty()) {
            System.out.println("  Sin consultas registradas.");
        } else {
            for (int i = 0; i < historial.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + historial.get(i));
            }
        }
    }

    public String getNombre()  { return nombre;  }
    public String getEspecie() { return especie; }
}
