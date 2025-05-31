package view.utente;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.utente.ControllerUtente;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.Scene;

public class UserScene implements Scene {

    private static final String SCENE_NAME = "User";
    private static final int TEXT_SIZE = 15;
    private static final int TITLE_SIZE = 25;
    private static final int COLOR_BUTTONS_PANEL = 0x30A4CF;
    private static final Color SELECTED_COLOR = new Color(0x99C2EA);

    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final JPanel workSpace;
    private JButton selected;
    private final ControllerUtente controller;
    private JButton homeButton;


    public UserScene(final ControllerUtente controller) {

        this.controller = controller;
        this.mainPanel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel title = GenericLabel.getGenericLabel("MP Bus", TITLE_SIZE);
        title.setForeground(new Color(COLOR_BUTTONS_PANEL));
        titlePanel.add(title);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(new Color(COLOR_BUTTONS_PANEL));
        var actionListener = new MenuActionListener();

        homeButton = GenericButton.getGenericButton("Home", TEXT_SIZE, "Home");
        homeButton.setForeground(Color.WHITE);
        JButton lineeButton = GenericButton.getGenericButton("Linee", TEXT_SIZE, "Linee");
        lineeButton.setForeground(Color.WHITE);
        JButton escursioniButton = GenericButton.getGenericButton("Escursioni", TEXT_SIZE, "Escursioni");
        escursioniButton.setForeground(Color.WHITE);
        JButton concertiButton = GenericButton.getGenericButton("Concerti", TEXT_SIZE, "Concerti");
        concertiButton.setForeground(Color.WHITE);
        JButton giroPanoramicoButton = GenericButton.getGenericButton("Giro Panoramico", TEXT_SIZE, "Giro Panoramico");
        giroPanoramicoButton.setForeground(Color.WHITE);

        homeButton.addActionListener(actionListener);
        lineeButton.addActionListener(actionListener);
        escursioniButton.addActionListener(actionListener);
        concertiButton.addActionListener(actionListener);
        giroPanoramicoButton.addActionListener(actionListener);

        buttonsPanel.add(homeButton);
        buttonsPanel.add(lineeButton);
        buttonsPanel.add(giroPanoramicoButton);
        buttonsPanel.add(escursioniButton);
        buttonsPanel.add(concertiButton);

        this.cardLayout = new CardLayout();
        this.workSpace = new JPanel(cardLayout);

        this.changeWorkPanel(new HomePanel(this.controller, this));
        this.selected = homeButton;
        GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(buttonsPanel);
        centerPanel.add(workSpace);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
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

    public JButton getSelectedButton() {
        return selected;
    }

    public JButton getHomeButton() {
        return this.homeButton;
    }

    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            var button = (JButton) e.getSource();
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, false);
            selected = button;
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);
            changeWorkPanel(switch(selected.getActionCommand()){
                case "Home" -> new HomePanel(controller, UserScene.this);
                case "Linee" -> new LineePanel(controller, UserScene.this);
                case "Escursioni" -> new EscursioniPanel(controller, UserScene.this);
                case "Concerti" -> new ConcertiPanel(controller, UserScene.this);
                case "Giro Panoramico" -> new GiroPanoramicoPanel(controller, UserScene.this);
                default -> new HomePanel(controller, UserScene.this);
            });
        }
    }
}
