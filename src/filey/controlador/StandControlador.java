package filey.controlador;

import filey.bd.BDException;
import filey.bd.StandDAO;
import filey.modelo.Stand;
import filey.vista.VistaStands;

/**
 * Una sola tarea: llevar los stands de la base de datos a la vista.
 * Depende de la interfaz StandDAO, no de JDBC.
 */
public class StandControlador {
    private final StandDAO dao;
    private final VistaStands vista;

    public StandControlador(StandDAO dao, VistaStands vista) {
        this.dao = dao;
        this.vista = vista;
    }

    /** @return true si la lectura se hizo sin errores. */
    public boolean mostrarLista() {
        try {
            vista.mostrarLista(dao.listarTodos());
            return true;
        } catch (BDException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }

    public boolean mostrarDetalle(int numero) {
        try {
            Stand stand = dao.buscarPorNumero(numero);
            if (stand == null) {
                vista.mostrarNoEncontrado("Stand #" + numero);
            } else {
                vista.mostrarDetalle(stand);
            }
            return true;
        } catch (BDException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }
}
