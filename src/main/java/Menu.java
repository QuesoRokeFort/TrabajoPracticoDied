import javax.swing.*;
import java.util.List;

public class Menu {
    public static void main(String[] args)  {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor.inicializar(jFrame);
        int caso = 1;
        while (caso != 0) {
            caso = Gestor.swingestor.swingMenuListaBotones(1, List.of(new String[]{"Sucursal", "listarSucursales","buscarSucursal"}));
            switch (caso) {
                case 1:
                    Gestor.buscarSucursal();
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
        }
        jFrame.dispose();
    }
}