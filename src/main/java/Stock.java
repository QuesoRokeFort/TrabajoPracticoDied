import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Stock extends Persistente{
    private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id_producto", "id_sucursal", "cantidad"));
    private static final String primaryKey = "id_producto, id_sucursal";
    private static final int CANTIDAD_COLUMNAS = columnas.size();
    private static final String nombreTabla = "stock";
    private boolean flagBorrado=false;
    private boolean modificada=false;
    private int id_producto;
    private int id_sucursal;
    private int cantidad;

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
    public boolean tieneValores(){
        return (id_producto != 0 && id_sucursal != 0 && cantidad != 0);
    }
    @Override
    public List<Object> getValores() {
        return null;
    }

    public boolean isFlagBorrado() {
        return flagBorrado;
    }

    public void setFlagBorrado(boolean flagBorrado) {
        this.flagBorrado = flagBorrado;
    }

    public boolean isModificada() {
        return modificada;
    }

    public void setModificada(boolean modificada) {
        this.modificada = modificada;
    }



    public int getId_sucursal() {
        return id_sucursal;
    }

    public void setId_sucursal(int id_sucursal) {
        this.id_sucursal = id_sucursal;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
