package filey.modelo;

import java.time.LocalDate;

/**
 * Entidad Reservacion (tabla RESERVACION). Relaciona una Editorial con un Stand
 * (FK editorial_id y FK stand_numero).
 */
public class Reservacion {
    private final String identificador;   // PK
    private final Editorial editorial;
    private final Stand stand;
    private final LocalDate fechaReservacion;
    private final LocalDate fechaLimitePago;
    private final double montoTotal;
    private final double montoPagado;

    public Reservacion(String identificador, Editorial editorial, Stand stand,
                       LocalDate fechaReservacion, LocalDate fechaLimitePago,
                       double montoTotal, double montoPagado) {
        this.identificador = identificador;
        this.editorial = editorial;
        this.stand = stand;
        this.fechaReservacion = fechaReservacion;
        this.fechaLimitePago = fechaLimitePago;
        this.montoTotal = montoTotal;
        this.montoPagado = montoPagado;
    }

    public double getMontoPendiente() { return Math.max(0, montoTotal - montoPagado); }

    public String getIdentificador() { return identificador; }
    public Editorial getEditorial() { return editorial; }
    public Stand getStand() { return stand; }
    public LocalDate getFechaReservacion() { return fechaReservacion; }
    public LocalDate getFechaLimitePago() { return fechaLimitePago; }
    public double getMontoTotal() { return montoTotal; }
    public double getMontoPagado() { return montoPagado; }
}
