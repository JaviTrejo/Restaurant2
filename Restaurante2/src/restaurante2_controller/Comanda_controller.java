package restaurante2_controller;

import restaurante2_gestion_de_mesas_y_comandas.Comanda;
import restaurante2_gestion_de_mesas_y_comandas.Mesa;
import restaurante2_gestion_de_mesas_y_comandas.Producto;

import java.io.*;
import java.util.*;

public class Comanda_controller {
    private static List<Comanda> comandas = new ArrayList<>();
    private static final String ARCHIVO_COMANDAS = "comandas.txt";

    // Cargar comandas desde el archivo al iniciar el programa
    public static void cargarComandasDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_COMANDAS))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                // Formato esperado: "Comanda #1 de Mesa 2 | Productos: 3 | Cerrada: false"
                String[] partes = linea.split("\\|");
                int numeroComanda = Integer.parseInt(partes[1].replace("#", ""));
                int numeroMesa = Integer.parseInt(partes[4]);
                boolean cerrada = Boolean.parseBoolean(partes[9]);

                Mesa mesa = new Mesa(numeroMesa);
                Comanda comanda = new Comanda(numeroComanda, mesa);
                if (cerrada) {
                    comanda.cerrarComanda();
                }
                comandas.add(comanda);
            }
        } catch (IOException e) {
            System.out.println("⚠ No se encontraron comandas previas.");
        }
    }

    // Guardar todas las comandas en el archivo
    private static void guardarComandasEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_COMANDAS))) {
            for (Comanda comanda : comandas) {
                writer.write(comanda.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("⚠ Error al guardar las comandas.");
        }
    }

    // Crear una nueva comanda y asignarla a una mesa
    public static void crearComanda(int numeroMesa) {
        Mesa mesa = new Mesa(numeroMesa);

        // Verificar si ya hay una comanda en esa mesa
        for (Comanda c : comandas) {
            if (c.getNumeroMesa() == numeroMesa && !c.isCerrada()) {
                System.out.println("⚠ Ya hay una comanda activa en la mesa #" + numeroMesa);
                return;
            }
        }

        int numeroComanda = comandas.size() + 1; // Genera un número único
        Comanda nuevaComanda = new Comanda(numeroComanda, mesa);
        comandas.add(nuevaComanda);
        guardarComandasEnArchivo();
        System.out.println("✅ Comanda creada para la Mesa #" + numeroMesa);
    }

    // Agregar un producto a una comanda
    public static void agregarProductoAComanda(int numeroComanda, String nombreProducto, double precio) {
        for (Comanda comanda : comandas) {
            if (comanda.getNumeroComanda() == numeroComanda && !comanda.isCerrada()) {
                Producto producto = new Producto(0, nombreProducto, precio);
                comanda.agregarProducto(producto);
                guardarComandasEnArchivo();
                System.out.println("✅ Producto agregado a la Comanda #" + numeroComanda);
                return;
            }
        }
        System.out.println("⚠ No se encontró la comanda o está cerrada.");
    }

    // Eliminar un producto de una comanda
    public static void eliminarProductoDeComanda(int numeroComanda, String nombreProducto) {
        for (Comanda comanda : comandas) {
            if (comanda.getNumeroComanda() == numeroComanda && !comanda.isCerrada()) {
                comanda.eliminarProducto(nombreProducto);
                guardarComandasEnArchivo();
                System.out.println("✅ Producto eliminado de la Comanda #" + numeroComanda);
                return;
            }
        }
        System.out.println("⚠ No se encontró la comanda o está cerrada.");
    }

    // Editar un producto dentro de una comanda
    public static void editarProductoEnComanda(int numeroComanda, String nombreProducto, String nuevoNombre, double nuevoPrecio) {
        for (Comanda comanda : comandas) {
            if (comanda.getNumeroComanda() == numeroComanda && !comanda.isCerrada()) {
                comanda.editarProducto(nombreProducto, nuevoNombre, nuevoPrecio);
                guardarComandasEnArchivo();
                System.out.println("✅ Producto editado en la Comanda #" + numeroComanda);
                return;
            }
        }
        System.out.println("⚠ No se encontró la comanda o está cerrada.");
    }

    // Cerrar una comanda
    public static void cerrarComanda(int numeroComanda) {
        for (Comanda comanda : comandas) {
            if (comanda.getNumeroComanda() == numeroComanda && !comanda.isCerrada()) {
                comanda.cerrarComanda();
                guardarComandasEnArchivo();
                System.out.println("✅ Comanda #" + numeroComanda + " cerrada.");
                return;
            }
        }
        System.out.println("⚠ No se encontró la comanda o ya está cerrada.");
    }

    // Listar todas las comandas
    public static void listarComandas() {
        if (comandas.isEmpty()) {
            System.out.println("⚠ No hay comandas registradas.");
            return;
        }

        System.out.println("\n=== LISTADO DE COMANDAS ===");
        for (Comanda comanda : comandas) {
            System.out.println(comanda);
        }
    }

    // Método principal para probar las funciones
    public static void mostrarMenuComandas {
        Scanner sc = new Scanner(System.in);
        cargarComandasDesdeArchivo();

        while (true) {
            System.out.println("\n=== MENÚ COMANDAS ===");
            System.out.println("1. Crear Comanda");
            System.out.println("2. Agregar Producto");
            System.out.println("3. Eliminar Producto");
            System.out.println("4. Editar Producto");
            System.out.println("5. Cerrar Comanda");
            System.out.println("6. Listar Comandas");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el número de la mesa: ");
                    int numMesa = sc.nextInt();
                    crearComanda(numMesa);
                    break;
                case 2:
                    System.out.print("Ingrese el número de comanda: ");
                    int numComanda = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Ingrese el nombre del producto: ");
                    String nombre = sc.nextLine();
                    System.out.print("Ingrese el precio del producto: ");
                    double precio = sc.nextDouble();
                    agregarProductoAComanda(numComanda, nombre, precio);
                    break;
                case 3:
                    System.out.print("Ingrese el número de comanda: ");
                    numComanda = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Ingrese el nombre del producto a eliminar: ");
                    nombre = sc.nextLine();
                    eliminarProductoDeComanda(numComanda, nombre);
                    break;
                case 4:
                    System.out.print("Ingrese el número de comanda: ");
                    numComanda = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Nombre del producto a editar: ");
                    nombre = sc.nextLine();
                    System.out.print("Nuevo nombre del producto: ");
                    String nuevoNombre = sc.nextLine();
                    System.out.print("Nuevo precio: ");
                    double nuevoPrecio = sc.nextDouble();
                    editarProductoEnComanda(numComanda, nombre, nuevoNombre, nuevoPrecio);
                    break;
                case 5:
                    System.out.print("Ingrese el número de comanda a cerrar: ");
                    numComanda = sc.nextInt();
                    cerrarComanda(numComanda);
                    break;
                case 6:
                    listarComandas();
                    break;
                case 7:
                    System.out.println("Saliendo...");
                    return;
                default:
                    System.out.println("⚠ Opción inválida.");
            }
        }
    }
}
