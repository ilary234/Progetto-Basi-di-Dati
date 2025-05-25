package view.api;

import java.awt.Font;

import javax.swing.JLabel;

public class GenericLabel{

    private static final String FONT_FAMILY = "Roboto";

    /**
     * Returns a new label with specific characteristics.
     * @param text the label text.
     * @return a new label.
     */
    static public JLabel getGenericLabel(final String text, final int fontSize) {
        final var label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setFont(new Font(FONT_FAMILY, Font.BOLD, fontSize));
        return label;
    }

}
