package view.amministratore.mezzi;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

import controller.amministratore.ControllerAmm;
import view.amministratore.api.MaxLenghtDocumentFilter;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;
import view.api.GenericLabel;

public class NewTransport extends JDialog{

    private static final int HEIGHT = 400;
    private static final int WIDTH = 500;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    private final CardLayout cardLayout;
    private final ControllerAmm controller;
    private final TransportPanel transportPanel;
    private final JPanel mainPanel;

    public NewTransport(final ControllerAmm controller, final TransportPanel transportPanel) {
        super((Window) controller.getFrame());
        this.controller = controller;
        this.transportPanel = transportPanel;

        this.cardLayout = new CardLayout();
        this.mainPanel =  new JPanel(cardLayout);
        this.changeDataPanel(new AssicurazionePanel());

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

    private class MezzoPanel implements WorkPanel {
        
        private final JPanel mainPanel;

        public MezzoPanel(String assicurazione) {
            var numero = GenericLabel.getGenericLabel("Numero*: ", TEXT_SIZE);
            var targa = GenericLabel.getGenericLabel("Targa*: ", TEXT_SIZE);
            var euro = GenericLabel.getGenericLabel("Euro*: ", TEXT_SIZE);
            var immatricolazione = GenericLabel.getGenericLabel("Immatricolazione*: ", TEXT_SIZE);
            var dataRevisione = GenericLabel.getGenericLabel("Data revisione*: ", TEXT_SIZE);
            var PAX = GenericLabel.getGenericLabel("PAX*: ", TEXT_SIZE);
            var kmTotali = GenericLabel.getGenericLabel("Km totali*: ", TEXT_SIZE);
            var CDPD = GenericLabel.getGenericLabel("CDPD*: ", TEXT_SIZE);
            var tipo = GenericLabel.getGenericLabel("Tipo*: ", TEXT_SIZE);
            var carrozzeria = GenericLabel.getGenericLabel("Carrozzeria*: ", TEXT_SIZE);
            var modello = GenericLabel.getGenericLabel("Modello*: ", TEXT_SIZE);
            var telaio = GenericLabel.getGenericLabel("Telaio*: ", TEXT_SIZE);
            var licenzaEuropea = GenericLabel.getGenericLabel("Licenza Europea*: ", TEXT_SIZE);

            var numeroText = new JTextField();
            var targaText = new JTextField();
            Integer[] euros = new Integer[]{1, 2, 3, 4, 5, 6};
            var euroBox = new JComboBox<>(euros);
            var annoImmatricolazioneBox = new JYearChooser();
            var dataRevisioneBox = new JDateChooser();
            var PAXText = new JTextField();
            var kmTotaliText = new JTextField();
            var CDPDBox = new JCheckBox();
            var tipoBox = new JComboBox<>(controller.getMezziTypes().toArray());
            var carrozzeriaText = new JTextField();
            var modelloText = new JTextField();
            var telaioText = new JTextField();
            var licenzaEuropeaText = new JTextField();

            var euroPanel = new JPanel(new GridLayout(1, 2));
            euroPanel.add(euro);
            euroPanel.add(euroBox);

            var CDPDPanel = new JPanel(new GridLayout(1, 2));
            CDPDPanel.add(CDPD);
            CDPDPanel.add(CDPDBox);


            ((AbstractDocument) targaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(7));
            ((AbstractDocument) carrozzeriaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
            ((AbstractDocument) modelloText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));
            ((AbstractDocument) telaioText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));
            ((AbstractDocument) licenzaEuropeaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(10));

            tipoBox.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    var tipo = tipoBox.getSelectedItem();
                    if (!tipo.equals("Pullman")) {
                        carrozzeriaText.setEnabled(false);
                        modelloText.setEnabled(false);
                        telaioText.setEnabled(false);
                        licenzaEuropeaText.setEnabled(false);
                    } else {
                        carrozzeriaText.setEnabled(true);
                        modelloText.setEnabled(true);
                        telaioText.setEnabled(true);
                        licenzaEuropeaText.setEnabled(true);
                    }
                }
                
            });

            var dataPanel = new JPanel(new GridLayout(1, 2, 15, 0));
            var firstPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            var secondPanel = new JPanel(new GridLayout(6, 2, 10, 10));

            firstPanel.add(numero);
            firstPanel.add(numeroText);
            firstPanel.add(targa);
            firstPanel.add(targaText);
            firstPanel.add(euroPanel);
            firstPanel.add(CDPDPanel);
            firstPanel.add(immatricolazione);
            firstPanel.add(annoImmatricolazioneBox);
            firstPanel.add(dataRevisione);
            firstPanel.add(dataRevisioneBox);
            firstPanel.add(PAX);
            firstPanel.add(PAXText);

            secondPanel.add(kmTotali);
            secondPanel.add(kmTotaliText);
            secondPanel.add(tipo);
            secondPanel.add(tipoBox);
            secondPanel.add(carrozzeria);
            secondPanel.add(carrozzeriaText);
            secondPanel.add(modello);
            secondPanel.add(modelloText);
            secondPanel.add(telaio);
            secondPanel.add(telaioText);
            secondPanel.add(licenzaEuropea);
            secondPanel.add(licenzaEuropeaText);

            dataPanel.add(firstPanel);
            dataPanel.add(secondPanel);
            var upPanel = new JPanel(new GridBagLayout());
            upPanel.add(dataPanel);

            var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
            GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
            aggiungi.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    var numero = numeroText.getText();
                    var targa = targaText.getText();
                    var euro = (int) euroBox.getSelectedItem();
                    var immatricolazione = annoImmatricolazioneBox.getYear();
                    var dataRevisione = dataRevisioneBox.getDate();
                    var PAX = PAXText.getText();
                    var kmTotali = kmTotaliText.getText();
                    var CDPD = CDPDBox.isSelected();
                    var tipo = (String) tipoBox.getSelectedItem();
                    var carrozzeria = carrozzeriaText.getText();
                    var modello = modelloText.getText();
                    var telaio = telaioText.getText();
                    var licenzaEuropea = licenzaEuropeaText.getText();
                    if (!numero.isBlank() && !targa.isBlank() && !PAX.isBlank() && !kmTotali.isBlank() && dataRevisione != null) {
                        if (tipo.equals("Pullman") && !carrozzeria.isBlank() && !modello.isBlank() && !telaio.isBlank() && !licenzaEuropea.isBlank()) {
                            controller.addMezzo(numero, targa, euro, immatricolazione, dataRevisione, PAX, kmTotali, CDPD, 
                                tipo, carrozzeria, modello, telaio, licenzaEuropea, assicurazione);
                        } else {
                            controller.addMezzo(numero, targa, euro, immatricolazione, dataRevisione, PAX, kmTotali, CDPD, 
                                tipo, null, null, null, null, assicurazione);
                        }
                    }
                    NewTransport.this.setVisible(false);
                    NewTransport.this.dispose();
                    transportPanel.updateMezzi();
                }
                
            });

            this.mainPanel = new JPanel(new BorderLayout());
            this.mainPanel.add(upPanel, BorderLayout.CENTER);
            this.mainPanel.add(aggiungi, BorderLayout.SOUTH);
        }

        public String getName() {
            return "Mezzo";
        }

        @Override
        public JPanel getPanel() {
            return this.mainPanel;
        }
    }

    private class AssicurazionePanel implements WorkPanel {
        
        private final JPanel mainPanel;

        public AssicurazionePanel() {
            var numero = GenericLabel.getGenericLabel("Numero Polizza*: ", TEXT_SIZE);
            var dataInizioValidità = GenericLabel.getGenericLabel("Data Inizio Validità*: ", TEXT_SIZE);
            var tipologia = GenericLabel.getGenericLabel("Tipologia*: ", TEXT_SIZE);
            var durata = GenericLabel.getGenericLabel("Durata (in mesi)*: ", TEXT_SIZE);

            var numeroText = new JTextField();
            var dataInizioValiditàBox = new JDateChooser();
            var tipologiaText = new JTextField();
            var durataText = new JTextField();

            ((AbstractDocument) numeroText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
            ((AbstractDocument) tipologiaText.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));

            var dataPanel = new JPanel(new GridLayout(4, 2, 10, 10));

            dataPanel.add(numero);
            dataPanel.add(numeroText);
            dataPanel.add(dataInizioValidità);
            dataPanel.add(dataInizioValiditàBox);
            dataPanel.add(tipologia);
            dataPanel.add(tipologiaText);
            dataPanel.add(durata);
            dataPanel.add(durataText);

            var upPanel = new JPanel(new GridBagLayout());
            upPanel.add(dataPanel);

            var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
            GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
            aggiungi.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    var numero = numeroText.getText();
                    var dataInizioValidità = dataInizioValiditàBox.getDate();
                    var tipologia = tipologiaText.getText();
                    var durata = durataText.getText();
                    if (!numero.isBlank() && !tipologia.isBlank() && !durata.isBlank()) {
                        controller.addAssicurazione(numero, dataInizioValidità, tipologia, durata);
                    }
                    NewTransport.this.changeDataPanel(new MezzoPanel(numero));
                }
                
            });

            this.mainPanel = new JPanel(new BorderLayout());
            this.mainPanel.add(upPanel, BorderLayout.CENTER);
            this.mainPanel.add(aggiungi, BorderLayout.SOUTH);
        }

        public String getName() {
            return "Assicurazione";
        }

        @Override
        public JPanel getPanel() {
            return this.mainPanel;
        }
    }

}
