package model.utente;

import java.sql.Connection;
import java.util.Objects;

public class ModelUser {

    private final Connection connection;

    public ModelUser(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

}
