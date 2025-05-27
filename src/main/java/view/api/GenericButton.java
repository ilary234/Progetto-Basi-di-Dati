package view.api;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

public class GenericButton{
    
    private static final String FONT_FAMILY = "Roboto";

    /**
     * Constructs a GenericButton with the specified text.
     * Initializes the button with specific styles.
     * @param text the text to be displayed on the button.
     */
    static public JButton getGenericButton(final String text, int fontSize, String actionCommand) { 
        final var button = new JButton(text);
        button.setActionCommand(actionCommand);
        button.setFont(new Font(FONT_FAMILY, Font.BOLD, fontSize));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorderPainted(false);

        return button;
    }

    static public void setBackgroundVisible(JButton button, Color color, boolean visible) {
        button.setBackground(color);
        button.setOpaque(visible);
        button.setContentAreaFilled(visible);
    }

}
