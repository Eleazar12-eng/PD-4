package filey.controlador;

import filey.bd.BDException;
import filey.bd.ReservacionDAO;
import filey.modelo.Reservacion;
import filey.vista.VistaReservaciones;

/**
 * Una sola tarea: llevar las reservaciones de la base de datos a la vista.
 * Depende de la interfaz ReservacionDAO, no de JDBC.
 */
public class ReservacionControlador {
    private final ReservacionDAO dao;
    private final VistaReservaciones vista;

    public ReservacionControlador(ReservacionDAO dao, VistaReservaciones vista) {
        this.dao = dao;
        this.vista = vista;
    }

    /** @return true si la lectura se hizo sin errores. */
    public boolean mostrarLista() {
        try {
            vista.mostrarLista(dao.listarTodas());
            return true;
        } catch (BDException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }

    public boolean mostrarDetalle(String identificador) {
        try {
            Reservacion reservacion = dao.buscarPorIdentificador(identificador);
            if (reservacion == null) {
                vista.mostrarNoEncontrado("Reservacion " + identificador);
            } else {
                vista.mostrarDetalle(reservacion);
            }
            return true;
        } catch (BDException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }
}
