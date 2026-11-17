package filey.modelo;

/** Entidad Stand (tabla STAND). Solo guarda datos; el área y el costo se calculan. */
public class Stand {
    private final int numero;          // PK
    private final double ancho;
    private final double largo;
    private final double costoPorM2;
    private final EstadoStand estado;

    public Stand(int numero, double ancho, double largo, double costoPorM2, EstadoStand estado) {
        this.numero = numero;
        this.ancho = ancho;
        this.largo = largo;
        this.costoPorM2 = costoPorM2;
        this.estado = estado;
    }

    public double calcularArea() { return ancho * largo; }
    public double calcularCostoTotal() { return calcularArea() * costoPorM2; }

    public int getNumero() { return numero; }
    public double getAncho() { return ancho; }
    public double getLargo() { return largo; }
    public double getCostoPorM2() { return costoPorM2; }
    public EstadoStand getEstado() { return estado; }
}
