package filey.bd;

/** Error de la capa de base de datos. Envuelve SQLException para que las capas superiores no dependan de JDBC. */
public class BDException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BDException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
