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
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanelEstadisticas extends JPanel {

    private final JLabel lblGenerados;
    private final JLabel lblEntregados;
    private final JLabel lblDevueltos;
    private final JLabel lblEnProceso;
    private final JLabel lblPendientes;
    private final JLabel lblTiempoPromedio;

    public PanelEstadisticas() {
        setLayout(new BorderLayout());
        setBackground(TemaUI.FONDO);

        lblGenerados = crearValor("0");
        lblEntregados = crearValor("0");
        lblDevueltos = crearValor("0");
        lblEnProceso = crearValor("0");
        lblPendientes = crearValor("0");
        lblTiempoPromedio = crearValor("0.0 s");

        add(crearPanel(), BorderLayout.NORTH);
    }

    private JPanel crearPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(TemaUI.BLANCO);

        panel.add(crearTarjeta("Paquetes generados", lblGenerados));
        panel.add(crearTarjeta("Paquetes entregados", lblEntregados));
        panel.add(crearTarjeta("Paquetes devueltos", lblDevueltos));
        panel.add(crearTarjeta("Paquetes en proceso", lblEnProceso));
        panel.add(crearTarjeta("Paquetes pendientes", lblPendientes));
        panel.add(crearTarjeta("Tiempo promedio", lblTiempoPromedio));

        return panel;
    }

    private JPanel crearTarjeta(String titulo, JLabel valor) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(TemaUI.FONDO);
        panel.setBorder(TemaUI.bordePanel());

        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setFont(TemaUI.NORMAL);

        panel.add(etiqueta, BorderLayout.NORTH);
        panel.add(valor, BorderLayout.CENTER);

        return panel;
    }

    private JLabel crearValor(String texto) {
        JLabel etiqueta = new JLabel(texto);
        etiqueta.setHorizontalAlignment(SwingConstants.CENTER);
        etiqueta.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 25));
        etiqueta.setForeground(TemaUI.AZUL_OSCURO);
        return etiqueta;
    }

    public void actualizarDatos(
            int generados,
            int entregados,
            int devueltos,
            int enProceso
    ) {
        lblGenerados.setText(String.valueOf(generados));
        lblEntregados.setText(String.valueOf(entregados));
        lblDevueltos.setText(String.valueOf(devueltos));
        lblEnProceso.setText(String.valueOf(enProceso));
    }

    public void limpiar() {
        actualizarDatos(0, 0, 0, 0);
        lblPendientes.setText("0");
        lblTiempoPromedio.setText("0.0 s");
    }

}
