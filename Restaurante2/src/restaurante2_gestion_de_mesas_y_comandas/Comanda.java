package restaurante2_gestion_de_mesas_y_comandas;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Comanda {
    private int numeroMesa;
    private int numeroComanda;
    private List<Producto> productos;
    private boolean cerrada;
    private Mesa mesa;

    // Constructor
    public Comanda(int numeroComanda, Mesa mesa) {
        this.numeroMesa = mesa.getNumeroMesa();
        this.numeroComanda = numeroComanda;
        this.mesa = mesa;
        this.productos = new ArrayList<>();
        this.cerrada = false;
    }

    // Getter y Setter
    public int getNumeroMesa() {
        return numeroMesa;
    }

    public int getNumeroComanda() {
        return numeroComanda;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public boolean isCerrada() {
        return cerrada;
    }

    public Mesa getMesa() {
        return mesa;
    }

    // Agregar producto a la comanda
    public void agregarProducto(Producto producto) {
        if (!cerrada) {
            productos.add(producto);
            guardarComandaEnArchivo();
        } else {
            System.out.println("¡La comanda está cerrada!");
        }
    }

    // Eliminar un producto de la comanda
    public void eliminarProducto(String nombreProducto) {
        if (!cerrada) {
            productos.removeIf(producto -> producto.getNombre().equalsIgnoreCase(nombreProducto));
            guardarComandaEnArchivo();
        } else {
            System.out.println("¡La comanda está cerrada!");
        }
    }

    // Editar un producto de la comanda
    public void editarProducto(String nombreProducto, String nuevoNombre, double nuevoPrecio) {
        if (!cerrada) {
            for (Producto producto : productos) {
                if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
                    producto.setNombre(nuevoNombre);
                    producto.setPrecio(nuevoPrecio);
                    guardarComandaEnArchivo();
                    return;
                }
            }
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println("¡La comanda está cerrada!");
        }
    }

    // Mostrar la comanda en pantalla
    public void mostrarComanda() {
        System.out.println("Comanda #" + numeroComanda + " | Mesa: " + numeroMesa);

        if (productos.isEmpty()) {
            System.out.println("Sin productos.");
        } else {
            for (Producto p : productos) {
                System.out.println("- " + p.getNombre() + " | Precio: $" + p.getPrecio());
            }
        }

        System.out.println("Total: $" + calcularTotal());
    }

    // Calcular el total de la comanda
    public double calcularTotal() {
        double total = 0;
        for (Producto p : productos) {
            total += p.getPrecio();
        }
        return total;
    }

    // Cerrar la comanda
    public void cerrarComanda() {
        cerrada = true;
        guardarComandaEnArchivo();
    }

    // Guardar la comanda en un archivo de texto
    private void guardarComandaEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("comandas.txt", true))) {
            writer.write(this.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Error al guardar la comanda en el archivo.");
        }
    }

    @Override
    public String toString() {
        return "Comanda #" + numeroComanda + " de Mesa " + numeroMesa +
               " | Productos: " + productos.size() +
               " | Cerrada: " + cerrada;
    }
}
