package controller.amministratore;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

import model.Volanda;
import model.amministratore.ModelAmm;
import view.amministratore.MainScene;
import view.api.View;

public class ControllerAmm {

    private final View mainView;
    private final ModelAmm model;

    public ControllerAmm(View mainView, ModelAmm model) {
        this.mainView = mainView;
        this.model = model;
    }

    public JFrame getFrame() {
        return this.mainView.getFrame();
    }

    public boolean checkImpiegato(String cod, char[] pass) {
        return this.model.checkImpiegato(cod, String.valueOf(pass));
    }

    public void enter() {
        this.mainView.changeScene(new MainScene(this));
    }

    public List<String> getGiornaliere() {
        return this.model.getGiornaliere();
    }

    public List<Volanda> getVolande(String date) {
        return this.model.getVolande(date);
    }

    public void deleteGiornaliera(String date) {
        this.model.deleteGiornaliera(date);
    }

    public void deleteVolanda(String day, int numeroVolanda) {
        this.model.deleteVolanda(day, numeroVolanda);
    }

    public void insertGiornaliera(Date data) {
        this.model.insertGiornaliera(data);
    }

    public List<Integer> getServiziCodes() {
        return this.model.getServiziCodes();
    }

    public void insertVolanda(String date, int codServizio, String note, String fornitore, float prezzo,
            int km) {
        this.model.insertVolanda(date, codServizio, note, fornitore, prezzo, km);
    }

    public List<String> getAutistiNames() {
        return this.model.getAutistiNames();
    }

    public List<Integer> getMezziNumbers() {
        return this.model.getMezziNumbers();
    }

    public List<String> getCommittentiNames() {
        return this.model.getCommittentiNames();
    }

}
