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

public class ChangeDialog extends JDialog{

    private static final int HEIGHT = 300;
    private static final int WIDTH = 600;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public ChangeDialog(final ControllerAmm controller, final DailyPanel dailyPanel) {
        super((Window) controller.getFrame());
        var dataPanel = new JPanel(new GridLayout(3, 2, -10, 15));
        var autistaLabel = GenericLabel.getGenericLabel("Autista: ", TEXT_SIZE);
        var mezzoLabel = GenericLabel.getGenericLabel("Mezzo: ", TEXT_SIZE);
        var committenteLabel = GenericLabel.getGenericLabel("Committente: ", TEXT_SIZE);
        var autistaBox = new JComboBox<>(controller.getAutistiNames().toArray());
        var mezzoBox = new JComboBox<>(controller.getMezziNumbers().toArray());
        var committenteBox = new JComboBox<>(controller.getCommittentiNames().toArray());

        dataPanel.add(autistaLabel);
        dataPanel.add(autistaBox);
        dataPanel.add(mezzoLabel);
        dataPanel.add(mezzoBox);
        dataPanel.add(committenteLabel);
        dataPanel.add(committenteBox);

        var salva = GenericButton.getGenericButton("Salva", BUTTON_SIZE, "Salva");
        GenericButton.setBackgroundVisible(salva, new Color(BACKGROUND_COLOR), true);
        salva.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                /*var codice = (int) autistaBox.getSelectedItem();
                var fornitore = mezzoBox.getText();
                try {
                    var prezzo = (float) Integer.parseInt(committenteBox.getText());
                    var km = Integer.parseInt(kmText.getText());
                    var note = noteText.getText();
                    if (prezzo > 0 && km > 0 && !note.isBlank()) {
                        dailyPanel.addVolanda(codice, note, fornitore, prezzo, km);
                        ChangeDialog.this.setVisible(false);
                        ChangeDialog.this.dispose();
                    }
                } catch (Exception error) {
                    committenteBox.setText("");
                    kmText.setText("");
                    error.printStackTrace();
                }*/
            }
            
        });

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
