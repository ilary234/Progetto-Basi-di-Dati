package view.amministratore.dipendenti.autisti;

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

import com.toedter.calendar.JDateChooser;

import controller.amministratore.ControllerAmm;
import view.api.GenericButton;
import view.api.GenericLabel;

public class AddKB extends JDialog {

    private static final int HEIGHT = 200;
    private static final int WIDTH = 300;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public AddKB(ControllerAmm controller, String cf) {
        super((Window) controller.getFrame());
        var dataPanel = new JPanel(new GridLayout(2, 2, 10, 15));
        var numero = GenericLabel.getGenericLabel("Numero KB*: ", TEXT_SIZE);
        var scadenza = GenericLabel.getGenericLabel("Scadenza KB*: ", TEXT_SIZE);

        var numeroText = new JTextField();
        var scadenzaBox = new JDateChooser();

        dataPanel.add(numero);
        dataPanel.add(numeroText);
        dataPanel.add(scadenza);
        dataPanel.add(scadenzaBox);

        var salva = GenericButton.getGenericButton("Salva", BUTTON_SIZE, "Salva");
        GenericButton.setBackgroundVisible(salva, new Color(BACKGROUND_COLOR), true);
        salva.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var numero = Integer.parseInt(numeroText.getText());
                var scadenza = scadenzaBox.getDate();
                if(numero > 0) {
                    controller.addKB(numero, cf, scadenza);
                    AddKB.this.setVisible(false);
                    AddKB.this.dispose();
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
