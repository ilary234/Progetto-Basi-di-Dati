package model;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Committente {

    public static final class DAO {

        public static Map<Integer, String> getCommittentiNames(Connection connection) {
            Map<Integer, String> committenti = new HashMap<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_COMMITTENTI_NAMES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codice = resultSet.getInt("CodCommittente");
                    var nominativo = resultSet.getString("Nominativo");
                    committenti.put(codice, nominativo);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return committenti;
        }
    }

}
