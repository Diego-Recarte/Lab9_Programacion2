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
    private final ListaEnlazada<Paquete> paquetes;

    public Repartidor(String id, String nombre, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = EstadoRepartidor.DISPONIBLE;
        this.ruta = null;
        this.paquetesCargados = 0;
        this.paquetesEntregados = 0;
        this.paquetes = new ListaEnlazada<>();
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

    public ListaEnlazada<Paquete> getPaquetes() {
        return paquetes;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public void setEstado(EstadoRepartidor estado) {
        this.estado = estado;
    }

    public boolean tieneEspacio() {
        return paquetesCargados < capacidad;
    }

    public boolean agregarPaquete(Paquete paquete) {
        if (!tieneEspacio()) {
            return false;
        }

        paquetes.agregar(paquete);
        paquetesCargados++;
        return true;
    }

    public Paquete sacarPrimerPaquete() {
        if (paquetes.estaVacia()) {
            return null;
        }

        Paquete paquete = paquetes.obtener(0);
        paquetes.eliminar(paquete);
        paquetesCargados--;
        return paquete;
    }

    public void registrarEntrega() {
        paquetesEntregados++;
    }

    public void limpiarCarga() {
        while (!paquetes.estaVacia()) {
            paquetes.eliminar(paquetes.obtener(0));
        }

        paquetesCargados = 0;
    }
}