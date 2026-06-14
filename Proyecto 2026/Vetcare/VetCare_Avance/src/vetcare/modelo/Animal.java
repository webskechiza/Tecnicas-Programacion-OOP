package vetcare.modelo;

public abstract class Animal {
    private String nombre;
    private String raza;
    private int    edad;
    private String sexo;
    private double peso;

    public Animal(String nombre, String raza, int edad, String sexo, double peso) {
        this.nombre = nombre;
        this.raza   = raza;
        this.edad   = edad;
        this.sexo   = sexo;
        this.peso   = peso;
    }

    public String getNombre() { return nombre; }
    public String getRaza()   { return raza; }
    public int    getEdad()   { return edad; }
    public String getSexo()   { return sexo; }
    public double getPeso()   { return peso; }

    // Método abstracto que cada especie implementa
    public abstract String getEspecie();

    @Override
    public String toString() {
        return getEspecie() + ": " + nombre + " | Raza: " + raza
                + " | " + edad + " año(s) | " + sexo + " | " + peso + " kg";
    }
}
