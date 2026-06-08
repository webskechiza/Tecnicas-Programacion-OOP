package Taller_Semana1_2;

public class Caso3_Sobrecarga {

    public static int sumar(int a, int b) {
        return a + b;
    }

    public static double sumar(double a, double b) {
        return a + b;
    }

    public static int sumar(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        System.out.println("sumar(3, 4)          = " + sumar(3, 4));
        System.out.println("sumar(2.5, 1.5)      = " + sumar(2.5, 1.5));
        System.out.println("sumar(1, 2, 3)       = " + sumar(1, 2, 3));
    }
}
