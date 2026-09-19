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

public class AlmacenThread extends Thread {

    private final ControlDistribucion control;
    private final DashboardPanel dashboard;

    private volatile boolean ejecutando;

    public AlmacenThread(
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
                        control.getListaRecepcion()
                                .eliminarPrimeroEsperando();

                control.almacenarPaquete(paquete);

                SwingUtilities.invokeLater(() -> {
                    dashboard.moverVisualmente(
                            paquete,
                            "RECEPCION",
                            "ALMACEN"
                    );
                });

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