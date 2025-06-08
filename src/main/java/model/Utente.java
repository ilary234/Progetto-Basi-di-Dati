package model;

import java.sql.Connection;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Utente {

    public static final class DAO {

        public static boolean findUtente(Connection connection, String username, String password) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_UTENTE, username, password);
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

        public static void inserisciUtente(Connection connection, String username, String password, String nome, String cognome, String email) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_USER, username, password, nome, cognome, email);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
}