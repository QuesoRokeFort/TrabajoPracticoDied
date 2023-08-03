import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.StringJoiner;

public class GestorTest {
    public void cargarEnTable(String tabla,int cantValores, List<String> columnas, List<Object> valores) {

        try ( Connection conn = Conexion.getInstance().getConn()) {
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
                        preparedStatement.setString(i + 1, (String)valores.get(i));
                    } else if (valores.get(i) instanceof Integer) {
                        preparedStatement.setInt(i + 1, (int)valores.get(i));
                    } else if (valores.get(i) instanceof Double) {
                        preparedStatement.setDouble(i + 1, (double)valores.get(i));
                    } else if (valores.get(i) instanceof Estado) {
                        preparedStatement.setBoolean(i + 1, valores.get(i).equals(Estado.OPERATIVA)?true:false);
                    }
                }
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private int obtenerTipoSql(Object valor) {
        if (valor instanceof String) {
            return Types.VARCHAR;
        } else if (valor instanceof Integer) {
            return Types.INTEGER;
        } else if (valor instanceof Double) {
            return Types.DOUBLE;
        } else if (valor instanceof Estado) {
            return Types.VARCHAR; // O el tipo de dato correcto para tu base de datos
        }
        // Agregar otros tipos según sea necesario
        return Types.OTHER;
    }
}