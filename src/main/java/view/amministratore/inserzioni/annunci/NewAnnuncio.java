package view.amministratore.inserzioni.annunci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.MaxLenghtDocumentFilter;
import view.api.GenericButton;
import view.api.GenericLabel;

public class NewAnnuncio extends JDialog {

    private static final int HEIGHT = 400;
    private static final int WIDTH = 800;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public NewAnnuncio(final ControllerAmm controller) {
        super((Window) controller.getFrame());
        var firstPanel = new JPanel(new GridLayout(5, 2, 10, 15));
        var codServizio = GenericLabel.getGenericLabel("Servizio*: ", TEXT_SIZE);
        var titolo = GenericLabel.getGenericLabel("Titolo*: ", TEXT_SIZE);
        var descrizione = GenericLabel.getGenericLabel("Descrizione*: ", TEXT_SIZE);
        var prezzoBase = GenericLabel.getGenericLabel("Prezzo Base*: ", TEXT_SIZE);
        var visibile = GenericLabel.getGenericLabel("Visibile*: ", TEXT_SIZE);
        var bigliettiDisponibili = GenericLabel.getGenericLabel("Biglietti Disponibili*: ", TEXT_SIZE);

        var codServizioBox = new JComboBox<>(controller.getServiziWithoutAnnuncioCodes().toArray());
        var titoloText = new JTextField();
        var descrizioneText = new JTextArea(10, 20);
        var prezzoBaseText = new JTextField();
        var visibileBox = new JCheckBox();
        var bigliettiDisponibiliText = new JTextField();
        ((AbstractDocument) titoloText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));

        firstPanel.add(codServizio);
        firstPanel.add(codServizioBox);
        firstPanel.add(titolo);
        firstPanel.add(titoloText);
        firstPanel.add(prezzoBase);
        firstPanel.add(prezzoBaseText);
        firstPanel.add(visibile);
        firstPanel.add(visibileBox);
        firstPanel.add(bigliettiDisponibili);
        firstPanel.add(bigliettiDisponibiliText);

        var secondPanel = new JPanel();
        var scrollPane = new JScrollPane(descrizioneText);
        secondPanel.add(descrizione);
        secondPanel.add(scrollPane);

        var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
        GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
        aggiungi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var codServizio = (int) codServizioBox.getSelectedItem();
                    var titolo = titoloText.getText();
                    var prezzoBase = Float.parseFloat(prezzoBaseText.getText());
                    var visibile = visibileBox.isSelected();
                    var bigliettiDisponibili = Integer.parseInt(bigliettiDisponibiliText.getText());
                    var descrizione = descrizioneText.getText();
                    if (!titolo.isBlank() && prezzoBase > 0 && !descrizione.isBlank()) {
                        controller.addAnnuncio(codServizio, titolo, prezzoBase, visibile, bigliettiDisponibili, descrizione);
                        NewAnnuncio.this.setVisible(false);
                        NewAnnuncio.this.dispose();
                    }
                } catch (Exception e1) {
                    prezzoBaseText.setText("");
                    bigliettiDisponibiliText.setText("");
                }
            }
            
        });

        var dataPanel = new JPanel();
        dataPanel.add(firstPanel);
        dataPanel.add(secondPanel);

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
