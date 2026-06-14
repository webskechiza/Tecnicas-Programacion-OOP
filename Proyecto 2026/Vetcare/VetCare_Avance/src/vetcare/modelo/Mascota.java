package vetcare.modelo;

public class Mascota {
    private int    id;
    private Animal animal;
    private String nombreDueno;
    private String dniDueno;

    public Mascota(int id, Animal animal, String nombreDueno, String dniDueno) {
        this.id          = id;
        this.animal      = animal;
        this.nombreDueno = nombreDueno;
        this.dniDueno    = dniDueno;
    }

    public int    getId()          { return id; }
    public Animal getAnimal()      { return animal; }
    public String getNombreDueno() { return nombreDueno; }
    public String getDniDueno()    { return dniDueno; }

    @Override
    public String toString() {
        return "[" + id + "] " + animal + " | Dueño: " + nombreDueno;
    }
}
