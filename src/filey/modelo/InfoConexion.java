package filey.modelo;

/** Datos que demuestran que la conexión a la base de datos funciona (la vista los muestra). */
public class InfoConexion {
    private final String url;
    private final String producto;
    private final String version;
    private final String driver;

    public InfoConexion(String url, String producto, String version, String driver) {
        this.url = url;
        this.producto = producto;
        this.version = version;
        this.driver = driver;
    }

    public String getUrl() { return url; }
    public String getProducto() { return producto; }
    public String getVersion() { return version; }
    public String getDriver() { return driver; }
}
