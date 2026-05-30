package Taller_Semana1_2;

import java.util.Scanner;

public class Caso1_Calculadora {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingresa el primer número: ");
        double num1 = sc.nextDouble();

        System.out.print("Ingresa el segundo número: ");
        double num2 = sc.nextDouble();

        System.out.println("Elige una operación: 1) Suma  2) Resta  3) Multiplicación  4) División");
        int opcion = sc.nextInt();

        double resultado = calcular(num1, num2, opcion);

        if (resultado == Double.MAX_VALUE) {
            System.out.println("Error: no se puede dividir entre cero.");
        } else if (opcion < 1 || opcion > 4) {
            System.out.println("Opción no válida.");
        } else {
            System.out.println("Resultado: " + resultado);
        }

        sc.close();
    }

    public static double calcular(double a, double b, int opcion) {
        switch (opcion) {
            case 1: return a + b;
            case 2: return a - b;
            case 3: return a * b;
            case 4:
                if (b == 0) return Double.MAX_VALUE;
                return a / b;
            default: return Double.MIN_VALUE;
        }
    }
}
