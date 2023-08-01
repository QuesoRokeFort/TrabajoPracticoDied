import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestor {
    ArrayList<Sucursal> sucursales= new ArrayList<Sucursal>();
    Swingestor swingestor =new Swingestor();
    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }

    public void addSucursales(JFrame jFrame) {

        Sucursal s=swingestor.addSucursal(jFrame);
        if (!s.getNombre().equals("")){sucursales.add(s);};
    }

    public Gestor() {
    }

    public int showMenu(JFrame jFrame) {
        return swingestor.swingMenu(jFrame);
    }

    public void showSucursales(JFrame jFrame) {
        for (Sucursal s: sucursales){
            swingestor.showSucursal(jFrame,s);
        }
    }
}
