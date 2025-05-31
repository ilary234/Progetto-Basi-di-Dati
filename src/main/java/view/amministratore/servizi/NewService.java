package view.amministratore.servizi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.MaxLenghtDocumentFilter;
import view.api.GenericButton;
import view.api.GenericLabel;

public class NewService extends JDialog{

    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public NewService(final ControllerAmm controller, final ServicePanel servicePanel) {
        super((Window) controller.getFrame());
        var dataPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        var partenza = GenericLabel.getGenericLabel("Partenza*: ", TEXT_SIZE);
        var destinazione = GenericLabel.getGenericLabel("Destinazione*: ", TEXT_SIZE);
        var orario = GenericLabel.getGenericLabel("Orario*: ", TEXT_SIZE);
        var categoria = GenericLabel.getGenericLabel("Categoria*: ", TEXT_SIZE);

        var categoriaBox = new JComboBox<>(controller.getCategorie().toArray());
        var partenzaText = new JTextField();
        var destinazioneText = new JTextField();
        ((AbstractDocument) partenzaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
        ((AbstractDocument) destinazioneText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));

        var orarioPanel = new JPanel();
        String[] ore = new String[25];
        for (int i = 0; i <= 24; i++) {
            ore[i] = String.format("%02d", i);
        }
        String[] minuti = new String[6];
        for (int i = 0; i <= 50; i += 10) {
            minuti[i/10] = String.format("%02d", i);
        }
        var oreBox = new JComboBox<>(ore);
        var minutiBox = new JComboBox<>(minuti);
        orarioPanel.add(oreBox);
        orarioPanel.add(minutiBox);

        dataPanel.add(partenza);
        dataPanel.add(partenzaText);
        dataPanel.add(destinazione);
        dataPanel.add(destinazioneText);
        dataPanel.add(orario);
        dataPanel.add(orarioPanel);
        dataPanel.add(categoria);
        dataPanel.add(categoriaBox);

        var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
        GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
        aggiungi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var partenza = partenzaText.getText();
                var destinazione = destinazioneText.getText();
                var categoria = (String) categoriaBox.getSelectedItem();
                var ora = (String) oreBox.getSelectedItem();
                var minuti = (String) minutiBox.getSelectedItem();
                var orario = ora + ":" + minuti;
                servicePanel.addServizio(partenza, destinazione, orario, categoria);
                NewService.this.setVisible(false);
                NewService.this.dispose();
            }
            
        });

        var panel = new JPanel(new GridBagLayout());
        panel.add(dataPanel);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.add(aggiungi, BorderLayout.SOUTH);
        this.setSize(WIDTH, HEIGHT);
        this.setModal(true);
        this.setLocationRelativeTo((Window) controller.getFrame());
        this.setVisible(true);
    }

}
