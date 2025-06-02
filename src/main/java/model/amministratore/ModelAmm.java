package model.amministratore;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import model.Autista;
import model.Committente;
import model.Giornaliera;
import model.Impiegato;
import model.Mezzo;
import model.Servizio;
import model.Volanda;

public class ModelAmm {

    private final Connection connection;
    private Optional<Impiegato> impiegato;
    private final SimpleDateFormat dateFormat;
    

    public ModelAmm(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
    }

    public boolean checkImpiegato(String cod, String pass) {
        this.impiegato = Impiegato.DAO.find(connection, cod, pass);
        if (this.impiegato.isPresent()) {
            return true;
        }
        return false;
    }

    public Impiegato getImpiegato() {
        return impiegato.get();
    }

    public List<String> getGiornaliere() {
        var dates = Giornaliera.DAO.dateList(connection);
        return dates.stream().map(d -> this.dateFormat.format(d)).toList();
    }

    public List<Volanda> getVolande(String date) {
        try {
            return Volanda.DAO.find(connection, this.dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return List.of();
    }

    public void deleteGiornaliera(String date) {
        try {
            Giornaliera.DAO.deleteDate(connection, this.dateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void deleteVolanda(String date, int numeroVolanda) {
        try {
            Volanda.DAO.deleteVolanda(connection, this.dateFormat.parse(date), numeroVolanda);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void insertGiornaliera(Date data) {
        Giornaliera.DAO.insertGiornaliera(connection, dateFormat.format(data), impiegato.get().getCodImpiegato());
    }

    public List<Integer> getServiziCodes() {
        return Servizio.DAO.getCodes(connection);
    }

    public void insertVolanda(String date, int numeroVolanda, int codServizio, String note, String fornitore, float prezzo,
            int km) {
        try {
            Volanda.DAO.insertVolanda(connection, this.dateFormat.parse(date), numeroVolanda, codServizio, note, fornitore, prezzo, km);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAutistiNames() {
        return Autista.DAO.getAutistiNames(connection);
    }

    public List<Integer> getMezziNumbers() {
        return Mezzo.DAO.getMezziNumbers(connection);
    }

    public Map<Integer, String> getCommittentiNames() {
        return Committente.DAO.getCommittentiNames(connection);
    }

    public void updateVolanda(String date, int numeroVolanda, String autista, int resAutista, String mezzo,
            int resMezzo, int codCommittente, int resCommittente) {
            
        if (resAutista != 0) {
            var cf = autista.split(",")[1].trim();
            Volanda.DAO.updateAutista(connection, date, numeroVolanda, cf, resAutista);
        }
        if (resMezzo != 0) {
            var numMezzo = mezzo.isBlank()? 0 : Integer.parseInt(mezzo);
            Volanda.DAO.updateMezzo(connection, date, numeroVolanda, numMezzo, resMezzo);
        }
        if (resCommittente != 0) {
            Volanda.DAO.updateCommittente(connection, date, numeroVolanda, codCommittente, resCommittente);
        }        
    }

    public List<Servizio> getServizi() {
        return Servizio.DAO.getServizi(connection);
    }

    public List<String> getCategorie() {
        return Servizio.DAO.getCategorie(connection);
    }

    public void addServizio(int codice, String partenza, String destinazione, String orario, int biglietti,
            String categoria) {
        Servizio.DAO.addServizio(connection, partenza, destinazione, orario, biglietti);
        Servizio.DAO.addCategoriaServizio(connection, codice, categoria);
    }

    public List<Mezzo> getMezzi() {
        return Mezzo.DAO.getMezzi(connection);
    }

    public List<String> getMezziTypes() {
        return Mezzo.getMezziTypes();
    }

    public void addMezzo(String numero, String targa, int euro, int immatricolazione, Date dataRevisione, String PAX,
            String kmTotali, boolean CDPD, String tipo, String carrozzeria, String modello, String telaio,
            String licenzaEuropea, String assicurazione) {
        Mezzo.DAO.addMezzo(connection, numero, targa, euro, immatricolazione, dateFormat.format(dataRevisione), PAX, 
                    kmTotali, CDPD, tipo, carrozzeria, modello, telaio, licenzaEuropea, assicurazione);
    }

    public void addAssicurazione(String numero, Date dataInizioValidità, String tipologia, String durata) {
        Mezzo.DAO.addAssicurazione(connection, numero, dateFormat.format(dataInizioValidità), tipologia, durata);
    }

    public List<Impiegato> getImpiegati() {
        return Impiegato.DAO.getImpiegati(connection);
    }

    public void addImpiegato(String cf, String nome, String cognome, Date dataNascita, String luogoNascita,
            String residenza, String telefono, String password) {
        Impiegato.DAO.addImpiegato(connection, cf, nome, cognome, dateFormat.format(dataNascita), 
            luogoNascita, residenza, telefono, password);
    }

    public void updateResidenza(int code, String residenza) {
        Impiegato.DAO.updateResidenza(connection, code, residenza);
    }

    public void updateTelefono(int code, String telefono) {
        Impiegato.DAO.updateTelefono(connection, code, telefono);
    }

    public void updatePassword(int code, String password) {
        Impiegato.DAO.updatePassword(connection, code, password);
    }

    public List<Autista> getAutisti() {
        return Autista.DAO.getAutisti(connection);
    }

    public void addPatente(String numero, String tipologia, Date scadenza) {
        Autista.DAO.addPatente(connection, numero, tipologia, dateFormat.format(scadenza));
    }

    public void addAutista(String cf, String patente, String nome, String cognome, Date dataNascita,
            String luogoNascita, String residenza, String telefono, Date scadenzaCQC) {
        Autista.DAO.addAutista(connection, cf, patente, nome, cognome, dateFormat.format(dataNascita), 
            luogoNascita, residenza, telefono, dateFormat.format(scadenzaCQC));
    }

    public void addKB(int numero, String cf, Date scadenza) {
        Autista.DAO.addKB(connection, numero, cf, dateFormat.format(scadenza));
    }

}
