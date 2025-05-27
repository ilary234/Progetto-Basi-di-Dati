package view.utente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.utente.ControllerUtente;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.Scene;
import view.api.WrapLayout;

public class HomeScene implements Scene {

    private static final String SCENE_NAME = "Home";
    private static final int TEXT_SIZE = 15;
    private static final int TITLE_SIZE = 25;
    private static final int COLOR_BUTTONS_PANEL = 0x30A4CF;

    private final JPanel mainPanel;
    private final ControllerUtente controller;
    private final List<String> titoliAnnunci;
    private final JPanel titlesPanel = new JPanel();

    public HomeScene(final ControllerUtente controller) {
        this.controller = controller;
        this.mainPanel = new JPanel(new BorderLayout());
        this.titoliAnnunci = new ArrayList<>();

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.CYAN);
        JLabel title = GenericLabel.getGenericLabel("MP Bus", TITLE_SIZE);
        titlePanel.add(title);
        mainPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JPanel utentePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel utenteLabel = GenericLabel.getGenericLabel("carrello + login", TEXT_SIZE);
        utentePanel.add(utenteLabel);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(new Color(COLOR_BUTTONS_PANEL));
        JButton homeButton = GenericButton.getGenericButton("Home");
        JButton lineeButton = GenericButton.getGenericButton("Linee");
        JButton escursioniButton = GenericButton.getGenericButton("Escursioni");
        JButton concertiButton = GenericButton.getGenericButton("Concerti");
        buttonsPanel.add(homeButton);
        buttonsPanel.add(lineeButton);
        buttonsPanel.add(escursioniButton);
        buttonsPanel.add(concertiButton);

        this.titlesPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        this.titlesPanel.setOpaque(false);

        createAnnunciServizi();

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(titlesPanel, BorderLayout.CENTER);

        JScrollPane annunciScrollPane = new JScrollPane(containerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        annunciScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        annunciScrollPane.setBorder(null);
        annunciScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        annunciScrollPane.getViewport().setBackground(Color.WHITE);

        annunciScrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = annunciScrollPane.getViewport().getWidth();
                titlesPanel.setPreferredSize(new Dimension(width, titlesPanel.getPreferredSize().height));
                titlesPanel.revalidate();
                titlesPanel.repaint();
            }
        });

        annunciScrollPane.setPreferredSize(new Dimension(Short.MAX_VALUE, 400));
        annunciScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        center.add(utentePanel);
        center.add(buttonsPanel);
        center.add(Box.createRigidArea(new Dimension(0, 10)));
        center.add(annunciScrollPane);

        mainPanel.add(center, BorderLayout.CENTER);
    }

    private void createAnnunciServizi() {
        this.titlesPanel.removeAll();
        this.titoliAnnunci.clear();
        this.titoliAnnunci.addAll(this.controller.getTitoliAnnunci());

        this.titoliAnnunci.forEach(titolo -> {
            JPanel titlePanel = new JPanel();
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            JButton button = GenericButton.getGenericButton("ANNUNCIO");
            JLabel label = GenericLabel.getGenericLabel(titolo, TEXT_SIZE);
            titlePanel.add(button);
            titlePanel.add(label);
            this.titlesPanel.add(titlePanel);
        });

        this.titlesPanel.revalidate();
        this.titlesPanel.repaint();
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
