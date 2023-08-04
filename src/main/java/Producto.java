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
    double precioUnitario;
    double pesoKg;

    public Producto() {
    }

    @Override
    public List<Object> getValores() {
        List<Object> valores = new ArrayList<>();
        valores.add(this.id);
        return valores;
    }

    public Producto(int id, String nombre, String descripcion, double precioUnitario, double pesoKg) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.pesoKg = pesoKg;
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

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(double pesoKg) {
        this.pesoKg = pesoKg;
    }
}
