/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab9_programacion2;

/**
 *
 * @author denam
 */

 import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;

public final class TemaUI {

    private TemaUI() {
    }

    public static final Color FONDO = new Color(245, 247, 250);
    public static final Color BLANCO = Color.WHITE;
    public static final Color AZUL = new Color(35, 93, 160);
    public static final Color AZUL_OSCURO = new Color(25, 65, 115);
    public static final Color VERDE = new Color(39, 125, 85);
    public static final Color NARANJA = new Color(220, 130, 35);
    public static final Color ROJO = new Color(190, 60, 60);
    public static final Color GRIS_TEXTO = new Color(85, 91, 100);
    public static final Color BORDE = new Color(215, 220, 228);

    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 24);
    public static final Font SUBTITULO = new Font("SansSerif", Font.BOLD, 15);
    public static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font PEQUENA = new Font("SansSerif", Font.PLAIN, 11);

    public static Border bordePanel() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
    }

    public static void configurarComponente(JComponent componente) {
        componente.setFont(NORMAL);
    }
}
