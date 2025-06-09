package controller.utente;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import model.utente.ModelUtente;

public class ControllerUtente {

    private final ModelUtente model;

    public ModelUtente getModel() {
        return model;
    }

    public ControllerUtente(ModelUtente model) {
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

    public void inserisciUtente(String username, String password, String nome, String cognome, String email) {
        this.model.inserisciUtente(username, password, nome, cognome, email);
    }

    public void creaNuovoOrdine(Time orario, Date data, String username) {
       this.model.creaNuovoOrdine(orario, data, username);
    }

    public void updateDatiOrdine(Time orario, Date data, String tipoPagamento, String username, float prezzoScontato) {
        this.model.updateDatiOrdine(orario, data, tipoPagamento, username, prezzoScontato);
    }

    public void aggiungiBigliettiAlCarrello(Map<String, Integer> bigliettiDaAggiungere, boolean isTransfer, String codAnnuncio, String username) {
        this.model.aggiungiBigliettiAlCarrello(bigliettiDaAggiungere, isTransfer, codAnnuncio, username);
    }

    public Integer getOrdineAperto() {
        return this.model.getCodOrdine();
    }

    public List<String> getDettagliOrdineAperto() {
        return this.model.getDettagliOrdineAperto();
    }

    public List<String> getOrdiniPrecedenti(String username) {
        return this.model.getOrdiniPrecedenti(username);
    }

    public float getCostoTotale() {
        return this.model.getCostoTotale();
    }

    public Map<String, Float> getCategoriePrezzi() {
        return this.model.getCategoriePrezzi();
    }
    
    public Map<String, Float> getSconti() {
        return this.model.getSconti();
    }

    public Map<String, Integer> getAnnunciDaModificare() {
        return this.model.getAnnunciDaModificare();
    }

    public void updateBigliettiAnnuncioServizio(String titolo, int quantita) {
        this.model.updateBigliettiAnnuncioServizio(titolo, quantita);
    }

    public void updateBigliettiServizio(String titolo, int quantita) {
        this.model.updateBigliettiServizio(titolo, quantita);
    }

    public int getBigliettiDisponibili(int codServizio) {
        return this.model.getBigliettiDisponibili(codServizio);
    }

    public void rimuoviDalCarrello(String titolo, String categoria) {
        this.model.rimuoviDalCarrello(titolo, categoria);
    }
}
