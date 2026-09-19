/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */
public class Paquete {

    private String codigo;
    private String cliente;
    private String direccion;
    private String ciudad;
    private double peso;
    private PrioridadPaquete prioridad;
    private EstadoPaquete estado;
    private Ruta ruta;
    private int intentos;

    public Paquete(String codigo, String cliente,String direccion,String ciudad, double peso, PrioridadPaquete prioridad) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.peso = peso;
        this.prioridad = prioridad;
        this.estado = EstadoPaquete.RECIBIDO;
        this.ruta = null;
        this.intentos = 0;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public double getPeso() {
        return peso;
    }

    public PrioridadPaquete getPrioridad() {
        return prioridad;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setPrioridad(PrioridadPaquete prioridad) {
        this.prioridad = prioridad;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public void aumentarIntentos() {
        this.intentos++;
    }

    public boolean puedeIntentarNuevamente() {
        return intentos < 3;
    }

    @Override
    public String toString() {
        return "Paquete{" + "codigo='" + codigo + '\'' + ", cliente='" + cliente + '\'' + ", direccion='" + direccion + '\''  + ", ciudad='" + ciudad + '\'' + ", peso=" + peso + ", prioridad=" + prioridad + ", estado=" + estado  + ", ruta=" + ruta  + ", intentos=" + intentos + '}';
    }
}