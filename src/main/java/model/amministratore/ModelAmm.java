package model.amministratore;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import model.Giornaliera;
import model.Impiegato;
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

}
