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
                new String[]{
                        "Código",
                        "Cliente",
                        "Prioridad",
                        "Estado"
                }
        );

        panelAlmacen = new PanelZona(
                "ALMACÉN",
                20,
                new String[]{
                        "Código",
                        "Ciudad",
                        "Peso",
                        "Prioridad"
                }
        );

        panelClasificacion = new PanelZona(
                "CLASIFICACIÓN",
                10,
                new String[]{
                        "Código",
                        "Clasificador",
                        "Ruta",
                        "Estado"
                }
        );

        panelEmpaquetado = new PanelZona(
                "EMPAQUETADO",
                8,
                new String[]{
                        "Código",
                        "Empaquetador",
                        "Peso",
                        "Estado"
                }
        );

        panelExpedicion = new PanelZona(
                "EXPEDICIÓN",
                15,
                new String[]{
                        "Código",
                        "Ruta",
                        "Prioridad",
                        "Estado"
                }
        );

        panelRepartidores = new PanelRepartidores(centro.getListaRepartidores());

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
        cargarDatosEjemplo();
    }

    private void construirInterfaz() {
        JPanel contenido = new JPanel();
        contenido.setLayout(new BorderLayout(10, 10));
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

    private void cargarDatosEjemplo() {
        panelRecepcion.agregarFila(
                "PKG-001",
                "Carlos López",
                "ALTA",
                "RECIBIDO"
        );

        panelRecepcion.agregarFila(
                "PKG-002",
                "Ana Martínez",
                "NORMAL",
                "RECIBIDO"
        );

        panelAlmacen.agregarFila(
                "PKG-003",
                "Barcelona",
                "4.5 kg",
                "URGENTE"
        );

        panelClasificacion.agregarFila(
                "PKG-004",
                "Clasificador 1",
                "R02",
                "CLASIFICANDO"
        );

        panelEmpaquetado.agregarFila(
                "PKG-005",
                "Empaquetador 1",
                "2.0 kg",
                "EMPAQUETANDO"
        );

        panelExpedicion.agregarFila(
                "PKG-006",
                "R01",
                "ALTA",
                "EN_EXPEDICION"
        );

        totalGenerados = 6;
        totalEnProceso = 6;

       
    }

    public void agregarPaquete(
            String codigo,
            String cliente,
            String ciudad,
            double peso,
            String prioridad
    ) {
        SwingUtilities.invokeLater(() -> {
            panelRecepcion.agregarFila(
                    codigo,
                    cliente,
                    prioridad,
                    "RECIBIDO"
            );

            totalGenerados++;
            totalEnProceso++;

            agregarRegistro(
                    codigo + " recibido"
            );
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

        agregarRegistro(
                "Sistema reiniciado"
        );
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
        return totalEnProceso;
    }

}
