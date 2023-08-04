import javax.swing.*;

public class Menu {
    public static void main(String[] args)  {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor.inicializar();
        int caso = 1;
        while (caso != 0) {
            caso = Gestor.showMenu(jFrame);
            switch (caso) {
                case 1:
                    Gestor.agregarSucursal(jFrame);
                    break;
                case 2:
                    Gestor.listarSucursales(jFrame);
                    break;
                case 3:
                    Gestor.buscarSucursal(jFrame);
                    break;
            }
        }
        jFrame.dispose();
    }
}