package filey.modelo;

/** Los 4 estados de un stand y su color en el mapa. */
public enum EstadoStand {
    DISPONIBLE("Verde"),
    RESERVADO("Amarillo"),
    OCUPADO("Rojo"),
    BLOQUEADO("Gris");

    private final String color;

    EstadoStand(String color) {
        this.color = color;
    }

    public String getColor() { return color; }

    public String texto() {
        String n = name();
        return n.charAt(0) + n.substring(1).toLowerCase();
    }
}
