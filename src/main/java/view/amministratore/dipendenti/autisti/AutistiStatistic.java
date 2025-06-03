package view.amministratore.dipendenti.autisti;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;

import controller.amministratore.ControllerAmm;
import view.api.GenericLabel;

public class AutistiStatistic extends JDialog {

    private static final int HEIGHT = 300;
    private static final int WIDTH = 500;
    private static final int TEXT_SIZE = 12;
    private static final int TITLE_SIZE = 18;

    public AutistiStatistic(final ControllerAmm controller, final String cf) {
        super((Window) controller.getFrame());
        var dataPanel = new JPanel(new GridLayout(6, 1, 10, 15));
        var title = GenericLabel.getGenericLabel("Servizi effettuati con maggiore frequenza:", TITLE_SIZE);
        dataPanel.add(title);
        var servizi = controller.getStatisticheAutista(cf);

        for (var i = 0; i < servizi.size(); i++) {
            var servizio = GenericLabel.getGenericLabel(i + 1 + ". " + servizi.get(i), TEXT_SIZE);
            dataPanel.add(servizio);
        }

        var panel = new JPanel(new GridBagLayout());
        panel.add(dataPanel);

        this.add(panel);
        this.setSize(WIDTH, HEIGHT);
        this.setModal(true);
        this.setLocationRelativeTo((Window) controller.getFrame());
        this.setVisible(true);
    }

}
