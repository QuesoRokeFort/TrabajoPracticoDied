import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Gestor {
    ArrayList<Camino> caminos = new ArrayList<Camino>();
    static Swingestor swingestor = new Swingestor();
    static int contadorSucursales;
    /*public void actualizarSucursales(){
            ArrayList<Sucursal> listaSucursales = new ArrayList<>();

            try (Connection conn = Conexion.getInstance().getConn();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM Sucursal")) {

                while (rs.next()) {
                    String nombre = rs.getString("Nombre");
                    int id = rs.getInt("Id");
                    int horaApertura = rs.getInt("HoraApertura");
                    int horaCierre = rs.getInt("HoraCierre");
                    boolean estado = rs.getBoolean("Estado");
                    Sucursal sucursal = new Sucursal(id,horaApertura,horaCierre,estado,nombre);;
                    listaSucursales.add(sucursal);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.sucursales=listaSucursales;
    }*/
    public static void inicializar() {
        contadorSucursales = Gestor.getLastValue("id","sucursal");
    }
    private static int getLastValue(String nombreColumna, String tabla) {
            int max = 0;
            try (Connection conn = Conexion.getInstance().getConn()) {
                max = 0;
                String queryMayorId = "select MAX(" + nombreColumna + ") from " + tabla;
                PreparedStatement preparedStatementid = conn.prepareStatement(queryMayorId);
                ResultSet resultSet = preparedStatementid.executeQuery();
                if (resultSet.next()) {
                    max = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return max;
        }
        public static int showMenu(JFrame jFrame) {
            return swingestor.swingMenu(jFrame);
        }

        public static void agregarSucursal(JFrame jFrame) {
            Sucursal nuevaSucursal = swingestor.addSucursal(jFrame, contadorSucursales + 1);
            Gestor.cargarEnTable(Sucursal.getNombreTabla(),Sucursal.getCantidadDeColumnas(), Sucursal.getNombresColumnas(), nuevaSucursal.getValores());
            contadorSucursales++;
        }
        public static void borrarSucursal(Sucursal sucursal){
            Gestor.eliminarFila(Sucursal.getNombreTabla(),Sucursal.getPrimaryKey(),sucursal.getId());
        }
        public static void borrarSucursal(int id_sucursal){
            Gestor.eliminarFila(Sucursal.getNombreTabla(),Sucursal.getPrimaryKey(),id_sucursal);
        }
        public static void modificarSucursal(Sucursal sucursal){
            sucursal.Modificada();
            Gestor.actualizarEnTable(Sucursal.getNombreTabla(), Sucursal.getCantidadDeColumnas(), Sucursal.getNombresColumnas(), sucursal.getValores(), Sucursal.getPrimaryKey(), sucursal.getId());
        }
        public static void showSucursales(JFrame jFrame) {
            swingestor.listarSucursales(jFrame);
        }
        public static void buscarSucursal(JFrame jFrame){
            int idBusqueda;
            idBusqueda = swingestor.menuBusqueda(jFrame);
            Sucursal sucursal= new Sucursal();
            //refactorizar con funcion lucio
            try (Connection conn = Conexion.getInstance().getConn()){
                String query = "SELECT * FROM Sucursal where id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,idBusqueda);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String nombre = rs.getString("Nombre");
                    int id = rs.getInt("Id");
                    int horaApertura = rs.getInt("HoraApertura");
                    int horaCierre = rs.getInt("HoraCierre");
                    Estado estado = rs.getBoolean("Estado")? Estado.OPERATIVA : Estado.NO_OPERATIVA;
                    sucursal = new Sucursal(id,horaApertura,horaCierre,estado,nombre);;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (idBusqueda != 0) {
                swingestor.modificarSucursal(jFrame, sucursal);
                if (sucursal.getFlagBorrado()) {
                    borrarSucursal(sucursal);
                } else {
                    if (sucursal.isModificada()) {
                        modificarSucursal(sucursal);
                    }
                }
            }
        }
        public static void listarSucursales(JFrame jFrame) {
            swingestor.listarSucursales(jFrame);
        }
        public static void cargarEnTable(String tabla, int cantValores, List<String> columnas, List<Object> valores) {

            try (Connection conn = Conexion.getInstance().getConn()) {
            StringJoiner columnasJoiner = new StringJoiner(", ");
            StringJoiner valoresJoiner = new StringJoiner(", ");
            for (int i = 0; i < cantValores; i++) {
                columnasJoiner.add(columnas.get(i));
                valoresJoiner.add("?");
            }
            String query = "INSERT INTO " + tabla + " (" + columnasJoiner.toString() + ") VALUES (" + valoresJoiner.toString() + ")";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                for (int i = 0; i < cantValores; i++) {
                    if (valores.get(i) instanceof String) {
                        preparedStatement.setString(i + 1, (String) valores.get(i));
                    } else if (valores.get(i) instanceof Integer) {
                        preparedStatement.setInt(i + 1, (int) valores.get(i));
                    } else if (valores.get(i) instanceof Double) {
                        preparedStatement.setDouble(i + 1, (double) valores.get(i));
                    } else if (valores.get(i) instanceof Estado) {
                        preparedStatement.setBoolean(i + 1, valores.get(i).equals(Estado.OPERATIVA) ? true : false);
                    }
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void eliminarFila(String tabla, String primary_key, int id) {
        try (Connection conn = Conexion.getInstance().getConn()) {
            String query = "DELETE FROM "+ tabla + " WHERE " + primary_key + " = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void actualizarEnTable(String tabla, int cantValores, List<String> columnas, List<Object> valores, String primaryKey, int id) {

        try (Connection conn = Conexion.getInstance().getConn()) {
            StringJoiner columnasJoiner = new StringJoiner(", ");
            for (int i = 0; i < cantValores; i++) {
                columnasJoiner.add(columnas.get(i) + " = ?");
            }
            String query = "UPDATE " + tabla + " SET " + columnasJoiner.toString() + " WHERE " + primaryKey + " = ?;";
            try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                for (int i = 0; i < cantValores; i++) {
                    if (valores.get(i) instanceof String) {
                        preparedStatement.setString(i + 1, (String) valores.get(i));
                    } else if (valores.get(i) instanceof Integer) {
                        preparedStatement.setInt(i + 1, (int) valores.get(i));
                    } else if (valores.get(i) instanceof Double) {
                        preparedStatement.setDouble(i + 1, (double) valores.get(i));
                    } else if (valores.get(i) instanceof Estado) {
                        preparedStatement.setBoolean(i + 1, valores.get(i).equals(Estado.OPERATIVA) ? true : false);
                    }
                }
                preparedStatement.setObject(cantValores + 1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
