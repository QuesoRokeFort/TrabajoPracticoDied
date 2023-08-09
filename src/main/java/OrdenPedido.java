import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrdenPedido {
	private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "idSucursal", "estado","fecha"));
	private static final int CANTIDAD_COLUMNAS = columnas.size();
	private static final String nombreTabla = "ordenpedido";
	private static final String primaryKey = "id";
	public static String getPrimaryKey(){
		return primaryKey;
	}
	public static String getNombreTabla() {
		return nombreTabla;
	}
	public static List<String> getNombresColumnas(){
		return columnas;
	}
	/*public List<Object> getValores(){
		List<Object> valores = new ArrayList<>();
		valores.add(this.id);
		valores.add(this.nombre);
		valores.add(this.horaApertura);
		valores.add(this.horaCierre);
		valores.add(this.estado);
		return valores;
	}*/
	public static int getCantidadDeColumnas() {
		return CANTIDAD_COLUMNAS;
	}

	public static class ReglonPedido {
		private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "idProducto", "cantidad"));

	}
}
