import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//endswith
//creategraph en gestor



public class GestorTestCarlo {
    public static void main(String[] args) throws SQLException, IOException {
        List<Object> grafo = Gestor.createGraph();
        Graph newG = (Graph) grafo.get(0);
        Set<DefaultWeightedEdge> edges = newG.edgeSet();
        ArrayList<Integer> pesos = (ArrayList<Integer>) grafo.get(1);
        int i = 0;
        /*for (DefaultWeightedEdge edge : edges) {
            String sourceVertex = (String) newG.getEdgeSource(edge);
            String targetVertex = (String) newG.getEdgeTarget(edge);
            double edgeWeight = newG.getEdgeWeight(edge);
            //double edgeWeight = newG[1].getEdgeWeight(edge);

            System.out.println("Arista: " + sourceVertex + " -> " + targetVertex + " | tiempo: " + edgeWeight + " | peso: " + pesos.get(i) + " kg");
            i++;
        }*/
        // Supongamos que tienes un número total de vértices, por ejemplo, 5
        int totalVertices = 5;
        int[][] graph = new int[totalVertices][totalVertices];
        for (DefaultWeightedEdge edge : edges) {
            String sourceVertex = (String) newG.getEdgeSource(edge);
            String targetVertex = (String) newG.getEdgeTarget(edge);
            double edgeWeight = newG.getEdgeWeight(edge);

            // Supongamos que los vértices están etiquetados desde 1 hasta totalVertices
            int sourceId = Integer.parseInt(sourceVertex.split("-")[0]);
            int targetId = Integer.parseInt(targetVertex.split("-")[0]);

            // Actualiza la matriz graph[][] con el peso de la arista
            // Resta 1 a sourceId y targetId para que coincidan con los índices de la matriz (si los índices comienzan en 0)
            graph[sourceId - 1][targetId - 1] = (int) edgeWeight;
        }
        for (i = 0; i < totalVertices; i++) {
            for (int j = 0; j < totalVertices; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println(); // Salto de línea después de cada fila
        }
    }
}