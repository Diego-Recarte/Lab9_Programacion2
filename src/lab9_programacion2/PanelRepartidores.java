/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelRepartidores extends JPanel {

    private final TarjetaRepartidor[] repartidores;

    public PanelRepartidores() {
        setLayout(new GridLayout(1, 4, 10, 10));
        setBackground(TemaUI.FONDO);

        repartidores = new TarjetaRepartidor[4];

        for (int i = 0; i < repartidores.length; i++) {
            repartidores[i] = new TarjetaRepartidor(
                    "Repartidor " + (i + 1),
                    "DISPONIBLE",
                    0,
                    i == 0 ? 5 : i == 1 ? 4 : 6
            );

            add(repartidores[i]);
        }
    }

    public void actualizarRepartidor(
            int posicion,
            String estado,
            int paquetes,
            int capacidad
    ) {
        if (posicion < 0 || posicion >= repartidores.length) {
            return;
        }

        repartidores[posicion].actualizar(
                estado,
                paquetes,
                capacidad
        );
    }

    public void limpiar() {
        for (TarjetaRepartidor repartidor : repartidores) {
            repartidor.actualizar("DISPONIBLE", 0, 0);
        }
    }

    private static class TarjetaRepartidor extends JPanel {

        private final JLabel nombre;
        private final JLabel estado;
        private final JLabel capacidad;

        public TarjetaRepartidor(
                String nombre,
                String estado,
                int paquetes,
                int capacidadMaxima
        ) {
            setLayout(new GridLayout(3, 1, 2, 2));
            setBackground(TemaUI.BLANCO);
            setBorder(TemaUI.bordePanel());

            this.nombre = new JLabel("🚚 " + nombre);
            this.estado = new JLabel("Estado: " + estado);
            this.capacidad = new JLabel(
                    "Paquetes: " + paquetes + "/" + capacidadMaxima
            );

            this.nombre.setHorizontalAlignment(SwingConstants.CENTER);
            this.estado.setHorizontalAlignment(SwingConstants.CENTER);
            this.capacidad.setHorizontalAlignment(SwingConstants.CENTER);

            this.nombre.setFont(TemaUI.SUBTITULO);
            this.estado.setFont(TemaUI.NORMAL);
            this.capacidad.setFont(TemaUI.NORMAL);

            add(this.nombre);
            add(this.estado);
            add(this.capacidad);
        }

        public void actualizar(
                String nuevoEstado,
                int cantidad,
                int capacidadMaxima
        ) {
            estado.setText("Estado: " + nuevoEstado);
            capacidad.setText(
                    "Paquetes: " + cantidad + "/" + capacidadMaxima
            );

            if ("EN_RUTA".equals(nuevoEstado)) {
                estado.setForeground(TemaUI.VERDE);
            } else if ("CARGANDO".equals(nuevoEstado)) {
                estado.setForeground(TemaUI.NARANJA);
            } else if ("FUERA_DE_SERVICIO".equals(nuevoEstado)) {
                estado.setForeground(TemaUI.ROJO);
            } else {
                estado.setForeground(TemaUI.GRIS_TEXTO);
            }
        }
    }

}
