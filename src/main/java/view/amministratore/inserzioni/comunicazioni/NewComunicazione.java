package view.amministratore.inserzioni.comunicazioni;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
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

public class NewComunicazione extends JDialog {

    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public NewComunicazione(final ControllerAmm controller) {
        super((Window) controller.getFrame());
        var firstPanel = new JPanel();
        var titolo = GenericLabel.getGenericLabel("Titolo*: ", TEXT_SIZE);
        var descrizione = GenericLabel.getGenericLabel("Descrizione*: ", TEXT_SIZE);

        var titoloText = new JTextField();
        var descrizioneText = new JTextArea(10, 20);
        ((AbstractDocument) titoloText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));

        firstPanel.add(titolo);
        firstPanel.add(titoloText);

        var secondPanel = new JPanel();
        var scrollPane = new JScrollPane(descrizioneText);
        secondPanel.add(descrizione);
        secondPanel.add(scrollPane);

        var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
        GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
        aggiungi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var titolo = titoloText.getText();
                var descrizione = descrizioneText.getText();
                if (!titolo.isBlank() && !descrizione.isBlank()) {
                        controller.addComunicazione(titolo, descrizione);
                        NewComunicazione.this.setVisible(false);
                        NewComunicazione.this.dispose();
                }
            }
            
        });

        var dataPanel = new JPanel();
        dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
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
