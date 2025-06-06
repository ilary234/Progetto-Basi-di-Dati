package view.amministratore.inserzioni.annunci;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
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

public class ChangeAnnuncio extends JDialog {

    private static final int HEIGHT = 400;
    private static final int WIDTH = 800;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public ChangeAnnuncio(ControllerAmm controller, int code, String pastTitolo, String pastDescrizione, 
            float pastPrezzo, boolean isVisibile, int pastbiglietti) {
        super((Window) controller.getFrame());
        var firstPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        var titolo = GenericLabel.getGenericLabel("Titolo*: ", TEXT_SIZE);
        var descrizione = GenericLabel.getGenericLabel("Descrizione*: ", TEXT_SIZE);
        var prezzoBase = GenericLabel.getGenericLabel("Prezzo Base*: ", TEXT_SIZE);
        var visibile = GenericLabel.getGenericLabel("Visibile*: ", TEXT_SIZE);
        var bigliettiDisponibili = GenericLabel.getGenericLabel("Biglietti Disponibili*: ", TEXT_SIZE);

        var titoloText = new JTextField(pastTitolo, 20);
        var descrizioneText = new JTextArea(10, 20);
        descrizioneText.setText(pastDescrizione);
        var prezzoBaseText = new JTextField(String.valueOf(pastPrezzo));
        var visibileBox = new JCheckBox();
        visibileBox.setSelected(isVisibile);
        var bigliettiDisponibiliText = new JTextField(String.valueOf(pastbiglietti));
        ((AbstractDocument) titoloText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));

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

        var salva = GenericButton.getGenericButton("Salva", BUTTON_SIZE, "Salva");
        GenericButton.setBackgroundVisible(salva, new Color(BACKGROUND_COLOR), true);
        salva.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var titolo = titoloText.getText();
                    var prezzoBase = Float.parseFloat(prezzoBaseText.getText());
                    var visibile = visibileBox.isSelected();
                    var bigliettiDisponibili = Integer.parseInt(bigliettiDisponibiliText.getText());
                    var descrizione = descrizioneText.getText();
                    if (!titolo.isBlank() && prezzoBase > 0 && !descrizione.isBlank()) {
                        controller.updateAnnuncio(code, titolo, descrizione, prezzoBase, visibile, bigliettiDisponibili);
                        ChangeAnnuncio.this.setVisible(false);
                        ChangeAnnuncio.this.dispose();
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
        this.add(salva, BorderLayout.SOUTH);
        this.setSize(WIDTH, HEIGHT);
        this.setModal(true);
        this.setLocationRelativeTo((Window) controller.getFrame());
        this.setVisible(true);
    }
}
