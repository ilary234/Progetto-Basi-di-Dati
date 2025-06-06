package view.utente;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import controller.utente.ControllerUtente;
import view.amministratore.api.MaxLenghtDocumentFilter;
import view.amministratore.api.WorkPanel;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.Scene;

public class UserScene implements Scene {

    private static final String SCENE_NAME = "User";
    private static final int TEXT_SIZE = 15;
    private static final int TITLE_SIZE = 25;
    private static final int COLOR_BUTTONS_PANEL = 0x30A4CF;
    private static final Color SELECTED_COLOR = new Color(0x99C2EA);

    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final JPanel workSpace;
    private JButton selected;
    private final ControllerUtente controller;
    private JButton homeButton;


    public UserScene(final ControllerUtente controller) {

        this.controller = controller;
        this.mainPanel = new JPanel(new BorderLayout());

        JPanel titlePanel = new JPanel(new GridBagLayout());
        titlePanel.setBackground(Color.WHITE);
        JLabel title = GenericLabel.getGenericLabel("MP Bus", TITLE_SIZE);
        title.setForeground(new Color(COLOR_BUTTONS_PANEL));
        titlePanel.add(title);

        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(Color.WHITE);

        JPanel rightButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        rightButtonsPanel.setOpaque(false);

        ImageIcon iconLogin = new ImageIcon(getClass().getResource("img/login.png"));
        Image scaledLoginImg = iconLogin.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledIconLogin = new ImageIcon(scaledLoginImg);
        JButton loginButton = GenericButton.getGenericButton(scaledIconLogin, TEXT_SIZE, "Login");
        
        ImageIcon iconCarrello = new ImageIcon(getClass().getResource("img/carrello.png"));
        Image scaledLCarrelloImg = iconCarrello.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon scaledCarrelloLogin = new ImageIcon(scaledLCarrelloImg);
        JButton carrelloButton = GenericButton.getGenericButton(scaledCarrelloLogin, TEXT_SIZE, "Carrello");

        carrelloButton.addActionListener(e -> {

            if (!controller.isLoggedIn()) {
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
                        return;
                    } else {
                        controller.setCredenziali(new Pair<>(username, password));
                        controller.getModel().aggiornaCodOrdineAperto(username);
                    }
                } else {
                    return;
                }
            }
            try {
                CarrelloPanel carrelloPanel = new CarrelloPanel(controller, this);
                carrelloPanel.showAsSidePanel(mainPanel);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(mainPanel, "Errore nell'apertura del carrello:\n" + ex.getMessage());
            }
        });

        rightButtonsPanel.add(loginButton);
        rightButtonsPanel.add(carrelloButton);

        loginButton.addActionListener(e -> {
            JPanel loginPanel = new JPanel(new GridLayout(2, 2, 5, 5));
            JTextField userField = new JTextField();
            JPasswordField passField = new JPasswordField();
            loginPanel.add(new JLabel("Username:"));
            loginPanel.add(userField);
            loginPanel.add(new JLabel("Password:"));
            loginPanel.add(passField);

            JButton accediBtn = new JButton("Accedi");
            JButton registratiBtn = new JButton("Registrati");
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            buttonPanel.add(accediBtn);
            buttonPanel.add(registratiBtn);

            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
            dialogPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 20, 15, 20)); 
            dialogPanel.add(loginPanel);
            dialogPanel.add(Box.createVerticalStrut(10));
            dialogPanel.add(buttonPanel);

            JDialog dialog = new JDialog();
            dialog.setTitle("Login");
            dialog.setModal(true);
            dialog.setResizable(false); 
            dialog.getContentPane().add(dialogPanel);
            dialog.pack();
            dialog.setLocationRelativeTo(mainPanel);

            accediBtn.addActionListener(ev -> {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                boolean loginOk = controller.checkUtente(username, password);
                if (loginOk) {
                    controller.setCredenziali(new Pair<>(username, password));
                    controller.getModel().aggiornaCodOrdineAperto(username);
                    JOptionPane.showMessageDialog(dialog, "Accesso effettuato!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Credenziali non valide!", "Errore", JOptionPane.ERROR_MESSAGE);
                    userField.setText("");
                    passField.setText("");
                }
            });

            registratiBtn.addActionListener(ev -> {
                dialog.dispose();

                JPanel regPanel = new JPanel(new GridLayout(6, 2, 5, 5));
                JTextField regUserField = new JTextField();
                JPasswordField regPassField = new JPasswordField();
                JPasswordField regConfPassField = new JPasswordField();
                JTextField regNomeField = new JTextField();
                JTextField regCognomeField = new JTextField();
                JTextField regEmailField = new JTextField();

                ((AbstractDocument) regUserField.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(25));
                ((AbstractDocument) regPassField.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));
                ((AbstractDocument) regNomeField.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
                ((AbstractDocument) regCognomeField.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(20));
                ((AbstractDocument) regEmailField.getDocument()).setDocumentFilter(new MaxLenghtDocumentFilter(50));

                regPanel.add(new JLabel("Username:"));
                regPanel.add(regUserField);
                regPanel.add(new JLabel("Password:"));
                regPanel.add(regPassField);
                regPanel.add(new JLabel("Conferma Password:"));
                regPanel.add(regConfPassField);
                regPanel.add(new JLabel("Nome:"));
                regPanel.add(regNomeField);
                regPanel.add(new JLabel("Cognome:"));
                regPanel.add(regCognomeField);
                regPanel.add(new JLabel("Email (opzionale):"));
                regPanel.add(regEmailField);

                int result = JOptionPane.showConfirmDialog(
                    mainPanel,
                    regPanel,
                    "Registrazione",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String username = regUserField.getText();
                    String password = new String(regPassField.getPassword());
                    String confPassword = new String(regConfPassField.getPassword());
                    String nome = regNomeField.getText();
                    String cognome = regCognomeField.getText();
                    String email = regEmailField.getText();

                    if (username.isEmpty() || password.isEmpty() || confPassword.isEmpty() || nome.isEmpty() || cognome.isEmpty()) {
                        JOptionPane.showMessageDialog(mainPanel, "Tutti i campi tranne email sono obbligatori.", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (!password.equals(confPassword)) {
                        JOptionPane.showMessageDialog(mainPanel, "Le password non coincidono.", "Errore", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (email.isEmpty()) {
                        this.controller.inserisciUtente(username, password, nome, cognome, null);
                    } else {
                        this.controller.inserisciUtente(username, password, nome, cognome, email);
                    }
                    controller.setCredenziali(new Pair<>(username, password));
                    controller.getModel().aggiornaCodOrdineAperto(username);
                    JOptionPane.showMessageDialog(mainPanel, "Registrazione avvenuta con successo!");
                }
            });

            dialog.setVisible(true);
        });
        
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.setBackground(new Color(COLOR_BUTTONS_PANEL));
        var actionListener = new MenuActionListener();

        homeButton = GenericButton.getGenericButton("Home", TEXT_SIZE, "Home");
        homeButton.setForeground(Color.WHITE);
        JButton lineeButton = GenericButton.getGenericButton("Linee", TEXT_SIZE, "Linee");
        lineeButton.setForeground(Color.WHITE);
        JButton escursioniButton = GenericButton.getGenericButton("Escursioni", TEXT_SIZE, "Escursioni");
        escursioniButton.setForeground(Color.WHITE);
        JButton concertiButton = GenericButton.getGenericButton("Concerti", TEXT_SIZE, "Concerti");
        concertiButton.setForeground(Color.WHITE);
        JButton giroPanoramicoButton = GenericButton.getGenericButton("Giro Panoramico", TEXT_SIZE, "Giro Panoramico");
        giroPanoramicoButton.setForeground(Color.WHITE);

        homeButton.addActionListener(actionListener);
        lineeButton.addActionListener(actionListener);
        escursioniButton.addActionListener(actionListener);
        concertiButton.addActionListener(actionListener);
        giroPanoramicoButton.addActionListener(actionListener);

        buttonsPanel.add(homeButton);
        buttonsPanel.add(lineeButton);
        buttonsPanel.add(giroPanoramicoButton);
        buttonsPanel.add(escursioniButton);
        buttonsPanel.add(concertiButton);

        this.cardLayout = new CardLayout();
        this.workSpace = new JPanel(cardLayout);

        this.changeWorkPanel(new HomePanel(this.controller, this));
        this.selected = homeButton;
        GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(buttonsPanel);
        centerPanel.add(workSpace);

        topBarPanel.add(titlePanel, BorderLayout.CENTER);
        topBarPanel.add(rightButtonsPanel, BorderLayout.EAST);

        mainPanel.add(topBarPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public String getSceneName() {
        return SCENE_NAME;
    }

    public final void changeWorkPanel(final WorkPanel panel) {
        this.workSpace.add(panel.getPanel(), panel.getName());
        this.cardLayout.show(this.workSpace, panel.getName());
    }

    public JButton getSelectedButton() {
        return selected;
    }

    public JButton getHomeButton() {
        return this.homeButton;
    }

    private class MenuActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            var button = (JButton) e.getSource();
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, false);
            selected = button;
            GenericButton.setBackgroundVisible(selected, SELECTED_COLOR, true);
            changeWorkPanel(switch(selected.getActionCommand()){
                case "Home" -> new HomePanel(controller, UserScene.this);
                case "Linee" -> new LineePanel(controller, UserScene.this);
                case "Escursioni" -> new EscursioniPanel(controller, UserScene.this);
                case "Concerti" -> new ConcertiPanel(controller, UserScene.this);
                case "Giro Panoramico" -> new GiroPanoramicoPanel(controller, UserScene.this);
                default -> new HomePanel(controller, UserScene.this);
            });
        }
    }
}
