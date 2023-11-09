import javax.swing.*;

import java.util.List;

public class Menu {
    public static void main(String[] args)  {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor.inicializar(jFrame);
        //test
        int caso = 1;
        while (caso != 0) {
            String tabla ;
            caso = Gestor.swingestor.swingMenuListaBotones(6, List.of(new String[]{"Sucursal", "Producto","Stock","camino","grafo","pedidos"}));
            switch (caso) {
                case 1 -> tabla = "sucursal";
                case 2 -> tabla = "producto";
                case 3 -> tabla = "stock";
                case 4 -> tabla = "camino";
                case 5 -> Gestor.swingestor.menuGrafo();
                case 6 -> tabla = "ordenpedido";
            }
            if (caso!=0 && caso !=5) Gestor.buscartabla(tabla);
        }
        jFrame.dispose();
    }
}