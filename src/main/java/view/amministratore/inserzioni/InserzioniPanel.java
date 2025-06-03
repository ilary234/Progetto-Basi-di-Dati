package view.amministratore.inserzioni;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.WorkPanel;
import view.amministratore.inserzioni.annunci.AnnuncioPanel;
import view.api.GenericButton;

public class InserzioniPanel implements WorkPanel {

    private static final String PANEL_NAME = "Inserzioni";
    private static final int BUTTON_SIZE = 15;

    private final JPanel mainPanel;
    private final JPanel dataPanel;
    private final JButton annunci;
    private final JButton comunicazioni;
    private final CardLayout cardLayout;
    private final ControllerAmm controller;

    public InserzioniPanel(final ControllerAmm controller){
        this.controller = controller;
        this.cardLayout = new CardLayout();
        this.dataPanel =  new JPanel(cardLayout);
        this.changeDataPanel(new AnnuncioPanel(controller));

        var buttonsPanel = new JPanel(new BorderLayout());
        var buttons = new JPanel(new GridLayout(1, 2));
        this.annunci = GenericButton.getGenericButton("Annunci", BUTTON_SIZE, "Annunci");
        this.comunicazioni = GenericButton.getGenericButton("Comunicazioni", BUTTON_SIZE, "Comunicazioni");
        var actionListener = new ButtonListener();
        annunci.addActionListener(actionListener);
        comunicazioni.addActionListener(actionListener);
        buttons.add(annunci);
        buttons.add(comunicazioni);
        buttonsPanel.add(buttons, BorderLayout.WEST);
        GenericButton.setBackgroundVisible(annunci, Color.LIGHT_GRAY, true);

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
                case "Annunci":
                    GenericButton.setBackgroundVisible(annunci, Color.LIGHT_GRAY, true);
                    GenericButton.setBackgroundVisible(comunicazioni, Color.LIGHT_GRAY, false);
                    changeDataPanel(new AnnuncioPanel(controller));
                    break;
                case "Comunicazioni":
                    GenericButton.setBackgroundVisible(annunci, Color.LIGHT_GRAY, false);
                    GenericButton.setBackgroundVisible(comunicazioni, Color.LIGHT_GRAY, true);
                    //changeDataPanel(new AutistiPanel(controller));
                    break;
                default:
                    break;
            }
        }

    }

}
