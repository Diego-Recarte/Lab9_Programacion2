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

    

    public void agregarPaquete(String codigo, String cliente,String direccion,String ciudad,double peso, String prioridad ) {
          PrioridadPaquete prioridadPaquete =
            PrioridadPaquete.valueOf(  prioridad.toUpperCase() );

            Paquete paquete = centro.crearPaquete(codigo, cliente,direccion, ciudad, peso,prioridadPaquete );

            Runnable actualizar = () -> {
                panelRecepcion.agregarFila(paquete.getCodigo(), paquete.getCliente(), paquete.getPrioridad(), paquete.getEstado() );

                totalGenerados++;
                totalEnProceso++;

                agregarRegistro(  paquete.getCodigo()  + " recibido");
            };

            if (SwingUtilities.isEventDispatchThread()) {
                actualizar.run();
            } else {
                SwingUtilities.invokeLater(actualizar);
            }
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
    
    private void eliminarPorCodigo(PanelZona panel,String codigo) {
        for (int i = 0; i < panel.getModelo().getRowCount();i++) {

            Object valor =panel.getModelo() .getValueAt(i, 0);

            if (codigo.equals(valor)) {
                panel.eliminarFila(i);
                return;
            }
        }
    }
    
    private void mostrarEnAlmacen(Paquete paquete) {
        panelAlmacen.agregarFila( paquete.getCodigo(), paquete.getCiudad(), paquete.getPeso() + " kg",paquete.getPrioridad() );
    }
    private void mostrarEnClasificacion( Paquete paquete) {
        String ruta = paquete.getRuta().getCodigo();

        panelClasificacion.agregarFila( paquete.getCodigo(), "Clasificador 1", ruta, paquete.getEstado());
    }
    
    private void mostrarEnEmpaquetado( Paquete paquete) {
        panelEmpaquetado.agregarFila( paquete.getCodigo(), "Empaquetador 1", paquete.getPeso() + " kg",  paquete.getEstado());
 
    }
    private void mostrarEnExpedicion( Paquete paquete) {
        String ruta =  paquete.getRuta().getCodigo();

        panelExpedicion.agregarFila( paquete.getCodigo(), ruta,paquete.getPrioridad(),paquete.getEstado());
    }
    private Repartidor obtenerRepartidorPorRuta( Ruta ruta) {
        if (ruta == null) {
            return null;
        }

        for (int i = 0;i < centro.getListaRepartidores().tamanio();i++) {

            Repartidor repartidor = centro.getListaRepartidores().obtener(i);

            if (repartidor.getRuta() != null && repartidor.getRuta().getCodigo() .equals(  ruta.getCodigo() )) {

                return repartidor;
            }
        }

        return null;
    }

    
    public void enviarAReparto() {
        if (centro.getListaExpedicion().estaVacia()) {
            agregarRegistro(
                    "No hay paquetes en expedición"
            );

            return;
        }

        Paquete paquete =
                centro.getListaExpedicion()
                        .obtener(0);

        Repartidor repartidor =
                centro.asignarPaqueteARepartidor(
                        paquete
                );

        if (repartidor == null) {
            agregarRegistro(
                    "No hay repartidores disponibles"
            );

            return;
        }

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
    }
    public void moverVisualmente( Paquete paquete,String origen, String destino) {
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
                            paquete.getRuta(),
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
                            paquete.getRuta(),
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
    public void actualizarRepartidores() {
        if (SwingUtilities.isEventDispatchThread()) {
            panelRepartidores.actualizarTodos();
        } else {
            SwingUtilities.invokeLater(() -> {
                panelRepartidores.actualizarTodos();
            });
        }
    }
    public ControlDistribucion getControl() {
        return centro;
    }
    
    public void moverExpedicionAReparto(Paquete paquete, Repartidor repartidor ) {
        Runnable actualizar = () -> {
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
        };

        if (SwingUtilities.isEventDispatchThread()) {
            actualizar.run();
        } else {
            SwingUtilities.invokeLater(actualizar);
        }
    }
   
    
    public void avanzarPaqueteManual() {/////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (!centro.getListaRecepcion().estaVacia()) {
            Paquete paquete =centro.getListaRecepcion().obtener(0);

            centro.almacenarPaquete(paquete);

            eliminarPorCodigo(
                    panelRecepcion,
                    paquete.getCodigo()
            );

            mostrarEnAlmacen(paquete);

            agregarRegistro(
                    paquete.getCodigo()
                            + " pasó a almacén"
            );

            return;
        }

        if (!centro.getListaAlmacen().estaVacia()) {
            Paquete paquete =
                    centro.getListaAlmacen().obtener(0);

            boolean clasificado =
                    centro.clasificarPaquete(paquete);

            if (!clasificado) {
                agregarRegistro(
                        "No se encontró una ruta para "
                                + paquete.getCiudad()
                );

                return;
            }

            eliminarPorCodigo(
                    panelAlmacen,
                    paquete.getCodigo()
            );

            mostrarEnClasificacion(paquete);

            agregarRegistro(
                    paquete.getCodigo()
                            + " pasó a clasificación"
            );

            return;
        }

        if (!centro.getListaClasificacion()
                .estaVacia()) {

            Paquete paquete =
                    centro.getListaClasificacion()
                            .obtener(0);

            centro.empaquetarPaquete(paquete);

            eliminarPorCodigo(
                    panelClasificacion,
                    paquete.getCodigo()
            );

            mostrarEnEmpaquetado(paquete);

            agregarRegistro(
                    paquete.getCodigo()
                            + " pasó a empaquetado"
            );

            return;
        }

        if (!centro.getListaEmpaquetado()
                .estaVacia()) {

            Paquete paquete =
                    centro.getListaEmpaquetado()
                            .obtener(0);

            centro.finalizarEmpaquetado(paquete);

            eliminarPorCodigo(
                    panelEmpaquetado,
                    paquete.getCodigo()
            );

            mostrarEnExpedicion(paquete);

            agregarRegistro(
                    paquete.getCodigo()
                            + " pasó a expedición"
            );

            return;
        }

        if (!centro.getListaExpedicion().estaVacia()) {

            enviarAReparto();
            return;
        }

        agregarRegistro(
                "No hay paquetes para avanzar"
        );
    }
    public void entregarPaqueteManual() {
        if (centro.getListaReparto().estaVacia()) {
            agregarRegistro(
                    "No hay paquetes en reparto"
            );

            return;
        }

        Paquete paquete =
                centro.getListaReparto()
                        .obtener(0);

        Repartidor repartidor =
                obtenerRepartidorPorRuta(
                        paquete.getRuta()
                );

        centro.entregarPaquete(paquete);

        if (repartidor != null) {
            repartidor.registrarEntrega();

            if (repartidor.getPaquetesCargados() == 0) {
                repartidor.setEstado(
                        EstadoRepartidor.REGRESANDO
                );
            }
        }

        panelRepartidores.actualizarTodos();

        agregarRegistro(
                paquete.getCodigo()
                        + " entregado"
        );
    }
    
    
    
    
    
    

}
