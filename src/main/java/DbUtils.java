import javax.swing.*;
import java.sql.*;
import java.util.Properties;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
public class DbUtils {
    public static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
class DataBase {
    private Connection getConnection() throws SQLException {
        return Conexion.getInstance().getConn();
    }

    ResultSet search(String searchFor, JPanel panel) {
        PreparedStatement statement;
        Connection connection = null;
        try {
            connection = getConnection();
            String qry = "select * from sucursal" + (searchFor.equals("")? "" :" where id = ? or nombre = ? "+ "order by id");
            statement = connection.prepareStatement(qry);
            if (searchFor.matches("\\d+")) {
                statement.setInt(1, Integer.parseInt(searchFor)); // Configurando el valor para el primer marcador de posición
                statement.setString(2, searchFor); // Dejando el segundo marcador de posición en blanco
            } else if(!searchFor.equals("")){
                statement.setInt(1, 0); // Dejando el primer marcador de posición en blanco
                statement.setString(2, searchFor); // Configurando el valor para el segundo marcador de posición
            }
            return statement.executeQuery();

        } catch (SQLException e) {
            if (connection == null) {
                JOptionPane.showMessageDialog(panel, "Connection to database failed.");
            } else {
                JOptionPane.showMessageDialog(panel, "No results found for" + searchFor + " in the database.");
            }
            e.printStackTrace();
        }
        return null;
    }
}
