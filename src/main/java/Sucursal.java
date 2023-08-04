import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sucursal extends Persistente{
    private boolean flagBorrado=false;
    private boolean modificada=false;
    private static String nombreTabla = "sucursal";
    private static String primaryKey = "id";
    private static int CANTIDAD_COLUMNAS = 5;
    private static ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "nombre", "horaApertura", "horaCierre", "estado"));
    private int id;
    private String nombre;
    private int horaApertura;
    private int horaCierre;
    private Estado estado;

    public static String getPrimaryKey(){
        return primaryKey;
    }
    public static String getNombreTabla() {
        return nombreTabla;
    }
    public static List<String> getNombresColumnas(){
        return columnas;
    }
    public List<Object> getValores(){
        List<Object> valores = new ArrayList<>();
        valores.add(this.id);
        valores.add(this.nombre);
        valores.add(this.horaApertura);
        valores.add(this.horaCierre);
        valores.add(this.estado);
        return valores;
    }
    public static int getCantidadDeColumnas() {
        return CANTIDAD_COLUMNAS;
    }
    public Sucursal() {
    }
    public boolean equals(Sucursal s){
        if (s.getNombre().equals(this.getNombre()) && s.getEstado().equals(this.getEstado()) && s.getHoraApertura() == this.getHoraApertura() && s.getHoraCierre()== this.getHoraCierre()){
            return true;
        }
        return false;
    }
    public Sucursal(int id, int horaApertura, int horaCierre, Estado estado, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.estado = estado;
    }

    public boolean isModificada() {
        return modificada;
    }

    public void Modificada() {
        this.modificada = !this.modificada;
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

    public int getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(int horaApertura) {
        this.horaApertura = horaApertura;
    }

    public int getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(int horaCierre) {
        this.horaCierre = horaCierre;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public boolean getFlagBorrado() {
        return flagBorrado;
    }
    public void borrarSucursal(){
        flagBorrado=true;
    }
}
