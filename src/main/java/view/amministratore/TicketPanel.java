package view.amministratore;

import javax.swing.JLabel;
import javax.swing.JPanel;

import view.amministratore.api.WorkPanel;

public class TicketPanel implements WorkPanel{

    private static final String PANEL_NAME = "Ticket";

    private final JPanel mainPanel;

    public TicketPanel(){

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
