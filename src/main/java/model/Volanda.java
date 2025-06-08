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

    private final int numeroVolanda;
    private final int codServizio;
    private String note;
    private String fornitore;
    private float prezzo;
    private int km;

    private String autista;
    private int mezzo;
    private String committente;

    public Volanda(int numeroVolanda, int codServizio, String note, String fornitore, float prezzo, int km,
                    String autista, int mezzo, String committente) {
        this.numeroVolanda = Objects.requireNonNull(numeroVolanda);
        this.codServizio = Objects.requireNonNull(codServizio);
        this.note = Objects.requireNonNull(note);
        this.fornitore = fornitore;
        this.prezzo = Objects.requireNonNull(prezzo);
        this.km = Objects.requireNonNull(km);
        this.autista = autista;
        this.mezzo = mezzo;
        this.committente = committente;
    }


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

        public static void insertVolanda(Connection connection, Date date, int numVolanda, int codServizio, String note,
                String fornitore, float prezzo, int km) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_VOLANDA, date, numVolanda, codServizio, note, fornitore, prezzo, km);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void updateAutista(Connection connection, String date, int numeroVolanda, String autista,
                int resAutista) {
            try (
                var statement = switch (resAutista) {
                case 1 -> DAOUtils.prepare(connection, Queries.UPDATE_GUIDA, autista, date, numeroVolanda);
                case 2 ->  DAOUtils.prepare(connection, Queries.INSERT_GUIDA, date, numeroVolanda, autista);
                default ->  DAOUtils.prepare(connection, Queries.DELETE_GUIDA, date, numeroVolanda);
                };
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void updateMezzo(Connection connection, String date, int numeroVolanda, int mezzo,
                int resMezzo) {
            try (
                var statement = switch (resMezzo) {
                case 1 -> DAOUtils.prepare(connection, Queries.UPDATE_VEICOLOVOLANDA, mezzo, date, numeroVolanda);
                case 2 ->  DAOUtils.prepare(connection, Queries.INSERT_VEICOLOVOLANDA, date, numeroVolanda, mezzo);
                default ->  DAOUtils.prepare(connection, Queries.DELETE_VEICOLOVOLANDA, date, numeroVolanda);
                };
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void updateCommittente(Connection connection, String date, int numeroVolanda, int codCommittente,
                int resCommittente) {
            try (
                var statement = switch (resCommittente) {
                case 1 -> DAOUtils.prepare(connection, Queries.UPDATE_COMMITTENTE, codCommittente, date, numeroVolanda);
                case 2 ->  DAOUtils.prepare(connection, Queries.INSERT_COMMITTENTE, date, numeroVolanda, codCommittente);
                default ->  DAOUtils.prepare(connection, Queries.DELETE_COMMITTENTE, date, numeroVolanda);
                };
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

    }

}
