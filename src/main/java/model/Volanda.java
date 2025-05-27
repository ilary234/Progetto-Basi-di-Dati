package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Volanda {

    private final int numeroVolanda; //integer not null,
    private final int codServizio; //integer not null,
    private String note; //varchar(200) not null,
    private String fornitore; //varchar(50),
    private float prezzo; // float not null check (`Prezzo` > 0),
    private int km; //integer not null check (`Km` > 0),

    private String autista;
    private int mezzo;
    private String committente;

    public Volanda(int numeroVolanda, int codServizio, String note, String fornitore, float prezzo, int km,
                    String autista, int mezzo, String committente) {
        this.numeroVolanda = Objects.requireNonNull(numeroVolanda);
        this.codServizio = Objects.requireNonNull(codServizio);
        this.note = Objects.requireNonNull(note);
        this.fornitore = fornitore;//.isBlank() ? fornitore : "";
        this.prezzo = Objects.requireNonNull(prezzo);
        this.km = Objects.requireNonNull(km);
        this.autista = autista;
        this.mezzo = mezzo;
        this.committente = committente;
    }

    /*public void setAutista(final String cF) {
        this.autista = cF;
    }

    public void setMezzo(final int mezzo) {
        this.mezzo = mezzo;
    }

    public void setCommittente(final int committente) {
        this.committente = committente;
    }*/

    public int getNumeroVolanda() {
        return numeroVolanda;
    }

    public int getCodServizio() {
        return codServizio;
    }

    public String getNote() {
        return note;
    }

    public String getFornitore() {
        return fornitore;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public int getKm() {
        return km;
    }

    public String getAutista() {
        return autista;
    }

    public int getMezzo() {
        return mezzo;
    }

    public String getCommittente() {
        return committente;
    }

    public static final class DAO {

        public static List<Volanda> find(Connection connection, Date data) {
            List<Volanda> volande = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_VOLANDE, data);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var numeroVolanda = resultSet.getInt("NumeroVolanda");
                    var codServizio = resultSet.getInt("CodServizio");
                    var note = resultSet.getString("Note");
                    var fornitore = resultSet.getString("Fornitore");
                    var prezzo = resultSet.getFloat("Prezzo");
                    var km = resultSet.getInt("Km");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var mezzo = resultSet.getInt("Mezzo");
                    var committente = resultSet.getString("Committente");

                    var autista = cognome == null ? "" : cognome + " " + nome;

                    var volanda = new Volanda(numeroVolanda, codServizio, note, fornitore, prezzo, km, 
                                                autista, mezzo, committente);
                    volande.add(volanda);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return volande;
        }

        public static void deleteVolanda(Connection connection, Date date, int numeroVolanda) {
            try (
                var statement1 = DAOUtils.prepare(connection, Queries.REMOVE_VOLANDA, date, numeroVolanda);
            ) {
                statement1.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

}
