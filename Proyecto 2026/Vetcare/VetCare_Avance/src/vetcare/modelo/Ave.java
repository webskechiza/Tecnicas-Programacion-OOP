package vetcare.modelo;

public class Ave extends Animal {
    private String tipoPico;

    public Ave(String nombre, String raza, int edad, String sexo, double peso, String tipoPico) {
        super(nombre, raza, edad, sexo, peso);
        this.tipoPico = tipoPico;
    }

    public String getTipoPico() { return tipoPico; }

    @Override
    public String getEspecie() { return "Ave"; }

    @Override
    public String toString() {
        return super.toString() + " | Tipo de pico: " + tipoPico;
    }
}
