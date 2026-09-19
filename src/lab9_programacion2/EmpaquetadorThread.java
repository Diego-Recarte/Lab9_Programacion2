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

public class EmpaquetadorThread extends Thread {

    private final ControlDistribucion control;
    private final DashboardPanel dashboard;

    private volatile boolean ejecutando;

    public EmpaquetadorThread(
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
                        control.getListaClasificacion()
                                .eliminarPrimeroEsperando();

                control.empaquetarPaquete(paquete);

                SwingUtilities.invokeLater(() -> {
                    dashboard.moverVisualmente(
                            paquete,
                            "CLASIFICACION",
                            "EMPAQUETADO"
                    );
                });

                Thread.sleep(
                        calcularTiempo(
                                paquete.getPeso()
                        )
                );

                control.finalizarEmpaquetado(
                        paquete
                );

                SwingUtilities.invokeLater(() -> {
                    dashboard.moverVisualmente(
                            paquete,
                            "EMPAQUETADO",
                            "EXPEDICION"
                    );
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private int calcularTiempo(double peso) {
        if (peso <= 2) {
            return 1000;
        }

        if (peso <= 5) {
            return 2000;
        }

        return 3000;
    }

    public void detener() {
        ejecutando = false;
        interrupt();
    }
}