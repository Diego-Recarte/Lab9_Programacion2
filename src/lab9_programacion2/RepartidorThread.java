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

    public RepartidorThread(
            ControlDistribucion control,
            DashboardPanel dashboard
    ) {
        this.control = control;
        this.dashboard = dashboard;
        this.ejecutando = true;
    }

    @Override
    public void run() {
        while (ejecutando) {
            try {
                Paquete paquete =
                        control.getListaExpedicion()
                                .eliminarPrimeroEsperando();

                Repartidor repartidor =
                        control.asignarPaqueteARepartidor(
                                paquete
                        );

                if (repartidor == null) {
                    control.getListaExpedicion()
                            .agregarEsperando(paquete);

                    Thread.sleep(500);
                    continue;
                }

                dashboard.moverExpedicionAReparto(
                        paquete,
                        repartidor
                );

                Thread.sleep(3000);

                entregarPaquete(
                        paquete,
                        repartidor
                );

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void entregarPaquete(
            Paquete paquete,
            Repartidor repartidor
    ) {
        boolean entregaExitosa =
                Math.random() < 0.8;

        if (entregaExitosa) {
            control.entregarPaquete(paquete);
            repartidor.registrarEntrega();

            if (repartidor.getPaquetesCargados() == 0) {
                repartidor.setEstado(
                        EstadoRepartidor.DISPONIBLE
                );

                repartidor.setRuta(null);
            }

            dashboard.actualizarRepartidores();

            dashboard.agregarRegistro(
                    paquete.getCodigo()
                            + " entregado"
            );

        } else {
            control.devolverPaquete(paquete);
            repartidor.registrarEntrega();

            if (repartidor.getPaquetesCargados() == 0) {
                repartidor.setEstado(
                        EstadoRepartidor.DISPONIBLE
                );

                repartidor.setRuta(null);
            }

            dashboard.agregarRegistro(
                    paquete.getCodigo()
                            + " cliente ausente"
            );
        }
    }

    public void detener() {
        ejecutando = false;
        interrupt();
    }
}