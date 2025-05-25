package view.api;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class GenericButton{

    private static final int COLOR_BUTTONS_PANEL = 0x30A4CF;
    private static final int R_BORDER = 0;
    private static final int G_BORDER = 0;
    private static final int B_BORDER = 0;
    private static final int A_BORDER = 50;
    private static final int FONT_SIZE = 15;
    private static final String FONT_FAMILY = "Roboto";
    private static final int THICKNESS = 2;

    /**
     * Constructs a GenericButton with the specified text.
     * Initializes the button with specific styles.
     * @param text the text to be displayed on the button.
     */
    static public JButton getGenericButton(final String text) { 
        final var button = new JButton(text);
        button.setBackground(new Color(COLOR_BUTTONS_PANEL));
        button.setFont(new Font(FONT_FAMILY, Font.BOLD, FONT_SIZE));
        //button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(), 
        //    BorderFactory.createLineBorder(new Color(R_BORDER, G_BORDER, B_BORDER, A_BORDER), THICKNESS, true)));
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        return button;
    }

}
