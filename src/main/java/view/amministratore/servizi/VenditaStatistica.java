package view.amministratore.servizi;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.JPanel;

import controller.amministratore.ControllerAmm;
import view.api.GenericLabel;

public class VenditaStatistica extends JDialog {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;
    private static final int TEXT_SIZE = 12;
    private static final int TITLE_SIZE = 18;

    public VenditaStatistica(final ControllerAmm controller, final int year) {
        super((Window) controller.getFrame());
        var mesi = controller.getStatisticheVendita(year);
        var dataPanel = new JPanel(new GridLayout(mesi.size() + 1, 1, 10, 15));
        var title = GenericLabel.getGenericLabel("Biglietti venduti:", TITLE_SIZE);
        dataPanel.add(title);

        for (var i = 0; i < mesi.size(); i++) {
            var mese = GenericLabel.getGenericLabel(mesi.get(i), TEXT_SIZE);
            dataPanel.add(mese);
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
