package model.amministratore;

import java.sql.Connection;
import java.util.Objects;
import java.util.Optional;

import model.Impiegato;

public class ModelAmm {

    private final Connection connection;
    private Optional<Impiegato> impiegato;

    public ModelAmm(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

    public boolean checkImpiegato(String cod, String pass) {
        this.impiegato = Impiegato.DAO.find(connection, cod, pass);
        if (this.impiegato.isPresent()) {
            return true;
        }
        return false;
    }

}
