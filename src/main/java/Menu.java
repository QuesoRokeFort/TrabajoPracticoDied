import java.lang.ref.Cleaner;

public class Menu {
    public static void main(String[] args) {

        Gestor gestor = new Gestor();
        int caso = 1;
        while (caso != 0) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            caso = gestor.showMenu();
            switch (caso) {
                case 1:
                    gestor.addSucursales();
                    break;
                case 2:
                    for (Object s: gestor.sucursales.stream().map(s -> s.getNombre()).toArray()){
                        System.out.println(s);
                    }
                    break;
            }
        }
        System.out.println("saliste felicitute");
    }
}