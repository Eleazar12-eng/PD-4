package filey.bd.jdbc;

import filey.bd.BDException;
import filey.bd.ConexionBD;
import filey.bd.StandDAO;
import filey.modelo.EstadoStand;
import filey.modelo.Stand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/** Lee la tabla STAND con JDBC y convierte cada fila en un objeto Stand. */
public class StandDAOJdbc implements StandDAO {
    private static final String SELECT =
            "SELECT numero, ancho, largo, costo_m2, estado FROM stand";

    private final ConexionBD conexion;

    public StandDAOJdbc(ConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Stand> listarTodos() {
        try (Connection con = conexion.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT + " ORDER BY numero");
             ResultSet rs = ps.executeQuery()) {
            List<Stand> stands = new ArrayList<>();
            while (rs.next()) {
                stands.add(mapear(rs));
            }
            // El orden por área se hace aquí (no en SQL) para que la consulta sea estándar en cualquier motor.
            // sort() es estable: a igual área se conserva el orden por número.
            stands.sort(Comparator.comparingDouble(Stand::calcularArea));
            return stands;
        } catch (SQLException e) {
            throw new BDException("Error al leer la tabla STAND: " + e.getMessage(), e);
        }
    }

    @Override
    public Stand buscarPorNumero(int numero) {
        try (Connection con = conexion.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT + " WHERE numero = ?")) {
            ps.setInt(1, numero);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new BDException("Error al buscar el stand " + numero + ": " + e.getMessage(), e);
        }
    }

    /** Fila -> objeto. */
    private Stand mapear(ResultSet rs) throws SQLException {
        return new Stand(
                rs.getInt("numero"),
                rs.getDouble("ancho"),
                rs.getDouble("largo"),
                rs.getDouble("costo_m2"),
                EstadoStand.valueOf(rs.getString("estado")));
    }
}
