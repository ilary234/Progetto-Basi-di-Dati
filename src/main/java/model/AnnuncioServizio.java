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

        public static float getPrezzo(Connection connection, String codAnnuncio) {
            var code = Integer.valueOf(codAnnuncio);
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PREZZO_BASE, code);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getFloat("PrezzoBase");
                } else {
                    throw new DAOException("Prezzo non trovato per codAnnuncio: " + codAnnuncio);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Map<String, Integer> getAnnunciDaModificare(Connection connection, int codOrdine) {
            Map<String, Integer> result = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ANNUNCI_QUANTITA, codOrdine, codOrdine);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    String titolo = resultSet.getString("Titolo");
                    int quantita = resultSet.getInt("Quantita");
                    result.put(titolo, quantita);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return result;
        }

        public static void updateBigliettiAnnuncioServizio(Connection connection, String titolo, int quantita) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_BIGLIETTI_DISPONIBILI, quantita, titolo);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static int getBigliettiDisponibili(Connection connection, int codServizio) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_BIGLIETTI_DISPONIBILI, codServizio);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getInt("BigliettiDisponibili");
                } else {
                    throw new DAOException("Non è stata trovata la quantità di biglietti disponibili");
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static int getCodiceServizio(Connection connection, String titolo) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CODICE_SERVIZIO, titolo);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getInt("CodServizio");
                } else {
                    throw new DAOException("Non è stato trovato nessun codice servizio!");
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
