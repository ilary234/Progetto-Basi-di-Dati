package view.amministratore.dipendenti;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.amministratore.ControllerAmm;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import view.amministratore.api.WorkPanel;
import view.amministratore.dipendenti.autisti.AutistiPanel;
import view.amministratore.dipendenti.impiegati.ImpiegatiPanel;
import view.api.GenericButton;

public class EmployeePanel implements WorkPanel{

    private static final String PANEL_NAME = "Employee";
    private static final int BUTTON_SIZE = 15;

    private final JPanel mainPanel;
    private final JPanel dataPanel;
    private final JButton impiegati;
    private final JButton autisti;
    private final CardLayout cardLayout;
    private final ControllerAmm controller;

    public EmployeePanel(final ControllerAmm controller){
        this.controller = controller;
        this.cardLayout = new CardLayout();
        this.dataPanel =  new JPanel(cardLayout);
        this.changeDataPanel(new ImpiegatiPanel(controller));

        var buttonsPanel = new JPanel(new BorderLayout());
        var buttons = new JPanel(new GridLayout(1, 2));
        this.impiegati = GenericButton.getGenericButton("Impiegati", BUTTON_SIZE, "Impiegati");
        this.autisti = GenericButton.getGenericButton("Autisti", BUTTON_SIZE, "Autisti");
        var actionListener = new ButtonListener();
        impiegati.addActionListener(actionListener);
        autisti.addActionListener(actionListener);
        buttons.add(impiegati);
        buttons.add(autisti);
        buttonsPanel.add(buttons, BorderLayout.WEST);
        GenericButton.setBackgroundVisible(impiegati, Color.LIGHT_GRAY, true);

        this.mainPanel = new JPanel(new BorderLayout());
        this.mainPanel.add(dataPanel, BorderLayout.CENTER);
        this.mainPanel.add(buttonsPanel, BorderLayout.NORTH);
    }

    public final void changeDataPanel(final WorkPanel panel) {
        this.dataPanel.add(panel.getPanel(), panel.getName());
        this.cardLayout.show(this.dataPanel, panel.getName());
    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public String getName() {
        return PANEL_NAME;
    }

    private class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var button = (JButton) e.getSource();
            switch (button.getActionCommand()) {
                case "Impiegati":
                    GenericButton.setBackgroundVisible(impiegati, Color.LIGHT_GRAY, true);
                    GenericButton.setBackgroundVisible(autisti, Color.LIGHT_GRAY, false);
                    changeDataPanel(new ImpiegatiPanel(controller));
                    break;
                case "Autisti":
                    GenericButton.setBackgroundVisible(impiegati, Color.LIGHT_GRAY, false);
                    GenericButton.setBackgroundVisible(autisti, Color.LIGHT_GRAY, true);
                    changeDataPanel(new AutistiPanel(controller));
                    break;
                default:
                    break;
            }
        }

    }

}
