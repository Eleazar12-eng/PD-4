package filey.bd.jdbc;

import filey.bd.BDException;
import filey.bd.ConexionBD;
import filey.bd.ReservacionDAO;
import filey.modelo.Editorial;
import filey.modelo.EstadoStand;
import filey.modelo.Reservacion;
import filey.modelo.Stand;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Lee la tabla RESERVACION. Como una reservación relaciona una editorial con un stand,
 * la consulta hace un JOIN con las otras dos tablas y arma el objeto completo.
 */
public class ReservacionDAOJdbc implements ReservacionDAO {
    private static final String SELECT =
            "SELECT r.identificador, r.fecha_reservacion, r.fecha_limite_pago, r.monto_total, r.monto_pagado, "
          + "e.id AS e_id, e.nombre AS e_nombre, e.correo AS e_correo, e.aprobada AS e_aprobada, "
          + "s.numero AS s_numero, s.ancho AS s_ancho, s.largo AS s_largo, "
          + "s.costo_m2 AS s_costo_m2, s.estado AS s_estado "
          + "FROM reservacion r "
          + "JOIN editorial e ON r.editorial_id = e.id "
          + "JOIN stand s ON r.stand_numero = s.numero";

    private final ConexionBD conexion;

    public ReservacionDAOJdbc(ConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public List<Reservacion> listarTodas() {
        try (Connection con = conexion.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT + " ORDER BY r.identificador");
             ResultSet rs = ps.executeQuery()) {
            List<Reservacion> reservaciones = new ArrayList<>();
            while (rs.next()) {
                reservaciones.add(mapear(rs));
            }
            return reservaciones;
        } catch (SQLException e) {
            throw new BDException("Error al leer la tabla RESERVACION: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservacion buscarPorIdentificador(String identificador) {
        try (Connection con = conexion.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT + " WHERE r.identificador = ?")) {
            ps.setString(1, identificador);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException e) {
            throw new BDException("Error al buscar la reservación " + identificador + ": " + e.getMessage(), e);
        }
    }

    private Reservacion mapear(ResultSet rs) throws SQLException {
        Editorial editorial = new Editorial(
                rs.getInt("e_id"),
                rs.getString("e_nombre"),
                rs.getString("e_correo"),
                rs.getBoolean("e_aprobada"));
        Stand stand = new Stand(
                rs.getInt("s_numero"),
                rs.getDouble("s_ancho"),
                rs.getDouble("s_largo"),
                rs.getDouble("s_costo_m2"),
                EstadoStand.valueOf(rs.getString("s_estado")));
        return new Reservacion(
                rs.getString("identificador"),
                editorial,
                stand,
                rs.getDate("fecha_reservacion").toLocalDate(),
                rs.getDate("fecha_limite_pago").toLocalDate(),
                rs.getDouble("monto_total"),
                rs.getDouble("monto_pagado"));
    }
}
