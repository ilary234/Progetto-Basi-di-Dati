package model;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Biglietto {

    private final int numeroBiglietto; // integer not null auto_increment,
    private final SimpleDateFormat dateFormat;
    private final Date dataOraAcquisto; // datetime not null,
    private final String tipoPagamento; // varchar(15) not null,
    private final float costo; // float not null check (`Costo` > 0),       
    private final String acquirente; // varchar(25) not null,
    private final int codAnnuncio; // integer not null,

    public Biglietto(int numeroBiglietto, Date dataOraAcquisto, String tipoPagamento, float costo, String acquirente, int codAnnuncio) {
        this.numeroBiglietto = Objects.requireNonNull(numeroBiglietto);
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.dataOraAcquisto = Objects.requireNonNull(dataOraAcquisto);
        this.tipoPagamento = Objects.requireNonNull(tipoPagamento);
        this.costo = Objects.requireNonNull(costo);
        this.acquirente = Objects.requireNonNull(acquirente);
        this.codAnnuncio = Objects.requireNonNull(codAnnuncio);
    }

    public static final class DAO {

        public static List<String> getStatistics(Connection connection, int year) {
            List<String> statistiche = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_BIGLIETTI_STATISTICS, year);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var mese = resultSet.getString("Mese");
                    var biglietti = resultSet.getInt("Biglietti venduti");

                    var stat = mese + ": " + biglietti;
                    statistiche.add(stat);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return statistiche;
        }
    }
}