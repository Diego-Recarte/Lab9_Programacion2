/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */


public class RepartidorThread extends Thread {
    private final ControlDistribucion control;
    private final DashboardPanel dashboard;
    private volatile boolean ejecutando;

    public RepartidorThread(ControlDistribucion control, DashboardPanel dashboard) {
        this.control = control;
        this.dashboard = dashboard;
        this.ejecutando = true;
    }

    @Override
    public void run() {
        while (ejecutando) {
            try {
                Paquete paquete = control.getListaExpedicion().eliminarPrimeroEsperando();

                Repartidor repartidor = control.asignarPaqueteARepartidor(paquete);

                if (repartidor == null) {
                    control.getListaExpedicion().agregarEsperando(paquete);
                    Thread.sleep(500);
                    continue;
                }

                dashboard.moverExpedicionAReparto(paquete, repartidor);
                dashboard.actualizarRepartidores();

                dashboard.agregarRegistro(
                        paquete.getCodigo()
                                + " cargado en "
                                + repartidor.getNombre()
                                + " ("
                                + repartidor.getPaquetesCargados()
                                + "/"
                                + repartidor.getCapacidad()
                                + ")"
                );

                if (repartidor.getPaquetesCargados() >= repartidor.getCapacidad()) {
                    iniciarRuta(repartidor);
                } else if (control.getListaExpedicion().estaVacia()) {
                    iniciarRuta(repartidor);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void iniciarRuta(Repartidor repartidor) {
        if (repartidor.getEstado() == EstadoRepartidor.EN_RUTA
                || repartidor.getEstado() == EstadoRepartidor.ENTREGANDO) {
            return;
        }

        repartidor.setEstado(EstadoRepartidor.EN_RUTA);
        dashboard.actualizarRepartidores();

        dashboard.agregarRegistro(
                repartidor.getNombre()
                        + " inició la ruta "
                        + repartidor.getRuta()
        );

        Thread ruta = new Thread(() -> {
            try {
                Thread.sleep(3000);
                entregarPaquetes(repartidor);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        ruta.start();
    }

    private void entregarPaquetes(Repartidor repartidor) {
        repartidor.setEstado(EstadoRepartidor.ENTREGANDO);
        dashboard.actualizarRepartidores();

        while (!repartidor.getPaquetes().estaVacia()) {
            Paquete paquete = repartidor.sacarPrimerPaquete();

            if (paquete == null) {
                break;
            }

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

            boolean entregaExitosa = Math.random() < 0.8;

            if (entregaExitosa) {
                control.entregarPaquete(paquete);

                dashboard.eliminarDeExpedicion(paquete);

                dashboard.agregarRegistro(
                        paquete.getCodigo()
                                + " entregado por "
                                + repartidor.getNombre()
                );
            } else {
                control.devolverPaquete(paquete);

                dashboard.agregarRegistro(
                        paquete.getCodigo()
                                + " cliente ausente"
                );
            }

            repartidor.registrarEntrega();
            dashboard.actualizarRepartidores();
        }

        repartidor.setEstado(EstadoRepartidor.DISPONIBLE);
        repartidor.setRuta(null);

        dashboard.actualizarRepartidores();

        dashboard.agregarRegistro(
                repartidor.getNombre()
                        + " regresó y está disponible"
        );
    }

    public void detener() {
        ejecutando = false;
        interrupt();
    }
}