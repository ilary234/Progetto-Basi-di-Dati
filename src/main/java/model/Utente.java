package model;

import java.sql.Connection;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Utente {
    
    private final String username; 
    private final String password; 
    private final String nome; 
    private final String cognome; 
    private final String email; 

    public Utente(String username, String password, String nome, String cognome, String email) {
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.nome = Objects.requireNonNull(nome);
        this.cognome = Objects.requireNonNull(cognome);
        this.email = email;
    }

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
    }
}