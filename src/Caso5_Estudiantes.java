import java.util.ArrayList;
import java.util.Scanner;

public class Caso5_Estudiantes {
    public static void main(String[] args) {
        ArrayList<String> estudiantes = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String entrada;

        System.out.println("Ingresa nombres de estudiantes (escribe 'fin' para terminar):");

        while (true) {
            try {
                System.out.print("Nombre: ");
                entrada = sc.nextLine().trim();

                if (entrada.equalsIgnoreCase("fin")) break;

                if (entrada.isEmpty()) {
                    throw new IllegalArgumentException("El nombre no puede estar vacío.");
                }

                estudiantes.add(entrada);
                System.out.println("  ✔ Agregado: " + entrada);

            } catch (IllegalArgumentException e) {
                System.out.println("  Error: " + e.getMessage());
            }
        }

        System.out.println("\n--- Lista de estudiantes ---");
        if (estudiantes.isEmpty()) {
            System.out.println("No se registró ningún estudiante.");
        } else {
            for (int i = 0; i < estudiantes.size(); i++) {
                System.out.println((i + 1) + ". " + estudiantes.get(i));
            }
        }

        sc.close();
    }
}
