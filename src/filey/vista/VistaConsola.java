package filey.vista;

import java.io.PrintStream;
import java.util.Locale;

/**
 * Base de las vistas de consola. Una vista solo imprime lo que le entregan:
 * no conoce la base de datos, los DAO ni los controladores.
 *
 * El texto no lleva acentos a propósito, para que se vea bien en cualquier consola de Windows.
 */
public abstract class VistaConsola {
    /** Locale fijo para que los números salgan igual en cualquier computadora (1,500.00). */
    protected static final Locale LOCALE = Locale.US;

    protected final PrintStream out;

    protected VistaConsola(PrintStream out) {
        this.out = out;
    }

    protected void titulo(String texto) {
        out.println();
        out.println("=== " + texto + " ===");
    }

    protected String linea(int largo) {
        return "-".repeat(largo);
    }

    protected String dinero(double valor) {
        return String.format(LOCALE, "$%,.2f", valor);
    }

    public void mostrarError(String mensaje) {
        out.println("[ERROR] " + mensaje);
    }

    public void mostrarNoEncontrado(String que) {
        out.println(que + ": no encontrado");
    }
}
