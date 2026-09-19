/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */
public class Simulador {
    

    private final ControlDistribucion control;
    private final DashboardPanel dashboard;

    private AlmacenThread almacenThread;
    private ClasificadorThread clasificadorThread;
    private EmpaquetadorThread empaquetadorThread;
    private RepartidorThread repartidorThread;

    private boolean ejecutando;

    public Simulador(
            ControlDistribucion control,
            DashboardPanel dashboard
    ) {
        this.control = control;
        this.dashboard = dashboard;
        this.ejecutando = false;
    }

    public void iniciar() {
        if (ejecutando) {
            return;
        }

        ejecutando = true;

        almacenThread = new AlmacenThread(
                control,
                dashboard
        );

        clasificadorThread =
                new ClasificadorThread(
                        control,
                        dashboard
                );

        empaquetadorThread =
                new EmpaquetadorThread(
                        control,
                        dashboard
                );

        repartidorThread =
                new RepartidorThread(
                        control,
                        dashboard
                );

        almacenThread.start();
        clasificadorThread.start();
        empaquetadorThread.start();
        repartidorThread.start();
    }

    public void detener() {
        ejecutando = false;

        if (almacenThread != null) {
            almacenThread.detener();
        }

        if (clasificadorThread != null) {
            clasificadorThread.detener();
        }

        if (empaquetadorThread != null) {
            empaquetadorThread.detener();
        }

        if (repartidorThread != null) {
            repartidorThread.detener();
        }
    }

    public boolean estaEjecutando() {
        return ejecutando;
    }

}
