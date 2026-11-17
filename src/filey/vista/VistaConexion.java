package filey.vista;

import filey.modelo.InfoConexion;

import java.io.PrintStream;

/** Muestra el resultado de probar la conexión y de preparar la base de datos. */
public class VistaConexion extends VistaConsola {

    public VistaConexion(PrintStream out) {
        super(out);
    }

    public void mostrarExito(InfoConexion info) {
        titulo("CONEXION A LA BASE DE DATOS");
        out.println("Estado : CONEXION EXITOSA");
        out.println("URL    : " + info.getUrl());
        out.println("Motor  : " + info.getProducto() + " " + info.getVersion());
        out.println("Driver : " + info.getDriver());
    }

    public void mostrarEsquemaListo() {
        out.println("Tablas STAND, EDITORIAL y RESERVACION listas (datos de ejemplo cargados).");
    }
}
