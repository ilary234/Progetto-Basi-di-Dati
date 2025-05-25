package view.amministratore;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.amministratore.ControllerAmm;
import view.api.GenericButton;
import view.api.GenericLabel;
import view.api.Scene;

public class AuthenticationScene implements Scene {

    private static final String SCENE_NAME = "Authentication";
    private static final int TEXT_SIZE = 15;
    private static final int TITLE_SIZE = 25;

    private final JPanel mainPanel;
    
    public AuthenticationScene(final ControllerAmm controller) {
        this.mainPanel = new JPanel(new GridBagLayout());
        var authenticationPanel = new JPanel(new GridLayout(6, 1, 0, 10));
        var titolo = GenericLabel.getGenericLabel("Accedi", TITLE_SIZE);
        var codice = GenericLabel.getGenericLabel("Codice*", TEXT_SIZE);
        codice.setHorizontalAlignment(JLabel.LEFT);
        var password = GenericLabel.getGenericLabel("Password*", TEXT_SIZE);
        password.setHorizontalAlignment(JLabel.LEFT);
        var insertCodice = new JTextField(50);
        var insertPassword = new JPasswordField(50);
        var accedi = GenericButton.getGenericButton("ACCEDI");
        accedi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var cod = insertCodice.getText();
                var pass = insertPassword.getPassword();
                if (!cod.isBlank() && pass.length > 0) {
                    if (controller.checkImpiegato(cod, pass)) {
                        controller.enter();
                    };
                }
                insertCodice.setText("");
                insertPassword.setText("");
            }
            
        });

        authenticationPanel.add(titolo);
        authenticationPanel.add(codice);
        authenticationPanel.add(insertCodice);
        authenticationPanel.add(password);
        authenticationPanel.add(insertPassword);
        authenticationPanel.add(accedi);
        this.mainPanel.add(authenticationPanel);

    }

    @Override
    public JPanel getPanel() {
        return this.mainPanel;
    }

    @Override
    public String getSceneName() {
        return SCENE_NAME;
    }

}
