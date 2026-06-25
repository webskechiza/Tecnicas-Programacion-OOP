package vetcare.util;

import vetcare.gestion.GestorCitas;
import vetcare.gestion.GestorMascotas;
import vetcare.modelo.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaArchivos {

    private final String rutaDuenos;
    private final String rutaVeterinarios;
    private final String rutaMascotas;
    private final String rutaConsultas;
    private final String rutaCitas;

    public PersistenciaArchivos(String carpetaData) {
        this.rutaDuenos       = carpetaData + File.separator + "duenos.txt";
        this.rutaVeterinarios = carpetaData + File.separator + "veterinarios.txt";
        this.rutaMascotas     = carpetaData + File.separator + "mascotas.txt";
        this.rutaConsultas    = carpetaData + File.separator + "consultas.txt";
        this.rutaCitas        = carpetaData + File.separator + "citas.txt";

        new File(carpetaData).mkdirs();
    }

    // ── DUEÑOS ────────────────────────────────────────────────────────────────
    public void guardarDuenos(List<Dueno> lista) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(rutaDuenos), StandardCharsets.UTF_8))) {
            for (Dueno d : lista) pw.println(d.toLinea());
        } catch (IOException e) {
            System.err.println("Error al guardar dueños: " + e.getMessage());
        }
    }

    public List<Dueno> cargarDuenos() {
        List<Dueno> lista = new ArrayList<>();
        File f = new File(rutaDuenos);
        if (!f.exists()) return lista;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null)
                if (!linea.trim().isEmpty()) lista.add(Dueno.fromLinea(linea));
        } catch (IOException e) {
            System.err.println("Error al cargar dueños: " + e.getMessage());
        }
        return lista;
    }

    // ── VETERINARIOS ─────────────────────────────────────────────────────────
    public void guardarVeterinarios(List<Veterinario> lista) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(rutaVeterinarios), StandardCharsets.UTF_8))) {
            for (Veterinario v : lista) pw.println(v.toLinea());
        } catch (IOException e) {
            System.err.println("Error al guardar veterinarios: " + e.getMessage());
        }
    }

    public List<Veterinario> cargarVeterinarios() {
        List<Veterinario> lista = new ArrayList<>();
        File f = new File(rutaVeterinarios);
        if (!f.exists()) return lista;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null)
                if (!linea.trim().isEmpty()) lista.add(Veterinario.fromLinea(linea));
        } catch (IOException e) {
            System.err.println("Error al cargar veterinarios: " + e.getMessage());
        }
        return lista;
    }

    // ── MASCOTAS (solo datos básicos, sin historial) ───────────────────────
    // El historial se guarda en consultas.txt enlazado por id de mascota
    public void guardarMascotas(List<Mascota> lista) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(rutaMascotas), StandardCharsets.UTF_8))) {
            for (Mascota m : lista) {
                Animal a = m.getAnimal();
                String animalLinea;
                if      (a instanceof Perro) animalLinea = ((Perro) a).toLinea();
                else if (a instanceof Gato)  animalLinea = ((Gato)  a).toLinea();
                else                          animalLinea = ((Ave)   a).toLinea();

                pw.println(m.getId() + ";" + m.getDueno().getDni() + ";"
                        + animalLinea + ";" + m.getObservaciones());
            }
        } catch (IOException e) {
            System.err.println("Error al guardar mascotas: " + e.getMessage());
        }
    }

    // ── CONSULTAS ─────────────────────────────────────────────────────────────
    public void guardarConsultas(List<Mascota> lista) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(rutaConsultas), StandardCharsets.UTF_8))) {
            for (Mascota m : lista)
                for (ConsultaMedica c : m.getHistorial())
                    pw.println(m.getId() + ";" + c.toLinea());
        } catch (IOException e) {
            System.err.println("Error al guardar consultas: " + e.getMessage());
        }
    }

    public void cargarConsultasEnMascotas(GestorMascotas gestor) {
        File f = new File(rutaConsultas);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                int sep = linea.indexOf(';');
                int idMascota = Integer.parseInt(linea.substring(0, sep).trim());
                String resto  = linea.substring(sep + 1);
                Mascota m = gestor.buscarPorId(idMascota);
                if (m != null) m.agregarConsulta(ConsultaMedica.fromLinea(resto));
            }
        } catch (IOException e) {
            System.err.println("Error al cargar consultas: " + e.getMessage());
        }
    }
}
