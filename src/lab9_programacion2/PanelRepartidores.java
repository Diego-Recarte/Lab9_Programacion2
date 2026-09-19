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

    private final ListaEnlazada<Repartidor>
            listaRepartidores;

    private TarjetaRepartidor[] tarjetas;

    public PanelRepartidores(
            ListaEnlazada<Repartidor>
                    listaRepartidores
    ) {
        this.listaRepartidores =
                listaRepartidores;

        setLayout(
                new GridLayout(1, 4, 10, 10)
        );

        setBackground(TemaUI.FONDO);

        cargarRepartidores();
    }

    private void cargarRepartidores() {
        int cantidad =
                listaRepartidores.tamanio();

        tarjetas = new TarjetaRepartidor[cantidad];

        for (int i = 0; i < cantidad; i++) {
            Repartidor repartidor =
                    listaRepartidores.obtener(i);

            tarjetas[i] =
                    new TarjetaRepartidor(repartidor);

            add(tarjetas[i]);
        }
    }

    public void actualizarRepartidor(
            int posicion
    ) {
        if (posicion < 0
                || posicion >= tarjetas.length) {
            return;
        }

        Repartidor repartidor =
                listaRepartidores.obtener(posicion);

        tarjetas[posicion].actualizar(
                repartidor
        );
    }

    public void actualizarTodos() {
        for (int i = 0; i < tarjetas.length; i++) {
            Repartidor repartidor =
                    listaRepartidores.obtener(i);

            tarjetas[i].actualizar(
                    repartidor
            );
        }

        revalidate();
        repaint();
    }

    public void limpiar() {
        for (int i = 0; i < tarjetas.length; i++) {
            Repartidor repartidor =
                    listaRepartidores.obtener(i);

            repartidor.setEstado(
                    EstadoRepartidor.DISPONIBLE
            );

            repartidor.setRuta(null);
            actualizarRepartidor(i);
        }
    }

    private static class TarjetaRepartidor
            extends JPanel {

        private final JLabel etiquetaNombre;
        private final JLabel etiquetaEstado;
        private final JLabel etiquetaRuta;
        private final JLabel etiquetaCapacidad;
        private final JLabel etiquetaEntregados;

        public TarjetaRepartidor(
                Repartidor repartidor
        ) {
            setLayout(
                    new GridLayout(5, 1, 2, 2)
            );

            setBackground(TemaUI.BLANCO);
            setBorder(TemaUI.bordePanel());

            etiquetaNombre = new JLabel();
            etiquetaEstado = new JLabel();
            etiquetaRuta = new JLabel();
            etiquetaCapacidad = new JLabel();
            etiquetaEntregados = new JLabel();

            configurarEtiqueta(etiquetaNombre);
            configurarEtiqueta(etiquetaEstado);
            configurarEtiqueta(etiquetaRuta);
            configurarEtiqueta(etiquetaCapacidad);
            configurarEtiqueta(etiquetaEntregados);

            etiquetaNombre.setFont(
                    TemaUI.SUBTITULO
            );

            add(etiquetaNombre);
            add(etiquetaEstado);
            add(etiquetaRuta);
            add(etiquetaCapacidad);
            add(etiquetaEntregados);

            actualizar(repartidor);
        }

        private void configurarEtiqueta(
                JLabel etiqueta
        ) {
            etiqueta.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            etiqueta.setFont(
                    TemaUI.NORMAL
            );
        }

        public void actualizar(
                Repartidor repartidor
        ) {
            etiquetaNombre.setText(
                    "🚚 "
                            + repartidor.getNombre()
            );

            etiquetaEstado.setText(
                    "Estado: "
                            + repartidor.getEstado()
            );

            if (repartidor.getRuta() == null) {
                etiquetaRuta.setText(
                        "Ruta: Sin asignar"
                );
            } else {
                etiquetaRuta.setText(
                        "Ruta: "
                                + repartidor
                                        .getRuta()
                                        .getCodigo()
                );
            }

            etiquetaCapacidad.setText(
                    "Capacidad: " + repartidor .getPaquetesCargados()  + "/"   + repartidor .getCapacidad()
            );

            etiquetaEntregados.setText(
                    "Entregados: "
                            + repartidor
                                    .getPaquetesEntregados()
            );

            cambiarColorEstado(
                    repartidor.getEstado()
            );
        }

        private void cambiarColorEstado(
                EstadoRepartidor estado
        ) {
            switch (estado) {
                case EN_RUTA:
                    etiquetaEstado.setForeground(
                            TemaUI.VERDE
                    );
                    break;

                case CARGANDO:
                    etiquetaEstado.setForeground(
                            TemaUI.NARANJA
                    );
                    break;

                case ENTREGANDO:
                    etiquetaEstado.setForeground(
                            TemaUI.AZUL
                    );
                    break;

                case FUERA_DE_SERVICIO:
                    etiquetaEstado.setForeground(
                            TemaUI.ROJO
                    );
                    break;

                default:
                    etiquetaEstado.setForeground(
                            TemaUI.GRIS_TEXTO
                    );
                    break;
            }
        }
    }
}