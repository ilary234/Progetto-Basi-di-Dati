package model;

import java.sql.Connection;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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

public class Ordine {

    private final int codOrdine;
    private final String tipoPagamento;
    private final LocalTime orarioAcquisto;
    private final SimpleDateFormat dateFormat;
    private final Date dataAcquisto;
    private final float costoTotale;
    private final String acquirente;
    
    public Ordine(int codOrdine, String tipoPagamento, LocalTime orarioAcquisto, Date dataAcquisto, float costoTotale, String acquirente) {
        this.codOrdine = Objects.requireNonNull(codOrdine);
        this.tipoPagamento = Objects.requireNonNull(tipoPagamento);
        this.orarioAcquisto = Objects.requireNonNull(orarioAcquisto);
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.dataAcquisto = Objects.requireNonNull(dataAcquisto);
        this.costoTotale = Objects.requireNonNull(costoTotale);
        this.acquirente = Objects.requireNonNull(acquirente);
    }

    public static final class DAO {

        public static int creaNuovoOrdine(Connection connection, Time orario, Date data, String username) {
            try (
                var statement = connection.prepareStatement(
                    Queries.INSERT_ORDINE,
                    java.sql.Statement.RETURN_GENERATED_KEYS
                )
            ) {
                statement.setObject(1, null);
                statement.setTime(2, orario);
                statement.setDate(3, new java.sql.Date(data.getTime()));
                statement.setString(4, "");
                statement.setFloat(5, 0);
                statement.setString(6, username);
                
                statement.executeUpdate();
                try (var rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else {
                        throw new DAOException("Nessun codice ordine generato!");
                    }
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Integer getOrdineAperto(Connection connection, String username) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ORDINE_APERTO,username, "");
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getInt("CodOrdine");
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return null;
        }

        public static void updateCostoTotale(Connection connection, int codOrdine, float costoDaAggiungere) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_COSTO_TOTALE, costoDaAggiungere, codOrdine);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void updateDatiOrdine(Connection connection, Time orario, Date data, String tipoPagamento, String username, int codice, float prezzoScontato) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_DATI_ORDINE, orario, data, tipoPagamento, prezzoScontato, username, codice);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<String> getDettagliOrdineAperto(Connection connection, int codOrdine) {
            List<String> dettagli = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_DETTAGLI_ORDINE_APERTO, codOrdine, codOrdine);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    String titolo = resultSet.getString("Titolo");
                    String categoria = resultSet.getString("Categoria");
                    int quantita = resultSet.getInt("Quantita");
                    dettagli.add(titolo + " - " + categoria + " x" + quantita);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return dettagli;
        }

        public static List<String> getOrdiniPrecedenti(Connection connection, String username) {
            List<String> dettagli = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_ORDINI_PRECEDENTI, "", username);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    String codOrdine = String.valueOf(resultSet.getInt("CodOrdine"));
                    Date data = resultSet.getDate("DataAcquisto");
                    float costoTotale = resultSet.getFloat("CostoTotale");
                    String costoTotaleStr = String.format("%.2f", costoTotale);
                    dettagli.add("Ordine #" + codOrdine + " - Data: " + data.toString() + " - Totale: " + costoTotaleStr + "€");
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return dettagli;
        }

        public static float getCostoTotale(Connection connection, int codOrdine) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_COSTO_TOTALE, codOrdine);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getFloat("CostoTotale");
                } else {
                    throw new DAOException("Non è stato trovato nessun costo totale!");
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static Map<String, Float> getCategoriePrezzi(Connection connection, int codOrdine) {
            Map<String, Float> dettagli = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CATEGORIE_PREZZI, codOrdine, codOrdine);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    String categoria = resultSet.getString("Categoria");
                    Float totale = resultSet.getFloat("TotaleCategoria");
                    dettagli.put(categoria, totale);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return dettagli;
        }

        public static Map<String, Float> getSconti(Connection connection, int codOrdine) {
            Map<String, Float> dettagli = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_SCONTI, codOrdine);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    String categoria = resultSet.getString("Categoria");
                    Float sconto = resultSet.getFloat("Sconto");
                    dettagli.put(categoria, sconto);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return dettagli;
        }

        public static void updateCostoOrdine(Connection connection, float costo, int codOrdine) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_COSTO_ORDINE, costo, codOrdine);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
