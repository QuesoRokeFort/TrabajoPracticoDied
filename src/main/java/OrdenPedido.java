import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrdenPedido {
	private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("id", "idsucursal", "estado","fecha"));
	private int id;
	private int idSucursal;
	private boolean estado;
	private String fecha;
	public List<Object> getValores() {
		List<Object> valores = new ArrayList<>();
		valores.add(this.id);
		valores.add(this.idSucursal);
		valores.add(this.estado);
		valores.add(this.fecha);
		return valores;
	}

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

	public static int getCantidadDeColumnas() {
		return CANTIDAD_COLUMNAS;
	}



	public static class ReglonPedido {
		private static final ArrayList<String> columnas = new ArrayList<>(Arrays.asList("idorden", "idProducto", "cantidad"));
		private int id;
		private int idProducto;
		private int cantidad;
		public List<Object> getValores(){
			List<Object> valores = new ArrayList<>();
			valores.add(this.id);
			valores.add(this.idProducto);
			valores.add(this.cantidad);
			return valores;
		}
		private static final int CANTIDAD_COLUMNAS = columnas.size();
		private static final String nombreTabla = "reglonpedido";
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
		public static int getCantidadDeColumnas() {
			return CANTIDAD_COLUMNAS;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getIdProducto() {
			return idProducto;
		}

		public void setIdProducto(int idProducto) {
			this.idProducto = idProducto;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdSucursal() {
		return idSucursal;
	}

	public void setIdSucursal(int idSucursal) {
		this.idSucursal = idSucursal;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
}
