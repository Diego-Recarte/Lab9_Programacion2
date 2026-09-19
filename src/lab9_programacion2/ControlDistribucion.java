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
        listaRecepcion = new ListaEnlazada<>();
        listaAlmacen = new ListaEnlazada<>();
        listaClasificacion = new ListaEnlazada<>();
        listaEmpaquetado = new ListaEnlazada<>();
        listaExpedicion = new ListaEnlazada<>();
        listaReparto = new ListaEnlazada<>();
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
        listaRutas.agregar(
                new Ruta("R01", "Tegucigalpa")
        );

        listaRutas.agregar(
                new Ruta("R02", "San Pedro")
        );

        listaRutas.agregar(
                new Ruta("R03", "Tela")
        );

        listaRutas.agregar(
                new Ruta("R04", "Guatemala")
        );
    }

    private void crearRepartidoresIniciales() {
        listaRepartidores.agregar(
                new Repartidor(
                        "RPD-001",
                        "Repartidor 1",
                        5
                )
        );

        listaRepartidores.agregar(
                new Repartidor(
                        "RPD-002",
                        "Repartidor 2",
                        4
                )
        );

        listaRepartidores.agregar(
                new Repartidor(
                        "RPD-003",
                        "Repartidor 3",
                        6
                )
        );

        listaRepartidores.agregar(
                new Repartidor(
                        "RPD-004",
                        "Repartidor 4",
                        5
                )
        );
    }

    public Paquete crearPaquete(
            String codigo,
            String cliente,
            String direccion,
            String ciudad,
            double peso,
            PrioridadPaquete prioridad
    ) {
        Paquete paquete = new Paquete(
                codigo,
                cliente,
                direccion,
                ciudad,
                peso,
                prioridad
        );

        listaRecepcion.agregar(paquete);
        totalGenerados++;

        return paquete;
    }

    public void almacenarPaquete(
            Paquete paquete
    ) {
        listaRecepcion.eliminar(paquete);

        paquete.setEstado(
                EstadoPaquete.ALMACENADO
        );

        listaAlmacen.agregar(paquete);
    }

    public boolean clasificarPaquete(
            Paquete paquete
    ) {
        if (!listaAlmacen.buscar(paquete)) {
            return false;
        }

        Ruta ruta = buscarRutaPorCiudad(
                paquete.getCiudad()
        );

        if (ruta == null) {
            return false;
        }

        listaAlmacen.eliminar(paquete);

        paquete.setEstado(
                EstadoPaquete.CLASIFICANDO
        );

        paquete.setRuta(ruta);

        paquete.setEstado(
                EstadoPaquete.CLASIFICADO
        );

        listaClasificacion.agregar(paquete);

        return true;
    }

    private Ruta buscarRutaPorCiudad(
            String ciudad
    ) {
        for (int i = 0;
                i < listaRutas.tamanio();
                i++) {

            Ruta ruta = listaRutas.obtener(i);

            if (ruta.getCiudad()
                    .equalsIgnoreCase(ciudad)) {

                return ruta;
            }
        }

        return null;
    }

    public void empaquetarPaquete(
            Paquete paquete
    ) {
        listaClasificacion.eliminar(paquete);

        paquete.setEstado(
                EstadoPaquete.EMPAQUETANDO
        );

        listaEmpaquetado.agregar(paquete);
    }

    public void finalizarEmpaquetado(
            Paquete paquete
    ) {
        listaEmpaquetado.eliminar(paquete);

        paquete.setEstado(
                EstadoPaquete.EMPAQUETADO
        );

        listaExpedicion.agregar(paquete);

        paquete.setEstado(
                EstadoPaquete.EN_EXPEDICION
        );
    }

    public void enviarAExpedicion(
            Paquete paquete
    ) {
        listaEmpaquetado.eliminar(paquete);

        paquete.setEstado(
                EstadoPaquete.EN_EXPEDICION
        );

        if (!listaExpedicion.buscar(paquete)) {
            listaExpedicion.agregar(paquete);
        }
    }

    public void asignarAReparto(
            Paquete paquete
    ) {
        listaExpedicion.eliminar(paquete);

        paquete.setEstado(
                EstadoPaquete.EN_REPARTO
        );

        listaReparto.agregar(paquete);
    }

    public void entregarPaquete(
            Paquete paquete
    ) {
        listaReparto.eliminar(paquete);

        paquete.setEstado(
                EstadoPaquete.ENTREGADO
        );

        listaEntregados.agregar(paquete);
        totalEntregados++;
    }

    public void devolverPaquete(
            Paquete paquete
    ) {
        listaReparto.eliminar(paquete);

        paquete.aumentarIntentos();

        if (paquete.puedeIntentarNuevamente()) {
            paquete.setEstado(
                    EstadoPaquete.NUEVO_INTENTO
            );

            listaExpedicion.agregar(paquete);
        } else {
            paquete.setEstado(
                    EstadoPaquete.DEVUELTO
            );

            listaDevueltos.agregar(paquete);
            totalDevueltos++;
        }
    }

    public Paquete obtenerPaqueteRecepcion(
            int posicion
    ) {
        return listaRecepcion.obtener(posicion);
    }

    public Paquete obtenerPaqueteAlmacen(
            int posicion
    ) {
        return listaAlmacen.obtener(posicion);
    }

    public Paquete obtenerPaqueteClasificacion(
            int posicion
    ) {
        return listaClasificacion.obtener(posicion);
    }

    public Paquete obtenerPaqueteEmpaquetado(
            int posicion
    ) {
        return listaEmpaquetado.obtener(posicion);
    }

    public Paquete obtenerPaqueteExpedicion(
            int posicion
    ) {
        return listaExpedicion.obtener(posicion);
    }

    public Paquete obtenerPaqueteReparto(
            int posicion
    ) {
        return listaReparto.obtener(posicion);
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
        return totalGenerados
                - totalEntregados
                - totalDevueltos;
    }

    public ListaEnlazada<Paquete>
            getListaRecepcion() {
        return listaRecepcion;
    }

    public ListaEnlazada<Paquete>
            getListaAlmacen() {
        return listaAlmacen;
    }

    public ListaEnlazada<Paquete>
            getListaClasificacion() {
        return listaClasificacion;
    }

    public ListaEnlazada<Paquete>
            getListaEmpaquetado() {
        return listaEmpaquetado;
    }

    public ListaEnlazada<Paquete>
            getListaExpedicion() {
        return listaExpedicion;
    }

    public ListaEnlazada<Paquete>
            getListaReparto() {
        return listaReparto;
    }

    public ListaEnlazada<Paquete>
            getListaEntregados() {
        return listaEntregados;
    }

    public ListaEnlazada<Paquete>
            getListaDevueltos() {
        return listaDevueltos;
    }

    public ListaEnlazada<Repartidor>
            getListaRepartidores() {
        return listaRepartidores;
    }

    public ListaEnlazada<Ruta>
            getListaRutas() {
        return listaRutas;
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
    public Repartidor asignarPaqueteARepartidor( Paquete paquete  ) {
        if (!listaExpedicion.buscar(paquete)) {
            return null;
        }

        for (int i = 0;
                i < listaRepartidores.tamanio();
                i++) {

            Repartidor repartidor =
                    listaRepartidores.obtener(i);

            if (repartidor.tieneEspacio()) {
                listaExpedicion.eliminar(paquete);

                paquete.setEstado(
                        EstadoPaquete.EN_REPARTO
                );

                listaReparto.agregar(paquete);

                repartidor.asignarPaquete(paquete);

                repartidor.setRuta(
                        paquete.getRuta()
                );

                repartidor.setEstado(
                        EstadoRepartidor.CARGANDO
                );

                return repartidor;
            }
        }

        return null;
    }
    
    
}
