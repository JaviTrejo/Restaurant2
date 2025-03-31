/* Este archivo es largo pero ya no se puede separar por modulos */
package restaurante2_controller;

import restaurante2.Usuario;
import restaurante2_gestion_de_mesas_y_comandas.Mesa;
import java.util.List;
import java.util.Scanner;

public class Menu {

    public static void mostrarMenu(Usuario usuario, List<Mesa> mesas) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ: " + usuario.getRol().toUpperCase() + " ===");
            switch (usuario.getRol()) {
                case "Administrador":
                    salir = menuAdministrador(sc);
                    break;
                case "Gerente":
                    salir = menuGerente(sc);
                    break;
                case "Capitan":
                    salir = menuCapitan(sc);
                    break;
                case "Mesero":
                    salir = menuMesero(sc, mesas);
                    break;
                default:
                    System.out.println("⚠ Rol desconocido.");
                    salir = true;
            }
        }
    }

    private static boolean menuAdministrador(Scanner sc) {
        System.out.println("1. Ingresar P.L.U");
        System.out.println("2. Registrar suministros en inventario");
        System.out.println("3. Ver inventario actual");
        System.out.println("4. Generar reporte de inventario diario");
        System.out.println("5. Reporte diario");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        switch (opcion) {
            case 1 ->
                System.out.println("Módulo P.L.U en construcción...");
            case 2 ->
                Inventario_controller.agregarProductoAlInventario();
            case 3 ->
                Inventario_controller.mostrarInventario();
            case 4 ->
                Inventario_controller.generarReporteDiario();
            case 5 ->
                Reporte_controller.generarReporteDiario(
                        Facturacion_controller.ventasTotalesDelDia,
                        Facturacion_controller.cantidadVentasDelDia,
                        Inventario_controller.getInventario()
                );
            case 6 -> {
                System.out.println("Cerrando sesión de Administrador...");
                return true;
            }
            default ->
                System.out.println("Opción no válida.");
        }
        return false;
    }

    private static boolean menuGerente(Scanner sc) {
        System.out.println("1. Hacer descuento");
        System.out.println("2. Imprimir cuentas");
        System.out.println("3. Ver inventario actual");
        System.out.println("4. Generar reporte de inventario diario");
        System.out.println("5. Reporte diario");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();

        switch (opcion) {
            case 1 ->
                System.out.println("Módulo de descuentos en construcción...");
            case 2 ->
                System.out.println("Módulo de impresión en construcción...");
            case 3 ->
                Inventario_controller.mostrarInventario();
            case 4 ->
                Inventario_controller.generarReporteDiario();
            case 5 ->
                Reporte_controller.generarReporteDiario(
                        Facturacion_controller.ventasTotalesDelDia,
                        Facturacion_controller.cantidadVentasDelDia,
                        Inventario_controller.getInventario()
                );
            case 6 -> {
                System.out.println("Cerrando sesión de Gerente...");
                return true;
            }
            default ->
                System.out.println("⚠ Opción no válida.");
        }
        return false;
    }

    private static boolean menuCapitan(Scanner sc) {
        int opcion;

        System.out.println("\n=== MENÚ DEL CAPITÁN ===");
        System.out.println("Bienvenido");
        System.out.println("1. Ver estado de las mesas");
        System.out.println("2. Asignar mesa a un cliente");
        System.out.println("3. Gestionar comandas");
        System.out.println("4. Liberar una mesa");
        System.out.println("5. Cerrar sesión");

        System.out.print("Seleccione una opción: ");
        opcion = sc.nextInt();

        switch (opcion) {
            case 1:
                Mesa_controller.listarMesas(); // Ver el estado de todas las mesas
                break;
            case 2:
                Mesa_controller.asignarMesa(); // Asignar una mesa a un cliente
                break;
            case 3:
                Comanda_controller.mostrarMenuComandas();// Gestionar comandas de las mesas activas
                break;
            case 4:
                Mesa_controller.liberarMesa(); // Liberar una mesa después de cerrar comanda
                break;
            case 5:
                System.out.println("Cerrando sesión del Capitán...");
                return true;
            default:
                System.out.println("Opción no válida.");
        }
        return false;
    }


    private static boolean menuMesero(Scanner sc, List<Mesa> mesas) {
        System.out.println("1. Ingresar productos a comandas");
        System.out.println("2. Salir");
        System.out.print("Seleccione una opción: ");
        int opcion = sc.nextInt();
        sc.nextLine(); // Limpiar buffer

        switch (opcion) {
            case 1 ->
                Comanda_controller.mostrarMenuComandas();
            case 2 -> {
                System.out.println("🔒 Cerrando sesión de Mesero...");
                return true;
            }
            default ->
                System.out.println("⚠ Opción no válida.");
        }
        return false;
    }
}
