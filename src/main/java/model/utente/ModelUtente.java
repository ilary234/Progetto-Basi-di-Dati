package model.utente;

import java.sql.Connection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import model.AnnuncioServizio;
import model.Biglietto;
import model.Ordine;
import model.Recensione;
import model.Servizio;
import model.TipoLinea;
import model.TipoTransfer;
import model.Utente;

public class ModelUtente {

    private final Connection connection;
    private final SimpleDateFormat dateFormat;
    private Pair<String, String> credenziali;
    private Integer codOrdine;

    public ModelUtente(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
    }

    public Map<Integer, String> getTitoliAnnunci() {
        return AnnuncioServizio.DAO.titlesList(connection);
    }

    public Map<Integer, String> getLinee() {
        return AnnuncioServizio.DAO.getLines(connection);
    }

    public Map<Integer, String> getGP() {
        return AnnuncioServizio.DAO.getGP(connection);
    }

    public Map<Integer, String> getConcerti() {
        return AnnuncioServizio.DAO.getConcerti(connection);
    }

    public Map<Integer, String> getEscursioni() {
        return AnnuncioServizio.DAO.getEscursioni(connection);
    }

    public List<String> findServizio(String codice) {
        return AnnuncioServizio.DAO.find(connection, codice);
    }

    public double avgRating(String codice) {
        return Recensione.DAO.avgRating(connection, codice);
    }

    public List<String> getRecensioni(String codice) {
        return Recensione.DAO.getRecensioni(connection, codice);
    }

    public Pair<String, String> getCredenziali() {
        return this.credenziali;
    }

    public boolean findCredenziali(String username, String password) {
        return Utente.DAO.findUtente(connection, username, password);
    }

    public void setCredenziali(Pair<String, String> credenziali) {
        this.credenziali = credenziali;
    }

    public void inserisciRecensione(String utente, int codAnnuncio, int valutazione, String commento, Date data) {
        Recensione.DAO.inserisciRecensione(connection, utente, codAnnuncio, valutazione, commento, data);
    }

    public boolean isTransfer(int codice) {
        return Servizio.DAO.isTransfer(connection, codice);
    }

    public List<String> getTransfers(int codice) {
        return TipoTransfer.DAO.getTransfers(connection, codice);
    }

    public List<String> getLinee(int codice) {
        return TipoLinea.DAO.getLinee(connection, codice);
    }

    public String getImageLines(String codice) {
        return TipoLinea.DAO.getImageLines(connection, codice);
    }

    public String getImageTransfers(String codice) {
        return TipoTransfer.DAO.getImageTransfers(connection, codice);
    }

    public void inserisciUtente(String username, String password, String nome, String cognome, String email) {
        Utente.DAO.inserisciUtente(connection, username, password, nome, cognome, email);
    }

    public void creaNuovoOrdine(Time orario, Date data, String username) {
        this.codOrdine = Ordine.DAO.creaNuovoOrdine(connection, orario, data, username);
    }

    public void updateDatiOrdine(Time orario, Date data, String tipoPagamento, String username, float prezzoScontato) {
        int codOrdine = Ordine.DAO.getOrdineAperto(connection, username);
        Ordine.DAO.updateDatiOrdine(connection, orario, data, tipoPagamento, username, codOrdine, prezzoScontato);
    }

    public void aggiungiBigliettiAlCarrello(Map<String, Integer> bigliettiDaAggiungere, boolean isTransfer, String codAnnuncio, String username) {
        bigliettiDaAggiungere.forEach((categoria, quantita) -> {
            float percentuale = isTransfer
                ? TipoTransfer.DAO.getPercentuale(connection, categoria)
                : TipoLinea.DAO.getPercentuale(connection, categoria);

            float prezzoAnnuncio = AnnuncioServizio.DAO.getPrezzo(connection, codAnnuncio);
            float costoBiglietto = prezzoAnnuncio * percentuale;
            int codOrdine = Ordine.DAO.getOrdineAperto(connection, username);

            for (int i = 0; i < quantita; i++) {
                int numeroBiglietto = Biglietto.DAO.creaBiglietto(connection, costoBiglietto, Integer.parseInt(codAnnuncio), codOrdine);
                if (isTransfer) {
                    TipoTransfer.DAO.creaAssociazione(connection, numeroBiglietto, categoria);

                } else {
                    TipoLinea.DAO.creaAssociazione(connection, numeroBiglietto, categoria);
                }
            }
            Ordine.DAO.updateCostoTotale(connection, codOrdine, costoBiglietto * quantita);
        });
    }

    public List<String> getDettagliOrdineAperto() {
        return Ordine.DAO.getDettagliOrdineAperto(connection, this.codOrdine);
    }

    public Integer getCodOrdine() {
        return codOrdine;
    }

    public void aggiornaCodOrdineAperto(String username) {
        this.codOrdine = Ordine.DAO.getOrdineAperto(connection, username);
    }

    public float getPercentuale(boolean isTransfer, String categoria) {
        float percentuale;
        if(isTransfer) {
            percentuale = TipoTransfer.DAO.getPercentuale(connection, categoria);
        } else {
            percentuale = TipoLinea.DAO.getPercentuale(connection, categoria);
        }
        return percentuale;
    }

    public List<String> getOrdiniPrecedenti(String username) {
        return Ordine.DAO.getOrdiniPrecedenti(connection, username);
    }

    public float getCostoTotale() {
        return Ordine.DAO.getCostoTotale(connection, this.codOrdine);
    }

    public Map<String, Float> getCategoriePrezzi() {
        return Ordine.DAO.getCategoriePrezzi(connection, this.codOrdine);
    }

    public Map<String, Float> getSconti() {
        return Ordine.DAO.getSconti(connection, this.codOrdine);
    }

    public Map<String, Integer> getAnnunciDaModificare() {
        return AnnuncioServizio.DAO.getAnnunciDaModificare(connection, this.codOrdine);
    }
    
    public void updateBigliettiAnnuncioServizio(String titolo, int quantita) {
        AnnuncioServizio.DAO.updateBigliettiAnnuncioServizio(connection, titolo, quantita);
    }

    public void updateBigliettiServizio(String titolo, int quantita) {
        Servizio.DAO.updateBigliettiServizio(connection, titolo, quantita);
    }

    public int getBigliettiDisponibili(int codServizio) {
        return AnnuncioServizio.DAO.getBigliettiDisponibili(connection, codServizio);
    }

    public void rimuoviDalCarrello(String titolo, String categoria) {
        List<Integer> numeriBiglietto = Biglietto.DAO.getBigliettiDaRimuovere(connection, codOrdine, categoria);
        int codServizio = AnnuncioServizio.DAO.getCodiceServizio(connection, titolo);
        boolean isTransfer = Servizio.DAO.isTransfer(connection, codServizio);
        for (Integer numeroBiglietto : numeriBiglietto) {
            if (isTransfer) {
                TipoTransfer.DAO.eliminaAssociazione(connection, numeroBiglietto, categoria);
            } else {
                TipoLinea.DAO.eliminaAssociazione(connection, numeroBiglietto, categoria);
            }
            float costo = Biglietto.DAO.getCostoBiglietto(connection, numeroBiglietto);
            Ordine.DAO.updateCostoOrdine(connection, costo, codOrdine);
            Biglietto.DAO.eliminaBiglietto(connection, numeroBiglietto);
        }
    }
}
