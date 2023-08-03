import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sucursal {
    private String nombre;
    private int horaApertura;
    private int horaCierre;
    private Estado estado;
    private int id;
    private boolean flagBorrado=false;
    private boolean modificada=false;
    public List<String> getListaColumnas(){
        List<String> columnas=new ArrayList<>();
        columnas.add("id");
        columnas.add("nombre");
        columnas.add("horaApertura");
        columnas.add("horaCierre");
        columnas.add("estado");
        return columnas;
    }
    public List<Object> getListaValores(){
        List<Object> valores = new ArrayList<>();
        valores.add(this.id);
        valores.add(this.nombre);
        valores.add(this.horaApertura);
        valores.add(this.horaCierre);
        valores.add(this.estado);
        return valores;
    }
    public int elementos() {
        return 5;
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
