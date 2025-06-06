package view.amministratore.servizi;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;

import controller.amministratore.ControllerAmm;
import view.api.GenericLabel;

public class ServiziStatistica extends JDialog {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;
    private static final int TEXT_SIZE = 12;
    private static final int TITLE_SIZE = 18;

    public ServiziStatistica(final ControllerAmm controller) {
        super((Window) controller.getFrame());
        var dataPanel = new JPanel(new GridLayout(11, 1, 10, 15));
        var title = GenericLabel.getGenericLabel("Servizi di cui c'è stata più richiesta:", TITLE_SIZE);
        dataPanel.add(title);
        var servizi = controller.getStatisticheServizi();

        for (var i = 0; i < servizi.size(); i++) {
            var servizio = GenericLabel.getGenericLabel(i + 1 + ". " + servizi.get(i), TEXT_SIZE);
            dataPanel.add(servizio);
        }

        var panel = new JPanel(new GridBagLayout());
        panel.add(dataPanel);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.setSize(WIDTH, HEIGHT);
        this.setModal(true);
        this.setLocationRelativeTo((Window) controller.getFrame());
        this.setVisible(true);
    }

}
