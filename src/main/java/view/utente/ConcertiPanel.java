package view.utente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
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

public class ConcertiPanel implements WorkPanel {

    private static final String PANEL_NAME = "Concerti";
    private static final int TEXT_SIZE = 15;
    private static final Color SELECTED_COLOR = new Color(0x99C2EA);
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JPanel titlesPanel = new JPanel();
    private final ControllerUtente controller;
    private int imageSize = 120; 
    private int dynamicFontSize = TEXT_SIZE;
    private UserScene userScene;

    public ConcertiPanel(ControllerUtente controller, UserScene userScene) {
        this.controller = controller;
        this.userScene = userScene;

        this.titlesPanel.setLayout(new WrapLayout(FlowLayout.LEFT, 20, 20));
        this.titlesPanel.setOpaque(false);
        
        JScrollPane annunciScrollPane = new JScrollPane(titlesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        annunciScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        annunciScrollPane.setBorder(null);
        annunciScrollPane.getViewport().setBackground(Color.WHITE); //si può omettere

        annunciScrollPane.getViewport().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = annunciScrollPane.getViewport().getWidth();
                imageSize = Math.max(60, width / 8); //numeri a caso
                dynamicFontSize = Math.max(10, width / 60); //numeri a caso (se non va bene si può togliere)
                updateConcerti();
            }
        });

        annunciScrollPane.setPreferredSize(new Dimension(Short.MAX_VALUE, 400));
        annunciScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(annunciScrollPane, BorderLayout.CENTER);

        updateConcerti();
    }

    public void updateConcerti() {
        this.titlesPanel.removeAll();
        Map<Integer, String> linee = controller.getConcerti();

        linee.entrySet().forEach(concerto -> {
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.WHITE);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

            ImageIcon icon = new ImageIcon(getClass().getResource("img/concerti.png"));
            Image scaledImage = icon.getImage().getScaledInstance(imageSize, imageSize, java.awt.Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JButton button = GenericButton.getGenericButton(scaledIcon, dynamicFontSize, "CONCERTO");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel codiceLabel = GenericLabel.getGenericLabel(String.valueOf(concerto.getKey()), dynamicFontSize);
            codiceLabel.setVisible(false);
            codiceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel categoriaLabel = GenericLabel.getGenericLabel(concerto.getValue(), dynamicFontSize);
            categoriaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            int codiceServizio = concerto.getKey();
            button.addActionListener(e -> {
                InfoPanel infoPanel = new InfoPanel(this.controller, this.userScene, scaledIcon, String.valueOf(codiceServizio));
                GenericButton.setBackgroundVisible(userScene.getSelectedButton(), SELECTED_COLOR, false);
                userScene.changeWorkPanel(infoPanel);
            });

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
