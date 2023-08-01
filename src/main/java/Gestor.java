import javax.swing.*;
import java.util.ArrayList;
import java.util.Optional;
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
    public void buscarSucursal(JFrame jFrame){
        String nombre;

        nombre=swingestor.menuBusqueda(jFrame, sucursales.stream().map(s -> s.getNombre()).toList());
        Optional<Sucursal> sucursalEncontrada = sucursales.stream()
                .filter(s -> s.getNombre().equals(nombre))
                .findFirst();
        Sucursal sucursal = sucursalEncontrada.orElse(null);
        if (!nombre.equals("")) showSucursal(jFrame,sucursal);
    }
    public void showSucursal(JFrame jFrame,Sucursal s) {
            swingestor.showSucursal(jFrame,s);
    }

    public void showSucursales(JFrame jFrame) {
        for (Sucursal s: sucursales){
            swingestor.showSucursal(jFrame,s);
        }
    }
}
