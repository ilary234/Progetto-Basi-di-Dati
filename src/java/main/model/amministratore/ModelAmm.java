package model.amministratore;

import java.sql.Connection;
import java.util.Objects;

public class ModelAmm {

    private final Connection connection;

    public ModelAmm(Connection connection) {
        Objects.requireNonNull(connection, "Model created with null connection");
        this.connection = connection;
    }

}
