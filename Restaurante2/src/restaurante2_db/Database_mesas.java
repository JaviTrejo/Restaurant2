package restaurante2_db;

import restaurante2_gestion_de_mesas_y_comandas.Mesa;
import java.util.ArrayList;
import java.util.List;

public class Database_mesas {
    
    // Lista estática que almacena las mesas
    private static List<Mesa> mesas = new ArrayList<>();
    
    // Método para obtener la lista de todas las mesas
    public static List<Mesa> getMesas() {
        return mesas;
    }
    
    // Método para obtener una mesa por su número
    public static Mesa getMesaPorNumero(int numeroMesa) {
        for (Mesa mesa : mesas) {
            if (mesa.getNumeroMesa() == numeroMesa) {
                return mesa;
            }
        }
        return null; // Si no se encuentra la mesa
    }
    
    // Método para agregar una nueva mesa
    public static void agregarMesa(Mesa mesa) {
        mesas.add(mesa);
    }
    
    // Método para eliminar una mesa
    public static void eliminarMesa(int numeroMesa) {
        mesas.removeIf(mesa -> mesa.getNumeroMesa() == numeroMesa);
    }
}