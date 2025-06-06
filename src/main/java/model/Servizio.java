package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Servizio {

    private final int codServizio;
    private final String partenza;
    private final String destinazione;
    private final String orarioPartenza;
    private int bigliettiVenduti;
    private final String categoriaServizio;

    public Servizio(int codServizio, String partenza, String destinazione, String orarioPartenza, int bigliettiVenduti,
            List<Corse> corse, String categoriaServizio) {
        this.codServizio = Objects.requireNonNull(codServizio);
        this.partenza = Objects.requireNonNull(partenza);
        this.destinazione = Objects.requireNonNull(destinazione);
        this.orarioPartenza = Objects.requireNonNull(orarioPartenza);
        this.bigliettiVenduti = Objects.requireNonNull(bigliettiVenduti);
        this.categoriaServizio = Objects.requireNonNull(categoriaServizio);
    }


    public int getCodServizio() {
        return codServizio;
    }

    public String getCategoriaServizio() {
        return categoriaServizio;
    }

    public String getPartenza() {
        return partenza;
    }


    public String getDestinazione() {
        return destinazione;
    }


    public String getOrarioPartenza() {
        return orarioPartenza;
    }


    public int getBigliettiVenduti() {
        return bigliettiVenduti;
    }

    public static final class DAO {

        public static List<Integer> getCodes(Connection connection) {
            List<Integer> codes = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_SERVIZI_CODES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codServizio = resultSet.getInt("CodServizio");
                    codes.add(codServizio);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return codes;
        }

        public static List<Integer> getCodesWithoutAnnuncio(Connection connection) {
            List<Integer> codes = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_SERVIZI__NO_ANNUNCIO_CODES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codServizio = resultSet.getInt("CodServizio");
                    codes.add(codServizio);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return codes;
        }

        public static List<Servizio> getServizi(Connection connection) {
            List<Servizio> servizi = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_SERVIZI);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codServizio = resultSet.getInt("CodServizio");
                    var partenza = resultSet.getString("Partenza");
                    var destinazione = resultSet.getString("Destinazione");
                    var orario = resultSet.getString("OrarioPartenza");
                    var bigliettiVenduti = resultSet.getInt("NumeroBigliettiVenduti");
                    var categoria = resultSet.getString("Categoria");

                    var servizio = new Servizio(codServizio, partenza, destinazione, orario, bigliettiVenduti, List.of(), categoria);
                    servizi.add(servizio);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return servizi;
        }

        public static List<String> getCategorie(Connection connection) {
            List<String> categorie = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_CATEGORIE);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var categoria = resultSet.getString("Categoria");
                    categorie.add(categoria);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return categorie;
        }

        public static void addServizio(Connection connection, String partenza, String destinazione,
                String orario, int biglietti) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_SERVIZIO, null, partenza, destinazione, orario, biglietti);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void addCategoriaServizio(Connection connection, int codice, String categoria) {
            var linee = AnnuncioServizio.DAO.getLines(connection).values();
            var query = "";
            if (linee.contains(categoria)) {
                query = Queries.INSERT_CATEGORIA;
            } else {
                query = Queries.INSERT_CLASSE;
            }
            try (
                var statement = DAOUtils.prepare(connection, query, codice, categoria);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<String> getStatistics(Connection connection) {
            List<String> servizi = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_SERVIZI_STATISTICS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var code = resultSet.getString("CodServizio");
                    var categoria = resultSet.getString("Categoria");
                    var biglietti = resultSet.getInt("NumeroBigliettiVenduti");

                    var servizio = "Cod: " + code + ", " + categoria + ", Biglietti: " + biglietti;
                    servizi.add(servizio);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return servizi;
        }

        public static boolean isTransfer(Connection connection, int codServizio) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_CATEGORIA, codServizio);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    int found = resultSet.getInt(1);
                    return found == 1;
                }
                return false;
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void updateBigliettiServizio(Connection connection, String titolo, int quantita) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_BIGLIETTI_VENDUTI, quantita, titolo);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

}
