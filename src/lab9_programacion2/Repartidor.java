/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */
public class Repartidor {
 
    private String id;
    private String nombre;
    private int capacidad;
    private Ruta ruta;
    private EstadoRepartidor estado;
    private int paquetesCargados;
    private int paquetesEntregados;

    public Repartidor(String id, String nombre, int capacidad ) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = EstadoRepartidor.DISPONIBLE;
        this.ruta = null;
        this.paquetesCargados = 0;
        this.paquetesEntregados = 0;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public EstadoRepartidor getEstado() {
        return estado;
    }

    public int getPaquetesCargados() {
        return paquetesCargados;
    }

    public int getPaquetesEntregados() {
        return paquetesEntregados;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public void setEstado(
            EstadoRepartidor estado
    ) {
        this.estado = estado;
    }

    public void cargarPaquete() {
        if (paquetesCargados < capacidad) {
            paquetesCargados++;
        }
    }

    public void registrarEntrega() {
        if (paquetesCargados > 0) {
            paquetesCargados--;
        }

        paquetesEntregados++;
    }

    public boolean tieneEspacio() {
        return paquetesCargados < capacidad;
    }
    
    public boolean asignarPaquete(Paquete paquete) {
        if (!tieneEspacio()) {
            return false;
        }

        cargarPaquete();
        return true;
    }


}
