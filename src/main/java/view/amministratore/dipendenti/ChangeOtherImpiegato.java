package view.amministratore.dipendenti;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.MaxLenghtDocumentFilter;
import view.api.GenericButton;
import view.api.GenericLabel;

public class ChangeOtherImpiegato extends JDialog {

    private static final int HEIGHT = 300;
    private static final int WIDTH = 400;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public ChangeOtherImpiegato(ControllerAmm controller, ImpiegatiPanel impiegatiPanel, String pastResidenza, String pastTelefono) {
        super((Window) controller.getFrame());
        var dataPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        var residenza = GenericLabel.getGenericLabel("Residenza*: ", TEXT_SIZE);
        var telefono = GenericLabel.getGenericLabel("Telefono*: ", TEXT_SIZE);

        var residenzaText = new JTextField(pastResidenza);
        var telefonoText = new JTextField(pastTelefono);
        ((AbstractDocument) residenzaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(15));
        ((AbstractDocument) telefonoText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(15));

        dataPanel.add(residenza);
        dataPanel.add(residenzaText);
        dataPanel.add(telefono);
        dataPanel.add(telefonoText);

        var salva = GenericButton.getGenericButton("Salva", BUTTON_SIZE, "Salva");
        GenericButton.setBackgroundVisible(salva, new Color(BACKGROUND_COLOR), true);
        salva.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var residenza = residenzaText.getText();
                var telefono = telefonoText.getText();
                if(!residenza.isBlank() && !telefono.isBlank()) {
                    if (!residenza.equals(pastResidenza)) {
                        impiegatiPanel.updateResidenza(residenza);
                    }
                    if (!telefono.equals(pastTelefono)) {
                        impiegatiPanel.updateTelefono(telefono);
                    }
                    ChangeOtherImpiegato.this.setVisible(false);
                    ChangeOtherImpiegato.this.dispose();
                };
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
