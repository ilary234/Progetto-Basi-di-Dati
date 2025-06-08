package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Biglietto {

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

        public static int creaBiglietto(Connection connection, float costoBiglietto, int codAnnuncio, int codOrdine) {
            try (
                var statement = connection.prepareStatement(
                    Queries.INSERT_BIGLIETTO,
                    java.sql.Statement.RETURN_GENERATED_KEYS
                )
            ) {
                statement.setObject(1, null);
                statement.setFloat(2, costoBiglietto);
                statement.setInt(3, codAnnuncio);
                statement.setInt(4, codOrdine);
                statement.executeUpdate();

                try (var rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    } else {
                        throw new DAOException("Nessun numero biglietto generato!");
                    }
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Integer> getBigliettiDaRimuovere(Connection connection, int codOrdine, String categoria) {
            List<Integer> info = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_BIGLIETTI_DA_RIMUOVERE, categoria, codOrdine, categoria, codOrdine);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var numeroBiglietto = resultSet.getInt("NumeroBiglietto");
                    info.add(numeroBiglietto);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return info;
        }

        public static void eliminaBiglietto(Connection connection, int numeroBiglietto) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.DELETE_TICKET, numeroBiglietto);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);  
            } 
        }

        public static float getCostoBiglietto(Connection connection, int numeroBiglietto) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_COSTO_BIGLIETTO, numeroBiglietto);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                        return resultSet.getFloat("Costo");
                    } else {
                        throw new DAOException("Nessun numero biglietto generato!");
                    }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}
