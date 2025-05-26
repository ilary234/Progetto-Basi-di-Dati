package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Giornaliera {

    private final Date data; //date not null,
    private final int codImp; //integer not null,
    private final List<Volanda> volande;

    public Giornaliera(Date data, int codImp, List<Volanda> volande) {
        this.data = Objects.requireNonNull(data);
        this.codImp = Objects.requireNonNull(codImp);
        this.volande = new ArrayList<>(volande);
    }

    public static final class DAO {

        public static List<Date> dateList(Connection connection) {
            List<Date> dates = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_DATES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var giorno = resultSet.getDate("Data");
                    dates.add(giorno);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return dates;
        }
    }

}
