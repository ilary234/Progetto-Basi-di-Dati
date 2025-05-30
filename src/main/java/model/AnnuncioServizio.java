package model;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class AnnuncioServizio extends Comunicazione {

    private final SimpleDateFormat dateFormat;
    private final int codServizio;
    private final float prezzoBase;
    private boolean visibile;
    private final int bigliettiDisponibili;
 
    public AnnuncioServizio(int codAnnuncio, int codServizio, String titolo, String descrizione, Date dataPubblicazione, float prezzoBase, boolean visibile, int bigliettiDisponibili, int codImpiegato) {
        super(codAnnuncio, titolo, descrizione, dataPubblicazione, codImpiegato);
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.codServizio = Objects.requireNonNull(codServizio);
        this.prezzoBase = Objects.requireNonNull(prezzoBase);
        this.visibile = Objects.requireNonNull(visibile);
        this.bigliettiDisponibili = Objects.requireNonNull(bigliettiDisponibili);
    }

    public static final class DAO {

        public static List<String> titlesList(Connection connection) {
            List<String> annunci = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TITLES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var titolo = resultSet.getString("Titolo");
                    annunci.add(titolo);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return annunci;
        }

        public static Map<Integer, String> getLines(Connection connection) {
            Map<Integer, String> result = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_LINES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codice = resultSet.getInt("CodServizio");
                    var categoria = resultSet.getString("NomeLinea");
                    result.put(codice, categoria);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return result;
        }

        public static Map<Integer, String> getGP(Connection connection) {
            Map<Integer, String> result = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_GP);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codice = resultSet.getInt("CodServizio");
                    var categoria = resultSet.getString("NomeTransfer");
                    result.put(codice, categoria);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return result;
        }

        public static Map<Integer, String> getConcerti(Connection connection) {
            Map<Integer, String> result = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CONCERTS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codice = resultSet.getInt("CodServizio");
                    var titolo = resultSet.getString("Titolo");
                    result.put(codice, titolo);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return result;
        }

        public static Map<Integer, String> getEscursioni(Connection connection) {
            Map<Integer, String> result = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TRIPS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codice = resultSet.getInt("CodServizio");
                    var titolo = resultSet.getString("Titolo");
                    result.put(codice, titolo);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return result;
        }
    }

}
