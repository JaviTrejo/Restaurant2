package restaurante2_db;

import restaurante2_gestion_de_mesas_y_comandas.Producto;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database_comida {
    // Lista estática que almacena los productos del menú
    private static List<Producto> menu = new ArrayList<>();
    
    // Ruta del archivo donde se guardarán los productos
    private static final String FILE_PATH = "productos.txt";

    // Bloque estático que carga los productos desde el archivo al iniciar la clase
    static {
        cargarProductosDesdeArchivo();
    }

    // Método que devuelve la lista completa de productos
    public static List<Producto> getMenu() {
        return menu;
    }

    // Método para agregar un nuevo producto a la lista y guardarlo en el archivo
    public static void agregarProducto(int id, String nombre, double precio) {
        Producto producto = new Producto(id, nombre, precio);
        menu.add(producto);
        guardarProductosEnArchivo(); // Se actualiza el archivo
    }

    // Método para eliminar un producto por su ID y actualizar el archivo
    public static void eliminarProducto(int id) {
        menu.removeIf(producto -> producto.getId() == id);
        guardarProductosEnArchivo(); // Se actualiza el archivo
    }

    // Método para obtener un producto por su ID
    public static Producto getProductoPorId(int id) {
        for (Producto p : menu) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // Retorna null si el producto no se encuentra
    }

    // Método para guardar los productos en el archivo "productos.txt"
    private static void guardarProductosEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Producto p : menu) {
                writer.write(p.getId() + "," + p.getNombre() + "," + p.getPrecio());
                writer.newLine(); // Nueva línea para cada producto
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores en caso de problemas con el archivo
        }
    }

    // Método para cargar los productos desde "productos.txt" al iniciar el programa
    private static void cargarProductosDesdeArchivo() {
        menu.clear(); // Se limpia la lista antes de cargar nuevos productos
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Separa los valores por comas
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]); // Convierte el ID a entero
                    String nombre = parts[1]; // Obtiene el nombre del producto
                    double precio = Double.parseDouble(parts[2]); // Convierte el precio a double
                    menu.add(new Producto(id, nombre, precio)); // Agrega el producto a la lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de errores si el archivo no existe o tiene problemas
        }
    }
}
