package controller.amministratore;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import model.AnnuncioServizio;
import model.Autista;
import model.Impiegato;
import model.Mezzo;
import model.Servizio;
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

    public void insertVolanda(String date, int numeroVolanda, int codServizio, String note, String fornitore, float prezzo,
            int km) {
        this.model.insertVolanda(date, numeroVolanda, codServizio, note, fornitore, prezzo, km);
    }

    public List<String> getAutistiNames() {
        return this.model.getAutistiNames();
    }

    public List<Integer> getMezziNumbers() {
        return this.model.getMezziNumbers();
    }

    public Map<Integer, String> getCommittentiNames() {
        return this.model.getCommittentiNames();
    }

    public void updateVolanda(String date, int numeroVolanda, String autista, int resAutista, String mezzo,
            int resMezzo, int codCommittente, int resCommittente) {
        this.model.updateVolanda(date, numeroVolanda, autista, resAutista, mezzo, resMezzo, codCommittente, resCommittente);
    }

    public List<Servizio> getServizi() {
        return this.model.getServizi();
    }

    public List<String> getCategorie() {
        return this.model.getCategorie();
    }

    public void addServizio(int codice, String partenza, String destinazione, String orario, int biglietti, String categoria) {
        this.model.addServizio(codice,  partenza, destinazione, orario, biglietti, categoria);
    }

    public List<Mezzo> getMezzi() {
        return this.model.getMezzi();
    }

    public List<String> getMezziTypes() {
        return this.model.getMezziTypes();
    }

    public void addMezzo(String numero, String targa, int euro, int immatricolazione, Date dataRevisione, String PAX,
            String kmTotali, boolean CDPD, String tipo, String carrozzeria, String modello, String telaio,
            String licenzaEuropea, String assicurazione) {
        this.model.addMezzo(numero, targa, euro, immatricolazione, dataRevisione, PAX, kmTotali, CDPD, 
                                tipo, carrozzeria, modello, telaio, licenzaEuropea, assicurazione);
    }

    public void addAssicurazione(String numero, Date dataInizioValidità, String tipologia, String durata) {
        this.model.addAssicurazione(numero, dataInizioValidità, tipologia, durata);
    }

    public List<Impiegato> getImpiegati() {
        return this.model.getImpiegati();
    }

    public void addImpiegato(String cf, String nome, String cognome, Date dataNascita, String luogoNascita,
            String residenza, String telefono, String password) {
        this.model.addImpiegato(cf, nome, cognome, dataNascita, luogoNascita, residenza, telefono, password);
    }

    public Impiegato getMyData() {
        return this.model.getImpiegato();
    }

    public void updateResidenza(int code, String residenza) {
        this.model.updateResidenza(code, residenza);
    }

    public void updateTelefono(int code, String telefono) {
        this.model.updateTelefono(code, telefono);
    }

    public void updatePassword(int code, String password) {
        this.model.updatePassword(code, password);
    }

    public List<Autista> getAutisti() {
        return this.model.getAutisti();
    }

    public void addPatente(String numero, String tipologia, Date scadenza) {
        this.model.addPatente(numero, tipologia, scadenza);
    }

    public void addAutista(String cf, String patente, String nome, String cognome, Date dataNascita,
            String luogoNascita, String residenza, String telefono, Date scadenzaCQC) {
        this.model.addAutista(cf, patente, nome, cognome, dataNascita, luogoNascita, residenza, telefono, scadenzaCQC);
    }

    public void addKB(int numero, String cf, Date scadenza) {
        this.model.addKB(numero, cf, scadenza);
    }

    public List<String> getStatisticheAutista(String cf) {
        return this.model.getStatisticheAutista(cf);
    }

    public List<String> getStatisticheServizi() {
        return this.model.getStatisticheServizi();
    }

    public List<AnnuncioServizio> getAnnunci() {
        return this.model.getAnnunci();
    }

    public void addAnnuncio(int codServizio, String titolo, float prezzoBase, boolean visibile,
            int bigliettiDisponibili, String descrizione) {
        this.model.addAnnuncio(codServizio, titolo, descrizione, prezzoBase, visibile, bigliettiDisponibili);
    }

    public void updateAnnuncio(int code, String titolo, String descrizione, float prezzoBase, boolean visibile,
            int bigliettiDisponibili) {
        this.model.updateAnnuncio(code, titolo, descrizione, prezzoBase, visibile, bigliettiDisponibili);
    }

    public List<Integer> getServiziWithoutAnnuncioCodes() {
        return this.model.getServiziWithoutAnnuncioCodes();
    }

}
