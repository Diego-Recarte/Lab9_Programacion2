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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout;
    private final JPanel panelCentral;

    private final DashboardPanel dashboardPanel;
    private final PanelEstadisticas estadisticasPanel;

    private final JButton btnIniciar;
    private final JButton btnPausar;
    private final JButton btnReanudar;
    private final JButton btnDetener;
    private final JButton btnReiniciar;
    private final JButton btnNuevoPaquete;
    private final JButton btnDashboard;
    private final JButton btnEstadisticas;
    
    
    private Simulador simulador;

    public MainFrame() {
        setTitle("Centro Logístico de Paquetería");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(1250, 800));
        setSize(1400, 800);
        
        setLocationRelativeTo(null);
         setExtendedState(JFrame.MAXIMIZED_BOTH);

        cardLayout = new CardLayout();
        panelCentral = new JPanel(cardLayout);
        panelCentral.setBackground(TemaUI.FONDO);

        dashboardPanel = new DashboardPanel();
        estadisticasPanel = new PanelEstadisticas();

        panelCentral.add(dashboardPanel, "DASHBOARD");
        panelCentral.add(estadisticasPanel, "ESTADISTICAS");

        btnIniciar = crearBoton("Iniciar", TemaUI.VERDE);
        btnPausar = crearBoton("Pausar", TemaUI.NARANJA);
        btnReanudar = crearBoton("Reanudar", TemaUI.AZUL);
        btnDetener = crearBoton("Detener", TemaUI.ROJO);
        btnReiniciar = crearBoton("Reiniciar",  new Color(100, 100, 100));
        btnNuevoPaquete = crearBoton("Nuevo paquete", TemaUI.AZUL );
        btnDashboard = crearBoton( "Dashboard", TemaUI.AZUL_OSCURO );
        btnEstadisticas = crearBoton("Estadísticas",TemaUI.AZUL_OSCURO );
        ///////////////////////////////////////////////////////////////////////////
        
        simulador = new Simulador(dashboardPanel.getControl(), dashboardPanel);

        construirInterfaz();
        configurarEventos();

        cardLayout.show(panelCentral, "DASHBOARD");
    }

    private void construirInterfaz() {
        setLayout(new BorderLayout(10, 10));

        JPanel contenedor = new JPanel(
                new BorderLayout(10, 10)
        );

        contenedor.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        contenedor.setBackground(TemaUI.FONDO);

        contenedor.add(
                crearEncabezado(),
                BorderLayout.NORTH
        );

        contenedor.add(
                panelCentral,
                BorderLayout.CENTER
        );

        add(contenedor, BorderLayout.CENTER);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(
                new BorderLayout(10, 10)
        );

        encabezado.setBackground(TemaUI.BLANCO);
        encabezado.setBorder(TemaUI.bordePanel());

        JPanel panelTitulo = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        5,
                        0
                )
        );

        panelTitulo.setOpaque(false);

        JLabel titulo = new JLabel(
                "CENTRO LOGÍSTICO"
        );

        titulo.setFont(TemaUI.TITULO);
        titulo.setForeground(TemaUI.AZUL_OSCURO);

        JLabel estado = new JLabel(
                "Estado: DETENIDO"
        );

        estado.setForeground(TemaUI.GRIS_TEXTO);

        panelTitulo.add(titulo);
        panelTitulo.add(estado);

        JPanel barraBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        6,
                        5
                )
        );

        barraBotones.setOpaque(false);

        barraBotones.add(btnIniciar);
        barraBotones.add(btnPausar);
        barraBotones.add(btnReanudar);
        barraBotones.add(btnDetener);
        barraBotones.add(btnReiniciar);
        barraBotones.add(btnNuevoPaquete);
        barraBotones.add(btnDashboard);
        barraBotones.add(btnEstadisticas);
       

        encabezado.add(
                panelTitulo,
                BorderLayout.NORTH
        );

        encabezado.add(
                barraBotones,
                BorderLayout.CENTER
        );

        return encabezado;
    }

    private void configurarEventos() {
        btnNuevoPaquete.addActionListener(e -> {
            abrirDialogoNuevoPaquete();
        });

        btnDashboard.addActionListener(e -> {
            cardLayout.show(
                    panelCentral,
                    "DASHBOARD"
            );
        });

        btnEstadisticas.addActionListener(e -> {
            estadisticasPanel.actualizarDatos(
                    dashboardPanel.getTotalGenerados(),
                    dashboardPanel.getTotalEntregados(),
                    dashboardPanel.getTotalDevueltos(),
                    dashboardPanel.getTotalEnProceso()
            );

            cardLayout.show(
                    panelCentral,
                    "ESTADISTICAS"
            );
        });

        btnReiniciar.addActionListener(e -> {
            dashboardPanel.limpiarTodo();
            estadisticasPanel.limpiar();

            cardLayout.show(
                    panelCentral,
                    "DASHBOARD"
            );
        });

        btnDetener.addActionListener(e -> {
            simulador.detener();

            dashboardPanel.agregarRegistro(
                    "Detenido"
            );
        });

        btnIniciar.addActionListener(e -> {
            simulador.iniciar();

            dashboardPanel.agregarRegistro(
                    "inicio"
            );
        });

        btnPausar.addActionListener(e -> {
            dashboardPanel.agregarRegistro(
                    "Simulación pausada"
            );
        });

        btnReanudar.addActionListener(e -> {
            dashboardPanel.agregarRegistro(
                    "Simulación reanudada"
            );
        });
        
        //////////////////////////////////////////////////////////////////
        ///
       
    }

    private void abrirDialogoNuevoPaquete() {
        DialogoNuevoPaquete dialogo =
                new DialogoNuevoPaquete(this);

        dialogo.setVisible(true);

        if (dialogo.isPaqueteCreado()) {
            dashboardPanel.agregarPaquete(
                    dialogo.getCodigo(),
                    dialogo.getCliente(),
                    dialogo.getDireccion(),
                    dialogo.getCiudad(),
                    dialogo.getPeso(),
                    dialogo.getPrioridad()
            );
        }
    }

    private JButton crearBoton(
            String texto,
            Color color
    ) {
        JButton boton = new JButton(texto);

        boton.setFont(TemaUI.NORMAL);
        boton.setForeground(Color.WHITE);
        boton.setBackground(color);
        boton.setFocusPainted(false);

        boton.setBorder(
                BorderFactory.createEmptyBorder( 8,14,8, 14 )
        );

        return boton;
    }
}
