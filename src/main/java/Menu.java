import javax.swing.*;

public class Menu {
    public static void main(String[] args)  {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor gestor = new Gestor();
        int caso = 1;
        while (caso != 0) {
            caso = gestor.showMenu(jFrame);
            switch (caso) {
                case 1:
                    gestor.agregarSucursal(jFrame);
                    break;
                case 2:
                    gestor.listarSucursales(jFrame);
                    break;
                case 3:
                    gestor.buscarSucursal(jFrame);
                    break;
            }
        }
        jFrame.dispose();
    }
}