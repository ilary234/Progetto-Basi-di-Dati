package view.utente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.WrapLayout;

public class HomePanel implements WorkPanel{

    private static final String PANEL_NAME = "Home";
    private static final int TEXT_SIZE = 15;
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel titlesPanel = new JPanel();
    private final ControllerUtente controller;
    private final List<String> titoliAnnunci;

    public HomePanel(ControllerUtente controller) {

        this.controller = controller;
        titlesPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        this.titoliAnnunci = new ArrayList<>();

        this.titlesPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        this.titlesPanel.setOpaque(false);

        updateAnnunci();

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(titlesPanel, BorderLayout.CENTER);
        
        JScrollPane annunciScrollPane = new JScrollPane(titlesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        annunciScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        annunciScrollPane.setBorder(null);
        annunciScrollPane.getViewport().setBackground(Color.WHITE); //si può omettere

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

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(annunciScrollPane, BorderLayout.CENTER);
    }

    public void updateAnnunci() {
        this.titlesPanel.removeAll();
        this.titoliAnnunci.clear();
        this.titoliAnnunci.addAll(this.controller.getTitoliAnnunci());

        this.titoliAnnunci.forEach(titolo -> {
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.WHITE);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            JButton button = GenericButton.getGenericButton("ANNUNCIO", TEXT_SIZE, "ANNUNCIO");
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
    public String getName() {
        return PANEL_NAME;
    }
    
}
