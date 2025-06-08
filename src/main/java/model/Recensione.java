package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Recensione {

     public static final class DAO {

        public static double avgRating(Connection connection, String codiceAnnuncio) {
            var code = Integer.valueOf(codiceAnnuncio);
            try (
                var statement = DAOUtils.prepare(connection, Queries.AVG_RATING, code);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    double media = resultSet.getDouble("MediaValutazioni");
                    if (resultSet.wasNull()) {
                        return 0.0; 
                    }
                    return media;
                } else {
                    return 0.0;
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<String> getRecensioni(Connection connection, String codiceAnnuncio) {
            List<String> recensioni = new ArrayList<>();
            var code = Integer.valueOf(codiceAnnuncio);
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_REVIEWS, code);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var utente = resultSet.getString("Utente");
                    recensioni.add(utente);
                    var valutazione = resultSet.getInt("Valutazione");
                    recensioni.add(String.valueOf(valutazione));
                    var descrizione = resultSet.getString("Commento");
                    recensioni.add(descrizione);
                    var data = resultSet.getDate("Data");
                    recensioni.add(data.toString());
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return recensioni;
        }

        public static void inserisciRecensione(Connection connection, String utente, int codAnnuncio, int valutazione, String commento, Date data) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_REVIEW, utente, codAnnuncio, valutazione, commento, data);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
    