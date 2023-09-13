import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private Connection conn;
    private static Conexion _INSTANCE;
    private Conexion(){

    }
    public static Conexion getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new Conexion();
        }
        return _INSTANCE;
    }
    public Connection getConn() throws SQLException {
        if (conn == null || conn.isClosed()){
            this.crearConexion();
        }
        return this.conn;
    }
    private void crearConexion(){
        String url = "jdbc:postgresql://localhost:5432/TPtest";
        String username = "postgres";
        String password = "gengis100"; //gengis100/123219
        try{
            Class.forName("org.postgresql.Driver");
            this.conn = DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
