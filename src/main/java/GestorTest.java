import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class GestorTest {
    public static void main(String[] args) throws SQLException, IOException {
        Graph newG = Gestor.createGraph();

        Set<DefaultWeightedEdge> edges = newG.edgeSet();

        for (DefaultWeightedEdge edge : edges) {
            String sourceVertex = (String) newG.getEdgeSource(edge);
            String targetVertex = (String) newG.getEdgeTarget(edge);
            double edgeWeight = newG.getEdgeWeight(edge);
            //double edgeWeight = newG[1].getEdgeWeight(edge);

            System.out.println("Arista: " + sourceVertex + " -> " + targetVertex + " | Peso: " + edgeWeight);
        }

    }



    public static void ordenDeProvicion(JFrame jframe, Sucursal sucursal) throws SQLException {
            JPanel jPanel = new JPanel(new FlowLayout()); // Usar FlowLayout como administrador de diseño
            Date fechaActual = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaHoy = formato.format(fechaActual);
            JLabel fecha = new JLabel(fechaHoy);
            List<String> cosas = new ArrayList<>();
            cosas.add("id");
            cosas.add("nombre");
            ResultSet listaProductos = Gestor.buscarCosas(cosas, "producto", "id");
            String nombre = "";
            List<String> nombres = new ArrayList<>();
            while (listaProductos.next()) {
                try {
                    nombre = listaProductos.getInt("id") + "-" + listaProductos.getString("nombre");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                nombres.add(nombre);
            }
            String[] opciones = nombres.toArray(new String[0]);
            List<JComboBox<String>> comboBoxes = new ArrayList<>();
            List<JTextField> textFields = new ArrayList<>();

        // Crear el JComboBox y agregarle las opciones
            JButton agregar = new JButton("agregar");
            agregar.addActionListener(e -> {
                JComboBox<String> comboBox = new JComboBox<>(opciones);
                JTextField textField = createPlaceholderTextField("Cantidad");
                jPanel.add(comboBox);
                jPanel.add(textField);
                jPanel.revalidate();
                jPanel.repaint();
                comboBoxes.add(comboBox);
                textFields.add(textField);
            });
            JButton guardar = new JButton("guardar");
            guardar.addActionListener(e -> {
                for (int i = 0; i < comboBoxes.size(); i++) {
                    JComboBox<String> comboBox = comboBoxes.get(i);
                    JTextField textField = textFields.get(i);

                    String selectedItem = (String) comboBox.getSelectedItem();
                    String cantidad = textField.getText();

                    System.out.println("Seleccionado: " + selectedItem + ", Cantidad: " + cantidad);
                    int indexOfDash = selectedItem.indexOf('-');
                    String idProducto = selectedItem.substring(0, indexOfDash);
                    System.out.println(idProducto);
                }
            });
            jframe.getContentPane().removeAll();
            jframe.revalidate();
            jframe.repaint();
            jframe.setSize(550, 600);
            jPanel.add(fecha);
            jPanel.add(agregar);
            jPanel.add(guardar);
            jframe.add(jPanel);
            jframe.setVisible(true);
        }
    public static JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField(15);
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        // Agregar un FocusListener para cambiar el texto cuando se obtiene y pierde el enfoque
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        return textField;
    }
}