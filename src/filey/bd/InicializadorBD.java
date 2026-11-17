package filey.bd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 * Crea las tablas y carga datos de ejemplo. Se usa porque la base de datos es en memoria
 * (nace vacía cada vez que corre el programa). El SQL es estándar, sin sintaxis propia de H2.
 */
public class InicializadorBD {
    private final ConexionBD conexion;

    public InicializadorBD(ConexionBD conexion) {
        this.conexion = conexion;
    }

    /** Crea el esquema y los datos de ejemplo si las tablas aún no existen. */
    public void crearEsquemaYDatos() {
        try (Connection con = conexion.obtener()) {
            if (existeTabla(con, "STAND")) {
                return; // ya está creada
            }
            crearTablas(con);
            cargarDatos(con);
        } catch (SQLException e) {
            throw new BDException("No se pudo inicializar la base de datos: " + e.getMessage(), e);
        }
    }

    private boolean existeTabla(Connection con, String nombre) throws SQLException {
        try (ResultSet rs = con.getMetaData().getTables(null, null, nombre, null)) {
            return rs.next();
        }
    }

    private void crearTablas(Connection con) throws SQLException {
        try (Statement st = con.createStatement()) {
            st.execute("CREATE TABLE editorial ("
                    + "id INT NOT NULL PRIMARY KEY, "
                    + "nombre VARCHAR(100) NOT NULL, "
                    + "correo VARCHAR(100) NOT NULL, "
                    + "aprobada BOOLEAN NOT NULL)");

            st.execute("CREATE TABLE stand ("
                    + "numero INT NOT NULL PRIMARY KEY, "
                    + "ancho DOUBLE NOT NULL, "
                    + "largo DOUBLE NOT NULL, "
                    + "costo_m2 DOUBLE NOT NULL, "
                    + "estado VARCHAR(20) NOT NULL, "
                    + "CONSTRAINT ck_stand_estado CHECK "
                    + "(estado IN ('DISPONIBLE', 'RESERVADO', 'OCUPADO', 'BLOQUEADO')))");

            st.execute("CREATE TABLE reservacion ("
                    + "identificador VARCHAR(20) NOT NULL PRIMARY KEY, "
                    + "editorial_id INT NOT NULL, "
                    + "stand_numero INT NOT NULL, "
                    + "fecha_reservacion DATE NOT NULL, "
                    + "fecha_limite_pago DATE NOT NULL, "
                    + "monto_total DOUBLE NOT NULL, "
                    + "monto_pagado DOUBLE NOT NULL, "
                    + "CONSTRAINT fk_res_editorial FOREIGN KEY (editorial_id) REFERENCES editorial (id), "
                    + "CONSTRAINT fk_res_stand FOREIGN KEY (stand_numero) REFERENCES stand (numero))");
        }
    }

    private void cargarDatos(Connection con) throws SQLException {
        // Editoriales
        editorial(con, 1, "Editorial Sol", "sol@editorial.mx", true);
        editorial(con, 2, "Letras Norte", "letras@editorial.mx", true);
        editorial(con, 3, "Papel Nuevo", "papel@editorial.mx", false);
        editorial(con, 4, "Tinta Viva", "tinta@editorial.mx", true);

        // Stands (costo por m2 = 1500)
        stand(con, 101, 4.0, 3.0, 1500.0, "OCUPADO");
        stand(con, 102, 2.0, 2.5, 1500.0, "RESERVADO");
        stand(con, 103, 3.0, 5.0, 1500.0, "DISPONIBLE");
        stand(con, 104, 5.0, 3.0, 1500.0, "OCUPADO");
        stand(con, 105, 4.0, 2.5, 1500.0, "DISPONIBLE");
        stand(con, 106, 6.0, 5.0, 1500.0, "BLOQUEADO");

        // Reservaciones: monto_total = area x costo_m2 (101 con 10 % de descuento por pronto pago)
        reservacion(con, "RES-001", 1, 101, LocalDate.of(2026, 11, 1), LocalDate.of(2026, 12, 1), 16200.0, 16200.0);
        reservacion(con, "RES-002", 2, 102, LocalDate.of(2026, 11, 3), LocalDate.of(2026, 12, 3), 7500.0, 0.0);
        reservacion(con, "RES-003", 4, 104, LocalDate.of(2026, 11, 5), LocalDate.of(2026, 12, 5), 22500.0, 3375.0);
    }

    private void editorial(Connection con, int id, String nombre, String correo, boolean aprobada) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO editorial VALUES (?, ?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setString(3, correo);
            ps.setBoolean(4, aprobada);
            ps.executeUpdate();
        }
    }

    private void stand(Connection con, int numero, double ancho, double largo, double costo, String estado)
            throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO stand VALUES (?, ?, ?, ?, ?)")) {
            ps.setInt(1, numero);
            ps.setDouble(2, ancho);
            ps.setDouble(3, largo);
            ps.setDouble(4, costo);
            ps.setString(5, estado);
            ps.executeUpdate();
        }
    }

    private void reservacion(Connection con, String id, int editorialId, int standNumero,
                             LocalDate fecha, LocalDate limite, double total, double pagado) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("INSERT INTO reservacion VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, id);
            ps.setInt(2, editorialId);
            ps.setInt(3, standNumero);
            ps.setDate(4, Date.valueOf(fecha));
            ps.setDate(5, Date.valueOf(limite));
            ps.setDouble(6, total);
            ps.setDouble(7, pagado);
            ps.executeUpdate();
        }
    }
}
