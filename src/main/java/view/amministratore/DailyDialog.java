package view.amministratore;

import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.toedter.calendar.JDateChooser;

import controller.amministratore.ControllerAmm;
import view.api.GenericButton;
import view.api.GenericLabel;

public class DailyDialog extends JDialog{

    private static final int HEIGHT = 300;
    private static final int WIDTH = 400;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 20;

    public DailyDialog(final ControllerAmm controller) {
        super((Window) controller.getFrame());
        var panel = new JPanel(new GridLayout(3, 0, 0, 15));
        var dateChooser = new JDateChooser();
        var label = GenericLabel.getGenericLabel(" Scegli data:  ", TEXT_SIZE);
        var aggiungi = GenericButton.getGenericButton("Aggiungi", BUTTON_SIZE, "Aggiungi");
        GenericButton.setBackgroundVisible(aggiungi, new Color(BACKGROUND_COLOR), true);
        panel.add(label);
        panel.add(dateChooser);
        panel.add(aggiungi);
        aggiungi.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var data = dateChooser.getDate();
                if (data != null) {
                    controller.insertGiornaliera(data);
                    DailyDialog.this.setVisible(false);
                    DailyDialog.this.dispose();
                }
            }
            
        });

        this.add(panel);
        this.setLayout(new GridBagLayout());
        this.setSize(WIDTH, HEIGHT);
        this.setModal(true);
        this.setLocationRelativeTo((Window) controller.getFrame());
        this.setVisible(true);
    }

}
