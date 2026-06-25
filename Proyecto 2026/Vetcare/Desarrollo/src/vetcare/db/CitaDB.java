package vetcare.db;

import vetcare.modelo.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDB {

    public static List<Object[]> listarFilas() throws SQLException {
        List<Object[]> filas = new ArrayList<>();
        String sql = "SELECT c.id, m.nombre AS mascota, v.nombre AS veterinario, " +
                     "c.fecha, c.hora, c.motivo, c.estado, c.motivo_cancelacion " +
                     "FROM citas c " +
                     "JOIN mascotas     m ON c.id_mascota     = m.id " +
                     "JOIN veterinarios v ON c.id_veterinario = v.id " +
                     "ORDER BY c.fecha, c.hora";
        try (Statement st = ConexionDB.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                filas.add(new Object[]{
                    rs.getInt("id"),
                    rs.getString("mascota"),
                    rs.getString("veterinario"),
                    rs.getString("fecha"),
                    rs.getString("hora"),
                    rs.getString("motivo"),
                    rs.getString("estado"),
                    rs.getString("motivo_cancelacion")
                });
            }
        }
        return filas;
    }

    public static void insertar(int idMascota, int idVeterinario,
                                String fecha, String hora, String motivo) throws SQLException {
        String sql = "INSERT INTO citas (id_mascota, id_veterinario, fecha, hora, motivo, estado) " +
                     "VALUES (?,?,?,?,?,'PENDIENTE')";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, idMascota);
            ps.setInt(2, idVeterinario);
            ps.setString(3, fecha);
            ps.setString(4, hora);
            ps.setString(5, motivo);
            ps.executeUpdate();
        }
    }

    public static void actualizarEstado(int id, String estado, String motivoCancelacion) throws SQLException {
        String sql = "UPDATE citas SET estado = ?, motivo_cancelacion = ? WHERE id = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, estado);
            ps.setString(2, motivoCancelacion == null ? "" : motivoCancelacion);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public static void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM citas WHERE id = ?";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public static boolean hayConflicto(String fecha, String hora, int idVeterinario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM citas WHERE fecha = ? AND hora = ? " +
                     "AND id_veterinario = ? AND estado = 'PENDIENTE'";
        try (PreparedStatement ps = ConexionDB.getConexion().prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setString(2, hora);
            ps.setInt(3, idVeterinario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }
}
