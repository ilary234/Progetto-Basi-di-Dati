package model;

import java.sql.Connection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Impiegato extends Dipendente {

    private final int codImpiegato; //integer not null auto_increment,
    private String password; //varchar(50) not null,

    public Impiegato(String cF, String nome, String cognome, Date dataNascita, String luogoNascita, String residenza,
            String telefono, int codImpiegato, String password) {

        super(cF, nome, cognome, dataNascita, luogoNascita, residenza, telefono);
        this.codImpiegato = Objects.requireNonNull(codImpiegato);
        this.password = Objects.requireNonNull(password);
    }

    public static final class DAO {

        public static Optional<Impiegato> find(Connection connection, String codice, String password) {
            Optional<Impiegato> impiegato = Optional.empty();
            var code = Integer.valueOf(codice);
            try (
                var statement = DAOUtils.prepare(connection, Queries.FIND_IMPIEGATO, code, password);
                var resultSet = statement.executeQuery();
            ) {
                if (resultSet.next()) {
                    var cf = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var dataNascita = resultSet.getDate("DataNascita");
                    var luogoNascita = resultSet.getString("LuogoNascita");
                    var residenza = resultSet.getString("Residenza");
                    var telefono = resultSet.getString("Telefono");

                    impiegato = Optional.of(
                        new Impiegato(cf, nome, cognome, dataNascita, luogoNascita, residenza, telefono, code, password));
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return impiegato;
        }
    }

}
