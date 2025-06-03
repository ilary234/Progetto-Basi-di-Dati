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

    public int getCodServizio() {
        return codServizio;
    }

    public float getPrezzoBase() {
        return prezzoBase;
    }

    public boolean isVisibile() {
        return visibile;
    }

    public int getBigliettiDisponibili() {
        return bigliettiDisponibili;
    }


    public static final class DAO {

        public static void insertAnnuncio(Connection connection, int codServizio, String titolo, String descrizione,
                String data, float prezzoBase, boolean visibile, int bigliettiDisponibili, int codImpiegato) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_ANNUNCIO, null, codServizio, titolo, 
                    descrizione, data, prezzoBase, visibile, bigliettiDisponibili, codImpiegato);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void updateAnnuncio(Connection connection, int code, String titolo, String descrizione, float prezzoBase,
                boolean visibile, int bigliettiDisponibili) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_ANNUNCIO, titolo, descrizione, 
                    prezzoBase, visibile, bigliettiDisponibili, code);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<AnnuncioServizio> getAnnunci(Connection connection) {
            List<AnnuncioServizio> annunci = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ANNUNCI);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codAnnuncio = resultSet.getInt("CodAnnuncio");
                    var codServizio = resultSet.getInt("CodServizio");
                    var titolo = resultSet.getString("Titolo");
                    var dataPubblicazione = resultSet.getDate("DataPubblicazione");
                    var descrizione = resultSet.getString("Descrizione");
                    var prezzoBase = resultSet.getFloat("PrezzoBase");
                    var visibile = resultSet.getBoolean("Visibile");
                    var bigliettiDisponibili = resultSet.getInt("BigliettiDisponibili");
                    var codImpiegato = resultSet.getInt("CodImpiegato");

                    var annuncio = new AnnuncioServizio(codAnnuncio, codServizio, titolo, descrizione, 
                            dataPubblicazione, prezzoBase, visibile, bigliettiDisponibili, codImpiegato);
                    annunci.add(annuncio);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return annunci;
        }

        public static Map<Integer, String> titlesList(Connection connection) {
            Map<Integer, String> annunci = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_TITLES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codice = resultSet.getInt("CodServizio");
                    var titolo = resultSet.getString("Titolo");
                    annunci.put(codice, titolo);
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

        public static List<String> find(Connection connection, String codiceServizio) {
            List<String> info = new ArrayList<>();
            var code = Integer.valueOf(codiceServizio);
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_SERVIZIO, code);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codice = resultSet.getInt("CodAnnuncio");
                    info.add(String.valueOf(codice));
                    var titolo = resultSet.getString("Titolo");
                    info.add(titolo);
                    var descrizione = resultSet.getString("Descrizione");
                    info.add(descrizione);
                    var prezzoBase = resultSet.getFloat("PrezzoBase");
                    info.add(String.valueOf(prezzoBase));
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return info;
        }
    }

}
