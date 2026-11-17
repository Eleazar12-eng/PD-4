package filey.vista;

import filey.modelo.Stand;

import java.io.PrintStream;
import java.util.List;

/** Presenta la lista de stands y el detalle de uno. */
public class VistaStands extends VistaConsola {
    private static final String FORMATO_ENCABEZADO = "%-5s %7s %7s %9s %12s %13s  %s";
    private static final String FORMATO_FILA = "%-5d %7.2f %7.2f %9.2f %12s %13s  %s (%s)";

    public VistaStands(PrintStream out) {
        super(out);
    }

    public void mostrarLista(List<Stand> stands) {
        titulo("TABLA STAND (" + stands.size() + " registros, ordenados por area)");
        if (stands.isEmpty()) {
            out.println("La tabla STAND esta vacia.");
            return;
        }
        out.println(String.format(LOCALE, FORMATO_ENCABEZADO,
                "No.", "Ancho", "Largo", "Area m2", "Costo/m2", "Costo total", "Estado"));
        out.println(linea(72));
        for (Stand s : stands) {
            out.println(fila(s));
        }
    }

    public void mostrarDetalle(Stand s) {
        out.println("Stand #" + s.getNumero() + " -> " + String.format(LOCALE, "%.2f m2", s.calcularArea())
                + ", " + dinero(s.calcularCostoTotal()) + ", estado " + s.getEstado().texto());
    }

    private String fila(Stand s) {
        return String.format(LOCALE, FORMATO_FILA,
                s.getNumero(), s.getAncho(), s.getLargo(), s.calcularArea(),
                dinero(s.getCostoPorM2()), dinero(s.calcularCostoTotal()),
                s.getEstado().texto(), s.getEstado().getColor());
    }
}
