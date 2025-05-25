package view.amministratore;

import java.awt.GridBagLayout;


import javax.swing.JPanel;

import controller.amministratore.ControllerAmm;
import view.api.GenericLabel;
import view.api.Scene;

public class DailyScene implements Scene{

    private static final String SCENE_NAME = "Daily";
    private static final int TITLE_SIZE = 25;

    private final JPanel mainPanel;
    
    public DailyScene(final ControllerAmm controller) {
        this.mainPanel = new JPanel(new GridBagLayout());
        var titolo = GenericLabel.getGenericLabel("Accedi", TITLE_SIZE);
        
        this.mainPanel.add(titolo);

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
