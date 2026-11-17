package filey.modelo;

/** Entidad Editorial (tabla EDITORIAL). */
public class Editorial {
    private final int id;              // PK
    private final String nombre;
    private final String correo;
    private final boolean aprobada;

    public Editorial(int id, String nombre, String correo, boolean aprobada) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.aprobada = aprobada;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public boolean isAprobada() { return aprobada; }
}
