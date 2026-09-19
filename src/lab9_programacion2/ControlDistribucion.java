/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */

public class ControlDistribucion {
    private final ListaEnlazada<Paquete> listaRecepcion;
    private final ListaEnlazada<Paquete> listaAlmacen;
    private final ListaEnlazada<Paquete> listaClasificacion;
    private final ListaEnlazada<Paquete> listaEmpaquetado;
    private final ListaEnlazada<Paquete> listaExpedicion;
    private final ListaEnlazada<Paquete> listaReparto;
    private final ListaEnlazada<Paquete> listaEntregados;
    private final ListaEnlazada<Paquete> listaDevueltos;
    private final ListaEnlazada<Repartidor> listaRepartidores;
    private final ListaEnlazada<Ruta> listaRutas;
    private int totalGenerados;
    private int totalEntregados;
    private int totalDevueltos;

    public ControlDistribucion() {
        listaRecepcion = new ListaEnlazada<>(10);
        listaAlmacen = new ListaEnlazada<>(20);
        listaClasificacion = new ListaEnlazada<>(10);
        listaEmpaquetado = new ListaEnlazada<>(8);
        listaExpedicion = new ListaEnlazada<>(15);
        listaReparto = new ListaEnlazada<>(30);
        listaEntregados = new ListaEnlazada<>();
        listaDevueltos = new ListaEnlazada<>();
        listaRepartidores = new ListaEnlazada<>();
        listaRutas = new ListaEnlazada<>();
        totalGenerados = 0;
        totalEntregados = 0;
        totalDevueltos = 0;
        crearRutasIniciales();
        crearRepartidoresIniciales();
    }

    private void crearRutasIniciales() {
        listaRutas.agregar(new Ruta("R01", "Tegucigalpa"));
        listaRutas.agregar(new Ruta("R02", "San Pedro Sula"));
        listaRutas.agregar(new Ruta("R03", "Tela"));
        listaRutas.agregar(new Ruta("R04", "Guatemala"));
    }

    private void crearRepartidoresIniciales() {
        listaRepartidores.agregar(new Repartidor("RPD-001", "Repartidor 1", 5));
        listaRepartidores.agregar(new Repartidor("RPD-002", "Repartidor 2", 4));
        listaRepartidores.agregar(new Repartidor("RPD-003", "Repartidor 3", 6));
        listaRepartidores.agregar(new Repartidor("RPD-004", "Repartidor 4", 5));
    }

    public Paquete crearPaquete(String codigo, String cliente, String direccion, String ciudad, double peso, PrioridadPaquete prioridad) {
        Paquete paquete = new Paquete(codigo, cliente, direccion, ciudad, peso, prioridad);
        listaRecepcion.agregar(paquete);
        totalGenerados++;
        return paquete;
    }

