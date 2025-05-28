package controller.utente;

import java.util.List;
import java.util.Map;

import model.utente.ModelUtente;
import view.api.View;

public class ControllerUtente {

     private final View mainView;
    private final ModelUtente model;

    public ControllerUtente(View mainView, ModelUtente model) {
        this.mainView = mainView;
        this.model = model;
    }

    public List<String> getTitoliAnnunci() {
        return this.model.getTitoliAnnunci();
    }

    public Map<Integer, String> getLinee() {
        return this.model.getLinee();
    }
}
