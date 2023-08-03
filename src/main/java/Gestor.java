import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class Gestor {
    ArrayList<Camino> caminos = new ArrayList<Camino>();
    Swingestor swingestor =new Swingestor();
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

    public void addSucursales(JFrame jFrame) {
            try (Connection conn = Conexion.getInstance().getConn()) {
                int id=0;
                String queryMayorId = "select MAX(id) from sucursal";
                PreparedStatement preparedStatementid = conn.prepareStatement(queryMayorId);
                ResultSet resultSet = preparedStatementid.executeQuery();
                if (resultSet.next()) {
                    id = resultSet.getInt(1)+1; // 1 is the column index for the first column (MAX(id))
                }
                Sucursal nuevaSucursal=swingestor.addSucursal(jFrame, id);
                if(nuevaSucursal.isModificada()) {
                    nuevaSucursal.Modificada();
                String query = "INSERT INTO " + "Sucursal" + " (Nombre, Id, HoraApertura, HoraCierre, Estado) VALUES(?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, nuevaSucursal.getNombre());
                preparedStatement.setInt(2, nuevaSucursal.getId());
                preparedStatement.setInt(3, nuevaSucursal.getHoraApertura());
                preparedStatement.setInt(4, nuevaSucursal.getHoraCierre());
                preparedStatement.setBoolean(5, nuevaSucursal.getEstado());
                preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public Gestor() {
    }

    public int showMenu(JFrame jFrame) {
        return swingestor.swingMenu(jFrame);
    }
    public void buscarSucursal(JFrame jFrame){
        int idBusqueda;

        idBusqueda=swingestor.menuBusqueda(jFrame);
        Sucursal sucursal= new Sucursal();
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
                boolean estado = rs.getBoolean("Estado");
                sucursal = new Sucursal(id,horaApertura,horaCierre,estado,nombre);;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (idBusqueda != 0) showModificarSucursal(jFrame,sucursal);
    }
    private void showModificarSucursal(JFrame jFrame, Sucursal s) {
            swingestor.modificarSucursal(jFrame,s);
            if(s.getFlagBorrado()) {
                borrarSucursal(s);
            }else{
                if (s.isModificada()) {
                    actualizarSucursal(s);
                }
            }
    }
    public void borrarSucursal(Sucursal s){
        try (Connection conn = Conexion.getInstance().getConn()) {
                String query = "DELETE FROM Sucursal where id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,s.getId());
                stmt.executeUpdate();
            }catch (SQLException e) {
                throw new RuntimeException(e);
        }
    }
    public void actualizarSucursal(Sucursal s){
        s.Modificada();
        try (Connection conn = Conexion.getInstance().getConn()) {
             String query = "UPDATE Sucursal SET Nombre = ?, HoraApertura = ?,HoraCierre = ?,Estado = ? WHERE Id = ?";
             PreparedStatement stmt = conn.prepareStatement(query);
             stmt.setString(1, s.getNombre());
             stmt.setInt(2, s.getHoraApertura());
             stmt.setInt(3, s.getHoraCierre());
             stmt.setBoolean(4, s.getEstado());
             stmt.setInt(5, s.getId());
             stmt.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void showSucursales(JFrame jFrame) {
            swingestor.listarSucursales(jFrame);
    }
}
