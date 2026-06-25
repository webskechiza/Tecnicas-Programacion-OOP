package vetcare.gui;

import vetcare.db.ConsultaDB;
import vetcare.db.MascotaDB;
import vetcare.modelo.ConsultaMedica;
import vetcare.modelo.Mascota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PanelHistorial extends JPanel {

    private DefaultTableModel modelo;
    private JTable            tabla;
    private JComboBox<String> cbMascota;
    private List<Mascota>     mascotas;

    private static final String[] COLUMNAS =
        {"ID", "Fecha", "Diagnostico", "Tratamiento", "Observaciones"};

    public PanelHistorial() {
        setLayout(new BorderLayout());
        setBackground(MainFrame.FONDO);
        construirUI();
        cargarMascotas();
    }

    private void construirUI() {
        // Barra superior: selector de mascota
        JPanel barraTop = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barraTop.setBackground(MainFrame.VERDE_CLAR);
        JLabel lbl = new JLabel("Mascota:");
        lbl.setFont(new Font("Calibri", Font.BOLD, 12));
        lbl.setForeground(MainFrame.VERDE);
        cbMascota = new JComboBox<>();
        cbMascota.setFont(new Font("Calibri", Font.PLAIN, 12));
        cbMascota.setPreferredSize(new Dimension(260, 26));
        JButton btnVer = boton("Ver historial", MainFrame.VERDE);
        JButton btnRef = boton("Actualizar lista", MainFrame.VERDE_MED);
        btnVer.addActionListener(e -> cargarHistorial());
        btnRef.addActionListener(e -> cargarMascotas());
        barraTop.add(lbl); barraTop.add(cbMascota); barraTop.add(btnVer); barraTop.add(btnRef);

        // Tabla
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
        tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(220);

        JScrollPane scroll = new JScrollPane(tabla);

        // Barra inferior: registrar consulta + eliminar
        JPanel barraBot = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        barraBot.setBackground(MainFrame.VERDE_CLAR);
        JButton btnNueva = boton("+ Registrar consulta",  MainFrame.VERDE);
        JButton btnElim  = boton("Eliminar seleccion",     new Color(0xC0, 0x39, 0x2B));
        btnNueva.addActionListener(e -> registrarConsulta());
        btnElim.addActionListener(e  -> eliminarConsulta());
        barraBot.add(btnNueva); barraBot.add(btnElim);

        add(barraTop, BorderLayout.NORTH);
        add(scroll,   BorderLayout.CENTER);
        add(barraBot, BorderLayout.SOUTH);
    }

    private void cargarMascotas() {
        cbMascota.removeAllItems();
        try {
            mascotas = MascotaDB.listar();
            mascotas.forEach(m -> cbMascota.addItem(m.getId() + " — " + m.getAnimal().getNombre()
                + " (" + m.getAnimal().getEspecie() + ")"));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHistorial() {
        modelo.setRowCount(0);
        if (cbMascota.getSelectedIndex() < 0 || mascotas == null || mascotas.isEmpty()) return;
        int idMascota = mascotas.get(cbMascota.getSelectedIndex()).getId();
        try {
            for (ConsultaMedica c : ConsultaDB.listarPorMascota(idMascota))
                modelo.addRow(new Object[]{
                    c.getId(), c.getFecha(), c.getDiagnostico(),
                    c.getTratamiento(), c.getObservaciones()
                });
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registrarConsulta() {
        if (cbMascota.getSelectedIndex() < 0 || mascotas == null || mascotas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecciona primero una mascota.");
            return;
        }
        int idMascota = mascotas.get(cbMascota.getSelectedIndex()).getId();

        JTextField fFecha   = new JTextField(LocalDate.now().toString(), 12);
        JTextField fDiag    = new JTextField(30);
        JTextField fTrat    = new JTextField(30);
        JTextField fObs     = new JTextField(30);

        Object[] campos = {
            "Fecha (yyyy-MM-dd):", fFecha,
            "Diagnostico:",        fDiag,
            "Tratamiento:",        fTrat,
            "Observaciones:",      fObs
        };
        int r = JOptionPane.showConfirmDialog(this, campos, "Registrar Consulta Medica",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (r != JOptionPane.OK_OPTION) return;

        String diag = fDiag.getText().trim();
        if (diag.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El diagnostico es obligatorio.");
            return;
        }
        try {
            ConsultaDB.insertar(idMascota, fFecha.getText().trim(), diag,
                fTrat.getText().trim(), fObs.getText().trim());
            cargarHistorial();
            JOptionPane.showMessageDialog(this, "Consulta registrada correctamente.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarConsulta() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona una consulta."); return; }
        int id = (int) modelo.getValueAt(fila, 0);
        int conf = JOptionPane.showConfirmDialog(this, "Eliminar consulta #" + id + "?",
            "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf != JOptionPane.YES_OPTION) return;
        try {
            ConsultaDB.eliminar(id);
            cargarHistorial();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
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
