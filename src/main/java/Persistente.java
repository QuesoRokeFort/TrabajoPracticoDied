import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Persistente {
    private static ArrayList<String> columnas;
    private static String nombreTabla;
    private static String primaryKey = "id";
    private static int id;
    private static int CANTIDAD_COLUMNAS;
    private boolean modificada = false;
    private boolean flagBorrado = false;

    public abstract List<Object> getValores();
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
}
