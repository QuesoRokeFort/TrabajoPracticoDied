import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Producto extends Persistente {
    private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "nombre", "descripcion", "precioUnitario", "pesoKg"));
    private static final int CANTIDAD_COLUMNAS = columnas.size();
    private static final String nombreTabla = "producto";
    private static final String primaryKey = "id";
    private boolean flagBorrado=false;
    private boolean modificada=false;
    int id;
    String nombre;
    String descripcion;
    Double precioUnitario;
    Double pesoKg;

    public Producto() {
    }
    public static List<String> getNombresColumnas(){
        return columnas;
    }
    public static int getCantidadDeColumnas() {
        return CANTIDAD_COLUMNAS;
    }
    @Override
    public List<Object> getValores() {
        List<Object> valores = new ArrayList<>();
        valores.add(this.id);
        valores.add(this.nombre);
        valores.add(this.descripcion);
        valores.add(this.precioUnitario);
        valores.add(this.pesoKg);
        return valores;
    }

    public Producto(int id, String nombre, String descripcion, double precioUnitario, double pesoKg) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.pesoKg = pesoKg;
    }
    public boolean tieneValores(){
        return (id != 0 && nombre != null && descripcion != null && precioUnitario != 0.0 && pesoKg != 0.0);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }
}
