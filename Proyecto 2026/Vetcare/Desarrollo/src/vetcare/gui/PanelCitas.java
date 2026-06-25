package vetcare.gui;

import vetcare.db.CitaDB;
import vetcare.db.MascotaDB;
import vetcare.db.VeterinarioDB;
import vetcare.modelo.Mascota;
import vetcare.modelo.Veterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class PanelCitas extends JPanel {

    private DefaultTableModel modelo;
    private JTable            tabla;

    private static final String[] COLUMNAS =
        {"ID", "Mascota", "Veterinario", "Fecha", "Hora", "Motivo", "Estado", "Cancelación"};

    public PanelCitas() {
        setLayout(new BorderLayout());
        setBackground(MainFrame.FONDO);
        construirUI();
        cargarTabla();
    }

    private void construirUI() {
        modelo = new DefaultTableModel(COLUMNAS, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabla = new JTable(modelo);
        tabla.setFont(new Font("Calibri", Font.PLAIN, 12));
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(MainFrame.VERDE);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(new Color(0xD5, 0xF5, 0xE3));
        tabla.getColumnModel().getColumn(0).setMaxWidth(45);

        JScrollPane scroll = new JScrollPane(tabla);

        JPanel barraBot = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        barraBot.setBackground(MainFrame.VERDE_CLAR);
        JButton btnNuevo    = boton("+ Agendar cita",        MainFrame.VERDE);
        JButton btnAtend    = boton("Marcar Atendida",        MainFrame.VERDE_MED);
        JButton btnNoAsis   = boton("Marcar No asistió",      MainFrame.AMBAR);
        JButton btnCancelar = boton("Cancelar cita",          new Color(0xC0, 0x39, 0x2B));
        JButton btnRef      = boton("Actualizar tabla",        new Color(0x2C, 0x3E, 0x50));

        btnNuevo.addActionListener(e    -> abrirFormulario());
        btnAtend.addActionListener(e    -> actualizarEstado("ATENDIDA", null));
        btnNoAsis.addActionListener(e   -> actualizarEstado("NO_ASISTIO", null));
        btnCancelar.addActionListener(e -> cancelar());
        btnRef.addActionListener(e      -> cargarTabla());

        barraBot.add(btnNuevo); barraBot.add(btnAtend); barraBot.add(btnNoAsis);
        barraBot.add(btnCancelar); barraBot.add(btnRef);

        add(scroll,   BorderLayout.CENTER);
        add(barraBot, BorderLayout.SOUTH);
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        try {
            for (Object[] fila : CitaDB.listarFilas())
                modelo.addRow(fila);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormulario() {
        // Cargar mascotas y veterinarios
        DefaultComboBoxModel<String> mMascotas  = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> mVets      = new DefaultComboBoxModel<>();
        List<Mascota>      mascotas;
        List<Veterinario>  veterinarios;
        try {
            mascotas     = MascotaDB.listar();
            veterinarios = VeterinarioDB.listar();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + ex.getMessage()); return;
        }
        if (mascotas.isEmpty())     { JOptionPane.showMessageDialog(this, "No hay mascotas registradas."); return; }
        if (veterinarios.isEmpty()) { JOptionPane.showMessageDialog(this, "No hay veterinarios registrados."); return; }

        mascotas.forEach(m -> mMascotas.addElement(m.getId() + " - " + m.getAnimal().getNombre()));
        veterinarios.stream().filter(Veterinario::isActivo)
            .forEach(v -> mVets.addElement(v.getId() + " - Dr/a. " + v.getNombre()));

        JComboBox<String> cbMasc = new JComboBox<>(mMascotas);
        JComboBox<String> cbVet  = new JComboBox<>(mVets);
        JTextField fFecha  = new JTextField("dd/MM/yyyy", 12);
        JTextField fHora   = new JTextField("HH:mm", 6);
        JTextField fMotivo = new JTextField(25);

        Object[] campos = {
            "Mascota:", cbMasc,
            "Veterinario:", cbVet,
            "Fecha (dd/MM/yyyy):", fFecha,
            "Hora (HH:mm):", fHora,
            "Motivo:", fMotivo
        };
        int r = JOptionPane.showConfirmDialog(this, campos, "Agendar Cita",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return;

        try {
            int idMasc = mascotas.get(cbMasc.getSelectedIndex()).getId();
            int idVet  = veterinarios.stream().filter(Veterinario::isActivo)
                .collect(java.util.stream.Collectors.toList())
                .get(cbVet.getSelectedIndex()).getId();

            String fecha  = fFecha.getText().trim();
            String hora   = fHora.getText().trim();
            String motivo = fMotivo.getText().trim();

            if (CitaDB.hayConflicto(fecha, hora, idVet)) {
                JOptionPane.showMessageDialog(this,
                    "El veterinario ya tiene una cita en ese horario.", "Conflicto", JOptionPane.WARNING_MESSAGE);
                return;
            }
            CitaDB.insertar(idMasc, idVet, fecha, hora, motivo);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Cita agendada correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarEstado(String estado, String motivoCancelacion) {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una cita."); return; }
        int    id          = (int) modelo.getValueAt(fila, 0);
        String estadoActual = (String) modelo.getValueAt(fila, 6);
        if (!"PENDIENTE".equals(estadoActual)) {
            JOptionPane.showMessageDialog(this, "Solo se pueden cambiar citas PENDIENTES.");
            return;
        }
        try {
            CitaDB.actualizarEstado(id, estado, motivoCancelacion);
            cargarTabla();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una cita."); return; }
        String motivo = JOptionPane.showInputDialog(this, "Motivo de cancelación:");
        if (motivo == null) return;
        actualizarEstado("CANCELADA", motivo);
    }

    private JButton boton(String texto, Color fondo) {
        JButton b = new JButton(texto);
        b.setBackground(fondo); b.setForeground(Color.WHITE);
        b.setFont(new Font("Calibri", Font.BOLD, 12));
        b.setFocusPainted(false); b.setBorderPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}
