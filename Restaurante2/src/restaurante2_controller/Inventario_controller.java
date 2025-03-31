/* Este archivo largo pero porque tiene muchos metodos, el archivo esta perfecto
   y ya no se puede separar por modulos */

package restaurante2_controller;

import restaurante2_gestion_de_mesas_y_comandas.Producto_inventario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;


public class Inventario_controller {

    public static void cargarInventarioDesdeArchivo() {
    File archivo = new File(ARCHIVO_INVENTARIO);

    if (!archivo.exists()) {
        System.out.println("⚠ No existe el archivo de inventario. Se creará uno nuevo al guardar.");
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] partes = linea.split(",");
            if (partes.length == 3) {
                String nombre = partes[0];
                int cantidad = Integer.parseInt(partes[1]);
                String unidadMedida = partes[2];

                Producto_inventario producto = new Producto_inventario(nombre, cantidad, unidadMedida);
                inventario.add(producto);
            }
        }
        System.out.println("✅ Inventario cargado desde " + ARCHIVO_INVENTARIO);
    } catch (IOException e) {
        System.out.println("❌ Error al cargar el inventario: " + e.getMessage());
    }
}
    
    private static List<Producto_inventario> inventario = new ArrayList<>();

    // Agregar producto nuevo o aumentar cantidad
    public static void agregarProductoAlInventario() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Entrada de Producto al Inventario ===");
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();

        System.out.print("Cantidad recibida: ");
        int cantidad = sc.nextInt();
        sc.nextLine();  // Limpiar buffer

        System.out.print("Unidad de medida (kg, lts, pza, etc): ");
        String unidadMedida = sc.nextLine();

        Producto_inventario productoExistente = buscarProducto(nombre);

        if (productoExistente != null) {
            productoExistente.agregarCantidad(cantidad);
            System.out.println("✅ Se agregó " + cantidad + " " + unidadMedida + " a " + nombre);
        } else {
            Producto_inventario nuevoProducto = new Producto_inventario(nombre, cantidad, unidadMedida);
            inventario.add(nuevoProducto);
            System.out.println("✅ Producto nuevo agregado al inventario.");
        }
        
        // Al final del método
        guardarInventarioEnArchivo();

    }

    // Mostrar el inventario actual
    public static void mostrarInventario() {
        System.out.println("\n=== INVENTARIO ACTUAL ===");

        if (inventario.isEmpty()) {
            System.out.println("⚠ No hay productos en el inventario.");
        } else {
            for (Producto_inventario producto : inventario) {
                System.out.println(producto);
            }
        }
    }

    // Reporte de inventario diario
    public static void generarReporteDiario() {
        System.out.println("\n=== GENERANDO REPORTE DE INVENTARIO DIARIO ===");

        if (inventario.isEmpty()) {
            System.out.println("⚠ No hay productos en el inventario para reportar.");
            return;
        }

        // Obtener la fecha actual para el nombre del archivo
        LocalDate hoy = LocalDate.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaActual = hoy.format(formato);

        String nombreArchivo = "reporte_inventario_" + fechaActual + ".txt";

        try (FileWriter fw = new FileWriter(nombreArchivo)) {

            fw.write("=== REPORTE DE INVENTARIO DIARIO ===\n");
            fw.write("Fecha: " + fechaActual + "\n\n");

            for (Producto_inventario producto : inventario) {
                fw.write(producto.toString() + "\n");
            }

            System.out.println("✅ Reporte guardado como: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println("❌ Error al generar el reporte diario: " + e.getMessage());
        }
}


    // Buscar un producto en el inventario por nombre
    private static Producto_inventario buscarProducto(String nombre) {
        for (Producto_inventario producto : inventario) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {
                return producto;
            }
        }
        return null;
    }

    // Si necesitas hacer un inventario diario automático al cierre
    public static void inventarioDiario() {
        generarReporteDiario();
        // Aquí podrías limpiar o reiniciar conteos diarios si fuera necesario
    }
    
    private static void guardarInventarioEnArchivo() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_INVENTARIO))) {
        for (Producto_inventario producto : inventario) {
            String linea = producto.getNombre() + "," + producto.getCantidad() + "," + producto.getUnidadMedida();
            writer.write(linea);
            writer.newLine();
        }
        System.out.println("✅ Inventario guardado en " + ARCHIVO_INVENTARIO);
    } catch (IOException e) {
        System.out.println("❌ Error al guardar el inventario: " + e.getMessage());
    }
}
    
    private static final String ARCHIVO_INVENTARIO = "inventario.txt";
    
    public static List<Producto_inventario> getInventario() {
        return inventario;
    }


}
