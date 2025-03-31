package restaurante2_gestion_de_mesas_y_comandas;

public class Mesa {

    private int numeroMesa;
    private boolean ocupada;
    private Comanda comanda; // la comanda actual de esta mesa

    public Mesa(int numeroMesa) {
        this.numeroMesa = numeroMesa;
        this.ocupada = false;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
        this.ocupada = (comanda != null);
    }
}
