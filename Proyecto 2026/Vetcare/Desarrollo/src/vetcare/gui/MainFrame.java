package vetcare.gui;

import vetcare.db.ConexionDB;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MainFrame extends JFrame {

    static final Color VERDE      = new Color(0x14, 0x5A, 0x32);
    static final Color VERDE_MED  = new Color(0x1E, 0x8B, 0x4F);
    static final Color AMBAR      = new Color(0xB7, 0x7A, 0x0C);
    static final Color FONDO      = new Color(0xF4, 0xF6, 0xF7);
    static final Color VERDE_CLAR = new Color(0xEA, 0xFA, 0xF1);

    public MainFrame() {
        setTitle("VetCare — Sistema de Gestión de Clínica Veterinaria");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1050, 680);
        setLocationRelativeTo(null);
        construirUI();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int r = JOptionPane.showConfirmDialog(MainFrame.this,
                    "Desea cerrar VetCare?", "Confirmar salida",
                    JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    ConexionDB.cerrar();
                    dispose();
                    System.exit(0);
                }
            }
        });
    }

    private void construirUI() {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(FONDO);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(VERDE);
        header.setBorder(new EmptyBorder(10, 20, 10, 20));
        JLabel lblTitulo = new JLabel("  VETCARE", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Calibri", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel("Sistema de Gestión de Clínica Veterinaria  |  UPN 2026", SwingConstants.RIGHT);
        lblSub.setFont(new Font("Calibri", Font.PLAIN, 12));
        lblSub.setForeground(new Color(0xD5, 0xF5, 0xE3));
        header.add(lblTitulo, BorderLayout.WEST);
        header.add(lblSub, BorderLayout.EAST);

        // Pestanas
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Calibri", Font.BOLD, 13));
        tabs.setBackground(FONDO);
        tabs.addTab("Mascotas",      new PanelMascotas());
        tabs.addTab("Dueños",        new PanelDuenos());
        tabs.addTab("Veterinarios",  new PanelVeterinarios());
        tabs.addTab("Citas",         new PanelCitas());
        tabs.addTab("Historial",     new PanelHistorial());

        // Footer
        JPanel footer = new JPanel();
        footer.setBackground(AMBAR);
        JLabel pie = new JLabel("Conectado a vetcare_db | Kevin Chirinos — N00521954 | TPOO UPN 2026");
        pie.setForeground(Color.WHITE);
        pie.setFont(new Font("Calibri", Font.PLAIN, 11));
        footer.add(pie);

        contenedor.add(header, BorderLayout.NORTH);
        contenedor.add(tabs, BorderLayout.CENTER);
        contenedor.add(footer, BorderLayout.SOUTH);
        add(contenedor);
    }
}
