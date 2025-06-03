package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Autista extends Dipendente{

    private String numeroPatente; //char(10) not null,
    private Date scadenzaCQC; //date not null,
    private int numeroKB;

    public Autista(String cF, String nome, String cognome, Date dataNascita, String luogoNascita, String residenza,
            String telefono, String numeroPatente, Date scadenzaCQC, int numeroKb) {

        super(cF, nome, cognome, dataNascita, luogoNascita, residenza, telefono);
        this.scadenzaCQC = Objects.requireNonNull(scadenzaCQC);
        this.numeroPatente = Objects.requireNonNull(numeroPatente);
        this.numeroKB = numeroKb;
    }

    public String getNumeroPatente() {
        return numeroPatente;
    }

    public Date getScadenzaCQC() {
        return scadenzaCQC;
    }

    public int getNumeroKB() {
        return numeroKB;
    }

    public static final class DAO {

        public static List<String> getAutistiNames(Connection connection) {
            List<String> autisti = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_AUTISTI_NAMES);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var CF = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");

                    var autista = cognome + " " + nome + ", " + CF;
                    autisti.add(autista);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return autisti;
        }

        public static List<Autista> getAutisti(Connection connection) {
            List<Autista> autisti = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_AUTISTI);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var CF = resultSet.getString("CF");
                    var nome = resultSet.getString("Nome");
                    var cognome = resultSet.getString("Cognome");
                    var dataNascita = resultSet.getDate("DataNascita");
                    var luogoNascita = resultSet.getString("LuogoNascita");
                    var residenza = resultSet.getString("Residenza");
                    var telefono = resultSet.getString("Telefono");
                    var patente = resultSet.getString("NumeroPatente");
                    var scadenzaCQC = resultSet.getDate("ScadenzaCQC");
                    var numeroKb = resultSet.getInt("NumeroKB");

                    var autista = new Autista(CF, nome, cognome, dataNascita, luogoNascita, residenza, telefono, 
                        patente, scadenzaCQC, numeroKb);
                    autisti.add(autista);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return autisti;
        }

        public static void addAutista(Connection connection, String cf, String patente, String nome, String cognome,
                String dataNascita, String luogoNascita, String residenza, String telefono, String scadenzaCQC) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_AUTISTA, cf, patente, nome, cognome, dataNascita, luogoNascita, 
                                residenza, telefono, scadenzaCQC);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void addPatente(Connection connection, String numero, String tipologia, String scadenza) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_PATENTE, numero, tipologia, scadenza);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void addKB(Connection connection, int numero, String cf, String scadenza) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_KB, numero, cf, scadenza);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<String> getStatistics(Connection connection, String cf) {
            List<String> servizi = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_AUTISTI_STATISTICS, cf);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var code = resultSet.getString("CodServizio");
                    var categoria = resultSet.getString("Categoria");

                    var servizio = "Cod: " + code + ", " + categoria;
                    servizi.add(servizio);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return servizi;
        }
    }

}
