/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */


import javax.swing.SwingUtilities;

public class ClasificadorThread extends Thread {

    private final ControlDistribucion control;
    private final DashboardPanel dashboard;

    private volatile boolean ejecutando;

    public ClasificadorThread(
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
                        control.getListaAlmacen()
                                .eliminarPrimeroEsperando();

                paquete.setEstado(
                        EstadoPaquete.CLASIFICANDO
                );

                Thread.sleep(1000);

                boolean clasificado =
                        control.clasificarPaquete(
                                paquete
                        );

                if (clasificado) {
                    SwingUtilities.invokeLater(() -> {
                        dashboard.moverVisualmente(
                                paquete,
                                "ALMACEN",
                                "CLASIFICACION"
                        );
                    });
                } else {
                    control.getListaAlmacen()
                            .agregarEsperando(paquete);

                    SwingUtilities.invokeLater(() -> {
                        dashboard.agregarRegistro(
                                "No se encontró ruta para "
                                        + paquete.getCiudad()
                        );
                    });
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void detener() {
        ejecutando = false;
        interrupt();
    }
}