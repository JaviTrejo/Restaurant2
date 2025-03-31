package restaurante2_controller;

import restaurante2_gestion_de_mesas_y_comandas.Comanda;

import java.util.Scanner;

public class Facturacion_controller {
    
    public static double ventasTotalesDelDia = 0;
    public static int cantidadVentasDelDia = 0;

    public static void facturarComanda(Comanda comanda) {
        System.out.println("\n=== FACTURACIÓN ===");
        System.out.println("Mesa #" + comanda.getNumeroMesa());
        comanda.mostrarComanda();  // Muestra productos y total

        double total = comanda.calcularTotal();

        Scanner sc = new Scanner(System.in);
        System.out.println("\nMétodos de pago disponibles:");
        System.out.println("1. Efectivo");
        System.out.println("2. Tarjeta de crédito");
        System.out.println("3. Transferencia");

        System.out.print("Seleccione el método de pago: ");
        int metodoPago = sc.nextInt();

        String metodo = switch (metodoPago) {
            case 1 -> "Efectivo";
            case 2 -> "Tarjeta de crédito";
            case 3 -> "Transferencia";
            default -> "Desconocido";
        };

        System.out.println("\n✅ Pago procesado exitosamente.");
        System.out.println("Método de pago: " + metodo);
        System.out.println("Total pagado: $" + total);

        // Marcar la mesa como libre nuevamente si quieres:
        comanda.getMesa().setComanda(null);
        
        ventasTotalesDelDia += comanda.calcularTotal();
        cantidadVentasDelDia++;
    }
}
