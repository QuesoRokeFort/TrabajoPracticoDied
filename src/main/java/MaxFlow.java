import java.lang.*;
import java.util.LinkedList;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;

class MaxFlow {
    static final int CANTIDAD_SUCURSALES = 6;

    boolean BusquedaEnAnchura(int rGraph[][], int fuente, int sumidero, int padre[]){
        boolean[] visitado = new boolean[CANTIDAD_SUCURSALES];

        LinkedList<Integer> visitadosCola = new LinkedList<Integer>();
        visitadosCola.add(fuente);
        visitado[fuente] = true;
        padre[fuente] = -1;

        while (visitadosCola.size() != 0) {
            int u = visitadosCola.poll();

            for (int v = 0; v < CANTIDAD_SUCURSALES; v++) {
                if (!visitado[v] && rGraph[u][v] > 0) {
                    if (v == sumidero) {
                        padre[v] = u;
                        return true;
                    }
                    visitadosCola.add(v);
                    padre[v] = u;
                    visitado[v] = true;
                }
            }
        }
        return false;
    }

    // algoritmo de flujo maximo
    int flujoMaximo(int pesosPorCaminoGrafo[][], int fuenteId, int sumideroId){
        int u, v;
        int[][] pesosResidualesGrafo = new int[CANTIDAD_SUCURSALES][CANTIDAD_SUCURSALES];

        for (u = 0; u < CANTIDAD_SUCURSALES; u++)
            for (v = 0; v < CANTIDAD_SUCURSALES; v++)
                pesosResidualesGrafo[u][v] = pesosPorCaminoGrafo[u][v];

        int[] padreDeVector = new int[CANTIDAD_SUCURSALES];

        int flujoMaximo = 0; // There is no flow initially

        while (BusquedaEnAnchura(pesosResidualesGrafo, fuenteId, sumideroId, padreDeVector)) {
            int flujoCamino = MAX_VALUE;
            for (v = sumideroId; v != fuenteId; v = padreDeVector[v]) {
                u = padreDeVector[v];
                flujoCamino = min(flujoCamino, pesosResidualesGrafo[u][v]);
            }

            for (v = sumideroId; v != fuenteId; v = padreDeVector[v]) {
                u = padreDeVector[v];
                pesosResidualesGrafo[u][v] -= flujoCamino;
                pesosResidualesGrafo[v][u] += flujoCamino;
            }

            flujoMaximo += flujoCamino;
        }
        return flujoMaximo;
    }
}
/*

public class Main {
    public static void main(String[] args)
            throws Exception {
        //    {0, 16, 13, 0, 0, 0}
        // 1 ->1,  2,  3, 4, 5, 6
        int graph[][] = new int[][]{
                {0, 16, 13, 0, 0, 0}, {0, 0, 10, 12, 0, 0},
                {0, 4, 0, 0, 14, 0}, {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4}, {0, 0, 0, 0, 0, 0}
        };
        MaxFlow m = new MaxFlow();

        System.out.println("The maximum possible flow is "
                + m.flujoMaximo(graph, 0, 5));
    }
}
 */