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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogoNuevoPaquete extends JDialog {

    private final JTextField txtCodigo;
    private final JTextField txtCliente;
    private final JTextField txtDireccion;
    private final JTextField txtPeso;

    private final JComboBox<String> comboCiudad;
    private final JComboBox<String> comboPrioridad;

    private boolean paqueteCreado;

    public DialogoNuevoPaquete(JFrame padre) {
        super(
                padre,
                "Registrar nuevo paquete",
                true
        );

        txtCodigo = new JTextField();
        txtCliente = new JTextField();
        txtDireccion = new JTextField();
        txtPeso = new JTextField();

        comboCiudad = new JComboBox<>(
                new String[]{
                        "Seleccione una ciudad",
                        "Tegucigalpa",
                        "San Pedro Sula",
                        "Tela",
                        "Guatemala"
                }
        );

        comboPrioridad = new JComboBox<>(
                new String[]{
                        "URGENTE",
                        "ALTA",
                        "NORMAL",
                        "BAJA"
                }
        );

        paqueteCreado = false;

        construirInterfaz();

        setSize(480, 420);
        setLocationRelativeTo(padre);
    }

    private void construirInterfaz() {
        JPanel contenido = new JPanel(
                new BorderLayout(10, 10)
        );

        contenido.setBorder(
                javax.swing.BorderFactory
                        .createEmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
        );

        JLabel titulo = new JLabel(
                "Información del paquete"
        );

        titulo.setFont(TemaUI.SUBTITULO);
        titulo.setForeground(TemaUI.AZUL_OSCURO);

        contenido.add(
                titulo,
                BorderLayout.NORTH
        );

        contenido.add(
                crearFormulario(),
                BorderLayout.CENTER
        );

        contenido.add(
                crearBotones(),
                BorderLayout.SOUTH
        );

        setContentPane(contenido);
    }

    private JPanel crearFormulario() {
        JPanel formulario = new JPanel(
                new GridBagLayout()
        );

        agregarCampo(
                formulario,
                "Código:",
                txtCodigo,
                0
        );

        agregarCampo(
                formulario,
                "Cliente:",
                txtCliente,
                1
        );

        agregarCampo(
                formulario,
                "Dirección:",
                txtDireccion,
                2
        );

        agregarCampo(
                formulario,
                "Ciudad:",
                comboCiudad,
                3
        );

        agregarCampo(
                formulario,
                "Peso (kg):",
                txtPeso,
                4
        );

        agregarCampo(
                formulario,
                "Prioridad:",
                comboPrioridad,
                5
        );

        return formulario;
    }

    private void agregarCampo(
            JPanel panel,
            String etiqueta,
            java.awt.Component componente,
            int fila
    ) {
        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets = new Insets(
                6,
                6,
                6,
                6
        );

        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        panel.add(
                new JLabel(etiqueta),
                gbc
        );

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(
                componente,
                gbc
        );
    }

    private JPanel crearBotones() {
        JPanel panel = new JPanel();

        JButton btnGuardar = new JButton(
                "Guardar"
        );

        JButton btnCancelar = new JButton(
                "Cancelar"
        );

        btnGuardar.addActionListener(
                e -> guardarPaquete()
        );

        btnCancelar.addActionListener(
                e -> dispose()
        );

        panel.add(btnGuardar);
        panel.add(btnCancelar);

        return panel;
    }

    private void guardarPaquete() {
        if (txtCodigo.getText().trim().isEmpty()
                || txtCliente.getText().trim().isEmpty()
                || txtDireccion.getText().trim().isEmpty()
                || txtPeso.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Completa los campos obligatorios.",
                    "Datos incompletos",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (comboCiudad.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Selecciona una ciudad.",
                    "Ciudad requerida",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {
            double peso = Double.parseDouble(
                    txtPeso.getText().trim()
            );

            if (peso <= 0) {
                throw new NumberFormatException();
            }

            paqueteCreado = true;
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "El peso debe ser un número mayor que cero.",
                    "Peso inválido",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public boolean isPaqueteCreado() {
        return paqueteCreado;
    }

    public String getCodigo() {
        return txtCodigo.getText().trim();
    }

    public String getCliente() {
        return txtCliente.getText().trim();
    }

    public String getDireccion() {
        return txtDireccion.getText().trim();
    }

    public String getCiudad() {
        return comboCiudad
                .getSelectedItem()
                .toString();
    }

    public double getPeso() {
        return Double.parseDouble(
                txtPeso.getText().trim()
        );
    }

    public String getPrioridad() {
        return comboPrioridad
                .getSelectedItem()
                .toString();
    }
}
