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

import com.toedter.calendar.JDateChooser;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.MaxLenghtDocumentFilter;
import view.api.GenericButton;
import view.api.GenericLabel;

public class NewImpiegato extends JDialog {
    
    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    public NewImpiegato(final ControllerAmm controller, final ImpiegatiPanel impiegatiPanel) {
        super((Window) controller.getFrame());
        var dataPanel = new JPanel(new GridLayout(4, 4, 10, 15));
        var cf = GenericLabel.getGenericLabel("CF*: ", TEXT_SIZE);
        var nome = GenericLabel.getGenericLabel("Nome*: ", TEXT_SIZE);
        var cognome = GenericLabel.getGenericLabel("Cognome*: ", TEXT_SIZE);
        var dataNascita = GenericLabel.getGenericLabel("Data nascita*: ", TEXT_SIZE);
        var luogoNascita = GenericLabel.getGenericLabel("Luogo nascita*: ", TEXT_SIZE);
        var residenza = GenericLabel.getGenericLabel("Residenza*: ", TEXT_SIZE);
        var telefono = GenericLabel.getGenericLabel("Telefono*: ", TEXT_SIZE);
        var password = GenericLabel.getGenericLabel("Password*: ", TEXT_SIZE);

        var cfText = new JTextField();
        var nomeText = new JTextField();
        var cognomeText = new JTextField();
        var dataNascitaBox = new JDateChooser();
        var luogoNascitaText = new JTextField();
        var residenzaText = new JTextField();
        var telefonoText = new JTextField();
        var passwordText = new JTextField();
        ((AbstractDocument) cfText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(16));
        ((AbstractDocument) nomeText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
        ((AbstractDocument) cognomeText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
        ((AbstractDocument) luogoNascitaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
        ((AbstractDocument) residenzaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(15));
        ((AbstractDocument) telefonoText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(15));
        ((AbstractDocument) passwordText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));

        dataPanel.add(cf);
        dataPanel.add(cfText);
        dataPanel.add(nome);
        dataPanel.add(nomeText);
        dataPanel.add(cognome);
        dataPanel.add(cognomeText);
        dataPanel.add(dataNascita);
        dataPanel.add(dataNascitaBox);
        dataPanel.add(luogoNascita);
        dataPanel.add(luogoNascitaText);
        dataPanel.add(residenza);
        dataPanel.add(residenzaText);
        dataPanel.add(telefono);
        dataPanel.add(telefonoText);
        dataPanel.add(password);
        dataPanel.add(passwordText);

        var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
        GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
        aggiungi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var cf = cfText.getText();
                var nome = nomeText.getText();
                var cognome = cognomeText.getText();
                var dataNascita = dataNascitaBox.getDate();
                var luogoNascita = luogoNascitaText.getText();
                var residenza = residenzaText.getText();
                var telefono = telefonoText.getText();
                var password = passwordText.getText();
                if (!cf.isBlank() && !nome.isBlank() && !cognome.isBlank() && !luogoNascita.isBlank() &&
                    !residenza.isBlank() && !telefono.isBlank() && !password.isBlank()) {
                    controller.addImpiegato(cf, nome, cognome, dataNascita, luogoNascita, residenza, telefono, password);
                    NewImpiegato.this.setVisible(false);
                    NewImpiegato.this.dispose();
                }
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
