import javax.swing.*;
import java.lang.ref.Cleaner;

public class Menu {
    public static void main(String[] args) {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor gestor = new Gestor();
        int caso = 1;
        while (caso != 0) {
            caso = gestor.showMenu(jFrame);
            switch (caso) {
                case 1:
                    gestor.addSucursales(jFrame);
                    break;
                case 2:
                    gestor.showSucursales(jFrame);
                    break;
            }
        }
        System.out.println("saliste felicitute");
        jFrame.dispose();
    }
}