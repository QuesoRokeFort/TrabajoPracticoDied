import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class Menu {
    public static void main(String[] args)  {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor.inicializar(jFrame);
        //test
        int caso = 1;
        while (caso != 0) {
            String tabla = new String();
            caso = Gestor.swingestor.swingMenuListaBotones(6, List.of(new String[]{"Sucursal", "Producto","Stock","camino","grafo","pedidos"}));
            switch (caso) {
                case 1:
                    tabla= "sucursal";
                    break;
                case 2:
                    tabla="producto";
                    break;
                case 3:
                    tabla="stock";
                    break;
                case 4:
                    tabla="camino";
                    break;
                case 5:
                    Gestor.swingestor.menuGrafo();
                    break;
                case 6:
                    tabla="ordenpedido";
                    break;
            }
            if (caso!=0 && caso !=5) Gestor.buscartabla(tabla);
        }
        jFrame.dispose();
    }
}