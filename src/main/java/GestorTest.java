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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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