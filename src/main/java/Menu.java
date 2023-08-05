import javax.swing.*;
import java.util.List;

public class Menu {
    public static void main(String[] args)  {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor.inicializar(jFrame);
        int caso = 1;
        while (caso != 0) {
            String tabla = new String();
            caso = Gestor.swingestor.swingMenuListaBotones(2, List.of(new String[]{"Sucursal", "Producto","buscarSucursal"}));
            switch (caso) {
                case 1:
                    tabla= "sucursal";
                    break;
                case 2:
                    tabla="producto";
                    break;
                case 3:
                    break;
            }
            if (caso!=0) Gestor.buscarSucursal(tabla);
        }
        jFrame.dispose();
    }
}