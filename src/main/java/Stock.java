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
    private int id;
    private int id_sucursal;
    private int cantidad;

    @Override
    public List<Object> getValores() {
        return null;
    }
}
