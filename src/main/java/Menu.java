import javax.swing.*;
import java.lang.ref.Cleaner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Menu {
    public static void main(String[] args)  {
        JFrame jFrame= new JFrame("trabajo practico");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Gestor gestor = new Gestor();
        gestor.actualizarSucursales();
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
                case 5:
                    gestor.buscarSucursal(jFrame);
                    break;
            }
        }
        System.out.println("saliste felicitute");
        jFrame.dispose();
    }
}