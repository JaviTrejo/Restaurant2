package restaurante2_controller;

import restaurante2_gestion_de_mesas_y_comandas.Mesa;
import restaurante2_gestion_de_mesas_y_comandas.Comanda;
import restaurante2_db.Database_mesas; 

import java.util.List;
import java.util.Scanner;

public class Mesa_controller {

    // Mostrar el listado de mesas con su estado actual
    public static void listarMesas() {
        List<Mesa> mesas = Database_mesas.getMesas();  // Cambié a Database_mesas

        if (mesas.isEmpty()) {
            System.out.println("⚠ No hay mesas registradas.");
            return;
        }

        System.out.println("\n=== LISTADO DE MESAS ===");
        for (Mesa mesa : mesas) {
            String estado = (mesa.getComanda() == null) ? "Libre" : (mesa.getComanda().isCerrada() ? "Cerrada" : "Ocupada");
            System.out.println("Mesa #" + mesa.getNumeroMesa() + " | Estado: " + estado);
        }
    }

    // Asignar una mesa a un cliente creando una comanda
    public static void asignarMesa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el número de la mesa a asignar al cliente: ");
        int numeroMesa = sc.nextInt();

        Mesa mesa = Database_mesas.getMesaPorNumero(numeroMesa);  // Cambié a Database_mesas

        if (mesa == null) {
            System.out.println("⚠ Mesa no encontrada.");
            return;
        }

        if (mesa.getComanda() != null && !mesa.getComanda().isCerrada()) {
            System.out.println("⚠ La mesa ya está ocupada con una comanda activa.");
            return;
        }

        int numeroComanda = generarNumeroComanda(); // Genera un número de comanda único
        Comanda nuevaComanda = new Comanda(numeroComanda, mesa);

        mesa.setComanda(nuevaComanda);

        System.out.println("✅ Mesa #" + numeroMesa + " ha sido asignada al cliente y tiene una comanda activa.");
    }
    
    // Liberar una mesa si la comanda ha sido cerrada y facturada
    public static void liberarMesa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese el número de la mesa a liberar: ");
        int numeroMesa = sc.nextInt();

        Mesa mesa = Database_mesas.getMesaPorNumero(numeroMesa);  // Cambié a Database_mesas

        if (mesa == null) {
            System.out.println("⚠ Mesa no encontrada.");
            return;
        }

        Comanda comanda = mesa.getComanda();

        if (comanda == null) {
            System.out.println("⚠ Esta mesa ya está libre.");
            return;
        }

        if (!comanda.isCerrada()) {
            System.out.println("⚠ La comanda sigue abierta. Cierra y factura antes de liberar la mesa.");
            return;
        }

        mesa.setComanda(null);
        System.out.println("✅ Mesa #" + numeroMesa + " liberada correctamente.");
    }
    
    // Generador de números de comandas únicos
    private static int contadorComandas = 1;
    
    private static int generarNumeroComanda(){
        return contadorComandas++;
    }
}
