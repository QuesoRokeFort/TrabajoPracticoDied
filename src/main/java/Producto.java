import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Producto extends Persistente {

    private static String nombreTabla="producto";
    private static String primaryKey = "id";
    private boolean modificada = false;
    private boolean flagBorrado = false;
    public static String getNombreTabla() {
        return nombreTabla;
    }
    public static String getPrimaryKey(){
        return primaryKey;
    }
    public static List<String> getNombresColumnas(){
        return columnas;
    }
    public static int getCantidadDeColumnas() {
        return CANTIDAD_COLUMNAS;
    }
    private int id;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private double pesoKg;
    private static int CANTIDAD_COLUMNAS = 5;
    private static ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "nombre", "descripcion", "precioUnitario", "pesoKg"));
    @Override
    public List<Object> getValores() {
        List<Object> valores = new ArrayList<>();
        valores.add(id);
        valores.add(nombre);
        valores.add(descripcion);
        valores.add(precioUnitario);
        valores.add(pesoKg);
        return  valores;
    }
    public Producto() {
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
