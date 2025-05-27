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

    private final Date data;
    private final int codImp;
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

        public static void deleteDate(Connection connection, Date date) {
            try (
                var statement1 = DAOUtils.prepare(connection, Queries.REMOVE_DATE, date);
                var statement2 = DAOUtils.prepare(connection, Queries.FIND_VOLANDE, date);
                var resultSet = statement2.executeQuery();
            ) {
                while (resultSet.next()) {
                    var numeroVolanda = resultSet.getInt("NumeroVolanda");
                    Volanda.DAO.deleteVolanda(connection, date, numeroVolanda);
                }
                statement1.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void insertGiornaliera(Connection connection, String date, int codImp) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_DATE, date, codImp);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

}
