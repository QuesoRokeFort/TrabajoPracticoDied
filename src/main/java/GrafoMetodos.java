import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.lang.*;
import java.sql.SQLException;
import java.util.*;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

class GrafoMetodos {
    Graph jGraph;
    int CANTIDAD_SUCURSALES;
    ArrayList<Integer> duracionRutas;
    Set<DefaultWeightedEdge> aristas;
    String nombreSumidero = "Sink"; //ARREGLAR (cuando arreglemos la bdd, poner sumidero como )
    String nombreFuente = "El Tunel"; //ARREGLAR
    int idFuente = -1;
    int idSumidero = -1;
    public GrafoMetodos(){
        try {
            List<Object> grafo = Gestor.createGraph();
            jGraph = (Graph) grafo.get(0);
            CANTIDAD_SUCURSALES = jGraph.vertexSet().size();
            this.duracionRutas = (ArrayList<Integer>) grafo.get(1);
            this.aristas = jGraph.edgeSet();
            for (DefaultWeightedEdge edge : aristas) {
                String sourceVertex = (String) jGraph.getEdgeSource(edge);
                String targetVertex = (String) jGraph.getEdgeTarget(edge);

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
        if (idFuente == -1 || idSumidero == -1) {
            System.out.println("Asegurese de que haya sumidero o fuente");
            return 0;
        }
        int[][] matrizDePesos = this.asMatrizPesos();
        return flujoMaximo(matrizDePesos, idFuente, idSumidero);
    }
    public double[] calcularPageRanks(){
        return this.pageRanks(this.asMatrizPesos());
    }

    public int[][] asMatrizPesos(){
        int[][] matriz = new int[CANTIDAD_SUCURSALES][CANTIDAD_SUCURSALES];

        for (DefaultWeightedEdge edge : this.aristas) {
            String sourceVertex = (String) jGraph.getEdgeSource(edge);
            String targetVertex = (String) jGraph.getEdgeTarget(edge);
            double edgeWeight = jGraph.getEdgeWeight(edge);
            int sourceId = Integer.parseInt(sourceVertex.split("-")[0]);
            int targetId = Integer.parseInt(targetVertex.split("-")[0]);
            matriz[sourceId - 1][targetId - 1] = (int) edgeWeight;
        }
        return matriz;
    }
    public double[] pageRanks(int[][] matrizPesos) {
        int n = matrizPesos.length;
        double[] pageRank = new double[n];
        double[] newPageRank = new double[n];

        double dampingFactor = 0.85; // generalmente 0.85 en PageRank de Google

        // Inicializa PageRank uniformemente
        for (int i = 0; i < n; i++) {
            pageRank[i] = 1.0 / n;
        }

        int maxIterations = 100; //
        double tolerance = 1e-6; // Tolerancia para la convergencia (ajusta según sea necesario)

        // Itera para calcular PageRank
        for (int iter = 0; iter < maxIterations; iter++) {
            double sum = 0.0;

            // Calcular la suma de PageRank de los nodos que enlazan a este nodo
            for (int i = 0; i < n; i++) {
                double inboundRank = 0.0;
                for (int j = 0; j < n; j++) {
                    if (matrizPesos[j][i] > 0) {
                        inboundRank += pageRank[j] / Arrays.stream(matrizPesos[j]).sum();
                    }
                }
                newPageRank[i] = (1.0 - dampingFactor) / n + dampingFactor * inboundRank;
                sum += newPageRank[i];
            }

            // Normalizar PageRank para que sume 1
            for (int i = 0; i < n; i++) {
                newPageRank[i] += (1.0 - sum) / n;
            }

            // Verificar convergencia
            boolean converged = true;
            for (int i = 0; i < n; i++) {
                if (Math.abs(pageRank[i] - newPageRank[i]) > tolerance) {
                    converged = false;
                    break;
                }
            }

            // Actualizar PageRank y salir si ha convergido
            System.arraycopy(newPageRank, 0, pageRank, 0, n);
            if (converged) {
                break;
            }
        }

        return pageRank;
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
        double[] pageRanks = pato.calcularPageRanks();
        for (int i=0;i<pageRanks.length; i++){
            System.out.print(pageRanks[i]+" ");
        }
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