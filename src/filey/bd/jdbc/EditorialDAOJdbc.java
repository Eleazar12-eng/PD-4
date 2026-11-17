package filey.bd.jdbc;

import filey.bd.BDException;
import filey.bd.ConexionBD;
import filey.bd.EditorialDAO;
import filey.modelo.Editorial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Lee la tabla EDITORIAL con JDBC y convierte cada fila en un objeto Editorial. */
public class EditorialDAOJdbc implements EditorialDAO {
    private static final String SELECT = "SELECT id, nombre, correo, aprobada FROM editorial";

    private final ConexionBD conexion;

    public EditorialDAOJdbc(ConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Editorial> listarTodas() {
        try (Connection con = conexion.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT + " ORDER BY id");
             ResultSet rs = ps.executeQuery()) {
            List<Editorial> editoriales = new ArrayList<>();
            while (rs.next()) {
                editoriales.add(mapear(rs));
            }
            return editoriales;
        } catch (SQLException e) {
            throw new BDException("Error al leer la tabla EDITORIAL: " + e.getMessage(), e);
        }
    }

    @Override
    public Editorial buscarPorId(int id) {
        try (Connection con = conexion.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT + " WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new BDException("Error al buscar la editorial " + id + ": " + e.getMessage(), e);
        }
    }

    private Editorial mapear(ResultSet rs) throws SQLException {
        return new Editorial(
                rs.getInt("id"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getBoolean("aprobada"));
    }
}
