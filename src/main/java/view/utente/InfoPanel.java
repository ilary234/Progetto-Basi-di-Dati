package view.utente;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import controller.utente.ControllerUtente;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;

public class InfoPanel implements WorkPanel {

    private static final String PANEL_NAME = "Info";
    private static final int COLOR_BUTTONS_PANEL = 0x30A4CF;
    private static final Color SELECTED_COLOR = new Color(0x99C2EA);
    private static final int TEXT_SIZE = 15;
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final ControllerUtente controller;
    private final ImageIcon imageIcon;
    private final String titolo;
    private final String descrizione;
    private final double stelle;
    private final String prezzo; 
    private final String codAnnuncio;
    private int imageSize = 120; 
    private int dynamicFontSize = TEXT_SIZE;
    private JButton selected;
    private String codServizio;
    private JPanel listaRecensioniPanel;
    private JLabel imageLabel;
    private JPanel imagePanel;

    private final JPanel rightPanel = new JPanel();
    private final JPanel switchPanel = new JPanel(new CardLayout());

    private static final String DETTAGLI = "Dettagli";
    private static final String RECENSIONI = "Recensioni";
    private static final String ACQUISTO = "Acquisto biglietti";

    public InfoPanel(ControllerUtente controller, ImageIcon imageIcon, String codServizio) {
        this.controller = controller;
        this.imageIcon = imageIcon;
        this.codServizio = codServizio;

        List<String> info = this.controller.findServizio(this.codServizio);

        this.titolo = info.get(1);
        this.descrizione = info.get(2);
        this.prezzo = info.get(3);
        this.codAnnuncio = info.get(0);
        this.stelle = this.controller.avgRating(this.codAnnuncio);

        this.imageLabel = new JLabel();
        this.imageLabel.setIcon(resizeIcon(this.imageIcon, imageSize, imageSize));
        this.imagePanel = new JPanel(new BorderLayout());
        this.imagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.imagePanel.add(this.imageLabel, BorderLayout.CENTER);
        this.imagePanel.setPreferredSize(new Dimension(300, 0)); // 1/3 dello spazio (adatta in base al layout finale)

        rightPanel.setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel centrePanel = new JPanel();
        centrePanel.setLayout(new BoxLayout(centrePanel, BoxLayout.Y_AXIS));
        centrePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titoloLabel = new JLabel(this.titolo);
        titoloLabel.setFont(new Font("Roboto", Font.BOLD, 22));
        String stelleFormattate;
        if (stelle == 0) {
            stelleFormattate = "0";
        } else {
            stelleFormattate = String.format("%.1f", stelle);
        }
        JLabel stelleLabel = new JLabel(stelleFormattate + "/5");
        stelleLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        String prezzoFormattato = String.format("%.2f", Double.valueOf(prezzo));
        JLabel prezzoLabel = new JLabel("Prezzo: " + prezzoFormattato + " €");
        prezzoLabel.setFont(new Font("Roboto", Font.BOLD, 18));
        topPanel.add(titoloLabel);
        topPanel.add(stelleLabel);
        topPanel.add(prezzoLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(COLOR_BUTTONS_PANEL));
        JButton dettagliButton = GenericButton.getGenericButton("Dettagli", dynamicFontSize, "Dettagli");
        dettagliButton.setForeground(Color.WHITE);
        JButton recensioniButton = GenericButton.getGenericButton("Recensioni", dynamicFontSize, "Recensioni");
        recensioniButton.setForeground(Color.WHITE);
        JButton acquistoButton = GenericButton.getGenericButton("Acquisto biglietti", dynamicFontSize, "Acquisto biglietti");
        acquistoButton.setForeground(Color.WHITE);
        buttonPanel.add(dettagliButton);
        buttonPanel.add(recensioniButton);
        buttonPanel.add(acquistoButton);

        JPanel dettagliPanel = new JPanel(new BorderLayout());
        dettagliPanel.add(new JLabel(descrizione), BorderLayout.CENTER);

        JPanel recensioniPanel = new JPanel(new BorderLayout());
        this.listaRecensioniPanel = new JPanel();
        this.listaRecensioniPanel.setLayout(new BoxLayout(this.listaRecensioniPanel, BoxLayout.Y_AXIS));
        updateRecensioniPanel(this.listaRecensioniPanel);

        JScrollPane recensioniScrollPane = new JScrollPane(listaRecensioniPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        recensioniScrollPane.setPreferredSize(new Dimension(500, 250)); 
        recensioniScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        recensioniScrollPane.setBorder(null);
        recensioniScrollPane.getViewport().setBackground(Color.WHITE);
        recensioniPanel.add(recensioniScrollPane, BorderLayout.CENTER);

        JButton aggiungiRecensioneBtn = GenericButton.getGenericButton("Aggiungi Recensione", dynamicFontSize, "Aggiungi Recensione");
        aggiungiRecensioneBtn.setForeground(Color.WHITE);
        recensioniPanel.setBackground(new Color(COLOR_BUTTONS_PANEL));
        recensioniPanel.add(aggiungiRecensioneBtn, BorderLayout.SOUTH);

        aggiungiRecensioneBtn.addActionListener(e -> {
            boolean isLogged = controller.isLoggedIn();

            if (!isLogged) {
                JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
                JTextField userField = new JTextField();
                JPasswordField passField = new JPasswordField();
                loginPanel.add(new JLabel("Username:"));
                loginPanel.add(userField);
                loginPanel.add(new JLabel("Password:"));
                loginPanel.add(passField);

                int result = JOptionPane.showConfirmDialog(
                    mainPanel,
                    loginPanel,
                    "Login",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String username = userField.getText();
                    String password = new String(passField.getPassword());
                    boolean loginOk = controller.checkUtente(username, password);
                    if (!loginOk) {
                        JOptionPane.showMessageDialog(mainPanel, "Credenziali non valide!", "Errore", JOptionPane.ERROR_MESSAGE);
                    } else {
                        mostraDialogRecensione(username);
                        controller.setCredenziali(new Pair<>(username, password));
                    }
                }
            } else {
                mostraDialogRecensione(controller.getCredenziali().left);
            }
        });

        switchPanel.add(recensioniPanel, RECENSIONI);

        JPanel acquistoPanel = new JPanel();
        acquistoPanel.add(new JLabel("Qui va il pannello di acquisto biglietto"));

        switchPanel.add(dettagliPanel, DETTAGLI);
        switchPanel.add(recensioniPanel, RECENSIONI);
        switchPanel.add(acquistoPanel, ACQUISTO);

        ActionListener menuListener = e -> {
            JButton button = (JButton) e.getSource();
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, false);
            selected = button;
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);
            CardLayout cL = (CardLayout) switchPanel.getLayout();
            cL.show(switchPanel, button.getActionCommand());
        };
        dettagliButton.addActionListener(menuListener);
        recensioniButton.addActionListener(menuListener);
        acquistoButton.addActionListener(menuListener);

