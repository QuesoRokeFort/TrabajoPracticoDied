import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Camino extends Persistente {
    private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "origen", "destino", "tiempoDeViaje", "capacidadMaxima", "estado"));
    private static final int CANTIDAD_COLUMNAS = columnas.size();
    private static final String nombreTabla = "camino";
    private static final String primaryKey = "id";
    private int id;
    private Sucursal origen;
    private Sucursal destino;
    private int tiempoDeViaje;
    private int capacidadMaxima;
    private Estado estado;

    public List<Object> getValores(){
        List<Object> valores = new ArrayList<>();
        valores.add(this.id);
        valores.add(this.origen);
        valores.add(this.destino);
        valores.add(this.tiempoDeViaje);
        valores.add(this.capacidadMaxima);
        valores.add(this.estado);
        return valores;
    }
    public Camino(int id, Sucursal origen, Sucursal destino, int tiempoDeTransito, int capacidadMaxima, Estado estado) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.tiempoDeViaje = tiempoDeTransito;
        this.capacidadMaxima = capacidadMaxima;
        this.estado = estado;
    }
    public Camino() {
    }

}
