package view.amministratore.giornaliere;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import controller.amministratore.ControllerAmm;
import view.api.GenericButton;
import view.api.GenericLabel;

public class ChangeDialog extends JDialog{

    private static final int HEIGHT = 300;
    private static final int WIDTH = 600;
    private static final int BACKGROUND_COLOR = 0x67A3E0;
    private static final int BUTTON_SIZE = 15;
    private static final int TEXT_SIZE = 12;

    private String initialAutista = "";
    private String initialMezzo = "";
    private String initialCommittente = "";

    public ChangeDialog(ControllerAmm controller, DailyPanel dailyPanel, String autista, String mezzo, String committente) {
        super((Window) controller.getFrame());
        var autisti = controller.getAutistiNames();
        var mezzi = new ArrayList<>(controller.getMezziNumbers().stream().map(m -> String.valueOf(m)).toList());
        var mapCommittenti = controller.getCommittentiNames(); 
        var committenti = new ArrayList<>(mapCommittenti.values());
        autisti.addFirst(initialAutista);
        mezzi.addFirst(initialMezzo);
        committenti.addFirst(initialCommittente);
        var dataPanel = new JPanel(new GridLayout(3, 2, -10, 15));
        var autistaLabel = GenericLabel.getGenericLabel("Autista: ", TEXT_SIZE);
        var mezzoLabel = GenericLabel.getGenericLabel("Mezzo: ", TEXT_SIZE);
        var committenteLabel = GenericLabel.getGenericLabel("Committente: ", TEXT_SIZE);
        var autistaBox = new JComboBox<>(autisti.toArray());
        var mezzoBox = new JComboBox<>(mezzi.toArray());
        var committenteBox = new JComboBox<>(committenti.toArray());

        if(!autista.isBlank()) {
            autistaBox.setSelectedItem(autisti.stream().filter(a -> a.contains(autista)).findAny().get());
            initialAutista = autistaBox.getSelectedItem().toString();
        }
        if(!mezzo.isBlank()) {
            mezzoBox.setSelectedItem(mezzi.stream().filter(m -> m.equals(mezzo)).findAny().get());
            initialMezzo = mezzoBox.getSelectedItem().toString();
        }
        if(!committente.isBlank()) {
            committenteBox.setSelectedItem(committenti.stream().filter(c -> c.equals(committente)).findAny().get());
            initialCommittente = committenteBox.getSelectedItem().toString();
        }

        dataPanel.add(autistaLabel);
        dataPanel.add(autistaBox);
        dataPanel.add(mezzoLabel);
        dataPanel.add(mezzoBox);
        dataPanel.add(committenteLabel);
        dataPanel.add(committenteBox);

        var salva = GenericButton.getGenericButton("Salva", BUTTON_SIZE, "Salva");
        GenericButton.setBackgroundVisible(salva, new Color(BACKGROUND_COLOR), true);
        salva.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                var autista = autistaBox.getSelectedItem().toString();
                var mezzo = mezzoBox.getSelectedItem().toString();
                var committente = committenteBox.getSelectedItem().toString();
                var resAutista = setRes(initialAutista, autista);
                var resMezzo = setRes(initialMezzo, mezzo);
                var resCommittente = setRes(initialCommittente, committente);
                var exist = mapCommittenti.entrySet().stream().filter(c -> c.getValue().equals(committente)).findAny();
                var codcom = exist.isPresent()? exist.get().getKey() : 0;
                dailyPanel.updateVolanda(autista, resAutista, mezzo, resMezzo, codcom, resCommittente);
                ChangeDialog.this.setVisible(false);
                ChangeDialog.this.dispose();
            }
            
        });

        var panel = new JPanel(new GridBagLayout());
        panel.add(dataPanel);

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.add(salva, BorderLayout.SOUTH);
        this.setSize(WIDTH, HEIGHT);
        this.setModal(true);
        this.setLocationRelativeTo((Window) controller.getFrame());
        this.setVisible(true);
    }

    private int setRes(String before, String after) {
        if (before.equals(after)) {
            return 0;
        } else if (!before.equals(after) && !before.equals("") && !after.equals("")) {
            return 1;
        } else if (!before.equals(after) && before.equals("")){
            return 2;
        } else {
            return 3;
        }
    }

}
