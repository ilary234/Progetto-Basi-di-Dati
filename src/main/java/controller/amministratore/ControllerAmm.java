package controller.amministratore;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

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

}
