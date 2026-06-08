package Taller_Semana3;

/**
 * Practica de campo - Semana 3
 * Demuestra: sobrecarga de metodos, ArrayList, try-catch.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("=== VetCare - Demo Semana 3 ===\n");

        // --- Sobrecarga de metodos ---
        Veterinario vet = new Veterinario("Dr. Carlos Ramos", "Medicina General", "CMV-1042");

        System.out.println("-- Sobrecarga de registrarConsulta() --");
        System.out.println(vet.registrarConsulta("Otitis leve"));
        System.out.println(vet.registrarConsulta("Dermatitis", "Antibiotico topico"));
        System.out.println(vet.registrarConsulta("Fractura leve", "Inmovilizacion + analgesico", 120.50));

        System.out.println();

        // --- ArrayList + try-catch ---
        Mascota mascota = new Mascota("Rocky", "Perro", 3);
        mascota.agregarConsulta(vet.registrarConsulta("Control de rutina"));
        mascota.agregarConsulta(vet.registrarConsulta("Parasitos internos", "Desparasitante oral", 45.00));
        mascota.agregarConsulta("");        // error: vacio
        mascota.agregarConsulta(null);     // error: null

        System.out.println();
        mascota.mostrarHistorial();

        System.out.println();

        // --- Error en constructor ---
        System.out.println("-- Prueba de validaciones en constructor --");
        try {
            Mascota invalida = new Mascota("", "Gato", 2);
        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR capturado] " + e.getMessage());
        }
        try {
            Mascota edadNegativa = new Mascota("Luna", "Gato", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("  [ERROR capturado] " + e.getMessage());
        }

        System.out.println("\n=== Fin de la demostracion ===");
    }
}
