package model.utente;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import model.AnnuncioServizio;

public class ModelUtente {

    private final Connection connection;
    private final SimpleDateFormat dateFormat;

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

}
