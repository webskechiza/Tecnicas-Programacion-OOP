package vetcare;

import vetcare.gestion.GestorMascotas;
import vetcare.modelo.*;

import java.util.List;
import java.util.Scanner;

/**
 * VetCare - Sistema de Gestión de Clínica Veterinaria
 * Versión Avance: módulo de mascotas con herencia y polimorfismo.
 * Próximas versiones: gestión de citas, historial médico y persistencia.
 */
public class Main {

    private static final GestorMascotas gestor = new GestorMascotas();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   VetCare — Módulo de Mascotas v0.1  ║");
        System.out.println("╚══════════════════════════════════════╝");

        cargarDatosDemo();

        int opcion;
        do {
            System.out.println("\n── Menú Principal ──");
            System.out.println("1. Registrar mascota");
            System.out.println("2. Listar todas las mascotas");
            System.out.println("3. Buscar por nombre");
            System.out.println("4. Filtrar por especie");
            System.out.println("5. Demostración de polimorfismo");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = leerInt();

            switch (opcion) {
                case 1 -> registrarMascota();
                case 2 -> {
                    System.out.println("\n── Mascotas registradas ──");
                    gestor.listar();
                }
                case 3 -> buscarPorNombre();
                case 4 -> filtrarPorEspecie();
                case 5 -> demoPolimorfismo();
                case 0 -> System.out.println("Cerrando VetCare...");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void cargarDatosDemo() {
        gestor.agregar(new Mascota(gestor.getSiguienteId(),
                new Perro("Toby", "Labrador", 3, "Macho", 12.5, "Grande"),
                "Maria Lopez", "12345678"));
        gestor.agregar(new Mascota(gestor.getSiguienteId(),
                new Gato("Luna", "Siamés", 2, "Hembra", 4.0, false),
                "Carlos Ruiz", "87654321"));
        gestor.agregar(new Mascota(gestor.getSiguienteId(),
                new Ave("Pico", "Loro amazónico", 5, "Macho", 0.8, "Ganchudo"),
                "Ana Torres", "45678901"));
        System.out.println("3 mascotas de demo cargadas.\n");
    }

    private static void registrarMascota() {
        System.out.println("\n── Registrar Mascota ──");
        System.out.print("Especie (1-Perro / 2-Gato / 3-Ave): ");
        int esp = leerInt();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Raza: ");
        String raza = sc.nextLine().trim();
        System.out.print("Edad (años): ");
        int edad = leerInt();
        System.out.print("Sexo (Macho/Hembra): ");
        String sexo = sc.nextLine().trim();
        System.out.print("Peso (kg): ");
        double peso = leerDouble();
        System.out.print("Nombre del dueño: ");
        String dueno = sc.nextLine().trim();
        System.out.print("DNI del dueño: ");
        String dni = sc.nextLine().trim();

        Animal animal;
        switch (esp) {
            case 1 -> {
                System.out.print("Tamaño (Pequeño/Mediano/Grande): ");
                animal = new Perro(nombre, raza, edad, sexo, peso, sc.nextLine().trim());
            }
            case 2 -> {
                System.out.print("¿Castrado? (s/n): ");
                animal = new Gato(nombre, raza, edad, sexo, peso,
                        sc.nextLine().trim().equalsIgnoreCase("s"));
            }
            case 3 -> {
                System.out.print("Tipo de pico (Curvo/Recto/Ganchudo): ");
                animal = new Ave(nombre, raza, edad, sexo, peso, sc.nextLine().trim());
            }
            default -> { System.out.println("Especie no válida."); return; }
        }

        Mascota m = new Mascota(gestor.getSiguienteId(), animal, dueno, dni);
        gestor.agregar(m);
        System.out.println("Registrada: " + m);
    }

    private static void buscarPorNombre() {
        System.out.print("Nombre a buscar: ");
        String nombre = sc.nextLine().trim();
        List<Mascota> resultado = gestor.buscarPorNombre(nombre);
        if (resultado.isEmpty()) System.out.println("No se encontraron mascotas.");
        else resultado.forEach(m -> System.out.println("  " + m));
    }

    private static void filtrarPorEspecie() {
        System.out.print("Especie (Perro/Gato/Ave): ");
        String esp = sc.nextLine().trim();
        List<Mascota> resultado = gestor.filtrarPorEspecie(esp);
        if (resultado.isEmpty()) System.out.println("No hay mascotas de esa especie.");
        else resultado.forEach(m -> System.out.println("  " + m));
    }

    private static void demoPolimorfismo() {
        System.out.println("\n── Demostración de Polimorfismo ──");
        System.out.println("Cada especie responde getEspecie() con su propio valor:");
        for (Mascota m : gestor.getTodas()) {
            Animal a = m.getAnimal();
            System.out.println("  " + a.getNombre() + " → getEspecie() = \"" + a.getEspecie() + "\"");
        }
    }

    private static int leerInt() {
        while (true) {
            try { return Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Número válido: "); }
        }
    }

    private static double leerDouble() {
        while (true) {
            try { return Double.parseDouble(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Número decimal válido: "); }
        }
    }
}
