package controller.utente;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import model.utente.ModelUtente;
import view.api.View;

public class ControllerUtente {

    private final View mainView;
    private final ModelUtente model;

    public ControllerUtente(View mainView, ModelUtente model) {
        this.mainView = mainView;
        this.model = model;
    }

    public Map<Integer, String> getTitoliAnnunci() {
        return this.model.getTitoliAnnunci();
    }

    public Map<Integer, String> getLinee() {
        return this.model.getLinee();
    }

    public Map<Integer, String> getGP() {
        return this.model.getGP();
    }

    public Map<Integer, String> getConcerti() {
        return this.model.getConcerti();
    }

    public Map<Integer, String> getEscursioni() {
        return this.model.getEscursioni();
    }

    public List<String> findServizio(String codice) {
        return this.model.findServizio(codice);
    }

    public double avgRating(String codice) {
        return this.model.avgRating(codice);
    }

    public List<String> getRecensioni(String codice) {
        return this.model.getRecensioni(codice);
    }

    public boolean isLoggedIn() {
        return (this.model.getCredenziali() != null);
    }

    public boolean checkUtente(String username, String password) {
        return this.model.findCredenziali(username, password);
    }

    public void signIn(String username, String password) {
        if (checkUtente(username, password)) {
            this.model.setCredenziali(new Pair<>(username, password));
        }
    }

    public Pair<String, String> getCredenziali() {
        return this.model.getCredenziali();
    }

    public void inserisciRecensione(String utente, int codAnnuncio, int valutazione, String commento, Date data) {
        this.model.inserisciRecensione(utente, codAnnuncio, valutazione, commento, data);
    }

    public void setCredenziali(Pair<String, String> credenziali) {
        this.model.setCredenziali(credenziali);
    }

    public boolean isTransfer(int codice) {
        return this.model.isTransfer(codice);
    }

    public List<String> getNomiCategorie(int codice) {
        if(this.model.isTransfer(codice)) {
            return this.model.getTransfers(codice);
        } else {
            return this.model.getLinee(codice);
        }
    }

    public String getImmagineCategoria(int codice) {
        String codiceServizio = String.valueOf(codice);
        if(this.model.isTransfer(codice)) {
            return this.model.getImageTransfers(codiceServizio);
        } else {
            return this.model.getImageLines(codiceServizio);
        }
    }
}