    public void almacenarPaquete(Paquete paquete) {
        paquete.setEstado(EstadoPaquete.ALMACENADO);

        try {
            listaAlmacen.agregarEsperando(paquete);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public boolean clasificarPaquete(Paquete paquete) {
        Ruta ruta = buscarRutaPorCiudad(paquete.getCiudad());

        if (ruta == null) {
            return false;
        }

        paquete.setEstado(EstadoPaquete.CLASIFICANDO);
        paquete.setRuta(ruta);
        paquete.setEstado(EstadoPaquete.CLASIFICADO);

        try {
            listaClasificacion.agregarEsperando(paquete);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    private Ruta buscarRutaPorCiudad(String ciudad) {
        for (int i = 0; i < listaRutas.tamanio(); i++) {
            Ruta ruta = listaRutas.obtener(i);

            if (ruta.getCiudad().equalsIgnoreCase(ciudad)) {
                return ruta;
            }
        }

        return null;
    }

    public void empaquetarPaquete(Paquete paquete) {
        paquete.setEstado(EstadoPaquete.EMPAQUETANDO);

        try {
            listaEmpaquetado.agregarEsperando(paquete);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void finalizarEmpaquetado(Paquete paquete) {
        paquete.setEstado(EstadoPaquete.EMPAQUETADO);
        paquete.setEstado(EstadoPaquete.EN_EXPEDICION);

        try {
            listaExpedicion.agregarEsperando(paquete);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public synchronized Repartidor asignarPaqueteARepartidor(Paquete paquete) {
        for (int i = 0; i < listaRepartidores.tamanio(); i++) {
            Repartidor repartidor = listaRepartidores.obtener(i);

            boolean disponible = repartidor.getEstado() == EstadoRepartidor.DISPONIBLE;
            boolean cargando = repartidor.getEstado() == EstadoRepartidor.CARGANDO;

            if (repartidor.tieneEspacio() && (disponible || cargando)) {
                if (repartidor.getRuta() == null) {
                    repartidor.setRuta(paquete.getRuta());
                }

                boolean agregado = repartidor.agregarPaquete(paquete);

                if (!agregado) {
                    continue;
                }

                paquete.setEstado(EstadoPaquete.EN_REPARTO);
                repartidor.setEstado(EstadoRepartidor.CARGANDO);

                try {
                    listaReparto.agregarEsperando(paquete);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }

                return repartidor;
            }
        }

        return null;
    }

    public void entregarPaquete(Paquete paquete) {
        paquete.setEstado(EstadoPaquete.ENTREGADO);
        listaReparto.eliminar(paquete);
        listaEntregados.agregar(paquete);
        totalEntregados++;
    }

    public void devolverPaquete(Paquete paquete) {
        paquete.aumentarIntentos();
        listaReparto.eliminar(paquete);

        if (paquete.puedeIntentarNuevamente()) {
            paquete.setEstado(EstadoPaquete.NUEVO_INTENTO);

            try {
                listaExpedicion.agregarEsperando(paquete);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            paquete.setEstado(EstadoPaquete.DEVUELTO);
            listaDevueltos.agregar(paquete);
            totalDevueltos++;
        }
    }

    public ListaEnlazada<Paquete> getListaRecepcion() {
        return listaRecepcion;
    }

    public ListaEnlazada<Paquete> getListaAlmacen() {
        return listaAlmacen;
    }

    public ListaEnlazada<Paquete> getListaClasificacion() {
        return listaClasificacion;
    }

    public ListaEnlazada<Paquete> getListaEmpaquetado() {
        return listaEmpaquetado;
    }

    public ListaEnlazada<Paquete> getListaExpedicion() {
        return listaExpedicion;
    }

    public ListaEnlazada<Paquete> getListaReparto() {
        return listaReparto;
    }

    public ListaEnlazada<Paquete> getListaEntregados() {
        return listaEntregados;
    }

    public ListaEnlazada<Paquete> getListaDevueltos() {
        return listaDevueltos;
    }

    public ListaEnlazada<Repartidor> getListaRepartidores() {
        return listaRepartidores;
    }

    public ListaEnlazada<Ruta> getListaRutas() {
        return listaRutas;
    }

    public int getTotalGenerados() {
        return totalGenerados;
    }

    public int getTotalEntregados() {
        return totalEntregados;
    }

    public int getTotalDevueltos() {
        return totalDevueltos;
    }

    public int getTotalEnProceso() {
        return totalGenerados - totalEntregados - totalDevueltos;
    }

    public int getCantidadRecepcion() {
        return listaRecepcion.tamanio();
    }

    public int getCantidadAlmacen() {
        return listaAlmacen.tamanio();
    }

    public int getCantidadClasificacion() {
        return listaClasificacion.tamanio();
    }

    public int getCantidadEmpaquetado() {
        return listaEmpaquetado.tamanio();
    }

    public int getCantidadExpedicion() {
        return listaExpedicion.tamanio();
    }

    public int getCantidadReparto() {
        return listaReparto.tamanio();
    }
}