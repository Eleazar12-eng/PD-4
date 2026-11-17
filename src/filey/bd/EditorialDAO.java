package filey.bd;

import filey.modelo.Editorial;

import java.util.List;

/** Contrato de lectura de la tabla EDITORIAL. */
public interface EditorialDAO {
    /** Todas las editoriales, ordenadas por id. */
    List<Editorial> listarTodas();

    /** La editorial con ese id, o null si no existe. */
    Editorial buscarPorId(int id);
}
