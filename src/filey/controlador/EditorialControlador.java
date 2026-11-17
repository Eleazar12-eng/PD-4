package filey.controlador;

import filey.bd.BDException;
import filey.bd.EditorialDAO;
import filey.modelo.Editorial;
import filey.vista.VistaEditoriales;

/**
 * Una sola tarea: llevar las editoriales de la base de datos a la vista.
 * Depende de la interfaz EditorialDAO, no de JDBC.
 */
public class EditorialControlador {
    private final EditorialDAO dao;
    private final VistaEditoriales vista;

    public EditorialControlador(EditorialDAO dao, VistaEditoriales vista) {
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

    public boolean mostrarDetalle(int id) {
        try {
            Editorial editorial = dao.buscarPorId(id);
            if (editorial == null) {
                vista.mostrarNoEncontrado("Editorial #" + id);
            } else {
                vista.mostrarDetalle(editorial);
            }
            return true;
        } catch (BDException e) {
            vista.mostrarError(e.getMessage());
            return false;
        }
    }
}
