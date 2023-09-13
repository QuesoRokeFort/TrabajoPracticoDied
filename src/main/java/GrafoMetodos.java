import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.lang.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

class GrafoMetodos {
    static final int CANTIDAD_SUCURSALES = 5; //ARREGLAR
    Graph newG;
    ArrayList<Integer> pesos;
    Set<DefaultWeightedEdge> edges;
    int idFuente;
    String nombreSumidero = "Sink"; //ARREGLAR
    String nombreFuente = "El Tunel"; //ARREGLAR
    int idSumidero;
    public GrafoMetodos(){
        try {
            List<Object> grafo = Gestor.createGraph();
            newG = (Graph) grafo.get(0);
            this.pesos = (ArrayList<Integer>) grafo.get(1);
            this.edges = newG.edgeSet();
            for (DefaultWeightedEdge edge : edges) {
                String sourceVertex = (String) newG.getEdgeSource(edge);
                String targetVertex = (String) newG.getEdgeTarget(edge);

                if (targetVertex.endsWith("-"+ nombreSumidero)) {
                    idSumidero = Integer.parseInt(targetVertex.split("-")[0]) - 1;
                }
                if (sourceVertex.endsWith("-"+ nombreFuente)) {
                    idFuente = Integer.parseInt(sourceVertex.split("-")[0]) - 1;
                }
        }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int calcularFlujoMaximo(){
        int[][] graph = new int[CANTIDAD_SUCURSALES][CANTIDAD_SUCURSALES];
        for (DefaultWeightedEdge edge : this.edges) {
            String sourceVertex = (String) newG.getEdgeSource(edge);
            String targetVertex = (String) newG.getEdgeTarget(edge);
            double edgeWeight = newG.getEdgeWeight(edge);

            // Los grafos estan etiquetados desde 1 hasta CANTIDAD_SUCURSALES
            int sourceId = Integer.parseInt(sourceVertex.split("-")[0]);
            int targetId = Integer.parseInt(targetVertex.split("-")[0]);

            // Actualiza la matriz graph[][] con el peso de la arista
            // Resta 1 a sourceId y targetId para que coincidan con los índices de la matriz (si los índices comienzan en 0)
            graph[sourceId - 1][targetId - 1] = (int) edgeWeight;
        }
        return flujoMaximo(graph, idFuente, idSumidero);
    }
    private boolean BusquedaEnAnchura(int grafoResiduales[][], int fuente, int sumidero, int padre[]){
        boolean[] visitado = new boolean[CANTIDAD_SUCURSALES];

        LinkedList<Integer> visitadosCola = new LinkedList<Integer>();
        visitadosCola.add(fuente);
        visitado[fuente] = true;
        padre[fuente] = -1;

        while (visitadosCola.size() != 0) {
            int nodoActual = visitadosCola.poll();

            for (int nodoVecino = 0; nodoVecino < CANTIDAD_SUCURSALES; nodoVecino++) {
                if (!visitado[nodoVecino] && grafoResiduales[nodoActual][nodoVecino] > 0) {
                    if (nodoVecino == sumidero) {
                        padre[nodoVecino] = nodoActual;
                        return true;
                    }
                    visitadosCola.add(nodoVecino);
                    padre[nodoVecino] = nodoActual;
                    visitado[nodoVecino] = true;
                }
            }
        }
        return false;
    }

    // algoritmo de flujo maximo
    private int flujoMaximo(int pesosGrafo[][], int fuenteId, int sumideroId){
        int u, v;
        int[][] pesosResidualesGrafo = new int[CANTIDAD_SUCURSALES][CANTIDAD_SUCURSALES];

        for (u = 0; u < CANTIDAD_SUCURSALES; u++)
            for (v = 0; v < CANTIDAD_SUCURSALES; v++)
                pesosResidualesGrafo[u][v] = pesosGrafo[u][v];

        int[] padreDe = new int[CANTIDAD_SUCURSALES];

        int flujoMaximo = 0; // There is no flow initially

        while (BusquedaEnAnchura(pesosResidualesGrafo, fuenteId, sumideroId, padreDe)) {
            int flujoCamino = MAX_VALUE;
            for (v = sumideroId; v != fuenteId; v = padreDe[v]) {
                u = padreDe[v];
                flujoCamino = min(flujoCamino, pesosResidualesGrafo[u][v]);
            }

            for (v = sumideroId; v != fuenteId; v = padreDe[v]) {
                u = padreDe[v];
                pesosResidualesGrafo[u][v] -= flujoCamino;
                pesosResidualesGrafo[v][u] += flujoCamino;
            }

            flujoMaximo += flujoCamino;
        }
        return flujoMaximo;
    }
    public static void main(String[] args)
            throws Exception{
        GrafoMetodos pato = new GrafoMetodos();
        System.out.println(pato.calcularFlujoMaximo());
    }
    /*    public static void main(String[] args)
                throws Exception {
            //    {0, 16, 13, 0, 0, 0}
            // 1 ->1,  2,  3, 4, 5, 6
            int graph[][] = new int[][]{
                    {0, 16, 13, 0, 0, 0},
                    {0, 0, 10, 12, 0, 0},
                    {0, 4, 0, 0, 14, 0},
                    {0, 0, 9, 0, 0, 20},
                    {0, 0, 0, 7, 0, 4},
                    {0, 0, 0, 0, 0, 0}
            };
            MaxFlow m = new MaxFlow();

            System.out.println("The maximum possible flow is "
                    + m.flujoMaximo(graph, 0, 5));
        }

     */
}