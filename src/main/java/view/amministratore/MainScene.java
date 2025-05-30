package view.amministratore;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.WorkPanel;
import view.amministratore.giornaliere.DailyPanel;
import view.amministratore.servizi.ServicePanel;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.Scene;

public class MainScene implements Scene{

    private static final String SCENE_NAME = "Main";
    private static final int BUTTON_SIZE = 15;
    private static final int TITLE_SIZE = 25;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final Color SELECTED_COLOR = new Color(0x99C2EA);

    private final ControllerAmm controller;
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final JPanel workSpace;
    private JButton selected;
    
    public MainScene(final ControllerAmm controller) {
        this.controller = controller;

        this.mainPanel = new JPanel(new BorderLayout(0, 5));
        var titlePanel = new JPanel();
        titlePanel.setBackground(new Color(BACKGROUND_COLOR));
        var titolo = GenericLabel.getGenericLabel("MP Bus", TITLE_SIZE);
        titlePanel.add(titolo);

        var menuPanel = new JPanel();
        menuPanel.setBackground(new Color(BACKGROUND_COLOR));
        var pagesPanel = new JPanel(new GridLayout(5, 1));
        pagesPanel.setBackground(new Color(BACKGROUND_COLOR));
        var actionListener = new MenuActionListener();
        var dipendenti = GenericButton.getGenericButton("Dipendenti", BUTTON_SIZE, "Dipendenti");
        var giornaliere = GenericButton.getGenericButton("Giornaliere", BUTTON_SIZE, "Giornaliere");
        var mezzi = GenericButton.getGenericButton("Mezzi", BUTTON_SIZE, "Mezzi");
        var servizi = GenericButton.getGenericButton("Servizi", BUTTON_SIZE, "Servizi");
        var biglietti = GenericButton.getGenericButton("Biglietti", BUTTON_SIZE, "Biglietti");
        dipendenti.addActionListener(actionListener);
        giornaliere.addActionListener(actionListener);
        mezzi.addActionListener(actionListener);
        servizi.addActionListener(actionListener);
        biglietti.addActionListener(actionListener);

        pagesPanel.add(giornaliere);
        pagesPanel.add(servizi);
        pagesPanel.add(dipendenti);
        pagesPanel.add(mezzi);
        pagesPanel.add(biglietti);
        menuPanel.add(pagesPanel);

        this.cardLayout = new CardLayout();
        this.workSpace =  new JPanel(cardLayout);
        this.changeWorkPanel(new DailyPanel(this.controller));
        this.selected = giornaliere;
        GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);
        
        this.mainPanel.add(titlePanel, BorderLayout.NORTH);
        this.mainPanel.add(menuPanel, BorderLayout.WEST);
        this.mainPanel.add(workSpace, BorderLayout.CENTER);


    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public String getSceneName() {
        return SCENE_NAME;
    }

    public final void changeWorkPanel(final WorkPanel panel) {
        this.workSpace.add(panel.getPanel(), panel.getName());
        this.cardLayout.show(this.workSpace, panel.getName());
    }

    private class MenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            var button = (JButton) e.getSource();
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, false);
            selected = button;
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);
            changeWorkPanel(switch(selected.getActionCommand()){
                case "Giornaliere" -> new DailyPanel(controller);
                case "Servizi" -> new ServicePanel(controller);
                case "Dipendenti" -> new EmployeePanel();
                case "Mezzi" -> new TransportPanel();
                case "Biglietti" -> new TicketPanel();
                default -> new DailyPanel(controller);
            });
        }

    }

}
