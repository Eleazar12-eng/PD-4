package filey.vista;

import filey.modelo.Editorial;

import java.io.PrintStream;
import java.util.List;

/** Presenta la lista de editoriales y el detalle de una. */
public class VistaEditoriales extends VistaConsola {
    private static final String FORMATO = "%-4s %-16s %-22s %s";

    public VistaEditoriales(PrintStream out) {
        super(out);
    }

    public void mostrarLista(List<Editorial> editoriales) {
        titulo("TABLA EDITORIAL (" + editoriales.size() + " registros)");
        if (editoriales.isEmpty()) {
            out.println("La tabla EDITORIAL esta vacia.");
            return;
        }
        out.println(String.format(LOCALE, FORMATO, "Id", "Nombre", "Correo", "Aprobada"));
        out.println(linea(56));
        for (Editorial e : editoriales) {
            out.println(String.format(LOCALE, FORMATO,
                    e.getId(), e.getNombre(), e.getCorreo(), e.isAprobada() ? "Si" : "No"));
        }
    }

    public void mostrarDetalle(Editorial e) {
        out.println("Editorial #" + e.getId() + " -> " + e.getNombre() + " <" + e.getCorreo() + ">, "
                + (e.isAprobada() ? "aprobada" : "NO aprobada"));
    }
}
