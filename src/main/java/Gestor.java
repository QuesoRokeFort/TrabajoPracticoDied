import java.util.ArrayList;
import java.util.Scanner;

public class Gestor {
    ArrayList<Sucursal> sucursales= new ArrayList<Sucursal>();
    Swingestor swingestor =new Swingestor();
    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }

    public void addSucursales() {
        Scanner sc= new Scanner(System.in);
        Sucursal s= new Sucursal();
        System.out.println("introduzca el nombre:");
        String nombre = sc.next();
        s.setNombre(nombre);
        System.out.println("introduzca id:");
        int id = sc.nextInt();
        s.setId(id);
        System.out.println("introduzca estado(false cerrado , true abierto):");
        Boolean estado=sc.nextBoolean();
        s.setEstado(estado);
        System.out.println("introduzca hora de apertura:(4 digitos sin ':' , ej: 1400)");
        int hora=sc.nextInt();
        s.setHoraApertura(hora);
        System.out.println("introduzca hora de cierre:(4 digitos sin ':' , ej: 2200)");
        hora=sc.nextInt();
        s.setHoraCierre(hora);
        this.sucursales.add(s);
    }

    public Gestor() {
    }

    public int showMenu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1-añadir sucursal." +'\n'+
                "2- Mostrar sucursal." +'\n'+
                "3- Editar Sucursal."+'\n'+
                "4- Borrar sucursal."+'\n'+
                "5- Busacr sucursal."+ '\n'+
                "0- Salir.");
        return (int)sc.nextInt();
    }

    public void showSucursales() {
        for (Sucursal s: sucursales){
            swingestor.showSucursal(s);
        }
    }
}
