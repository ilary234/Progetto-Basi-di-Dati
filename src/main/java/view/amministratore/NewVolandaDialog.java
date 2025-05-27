package view.amministratore;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.amministratore.ControllerAmm;
import view.api.GenericButton;
import view.api.GenericLabel;

public class NewVolandaDialog extends JDialog{

    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public NewVolandaDialog(final ControllerAmm controller, final DailyPanel dailyPanel) {
        super((Window) controller.getFrame());
        var firstPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        var codiceLabel = GenericLabel.getGenericLabel("Servizio*: ", TEXT_SIZE);
        var fornitoreLabel = GenericLabel.getGenericLabel("Fornitore: ", TEXT_SIZE);
        var prezzoLabel = GenericLabel.getGenericLabel("Prezzo*: ", TEXT_SIZE);
        var kmLabel = GenericLabel.getGenericLabel("Km*: ", TEXT_SIZE);
        var codiceText = new JComboBox<>(controller.getServiziCodes().toArray());
        var fornitoreText = new JTextField();
        var prezzoText = new JTextField();
        var kmText = new JTextField();

        firstPanel.add(codiceLabel);
        firstPanel.add(codiceText);
        firstPanel.add(fornitoreLabel);
        firstPanel.add(fornitoreText);
        firstPanel.add(prezzoLabel);
        firstPanel.add(prezzoText);
        firstPanel.add(kmLabel);
        firstPanel.add(kmText);

        var secondPanel = new JPanel();
        var noteLabel = GenericLabel.getGenericLabel("Note*: ", TEXT_SIZE);
        var noteText = new JTextArea(10, 20);
        var scrollPane = new JScrollPane(noteText);
        secondPanel.add(noteLabel);
        secondPanel.add(scrollPane);

        var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
        GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
        aggiungi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var codice = (int) codiceText.getSelectedItem();
                var fornitore = fornitoreText.getText();
                try {
                    var prezzo = (float) Integer.parseInt(prezzoText.getText());
                    var km = Integer.parseInt(kmText.getText());
                    var note = noteText.getText();
                    if (prezzo > 0 && km > 0 && !note.isBlank()) {
                        dailyPanel.addVolanda(codice, note, fornitore, prezzo, km);
                        NewVolandaDialog.this.setVisible(false);
                        NewVolandaDialog.this.dispose();
                    }
                } catch (Exception error) {
                    prezzoText.setText("");
                    kmText.setText("");
                    error.printStackTrace();
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
