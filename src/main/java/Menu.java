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
                    // fijate si podes hacer lo mismo q hice con el show sucursal en un menu de swing para cargar,
                    // al final termino siendo una pantalla de modicacion de datos y no de muestras pero anyways
                    // despues acomodamos bien los nombre de las funciones y eso de momento hagamos el esqueleto
                    gestor.addSucursales();

                    break;
                case 2:
                    gestor.showSucursales();
                    break;
            }
        }
        System.out.println("saliste felicitute");
        return;
    }
}