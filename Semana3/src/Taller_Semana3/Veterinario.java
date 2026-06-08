package Taller_Semana3;

/**
 * Clase que representa un veterinario.
 * Demuestra sobrecarga de metodos (method overloading).
 */
public class Veterinario {
    private String nombre;
    private String especialidad;
    private String colegiatura;

    public Veterinario(String nombre, String especialidad, String colegiatura) {
        this.nombre       = nombre;
        this.especialidad = especialidad;
        this.colegiatura  = colegiatura;
    }

    // Sobrecarga 1: solo diagnostico
    public String registrarConsulta(String diagnostico) {
        return "[" + nombre + "] Diagnostico: " + diagnostico;
    }

    // Sobrecarga 2: diagnostico + tratamiento
    public String registrarConsulta(String diagnostico, String tratamiento) {
        return "[" + nombre + "] Diagnostico: " + diagnostico + " | Tratamiento: " + tratamiento;
    }

    // Sobrecarga 3: diagnostico + tratamiento + costo
    public String registrarConsulta(String diagnostico, String tratamiento, double costo) {
        return "[" + nombre + "] Diagnostico: " + diagnostico
             + " | Tratamiento: " + tratamiento
             + " | Costo: S/. " + String.format("%.2f", costo);
    }

    public String getNombre() { return nombre; }
    public String getEspecialidad() { return especialidad; }

    @Override
    public String toString() {
        return "Veterinario{nombre='" + nombre + "', especialidad='" + especialidad + "'}";
    }
}
