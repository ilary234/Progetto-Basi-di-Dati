package view.amministratore.dipendenti.autisti;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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

import com.toedter.calendar.JDateChooser;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.MaxLenghtDocumentFilter;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;
import view.api.GenericLabel;

public class NewAutista extends JDialog {

    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    private final CardLayout cardLayout;
    private final ControllerAmm controller;
    private final JPanel mainPanel;

    public NewAutista(final ControllerAmm controller) {
        super((Window) controller.getFrame());
        this.controller = controller;

        this.cardLayout = new CardLayout();
        this.mainPanel =  new JPanel(cardLayout);
        this.changeDataPanel(new PatentePanel());

        this.add(mainPanel);
        this.setSize(WIDTH, HEIGHT);
        this.setModal(true);
        this.setLocationRelativeTo((Window) controller.getFrame());
        this.setVisible(true);
    }

    public final void changeDataPanel(final WorkPanel panel) {
        this.mainPanel.add(panel.getPanel(), panel.getName());
        this.cardLayout.show(this.mainPanel, panel.getName());
    }

    private class AutistaPanel implements WorkPanel {
        
        private final JPanel mainPanel;

        public AutistaPanel(String patente) {
            var cf = GenericLabel.getGenericLabel("CF*: ", TEXT_SIZE);
            var nome = GenericLabel.getGenericLabel("Nome*: ", TEXT_SIZE);
            var cognome = GenericLabel.getGenericLabel("Cognome*: ", TEXT_SIZE);
            var dataNascita = GenericLabel.getGenericLabel("Data nascita*: ", TEXT_SIZE);
            var luogoNascita = GenericLabel.getGenericLabel("Luogo nascita*: ", TEXT_SIZE);
            var residenza = GenericLabel.getGenericLabel("Residenza*: ", TEXT_SIZE);
            var telefono = GenericLabel.getGenericLabel("Telefono*: ", TEXT_SIZE);
            var scadenzaCQC = GenericLabel.getGenericLabel("Scadenza CQC*: ", TEXT_SIZE);

            var cfText = new JTextField();
            var nomeText = new JTextField();
            var cognomeText = new JTextField();
            var dataNascitaBox = new JDateChooser();
            var luogoNascitaText = new JTextField();
            var residenzaText = new JTextField();
            var telefonoText = new JTextField();
            var scadenzaCQCBox = new JDateChooser();
            ((AbstractDocument) cfText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(16));
            ((AbstractDocument) nomeText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
            ((AbstractDocument) cognomeText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
            ((AbstractDocument) luogoNascitaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
            ((AbstractDocument) residenzaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(15));
            ((AbstractDocument) telefonoText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(15));


            var dataPanel = new JPanel(new GridLayout(4, 4, 15, 0));
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
            dataPanel.add(scadenzaCQC);
            dataPanel.add(scadenzaCQCBox);
            var upPanel = new JPanel(new GridBagLayout());
            upPanel.add(dataPanel);

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
                    var scadenzaCQC = scadenzaCQCBox.getDate();
                    if (!cf.isBlank() && !nome.isBlank() && !cognome.isBlank() && !luogoNascita.isBlank() &&
                        !residenza.isBlank() && !telefono.isBlank()) {
                        controller.addAutista(cf, patente, nome, cognome, dataNascita, luogoNascita, residenza, telefono, scadenzaCQC);
                        NewAutista.this.setVisible(false);
                        NewAutista.this.dispose();
                    }
                }
                
            });

            this.mainPanel = new JPanel(new BorderLayout());
            this.mainPanel.add(upPanel, BorderLayout.CENTER);
            this.mainPanel.add(aggiungi, BorderLayout.SOUTH);
        }

        public String getName() {
            return "Autista";
        }

        @Override
        public JPanel getPanel() {
            return this.mainPanel;
        }
    }

    private class PatentePanel implements WorkPanel {
        
        private final JPanel mainPanel;

        public PatentePanel() {
            var numero = GenericLabel.getGenericLabel("Numero Patente*: ", TEXT_SIZE);
            var tipologia = GenericLabel.getGenericLabel("Tipologia*: ", TEXT_SIZE);
            var scadenza = GenericLabel.getGenericLabel("Scadenza*: ", TEXT_SIZE);

            var numeroText = new JTextField();
            var scadenzaBox = new JDateChooser();
            String[] patenti = new String[]{"C", "D", "E"};
            var tipologiaText = new JComboBox<>(patenti);

            ((AbstractDocument) numeroText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(10));

            var dataPanel = new JPanel(new GridLayout(3, 2, 10, 10));

            dataPanel.add(numero);
            dataPanel.add(numeroText);
            dataPanel.add(tipologia);
            dataPanel.add(tipologiaText);
            dataPanel.add(scadenza);
            dataPanel.add(scadenzaBox);

            var upPanel = new JPanel(new GridBagLayout());
            upPanel.add(dataPanel);

            var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
            GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
            aggiungi.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    var numero = numeroText.getText();
                    var scadenza = scadenzaBox.getDate();
                    var tipologia = (String) tipologiaText.getSelectedItem();
                    if (numero.length() == 10) {
                        controller.addPatente(numero, tipologia, scadenza);
                    }
                    NewAutista.this.changeDataPanel(new AutistaPanel(numero));
                }
                
            });

            this.mainPanel = new JPanel(new BorderLayout());
            this.mainPanel.add(upPanel, BorderLayout.CENTER);
            this.mainPanel.add(aggiungi, BorderLayout.SOUTH);
        }

        public String getName() {
            return "Patente";
        }

        @Override
        public JPanel getPanel() {
            return this.mainPanel;
        }
    }

}
