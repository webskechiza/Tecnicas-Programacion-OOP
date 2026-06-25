package vetcare.db;

import vetcare.modelo.ConsultaMedica;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDB {

    public static List<ConsultaMedica> listarPorMascota(int idMascota) throws SQLException {
        List<ConsultaMedica> lista = new ArrayList<>();
        String sql = "SELECT * FROM consultas WHERE id_mascota = ? ORDER BY id DESC";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idMascota);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ConsultaMedica(
                        rs.getInt("id"),
                        rs.getString("fecha"),
                        rs.getString("diagnostico"),
                        rs.getString("tratamiento"),
                        rs.getString("observaciones")
                    ));
                }
            }
        }
        return lista;
    }

    public static void insertar(int idMascota, String fecha,
                                String diagnostico, String tratamiento,
                                String observaciones) throws SQLException {
        String sql = "INSERT INTO consultas (id_mascota, fecha, diagnostico, tratamiento, observaciones) " +
                     "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idMascota);
            ps.setString(2, fecha);
            ps.setString(3, diagnostico);
            ps.setString(4, tratamiento);
            ps.setString(5, observaciones);
            ps.executeUpdate();
        }
    }

    public static void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM consultas WHERE id = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
