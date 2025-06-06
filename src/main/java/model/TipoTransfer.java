package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class TipoTransfer {

    private final String nomeTipologia;
    private final int etaMinima;
    private final int etaMassima;
    private final float percentualeDaPagare;
    private final String categoriaTransfer;

    public TipoTransfer(String nomeTipologia, int etaMinima, int etaMassima, float percentualeDaPagare, String categoriaTransfer) {
        this.nomeTipologia = Objects.requireNonNull(nomeTipologia);
        this.etaMinima = Objects.requireNonNull(etaMinima);
        this.etaMassima = Objects.requireNonNull(etaMassima);
        this.percentualeDaPagare = Objects.requireNonNull(percentualeDaPagare);
        this.categoriaTransfer = Objects.requireNonNull(categoriaTransfer);
    }

    public static final class DAO {

        public static List<String> getTransfers(Connection connection, int codice) {
            List<String> info = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_NAME_TRANSFERS, codice);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var nomeTipologia = resultSet.getString("NomeTipologia");
                    info.add(nomeTipologia);
                    var etaMinima = resultSet.getInt("EtàMinima");
                    info.add(String.valueOf(etaMinima));
                    var etaMassima = resultSet.getInt("EtàMassima");
                    info.add(String.valueOf(etaMassima));
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return info;
        }

        public static String getImageTransfers(Connection connection, String codiceServizio) {
            var code = Integer.valueOf(codiceServizio);
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_IMAGE_TRANSFERS, code);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    String categoria = resultSet.getString("CategoriaTransfer");
                    return categoria;
                } else {
                    return null;
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static float getPercentuale(Connection connection, String categoria) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_PERCENTUALE_TRANSFERS, categoria);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return resultSet.getFloat("PercentualeDaPagare");
                }
                throw new DAOException("Percentuale non trovata per la categoria: " + categoria);
            } catch (Exception e) {
                throw new DAOException("Errore durante il recupero della percentuale da pagare", e);
            }
        }

        public static void creaAssociazione(Connection connection, int numeroBiglietto, String nomeTipologia) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_TIPOLOGIA, numeroBiglietto, nomeTipologia);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);  
            }   
        }

        public static void eliminaAssociazione(Connection connection, int codOrdine, String categoria) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.DELETE_TICKETS_TRANSFER, categoria, codOrdine);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);  
            }   
        }
    }
}
