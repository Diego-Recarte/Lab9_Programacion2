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
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.table.DefaultTableModel;

public class PanelZona extends JPanel {

    private final String nombreZona;
    private final int capacidad;

    private final JLabel etiquetaCantidad;
    private final JProgressBar barraCapacidad;
    private final DefaultTableModel modelo;
    private final JTable tabla;

    public PanelZona(
            String nombreZona,
            int capacidad,
            String[] columnas
    ) {
        this.nombreZona = nombreZona;
        this.capacidad = capacidad;

        setLayout(new BorderLayout(5, 5));
        setBackground(TemaUI.BLANCO);
        setBorder(TemaUI.bordePanel());

        etiquetaCantidad = new JLabel();
        barraCapacidad = new JProgressBar(0, capacidad);

        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setAutoCreateRowSorter(true);
        tabla.getTableHeader().setReorderingAllowed(false);

        construirPanel();
        actualizarCapacidad();
    }

    private void construirPanel() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);

        JLabel titulo = new JLabel(nombreZona);
        titulo.setFont(TemaUI.SUBTITULO);
        titulo.setForeground(TemaUI.AZUL_OSCURO);

        etiquetaCantidad.setForeground(TemaUI.GRIS_TEXTO);

        encabezado.add(titulo, BorderLayout.WEST);
        encabezado.add(etiquetaCantidad, BorderLayout.EAST);

        barraCapacidad.setStringPainted(true);
        barraCapacidad.setForeground(TemaUI.AZUL);

        JPanel parteSuperior = new JPanel(new BorderLayout(5, 5));
        parteSuperior.setOpaque(false);
        parteSuperior.add(encabezado, BorderLayout.NORTH);
        parteSuperior.add(barraCapacidad, BorderLayout.SOUTH);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(TemaUI.BORDE));

        add(parteSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void agregarFila(Object... datos) {
        modelo.addRow(datos);
        actualizarCapacidad();
    }

    public void eliminarFila(int fila) {
        if (fila >= 0 && fila < modelo.getRowCount()) {
            modelo.removeRow(fila);
            actualizarCapacidad();
        }
    }

    public void limpiar() {
        modelo.setRowCount(0);
        actualizarCapacidad();
    }

    public void actualizarFila(int fila, int columna, Object valor) {
        if (fila >= 0 && fila < modelo.getRowCount()) {
            modelo.setValueAt(valor, fila, columna);
        }
    }

    public void actualizarCapacidad() {
        int cantidad = modelo.getRowCount();

        etiquetaCantidad.setText(
                "Paquetes: " + cantidad + " / " + capacidad
        );

        barraCapacidad.setValue(Math.min(cantidad, capacidad));
        barraCapacidad.setString(cantidad + " / " + capacidad);

        if (cantidad >= capacidad) {
            barraCapacidad.setForeground(TemaUI.ROJO);
        } else if (cantidad >= capacidad * 0.75) {
            barraCapacidad.setForeground(TemaUI.NARANJA);
        } else {
            barraCapacidad.setForeground(TemaUI.VERDE);
        }
    }

    public int getCantidad() {
        return modelo.getRowCount();
    }

    public DefaultTableModel getModelo() {
        return modelo;
    }

    public JTable getTabla() {
        return tabla;
    }

}
