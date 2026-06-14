package vetcare.gestion;

import vetcare.modelo.Mascota;
import java.util.ArrayList;
import java.util.List;

public class GestorMascotas {
    private ArrayList<Mascota> mascotas;
    private int                siguienteId;

    public GestorMascotas() {
        mascotas    = new ArrayList<>();
        siguienteId = 1;
    }

    public void agregar(Mascota m) {
        mascotas.add(m);
        siguienteId++;
    }

    public int getSiguienteId() { return siguienteId; }

    public ArrayList<Mascota> getTodas() { return mascotas; }

    public List<Mascota> buscarPorNombre(String nombre) {
        List<Mascota> resultado = new ArrayList<>();
        for (Mascota m : mascotas)
            if (m.getAnimal().getNombre().toLowerCase().contains(nombre.toLowerCase()))
                resultado.add(m);
        return resultado;
    }

    public List<Mascota> filtrarPorEspecie(String especie) {
        List<Mascota> resultado = new ArrayList<>();
        for (Mascota m : mascotas)
            if (m.getAnimal().getEspecie().equalsIgnoreCase(especie))
                resultado.add(m);
        return resultado;
    }

    public void listar() {
        if (mascotas.isEmpty()) {
            System.out.println("  No hay mascotas registradas.");
            return;
        }
        for (Mascota m : mascotas)
            System.out.println("  " + m);
    }
}
