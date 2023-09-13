import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.io.IOException;
import java.lang.*;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.IntStream;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

class GrafoMetodos {
    private static final int ITERACIONES_PAGE_RANK = 100; //ITERACIONES_PAGE_RANK
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
            System.out.println("PARA CALCULAR EL FLUJO MAXIMO DEBE HABER FUENTE Y SUMIDERO (Y deben estar nombrados correctamente)");
            return -1;
        }
        int[][] matrizPesos = this.asMatrizPesos();
        return flujoMaximo(matrizPesos);
    }
    public List<List<Integer>> encontrarCaminos(int origen, int destino) {
        boolean[] visitado = new boolean[CANTIDAD_SUCURSALES];
        ArrayList<Integer> camino = new ArrayList<>();
        List<List<Integer>> caminos = new ArrayList<>();
        origen -= 1;
        destino -= 1;

        buscarEnProfundidad(origen, destino, visitado, camino, caminos);
        for (List<Integer> caminoActual : caminos) {
            for (int i = 0; i < caminoActual.size(); i++) {
                caminoActual.set(i, caminoActual.get(i) + 1);
            }
        }
        return caminos;
    }

    public double[] calcularPageRanks(){
        double[] resultado = this.pageRanks(this.asMatrizPesos());
        if (resultado[1] == -1) {
            return resultado; //cambiar: tirar error de no convergencia o falla en alg.
        };
        return resultado;
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
    public LinkedList<Integer>[] getAdyacencia() {
        //int CANTIDAD_SUCURSALES = matrizPesos.length;
        LinkedList<Integer>[] adyacencias = new LinkedList[CANTIDAD_SUCURSALES];
        int[][] matrizPesos = asMatrizPesos(); //cambiar: matrizPesos deberia ser una variable de la instancia cuando inicializo para no tener que gettear cada vez...

        for (int i = 0; i < CANTIDAD_SUCURSALES; i++) {
            adyacencias[i] = new LinkedList<>();
            for (int j = 0; j < CANTIDAD_SUCURSALES; j++) {
                if (matrizPesos[i][j] > 0) {
                    adyacencias[i].add(j);
                }
            }
        }
        return adyacencias;
    }
    public double[] pageRanks(int[][] matrizPesos) {
        //int CANTIDAD_SUCURSALES = matrizPesos.length;
        double[] pageRank;
        double[] nuevoPageRank = new double[CANTIDAD_SUCURSALES];
        double factorAmortiguacion = 0.85;
        double tolerancia = 1e-6;

        // Inicializa PageRank uniformemente
        pageRank = IntStream.range(0, CANTIDAD_SUCURSALES).mapToDouble(i -> 1.0 / CANTIDAD_SUCURSALES).toArray();

        // Itera para calcular PageRank, la variable ITERACIONES_PAGE_RANK es de la clase!!
        for (int vuelta = 0; vuelta < ITERACIONES_PAGE_RANK; vuelta++) {
            double suma = 0.0;

            // Calcular la suma de PageRank de los nodos que enlazan a este nodo
            for (int i = 0; i < CANTIDAD_SUCURSALES; i++) {
                double rankingEntrada = 0.0;
                for (int j = 0; j < CANTIDAD_SUCURSALES; j++) {
                    if (matrizPesos[j][i] > 0) {
                        rankingEntrada += pageRank[j] / Arrays.stream(matrizPesos[j]).sum();
                    }
                }
                nuevoPageRank[i] = (1.0 - factorAmortiguacion) / CANTIDAD_SUCURSALES + factorAmortiguacion * rankingEntrada;
                suma += nuevoPageRank[i];
            }

            // Normalizar PageRank para que sume 1
            for (int i = 0; i < CANTIDAD_SUCURSALES; i++) {
                nuevoPageRank[i] += (1.0 - suma) / CANTIDAD_SUCURSALES;
            }

            // converge si epsilon < tolerancia
            boolean converged = true;
            for (int i = 0; i < CANTIDAD_SUCURSALES; i++) {
                if (Math.abs(pageRank[i] - nuevoPageRank[i]) > tolerancia) {
                    converged = false;
                    break;
                }
            }

            System.arraycopy(nuevoPageRank, 0, pageRank, 0, CANTIDAD_SUCURSALES);
            if (converged) {
                return pageRank;
            }
        }
        return new double[]{-1};
    }
    private boolean BusquedaEnAnchura(int[][] grafoResiduales, int[] padre){
        boolean[] visitado = new boolean[CANTIDAD_SUCURSALES];

        LinkedList<Integer> visitadosCola = new LinkedList<Integer>();
        visitadosCola.add(idFuente);
        visitado[idFuente] = true;
        padre[idFuente] = -1;

        while (visitadosCola.size() != 0) {
            int nodoActual = visitadosCola.poll();

            for (int nodoVecino = 0; nodoVecino < CANTIDAD_SUCURSALES; nodoVecino++) {
                if (!visitado[nodoVecino] && grafoResiduales[nodoActual][nodoVecino] > 0) {
                    if (nodoVecino == idSumidero) {
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

    private void buscarEnProfundidad(int origen, int destino, boolean[] visitado, ArrayList<Integer> camino, List<List<Integer>> caminos) {
        visitado[origen] = true;
        camino.add(origen);
        LinkedList<Integer>[] adyacencia = this.getAdyacencia();
        if (origen == destino) {
            // Si llegamos al destino, agregamos el camino actual a la lista de caminos
            caminos.add(new ArrayList<>(camino));
        } else {
            for (Integer adyacente : adyacencia[origen]) {
                if (!visitado[adyacente]) {
                    buscarEnProfundidad(adyacente, destino, visitado, camino, caminos);
                }
            }
        }

        // Marcamos el vértice como no visitado para explorar otros caminos
        visitado[origen] = false;
        camino.remove(camino.size() - 1);
    }
    // Encuentra todos los caminos posibles entre origen y destino

    // algoritmo de flujo maximo (para mostrar los tests, se hizo publico)
    public int flujoMaximo(int[][] pesosGrafo){
        int u, v;
        int[][] pesosResidualesGrafo = new int[CANTIDAD_SUCURSALES][CANTIDAD_SUCURSALES];

        for (u = 0; u < CANTIDAD_SUCURSALES; u++)
            for (v = 0; v < CANTIDAD_SUCURSALES; v++)
                pesosResidualesGrafo[u][v] = pesosGrafo[u][v];

        int[] padreDe = new int[CANTIDAD_SUCURSALES];

        int flujoMaximo = 0; // There is no flow initially

        while (BusquedaEnAnchura(pesosResidualesGrafo, padreDe)) {
            int flujoCamino = MAX_VALUE;
            for (v = idSumidero; v != idFuente; v = padreDe[v]) {
                u = padreDe[v];
                flujoCamino = min(flujoCamino, pesosResidualesGrafo[u][v]);
            }

            for (v = idSumidero; v != idFuente; v = padreDe[v]) {
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
        //notas internas:

        //ejemplo de uso de calcular page ranks:
        double[] pageRanks = pato.calcularPageRanks();
        for (int i=0;i<pageRanks.length; i++){
            System.out.print(pageRanks[i]+" ");
        }
        System.out.println();

        //ejemplos de uso de calcularFlujoMaximo:
        System.out.println(pato.calcularFlujoMaximo());

        //ejemplo de uso de encontrarCaminos y como obtener los caminos!
        List<List<Integer>> caminos = pato.encontrarCaminos(4,5);
        System.out.println("caminos: ");
        for (int i = 0; i < caminos.size(); i++) {
            List<Integer> camino = caminos.get(i);
            System.out.print("Camino " + (i + 1) + ": ");
            for (int j = 0; j < camino.size(); j++) {
                System.out.print(camino.get(j));
                if (j < camino.size() - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        }
    }
    /*      Ejemplo de uso de la funcion original de Flujo Maximo en que se baso esta Clase

            public static void main(String[] args)
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