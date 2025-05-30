package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Committente {

    private final int codice; //integer not null auto_increment,
    private final String nominativo; //varchar(25) not null,
    private final String telefono; //varchar(15) not null,
    private final String tipo; // not null,
    private final int p_Iva; //integer(11),
    private final String città; //varchar(15),
    private final int CAP; //integer(5),
    private final String provincia; //char(2),
    private final String SD; //char(7),
    private final String email; //varchar(50),
    private final String pec; //varchar(330),

    public Committente(int codice, String nominativo, String telefono, String tipo, int p_Iva, String città, int cAP,
            String provincia, String sD, String email, String pec) {
        this.codice =  Objects.requireNonNull(codice);
        this.nominativo =  Objects.requireNonNull(nominativo);
        this.telefono =  Objects.requireNonNull(telefono);
        this.tipo = tipo;
        this.p_Iva = p_Iva;
        this.città = città;
        CAP = cAP;
        this.provincia = provincia;
        SD = sD;
        this.email = email;
        this.pec = pec;
    }

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
