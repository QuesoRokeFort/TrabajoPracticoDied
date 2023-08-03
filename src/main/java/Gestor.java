import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class Gestor {
    ArrayList<Sucursal> sucursales = new ArrayList<Sucursal>();
    ArrayList<Camino> caminos = new ArrayList<Camino>();
    Swingestor swingestor =new Swingestor();
    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }
    public void actualizarSucursales(){
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
                    Sucursal sucursal = new Sucursal(nombre,horaApertura,horaCierre,estado,id);;
                    listaSucursales.add(sucursal);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            this.sucursales=listaSucursales;
    }

    public void addSucursales(JFrame jFrame) {
        int mayorId = sucursales.stream()
                .mapToInt(Sucursal::getId)
                .max()
                .orElse(0);
        Sucursal s=swingestor.addSucursal(jFrame,(mayorId+1));
        if (!s.getNombre().equals("")) {
            try (Connection conn = Conexion.getInstance().getConn()) {
                String query = "INSERT INTO " + "Sucursal" + " (Nombre, Id, HoraApertura, HoraCierre, Estado) VALUES(?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, s.getNombre());
                preparedStatement.setInt(2,s.getId());
                preparedStatement.setInt(3, s.getHoraApertura());
                preparedStatement.setInt(4, s.getHoraCierre());
                preparedStatement.setBoolean(5, s.getEstado());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.actualizarSucursales();
        }
    }

    public Gestor() {
    }

    public int showMenu(JFrame jFrame) {
        return swingestor.swingMenu(jFrame);
    }
    public void buscarSucursal(JFrame jFrame){
        String nombre;

        nombre=swingestor.menuBusqueda(jFrame, sucursales.stream().map(s -> s.getNombre()).toList());
        Optional<Sucursal> sucursalEncontrada = sucursales.stream()
                .filter(s -> s.getNombre().equals(nombre))
                .findFirst();
        Sucursal sucursal = sucursalEncontrada.orElse(null);
        if (!nombre.equals("")) showModificarSucursal(jFrame,sucursal);
    }
    private void showModificarSucursal(JFrame jFrame, Sucursal s) {
            Sucursal sucursal=swingestor.modificarSucursal(jFrame,s);
            System.out.println(sucursal.getNombre());
            if(! (sucursal.getNombre() == null)) {
                actualizarSucursal(sucursal, s);
                actualizarSucursales();
            }
    }
    public void actualizarSucursal(Sucursal sucursal, Sucursal s){
        try (Connection conn = Conexion.getInstance().getConn()) {
            if (sucursal.getFlagBorrado()) {
                System.out.println(sucursal.getId());
                String query = "DELETE FROM Sucursal where id = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1,sucursal.getId());
                stmt.executeUpdate();
            } else {
                if (!sucursal.equals(s)) {
                    String query = "UPDATE Sucursal SET Nombre = ?, HoraApertura = ?,HoraCierre = ?,Estado = ? WHERE Id = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, sucursal.getNombre());
                    stmt.setInt(2, sucursal.getHoraApertura());
                    stmt.setInt(3, sucursal.getHoraCierre());
                    stmt.setBoolean(4, sucursal.getEstado());
                    stmt.setInt(5, sucursal.getId());
                    stmt.executeUpdate();
                }
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void showSucursales(JFrame jFrame) {
            swingestor.showSucursal(jFrame,sucursales);
    }
}
