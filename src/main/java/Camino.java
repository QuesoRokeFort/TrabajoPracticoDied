public class Camino {
    private int id;
    private Sucursal origen;
    private Sucursal destino;
    private int tiempoDeTransito;
    private int capacidadMaxima;
    private Estado estado;
    public Camino(int id, Sucursal origen, Sucursal destino, int tiempoDeTransito, int capacidadMaxima, Estado estado) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.tiempoDeTransito = tiempoDeTransito;
        this.capacidadMaxima = capacidadMaxima;
        this.estado = estado;
    }
    public Camino() {
    }
}
