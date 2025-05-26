package controller.amministratore;

import java.util.List;

import model.amministratore.ModelAmm;
import view.amministratore.DailyScene;
import view.api.View;

public class ControllerAmm {

    private final View mainView;
    private final ModelAmm model;

    public ControllerAmm(View mainView, ModelAmm model) {
        this.mainView = mainView;
        this.model = model;
    }

    public boolean checkImpiegato(String cod, char[] pass) {
        return this.model.checkImpiegato(cod, String.valueOf(pass));
    }

    public void enter() {
        this.mainView.changeScene(new DailyScene(this));
    }

    public List<String> getGiornaliere() {
        return this.model.getGiornaliere();
    }

}
