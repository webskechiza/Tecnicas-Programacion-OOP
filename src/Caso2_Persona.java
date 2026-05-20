public class Caso2_Persona {
    // Atributos
    String nombre;
    int edad;

    // Constructor
    public Caso2_Persona(String nombre, int edad) {
        this.nombre = nombre;
        this.edad = edad;
    }

    // Método mostrarDatos
    public void mostrarDatos() {
        System.out.println("Nombre: " + nombre + " | Edad: " + edad + " años");
    }

    public static void main(String[] args) {
        Caso2_Persona p1 = new Caso2_Persona("Kevin", 20);
        Caso2_Persona p2 = new Caso2_Persona("María", 22);

        p1.mostrarDatos();
        p2.mostrarDatos();
    }
}
