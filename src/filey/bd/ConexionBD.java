package filey.bd;

import filey.modelo.InfoConexion;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Único punto de acceso a la conexión JDBC. Los DAO piden aquí su conexión y no
 * conocen la URL, el usuario ni el motor de base de datos.
 *
 * Por defecto usa H2 en memoria. Se puede cambiar sin tocar el código:
 *   java -Dfiley.db.url=jdbc:h2:mem:otra ... filey.Main
 */
public class ConexionBD {
    public static final String URL_POR_DEFECTO = "jdbc:h2:mem:filey;DB_CLOSE_DELAY=-1";

    private final String url;
    private final String usuario;
    private final String clave;

    public ConexionBD(String url, String usuario, String clave) {
        this.url = url;
        this.usuario = usuario;
        this.clave = clave;
    }

    /** Lee la configuración de propiedades del sistema; si no existen, usa H2 en memoria. */
    public static ConexionBD desdePropiedades() {
        return new ConexionBD(
                System.getProperty("filey.db.url", URL_POR_DEFECTO),
                System.getProperty("filey.db.user", "sa"),
                System.getProperty("filey.db.password", ""));
    }

    /** Abre una conexión nueva. Quien la pida debe cerrarla (try-with-resources). */
    public Connection obtener() throws SQLException {
        return DriverManager.getConnection(url, usuario, clave);
    }

    /** Abre una conexión y lee los metadatos del motor para comprobar que responde. */
    public InfoConexion probar() {
        try (Connection con = obtener()) {
            if (!con.isValid(2)) {
                throw new SQLException("La conexión se abrió pero no respondió");
            }
            DatabaseMetaData meta = con.getMetaData();
            return new InfoConexion(url, meta.getDatabaseProductName(),
                    meta.getDatabaseProductVersion(), meta.getDriverName() + " " + meta.getDriverVersion());
        } catch (SQLException e) {
            throw new BDException("No se pudo conectar a la base de datos (" + url + "): "
                    + e.getMessage() + pistaSiFaltaDriver(e), e);
        }
    }

    public String getUrl() { return url; }

    /** El error más común: el jar del driver (h2-*.jar) no está en el classpath. */
    private String pistaSiFaltaDriver(SQLException e) {
        String mensaje = e.getMessage();
        if (mensaje != null && mensaje.contains("No suitable driver")) {
            return ". Falta el driver JDBC: agrega h2-*.jar al classpath (carpeta lib/).";
        }
        return "";
    }
}
