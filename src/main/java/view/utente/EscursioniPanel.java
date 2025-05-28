package view.utente;

import javax.swing.JLabel;
import javax.swing.JPanel;

import view.amministratore.api.WorkPanel;

public class EscursioniPanel implements WorkPanel {

    private static final String PANEL_NAME = "Escursioni";

    private final JPanel mainPanel;

    public EscursioniPanel() {
        this.mainPanel = new JPanel();
        var label = new JLabel(PANEL_NAME);
        mainPanel.add(label);
    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public String getName() {
        return PANEL_NAME;
    }
}
