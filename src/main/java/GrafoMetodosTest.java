import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;

public class GrafoMetodosTest {
    private GrafoMetodos grafoMetodos;

    @BeforeEach
    public void setUp() {
        // Configura el objeto GrafoMetodos antes de cada prueba si es necesario.
        grafoMetodos = new GrafoMetodos();
    }
    @Test
    public void testCalcularFlujoMaximo_CasoNormal() {
        int valorEsperado = 23;
        grafoMetodos.CANTIDAD_SUCURSALES = 6;
        grafoMetodos.idFuente = 0;
        grafoMetodos.idSumidero = 5;
        int grafoDePrueba[][] = new int[][] {
                    { 0, 16, 13, 0, 0, 0 }, { 0, 0, 10, 12, 0, 0 },
                    { 0, 4, 0, 0, 14, 0 },  { 0, 0, 9, 0, 0, 20 },
                    { 0, 0, 0, 7, 0, 4 },   { 0, 0, 0, 0, 0, 0 }
            };
        int flujoMaximo = grafoMetodos.flujoMaximo(grafoDePrueba);
        assertEquals(valorEsperado, flujoMaximo);
    }
    @Test
    public void testCalcularFlujoMaximo_CasoSinFuenteYSumidero() {
        int valorEsperado = -1;
        grafoMetodos.CANTIDAD_SUCURSALES = 6;
        grafoMetodos.idFuente = -1; //si no se setea fuente o sumidero estos estarían inicializados como -1
        grafoMetodos.idSumidero = -1;

        int flujoMaximo = grafoMetodos.calcularFlujoMaximo();
        assertEquals(valorEsperado, flujoMaximo);
    }

}
