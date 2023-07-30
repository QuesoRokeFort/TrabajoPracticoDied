import java.util.ArrayList;
import java.util.Scanner;

public class Gestor {
    ArrayList<Sucursal> sucursales= new ArrayList<Sucursal>();

    public ArrayList<Sucursal> getSucursales() {
        return sucursales;
    }

    public void addSucursales() {
        Scanner sc= new Scanner(System.in);
        Sucursal s= new Sucursal();
        System.out.println("introduzca id:");
        int id = sc.nextInt();
        s.setId(id);
        System.out.println("introduzca estado(0 cerrado , 1 abierto):");
        Boolean estado=sc.nextBoolean();
        s.setEstado(estado);
        System.out.println("introduzca hora de apertura:(4 digitos sin ':' , ej: 1400)");
        int hora=sc.nextInt();
        s.setHoraApertura(hora);
        System.out.println("introduzca hora de cierre:(4 digitos sin ':' , ej: 2200)");
        hora=sc.nextInt();
        s.setHoraCierre(hora);
        System.out.println("introduzca el nombre:");
        String nombre = sc.nextLine();
        s.setNombre(nombre);
        this.sucursales.add(s);
    }

    public Gestor() {
    }

}
