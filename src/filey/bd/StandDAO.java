package filey.bd;

import filey.modelo.Stand;

import java.util.List;

/** Contrato de lectura de la tabla STAND. Los controladores dependen de esta interfaz, no de JDBC. */
public interface StandDAO {
    /** Todos los stands, ordenados por área (menor a mayor) y luego por número. */
    List<Stand> listarTodos();

    /** El stand con ese número, o null si no existe. */
    Stand buscarPorNumero(int numero);
}
