package view.utente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.utente.ControllerUtente;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.WrapLayout;

public class GiroPanoramicoPanel implements WorkPanel {
    
    private static final String PANEL_NAME = "Giro Panoramico";
    private static final int TEXT_SIZE = 15;
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel titlesPanel = new JPanel();
    private final ControllerUtente controller;

    public GiroPanoramicoPanel(ControllerUtente controller) {
        
        this.controller = controller;

        this.titlesPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        this.titlesPanel.setOpaque(false);

        updateGP();

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

    public void updateGP() {
        this.titlesPanel.removeAll();
        Map<Integer, String> linee = controller.getGP();

        linee.entrySet().forEach(gp -> {
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.WHITE);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
            //ImageIcon icon = new ImageIcon(getClass().getResource("img/giusto.png"));
            //JButton button = GenericButton.getGenericButton(icon, TEXT_SIZE, "GP");
            JButton button = GenericButton.getGenericButton("GP", TEXT_SIZE, "GP");
            JLabel codiceLabel = GenericLabel.getGenericLabel(String.valueOf(gp.getKey()), TEXT_SIZE);
            codiceLabel.setVisible(false);
            JLabel categoriaLabel = GenericLabel.getGenericLabel(gp.getValue(), TEXT_SIZE);
            titlePanel.add(button);
            titlePanel.add(codiceLabel);
            titlePanel.add(categoriaLabel);
            titlesPanel.add(titlePanel);
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
