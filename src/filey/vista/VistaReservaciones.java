package filey.vista;

import filey.modelo.Reservacion;

import java.io.PrintStream;
import java.util.List;

/** Presenta la lista de reservaciones (ya unidas con su editorial y su stand) y el detalle de una. */
public class VistaReservaciones extends VistaConsola {
    private static final String FORMATO = "%-9s %-16s %-6s %-11s %-11s %11s %11s %11s";

    public VistaReservaciones(PrintStream out) {
        super(out);
    }

    public void mostrarLista(List<Reservacion> reservaciones) {
        titulo("TABLA RESERVACION (" + reservaciones.size() + " registros)");
        if (reservaciones.isEmpty()) {
            out.println("La tabla RESERVACION esta vacia.");
            return;
        }
        out.println(String.format(LOCALE, FORMATO,
                "Id", "Editorial", "Stand", "Fecha", "Limite pago", "Total", "Pagado", "Pendiente"));
        out.println(linea(94));
        for (Reservacion r : reservaciones) {
            out.println(String.format(LOCALE, FORMATO,
                    r.getIdentificador(), r.getEditorial().getNombre(), "#" + r.getStand().getNumero(),
                    r.getFechaReservacion(), r.getFechaLimitePago(),
                    dinero(r.getMontoTotal()), dinero(r.getMontoPagado()), dinero(r.getMontoPendiente())));
        }
    }

    public void mostrarDetalle(Reservacion r) {
        out.println("Reservacion " + r.getIdentificador() + " -> " + r.getEditorial().getNombre()
                + ", stand #" + r.getStand().getNumero()
                + ", pendiente " + dinero(r.getMontoPendiente()) + " (limite " + r.getFechaLimitePago() + ")");
    }
}
