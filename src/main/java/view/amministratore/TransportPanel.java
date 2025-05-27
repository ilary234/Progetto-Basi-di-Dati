package view.amministratore;

import javax.swing.JLabel;
import javax.swing.JPanel;

import view.amministratore.api.WorkPanel;

public class TransportPanel implements WorkPanel{

    private static final String PANEL_NAME = "Transport";

    private final JPanel mainPanel;

    public TransportPanel(){

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
