package filey.bd;

import filey.modelo.Reservacion;

import java.util.List;

/** Contrato de lectura de la tabla RESERVACION (ya unida con su editorial y su stand). */
public interface ReservacionDAO {
    /** Todas las reservaciones, ordenadas por identificador. */
    List<Reservacion> listarTodas();

    /** La reservación con ese identificador, o null si no existe. */
    Reservacion buscarPorIdentificador(String identificador);
}
