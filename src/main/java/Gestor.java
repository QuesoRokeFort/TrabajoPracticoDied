import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Gestor {
    ArrayList<Camino> caminos = new ArrayList<Camino>();
    static Swingestor swingestor = new Swingestor();


    public static boolean tieneId(String tabla){
        try (Connection conn = Conexion.getInstance().getConn()) {
            String tieneId = "select id from " + tabla;
            PreparedStatement preparedStatementid = conn.prepareStatement(tieneId);
            preparedStatementid.executeQuery();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    static int contadorSucursales;
    public static List<Object> buscarCosas(List<String> cosasABuscar, String tabla,String orden) {
        ArrayList<Object> listaCosas = new ArrayList<>();

        try (Connection conn = Conexion.getInstance().getConn()) {
            Statement stmt = conn.createStatement();
            StringJoiner columnasJoiner = new StringJoiner(", ");

            for (int i = 0; i < cosasABuscar.size(); i++) {
                columnasJoiner.add(cosasABuscar.get(i));
            }

            String query = "SELECT " + columnasJoiner + " FROM " + tabla;
            if(!orden.equals(""))query+=" order by "+orden +" asc";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                if (tabla.equals("sucursal")) {
                    if (cosasABuscar.stream().anyMatch(columna -> columna.equals("id"))) listaCosas.add(rs.getInt("id"));
                    if (cosasABuscar.stream().anyMatch(columna -> columna.equals("nombre"))) listaCosas.add(rs.getString("nombre"));
                    if (cosasABuscar.stream().anyMatch(columna -> columna.equals("horaApertura")))listaCosas.add(rs.getInt("horaApertura"));
                    if (cosasABuscar.stream().anyMatch(columna -> columna.equals("horaCierre")))listaCosas.add(rs.getInt("horaCierre"));
                    if (cosasABuscar.stream().anyMatch(columna -> columna.equals("estado")))listaCosas.add(rs.getBoolean("estado")?Estado.OPERATIVA : Estado.NO_OPERATIVA);
                }else{
                    if (tabla.equals("camino")){
                        if (cosasABuscar.stream().anyMatch(columna -> columna.equals("id"))) listaCosas.add(rs.getInt("id"));
                        if (cosasABuscar.stream().anyMatch(columna -> columna.equals("idSucursalOrigen"))) listaCosas.add(rs.getInt("idSucursalOrigen"));
                        if (cosasABuscar.stream().anyMatch(columna -> columna.equals("idSucursalDestino"))) listaCosas.add(rs.getInt("idSucursalDestino"));
                        if (cosasABuscar.stream().anyMatch(columna -> columna.equals("tiempoDeViaje"))) listaCosas.add(rs.getInt("tiempoDeViaje"));
                        if (cosasABuscar.stream().anyMatch(columna -> columna.equals("capacidadMaxima"))) listaCosas.add(rs.getInt("capacidadMaxima"));
                        if (cosasABuscar.stream().anyMatch(columna -> columna.equals("estado"))) listaCosas.add(rs.getBoolean("estado"));
                    }
                    //expandir
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaCosas;
    }
    public static void inicializar(JFrame jFrame) {
        contadorSucursales = Gestor.getLastValue("id","sucursal");
        jFrame.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - jFrame.getWidth()) / 3;
        int y = (screenSize.height - jFrame.getHeight()) / 4;
        jFrame.setLocation(x, y);
        jFrame.setVisible(true);
        swingestor.setjFrame(jFrame);
    }
    public static int getLastValue(String nombreColumna, String tabla) {
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
    public static void actulizarProducto(Producto producto) {
        List<Integer> ids= new ArrayList<>();
        ids.add(producto.getId());
        Gestor.actualizarEnTable("producto",Producto.getCantidadDeColumnas(),Producto.getNombresColumnas(),producto.getValores(),Producto.getPrimaryKey(),ids);
    }
    public static void actulizarSucursal(Sucursal sucursal){
        List<Integer> ids= new ArrayList<>();
        ids.add(sucursal.getId());
        Gestor.actualizarEnTable("sucursal",Sucursal.getCantidadDeColumnas(),Sucursal.getNombresColumnas(),sucursal.getValores(),Sucursal.getPrimaryKey(),ids);
    }
    public static void buscarSucursal(String tabla){
             swingestor.menuBusqueda(tabla);
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

    public static void eliminarFila(String tabla, String primary_key, List<Integer> ids) {
        try (Connection conn = Conexion.getInstance().getConn()) {
            String query = "DELETE FROM "+ tabla + " WHERE " + primary_key + " = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            for (int i=0; i<ids.size();i++) {
                stmt.setInt(i + 1, ids.get(i));
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void actualizarEnTable(String tabla, int cantValores, List<String> columnas, List<Object> valores, String primaryKey,List<Integer> primaryKeys) {

            try (Connection conn = Conexion.getInstance().getConn()) {
                StringJoiner columnasJoiner = new StringJoiner(", ");
                for (int i = 0; i < cantValores; i++) {
                    columnasJoiner.add(columnas.get(i) + " = ?");
                }
                String query = "UPDATE " + tabla + " SET " + columnasJoiner.toString() + " WHERE " + primaryKey + " = ?;";
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    int i = 0;
                    for (; i < cantValores; i++) {
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
                    int techo=i+primaryKeys.size();
                    for (int j=0; i<techo;j++) {
                        preparedStatement.setInt(i + 1, primaryKeys.get(j));
                        i++;
                    }
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    public static Object datosFilaPorId(String tabla, int idBusqueda) {
        try (Connection conn = Conexion.getInstance().getConn()) {
            String query = "SELECT * FROM "+ tabla +" where id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, idBusqueda);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                if(tabla.equals("sucursal") ||tabla.equals("Sucursal")) {
                    String nombre = rs.getString("Nombre");
                    int id = rs.getInt("Id");
                    int horaApertura = rs.getInt("HoraApertura");
                    int horaCierre = rs.getInt("HoraCierre");
                    Estado estado = rs.getBoolean("Estado") ? Estado.OPERATIVA : Estado.NO_OPERATIVA;
                     return new Sucursal(id, horaApertura, horaCierre, estado, nombre);

                }else{
                    if(tabla.equals("producto") ||tabla.equals("Producto")) {
                        String nombre = rs.getString("nombre");
                        int id = rs.getInt("id");
                        String descripcion = rs.getString("descripcion");
                        Double precioUnitario = rs.getDouble("preciounitario");
                        Double pesoKg = rs.getDouble("pesokg");
                        return new Producto(id,nombre,descripcion,precioUnitario,pesoKg);
                    }
                }
                //expandir
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void actulizarStock(Stock stock, List<Integer> ids) {
       Gestor.actualizarEnTable("stock",Stock.getCantidadDeColumnas(),Stock.getNombresColumnas(),stock.getValores(),Stock.getPrimaryKey(),ids);
    }

    public static void actualizarCamino(Camino camino) {
        List<Integer> ids= new ArrayList<>();
        ids.add(camino.getId());
        Gestor.actualizarEnTable("camino",Camino.getCantidadDeColumnas(),Camino.getNombresColumnas(),camino.getValores(),Camino.getPrimaryKey(),ids);
    }

    public static void borrarSucursal(int id_sucursal){
        List<Integer> ids= new ArrayList<>();
        ids.add(id_sucursal);
        Gestor.eliminarFila(Sucursal.getNombreTabla(),Sucursal.getPrimaryKey(),ids);
    }

    public static void borrarProducto(int id) {
        List<Integer> ids= new ArrayList<>();
        ids.add(id);
        Gestor.eliminarFila("producto",Producto.getPrimaryKey(),ids);
    }

    public static void borrarStock(int idsucursal, int idproducto) {
        List<Integer> ids= new ArrayList<>();
        ids.add(idsucursal);
        ids.add(idproducto);
        Gestor.eliminarFila("stock",Stock.getPrimaryKey(),ids);
    }

    public static void borrarCamino(int id) {
        List<Integer> ids= new ArrayList<>();
        ids.add(id);
        Gestor.eliminarFila("camino",Producto.getPrimaryKey(),ids);
    }

    public static void cargarSucursal(Sucursal sucursal){
        Gestor.cargarEnTable("sucursal", Sucursal.getCantidadDeColumnas(), Sucursal.getNombresColumnas(), sucursal.getValores());
    }

    public static void cargarProducto(Producto producto) {
        Gestor.cargarEnTable("producto", Producto.getCantidadDeColumnas(), Producto.getNombresColumnas(), producto.getValores());
    }

    public static void cargarStock(Stock stock) {
        Gestor.cargarEnTable("stock", Stock.getCantidadDeColumnas(), Stock.getNombresColumnas(), stock.getValores());
    }

    public static void cargarCamino(Camino camino) {
        Gestor.cargarEnTable("camino", Camino.getCantidadDeColumnas(), Camino.getNombresColumnas(), camino.getValores());
    }
}
