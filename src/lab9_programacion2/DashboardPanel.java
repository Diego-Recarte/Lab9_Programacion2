/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class DashboardPanel extends JPanel {
    private final PanelZona panelRecepcion;
    private final PanelZona panelAlmacen;
    private final PanelZona panelClasificacion;
    private final PanelZona panelEmpaquetado;
    private final PanelZona panelExpedicion;
    private final ControlDistribucion centro;
    private final PanelRepartidores panelRepartidores;
    private final JTextArea areaRegistro;
    private int totalGenerados;
    private int totalEntregados;
    private int totalDevueltos;
    private int totalEnProceso;

    public DashboardPanel() {
        centro = new ControlDistribucion();
        setLayout(new BorderLayout(10, 10));
        setBackground(TemaUI.FONDO);

        panelRecepcion = new PanelZona(
                "RECEPCIÓN",
                10,
                new String[]{"Código", "Cliente", "Prioridad", "Estado"}
        );

        panelAlmacen = new PanelZona(
                "ALMACÉN",
                20,
                new String[]{"Código", "Ciudad", "Peso", "Prioridad"}
        );

        panelClasificacion = new PanelZona(
                "CLASIFICACIÓN",
                10,
                new String[]{"Código", "Clasificador", "Ruta", "Estado"}
        );

        panelEmpaquetado = new PanelZona(
                "EMPAQUETADO",
                8,
                new String[]{"Código", "Empaquetador", "Peso", "Estado"}
        );

        panelExpedicion = new PanelZona(
                "EXPEDICIÓN",
                15,
                new String[]{"Código", "Ruta", "Prioridad", "Estado"}
        );

        panelRepartidores = new PanelRepartidores(
                centro.getListaRepartidores()
        );

        areaRegistro = new JTextArea();
        areaRegistro.setEditable(false);
        areaRegistro.setLineWrap(false);
        areaRegistro.setWrapStyleWord(false);
        areaRegistro.setFont(
                new java.awt.Font(
                        "Monospaced",
                        java.awt.Font.PLAIN,
                        12
                )
        );

        construirInterfaz();
    }

    private void construirInterfaz() {
        JPanel contenido = new JPanel(
                new BorderLayout(10, 10)
        );

        contenido.setBackground(TemaUI.FONDO);
        contenido.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                        5,
                        5,
                        5,
                        5
                )
        );

        JPanel filaSuperior = new JPanel(
                new GridLayout(1, 3, 10, 10)
        );

        filaSuperior.setBackground(TemaUI.FONDO);
        filaSuperior.setPreferredSize(
                new Dimension(0, 300)
        );

        filaSuperior.add(panelRecepcion);
        filaSuperior.add(panelAlmacen);
        filaSuperior.add(panelClasificacion);

        JPanel columnaInferior = new JPanel(
                new GridLayout(2, 1, 10, 10)
        );

        columnaInferior.setBackground(TemaUI.FONDO);
        columnaInferior.setPreferredSize(
                new Dimension(0, 500)
        );

        columnaInferior.add(panelEmpaquetado);
        columnaInferior.add(panelExpedicion);

        JPanel panelZonas = new JPanel(
                new BorderLayout(10, 10)
        );

        panelZonas.setBackground(TemaUI.FONDO);
        panelZonas.add(
                filaSuperior,
                BorderLayout.NORTH
        );

        panelZonas.add(
                columnaInferior,
                BorderLayout.CENTER
        );

        JPanel panelRepartidoresRegistro = new JPanel(
                new BorderLayout(10, 10)
        );

        panelRepartidoresRegistro.setBackground(
                TemaUI.FONDO
        );

        panelRepartidoresRegistro.add(
                panelRepartidores,
                BorderLayout.NORTH
        );

        panelRepartidoresRegistro.add(
                crearPanelRegistro(),
                BorderLayout.CENTER
        );

        panelZonas.add(
                panelRepartidoresRegistro,
                BorderLayout.SOUTH
        );

        contenido.add(
                panelZonas,
                BorderLayout.CENTER
        );

        JScrollPane scrollPrincipal = new JScrollPane(
                contenido
        );

        scrollPrincipal.setBorder(null);
        scrollPrincipal.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
        scrollPrincipal.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        add(
                scrollPrincipal,
                BorderLayout.CENTER
        );
    }

    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setBackground(TemaUI.BLANCO);
        panel.setBorder(TemaUI.bordePanel());
        panel.setPreferredSize(
                new Dimension(0, 170)
        );

        JLabel titulo = new JLabel(
                "REGISTRO DEL SISTEMA"
        );

        titulo.setFont(TemaUI.SUBTITULO);
        titulo.setForeground(TemaUI.AZUL_OSCURO);

        JScrollPane scroll = new JScrollPane(
                areaRegistro
        );

        panel.add(
                titulo,
                BorderLayout.NORTH
        );

        panel.add(
                scroll,
                BorderLayout.CENTER
        );

        return panel;
    }

    public void agregarPaquete(
            String codigo,
            String cliente,
            String direccion,
            String ciudad,
            double peso,
            String prioridad
    ) {
        PrioridadPaquete prioridadPaquete =
                PrioridadPaquete.valueOf(
                        prioridad.trim().toUpperCase()
                );

        Paquete paquete = centro.crearPaquete(
                codigo,
                cliente,
                direccion,
                ciudad,
                peso,
                prioridadPaquete
        );

        Runnable actualizar = () -> {
            panelRecepcion.agregarFila(
                    paquete.getCodigo(),
                    paquete.getCliente(),
                    paquete.getPrioridad(),
                    paquete.getEstado()
            );

            totalGenerados++;
            totalEnProceso++;

            agregarRegistro(
                    paquete.getCodigo()
                            + " recibido"
            );
        };

        if (SwingUtilities.isEventDispatchThread()) {
            actualizar.run();
        } else {
            SwingUtilities.invokeLater(actualizar);
        }
    }

    public void moverVisualmente(
            Paquete paquete,
            String origen,
            String destino
    ) {
        Runnable accion = () -> {
            switch (origen) {
                case "RECEPCION":
                    eliminarPorCodigo(
                            panelRecepcion,
                            paquete.getCodigo()
                    );
                    break;

                case "ALMACEN":
                    eliminarPorCodigo(
                            panelAlmacen,
                            paquete.getCodigo()
                    );
                    break;

                case "CLASIFICACION":
                    eliminarPorCodigo(
                            panelClasificacion,
                            paquete.getCodigo()
                    );
                    break;

                case "EMPAQUETADO":
                    eliminarPorCodigo(
                            panelEmpaquetado,
                            paquete.getCodigo()
                    );
                    break;

                case "EXPEDICION":
                    eliminarPorCodigo(
                            panelExpedicion,
                            paquete.getCodigo()
                    );
                    break;
            }

            String ruta = paquete.getRuta() == null
                    ? "Sin ruta"
                    : paquete.getRuta().getCodigo();

            switch (destino) {
                case "ALMACEN":
                    panelAlmacen.agregarFila(
                            paquete.getCodigo(),
                            paquete.getCiudad(),
                            paquete.getPeso() + " kg",
                            paquete.getPrioridad()
                    );
                    break;

                case "CLASIFICACION":
                    panelClasificacion.agregarFila(
                            paquete.getCodigo(),
                            "Clasificador",
                            ruta,
                            paquete.getEstado()
                    );
                    break;

                case "EMPAQUETADO":
                    panelEmpaquetado.agregarFila(
                            paquete.getCodigo(),
                            "Empaquetador",
                            paquete.getPeso() + " kg",
                            paquete.getEstado()
                    );
                    break;

                case "EXPEDICION":
                    panelExpedicion.agregarFila(
                            paquete.getCodigo(),
                            ruta,
                            paquete.getPrioridad(),
                            paquete.getEstado()
                    );
                    break;
            }

            agregarRegistro(
                    paquete.getCodigo()
                            + " pasó a "
                            + destino
            );
        };

        if (SwingUtilities.isEventDispatchThread()) {
            accion.run();
        } else {
            SwingUtilities.invokeLater(accion);
        }
    }

    public void moverExpedicionAReparto(
            Paquete paquete,
            Repartidor repartidor
    ) {
        SwingUtilities.invokeLater(() -> {
            eliminarPorCodigo(
                    panelExpedicion,
                    paquete.getCodigo()
            );

            panelRepartidores.actualizarTodos();

            agregarRegistro(
                    paquete.getCodigo()
                            + " asignado a "
                            + repartidor.getNombre()
            );
        });
    }

    public void eliminarDeExpedicion(
            Paquete paquete
    ) {
        SwingUtilities.invokeLater(() -> {
            eliminarPorCodigo(
                    panelExpedicion,
                    paquete.getCodigo()
            );
        });
    }

    public void actualizarRepartidores() {
        SwingUtilities.invokeLater(() -> {
            panelRepartidores.actualizarTodos();
        });
    }

    public void agregarRegistro(String mensaje) {
        Runnable accion = () -> {
            String hora = new SimpleDateFormat(
                    "HH:mm:ss"
            ).format(new Date());

            areaRegistro.append(
                    hora + " | " + mensaje + "\n"
            );

            areaRegistro.setCaretPosition(
                    areaRegistro.getDocument().getLength()
            );
        };

        if (SwingUtilities.isEventDispatchThread()) {
            accion.run();
        } else {
            SwingUtilities.invokeLater(accion);
        }
    }

    private void eliminarPorCodigo(
            PanelZona panel,
            String codigo
    ) {
        for (int i = 0;
                i < panel.getModelo().getRowCount();
                i++) {

            Object valor =
                    panel.getModelo()
                            .getValueAt(i, 0);

            if (codigo.equals(valor)) {
                panel.eliminarFila(i);
                return;
            }
        }
    }

    public void limpiarTodo() {
        panelRecepcion.limpiar();
        panelAlmacen.limpiar();
        panelClasificacion.limpiar();
        panelEmpaquetado.limpiar();
        panelExpedicion.limpiar();
        panelRepartidores.limpiar();
        areaRegistro.setText("");
        totalGenerados = 0;
        totalEntregados = 0;
        totalDevueltos = 0;
        totalEnProceso = 0;
        agregarRegistro("Sistema reiniciado");
    }

    public ControlDistribucion getControl() {
        return centro;
    }

    public int getTotalGenerados() {
        return centro.getTotalGenerados();
    }

    public int getTotalEntregados() {
        return centro.getTotalEntregados();
    }

    public int getTotalDevueltos() {
        return centro.getTotalDevueltos();
    }

    public int getTotalEnProceso() {
        return centro.getTotalEnProceso();
    }
}