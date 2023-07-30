public class Ruta {
    Sucursal origen,destino;
    int capacidad;
    int horasDeViaje;
    Boolean estado;

    public Ruta() {
    }

    public Ruta(Sucursal origen, Sucursal destino, int capacidad, int horasDeViaje, Boolean estado) {
        this.origen = origen;
        this.destino = destino;
        this.capacidad = capacidad;
        this.horasDeViaje = horasDeViaje;
        this.estado = estado;
    }

    public Sucursal getOrigen() {
        return origen;
    }

    public void setOrigen(Sucursal origen) {
        this.origen = origen;
    }

    public Sucursal getDestino() {
        return destino;
    }

    public void setDestino(Sucursal destino) {
        this.destino = destino;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getHorasDeViaje() {
        return horasDeViaje;
    }

    public void setHorasDeViaje(int horasDeViaje) {
        this.horasDeViaje = horasDeViaje;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
}
