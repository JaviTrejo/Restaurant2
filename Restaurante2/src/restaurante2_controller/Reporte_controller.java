package restaurante2_controller;

import restaurante2_gestion_de_mesas_y_comandas.Producto_inventario;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.File;


public class Reporte_controller {

    public static void generarReporteDiario(double ventasDelDia, int cantidadVentas, List<Producto_inventario> inventarioActual) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fecha = LocalDate.now().format(formatter);
        String carpeta = "reportes";  // Carpeta donde se guardará el reporte
        String nombreArchivo = carpeta + "/reporte_" + fecha + ".txt";

        // 📌 Crear la carpeta si no existe
        File directorio = new File(carpeta);
        if (!directorio.exists()) {
            directorio.mkdirs();  // Crea la carpeta
        }

        try {
            FileWriter writer = new FileWriter(nombreArchivo);

            writer.write("====== REPORTE DIARIO ======\n");
            writer.write("Fecha: " + fecha + "\n\n");

            writer.write("Ventas totales del día: $" + ventasDelDia + "\n");
            writer.write("Cantidad de ventas realizadas: " + cantidadVentas + "\n\n");

            writer.write("---- INVENTARIO ACTUAL ----\n");
            if (inventarioActual.isEmpty()) {
                writer.write("Inventario vacío.\n");
            } else {
                for (Producto_inventario producto : inventarioActual) {
                    writer.write("- " + producto.getNombre() + ": " + producto.getCantidad() + " " + producto.getUnidadMedida() + "\n");
                }
            }

            writer.write("\n===== Fin del reporte =====\n");

            writer.close();
            System.out.println(" Reporte diario guardado en: " + nombreArchivo);

        } catch (IOException e) {
            System.out.println(" Error al guardar el reporte: " + e.getMessage());
        }
    }
}
