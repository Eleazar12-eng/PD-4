package filey.controlador;

import filey.bd.BDException;
import filey.bd.ConexionBD;
import filey.bd.InicializadorBD;
import filey.vista.VistaConexion;

/** Una sola tarea: comprobar la conexión a la base de datos y dejar las tablas listas. */
public class ConexionControlador {
    private final ConexionBD conexion;
    private final InicializadorBD inicializador;
    private final VistaConexion vista;

    public ConexionControlador(ConexionBD conexion, InicializadorBD inicializador, VistaConexion vista) {
        this.conexion = conexion;
        this.inicializador = inicializador;
        this.vista = vista;
    }

    /** @return true si la conexión responde. */
    public boolean verificarConexion() {
        try {
            vista.mostrarExito(conexion.probar());
            return true;
        } catch (BDException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }

    /** Crea las tablas y carga los datos de ejemplo (la BD en memoria nace vacía). */
    public boolean prepararEsquema() {
        try {
            inicializador.crearEsquemaYDatos();
            vista.mostrarEsquemaListo();
            return true;
        } catch (BDException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }
}
