package view.amministratore;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.amministratore.ControllerAmm;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.Scene;

public class DailyScene implements Scene{

    private static final String SCENE_NAME = "Daily";
    private static final int TEXT_SIZE = 12;
    private static final int TITLE_SIZE = 25;
    private static final int BACKGROUND_COLOR = 0x30A4CF;

    private final ControllerAmm controller;
    private final List<String> giornaliere;
    private final JPanel mainPanel;
    private final JPanel work;
    
    public DailyScene(final ControllerAmm controller) {
        this.controller = controller;
        this.giornaliere = new ArrayList<>();

        this.mainPanel = new JPanel(new BorderLayout(0, 5));
        var titlePanel = new JPanel();
        titlePanel.setBackground(new Color(BACKGROUND_COLOR));
        var titolo = GenericLabel.getGenericLabel("Giornaliere", TITLE_SIZE);
        titlePanel.add(titolo);

        var menuPanel = new JPanel();
        menuPanel.setBackground(new Color(BACKGROUND_COLOR));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        var autisti = GenericButton.getGenericButton("Autisti");
        var mezzi = GenericButton.getGenericButton("Mezzi");
        var servizi = GenericButton.getGenericButton("Servizi");
        var biglietti = GenericButton.getGenericButton("Biglietti");
        biglietti.setBorderPainted(false);
        menuPanel.add(servizi);
        menuPanel.add(autisti);
        menuPanel.add(mezzi);
        menuPanel.add(biglietti);

        this.work = new JPanel(new BorderLayout());
        var buttonsPanel = new JPanel(new BorderLayout());
        var createButton = GenericButton.getGenericButton("Nuova +");
        buttonsPanel.add(createButton, BorderLayout.EAST);
        this.createGiornaliere();
        work.add(buttonsPanel, BorderLayout.NORTH);
        
        this.mainPanel.add(titlePanel, BorderLayout.NORTH);
        this.mainPanel.add(menuPanel, BorderLayout.WEST);
        this.mainPanel.add(work, BorderLayout.CENTER);


    }

    private void createGiornaliere() {
        var listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        this.giornaliere.clear();
        this.giornaliere.addAll(this.controller.getGiornaliere());
        this.giornaliere.forEach(g -> {
            var date = GenericLabel.getGenericLabel("-" + g, TEXT_SIZE);
            var see = GenericButton.getGenericButton("+");
            var dayPanel = new JPanel();
            dayPanel.add(date);
            dayPanel.add(see);
            listPanel.add(dayPanel); 
        });
        this.work.add(listPanel, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public String getSceneName() {
        return SCENE_NAME;
    }


}
