package model.utente;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

import model.AnnuncioServizio;
import model.Recensione;
import model.Utente;

public class ModelUtente {

    private final Connection connection;
    private final SimpleDateFormat dateFormat;
    private Pair<String, String> credenziali;

    public ModelUtente(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
    }

    public List<String> getTitoliAnnunci() {
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
}
