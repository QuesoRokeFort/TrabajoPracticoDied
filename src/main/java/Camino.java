import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Camino extends Persistente {
    private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "origen", "destino", "tiempoDeViaje", "capacidadMaxima", "estado"));
    private static final int CANTIDAD_COLUMNAS = columnas.size();
    private static final String nombreTabla = "camino";
    private static final String primaryKey = "id";
    private int id;
    private int idSucursalOrigen;
    private int idSucursalDestino;
    private int tiempoDeViaje;
    private int capacidadMaxima;
    private Estado estado;

    public List<Object> getValores(){
        List<Object> valores = new ArrayList<>();
        valores.add(this.id);
        valores.add(this.idSucursalOrigen);
        valores.add(this.idSucursalDestino);
        valores.add(this.tiempoDeViaje);
        valores.add(this.capacidadMaxima);
        valores.add(this.estado);
        return valores;
    }
    public Camino(int id, int idSucursalOrigen, int idSucursalDestino, int tiempoDeTransito, int capacidadMaxima, Estado estado) {
        this.id = id;
        this.idSucursalOrigen = idSucursalOrigen;
        this.idSucursalDestino = idSucursalDestino;
        this.tiempoDeViaje = tiempoDeTransito;
        this.capacidadMaxima = capacidadMaxima;
        this.estado = estado;
    }
    public Camino() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdSucursalOrigen() {
        return idSucursalOrigen;
    }
    public boolean tieneValores(){
        return (id != 0 && idSucursalOrigen != 0 && idSucursalDestino != 0 && tiempoDeViaje != 0 && capacidadMaxima != 0 && estado != null);
    }
    public void setIdSucursalOrigen(int idSucursalOrigen) {
        this.idSucursalOrigen = idSucursalOrigen;
    }

    public int getIdSucursalDestino() {
        return idSucursalDestino;
    }

    public void setIdSucursalDestino(int idSucursalDestino) {
        this.idSucursalDestino = idSucursalDestino;
    }

    public int getTiempoDeViaje() {
        return tiempoDeViaje;
    }

    public void setTiempoDeViaje(int tiempoDeViaje) {
        this.tiempoDeViaje = tiempoDeViaje;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
}