        this.selected = dettagliButton;
        GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);

        centrePanel.add(topPanel);
        centrePanel.add(buttonPanel);
        centrePanel.add(switchPanel);
        rightPanel.add(centrePanel, BorderLayout.CENTER);
        
        mainPanel.add(imagePanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = mainPanel.getWidth();
                int newImageSize = Math.max(100, width / 4); 
                imageLabel.setIcon(resizeIcon(imageIcon, newImageSize, newImageSize));
                imagePanel.setPreferredSize(new Dimension(newImageSize + 40, 0)); 

                mainPanel.revalidate();
                mainPanel.repaint();
            }
        });
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void mostraDialogRecensione(String username) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        JPanel valutazionePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        valutazionePanel.add(new JLabel("Valutazione:"));
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
        JComponent editor = spinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setEditable(false);
        tf.setFocusable(false); 
        valutazionePanel.add(spinner);

        JPanel commentoPanel = new JPanel(new BorderLayout());
        commentoPanel.add(new JLabel("Commento (max 200 caratteri):"), BorderLayout.NORTH);
        JTextArea commentoArea = new JTextArea(5, 30);
        ((AbstractDocument) commentoArea.getDocument()).setDocumentFilter(new LimitDocumentFilter(200));
        commentoArea.setLineWrap(true);
        commentoArea.setWrapStyleWord(true);
        commentoPanel.add(new JScrollPane(commentoArea), BorderLayout.CENTER);

        JLabel contaLabel = new JLabel("0/200");
        commentoArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { aggiorna(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { aggiorna(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { aggiorna(); }
            private void aggiorna() {
                int len = commentoArea.getText().length();
                contaLabel.setText(len + "/200");
                if (len > 200) {
                    commentoArea.setText(commentoArea.getText().substring(0, 200));
                }
            }
        });
        commentoPanel.add(contaLabel, BorderLayout.SOUTH);

        panel.add(valutazionePanel, BorderLayout.NORTH);
        panel.add(commentoPanel, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
            mainPanel,
            panel,
            "Inserisci la tua recensione",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            int valutazione = (Integer) spinner.getValue();
            String commento = commentoArea.getText();
            if (commento.length() > 200) {
                commento = commento.substring(0, 200);
            }
            java.sql.Date data = new java.sql.Date(System.currentTimeMillis());

            controller.inserisciRecensione(username, Integer.parseInt(this.codAnnuncio), valutazione, commento, data);
            updateRecensioniPanel(this.listaRecensioniPanel);
            JOptionPane.showMessageDialog(mainPanel, "Recensione inserita con successo!");
        }
    }

    private void updateRecensioniPanel(JPanel listaRecensioniPanel) {
        listaRecensioniPanel.removeAll();
        List<String> recensioni = this.controller.getRecensioni(this.codAnnuncio);
        for (int i = 0; i < recensioni.size(); i += 4) {
            String utente = recensioni.get(i);
            String valutazione = recensioni.get(i + 1);
            String commento = recensioni.get(i + 2);
            String data = recensioni.get(i + 3);

            JPanel singolaRecensione = new JPanel();
            singolaRecensione.setLayout(new BoxLayout(singolaRecensione, BoxLayout.Y_AXIS));
            singolaRecensione.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(java.awt.Color.LIGHT_GRAY)
            ));

            JLabel utenteDataLabel = new JLabel(utente + " - " + data);
            utenteDataLabel.setFont(new Font("Roboto", Font.BOLD, 14));
            String stelle = "★".repeat(Integer.parseInt(valutazione));
            JLabel valutazioneLabel = new JLabel(stelle);
            valutazioneLabel.setFont(new Font("Roboto", Font.PLAIN, 13));
            int maxWidth = 400; 
            String commentoHtml = "<html><div style='width:" + maxWidth + "px'><i>" +
                (commento != null && !commento.isEmpty() ? commento : "Nessun commento") +
                "</i></div></html>";
            JLabel commentoLabel = new JLabel(commentoHtml);
            commentoLabel.setFont(new Font("Roboto", Font.PLAIN, 12));
            commentoLabel.setFont(new Font("Roboto", Font.PLAIN, 12));

            singolaRecensione.add(utenteDataLabel);
            singolaRecensione.add(valutazioneLabel);
            singolaRecensione.add(commentoLabel);

            listaRecensioniPanel.add(singolaRecensione);
            listaRecensioniPanel.add(Box.createVerticalStrut(10));
        }
        listaRecensioniPanel.revalidate();
        listaRecensioniPanel.repaint();
    }

    private static class LimitDocumentFilter extends DocumentFilter {
        private final int max;

        public LimitDocumentFilter(int max) {
            this.max = max;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (fb.getDocument().getLength() + string.length() <= max) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (fb.getDocument().getLength() - length + (text != null ? text.length() : 0) <= max) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    @Override
    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public String getName() {
        return PANEL_NAME;
    }
}
