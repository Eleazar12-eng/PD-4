package filey;

import filey.bd.ConexionBD;
import filey.bd.InicializadorBD;
import filey.bd.jdbc.EditorialDAOJdbc;
import filey.bd.jdbc.ReservacionDAOJdbc;
import filey.bd.jdbc.StandDAOJdbc;
import filey.controlador.ConexionControlador;
import filey.controlador.EditorialControlador;
import filey.controlador.ReservacionControlador;
import filey.controlador.StandControlador;
import filey.vista.VistaConexion;
import filey.vista.VistaEditoriales;
import filey.vista.VistaReservaciones;
import filey.vista.VistaStands;

import java.io.PrintStream;

/**
 * Punto de entrada: arma las capas (BD -> DAO -> controlador <- vista) y demuestra
 * que se puede conectar a la base de datos y leer las tablas STAND, EDITORIAL y RESERVACION.
 */
public class Main {

    public static void main(String[] args) {
        PrintStream out = System.out;

        // Capa de BD
        ConexionBD conexion = ConexionBD.desdePropiedades();

        // 1. Conexión y esquema
        ConexionControlador conexionCtrl = new ConexionControlador(
                conexion, new InicializadorBD(conexion), new VistaConexion(out));
        if (!conexionCtrl.verificarConexion() || !conexionCtrl.prepararEsquema()) {
            System.exit(1);
        }

        // 2. Cada controlador recibe su DAO (capa BD) y su vista (capa de presentación)
        StandControlador standCtrl = new StandControlador(
                new StandDAOJdbc(conexion), new VistaStands(out));
        EditorialControlador editorialCtrl = new EditorialControlador(
                new EditorialDAOJdbc(conexion), new VistaEditoriales(out));
        ReservacionControlador reservacionCtrl = new ReservacionControlador(
                new ReservacionDAOJdbc(conexion), new VistaReservaciones(out));

        // 3. Lectura de las tres tablas (& evalúa los tres: un fallo no oculta a los demás)
        boolean ok = standCtrl.mostrarLista();
        ok &= editorialCtrl.mostrarLista();
        ok &= reservacionCtrl.mostrarLista();

        // 4. Consulta por clave (WHERE con parámetro)
        out.println();
        out.println("=== CONSULTA POR CLAVE ===");
        ok &= standCtrl.mostrarDetalle(102);
        ok &= editorialCtrl.mostrarDetalle(1);
        ok &= reservacionCtrl.mostrarDetalle("RES-003");
        ok &= standCtrl.mostrarDetalle(999); // no existe: la vista lo informa

        System.exit(ok ? 0 : 1);
    }
}
