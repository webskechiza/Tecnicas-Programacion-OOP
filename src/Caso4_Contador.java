public class Caso4_Contador {

    // Variable estática: compartida entre todos los objetos
    static int contador = 0;

    public Caso4_Contador() {
        contador++;
        System.out.println("Objeto creado. Total de objetos: " + contador);
    }

    public static void main(String[] args) {
        new Caso4_Contador();
        new Caso4_Contador();
        new Caso4_Contador();

        System.out.println("Se crearon " + contador + " objetos en total.");
    }
}
