package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Comunicazione {

    private final int codComunicazione;
    private final String titolo;
    private final String descrizione;
    private final Date dataPubblicazione;
    private final int codImpiegato;

    public Comunicazione(int codComunicazione, String titolo, String descrizione, Date dataPubblicazione, int codImpiegato) {
        this.codComunicazione = Objects.requireNonNull(codComunicazione);
        this.titolo = Objects.requireNonNull(titolo);
        this.descrizione = Objects.requireNonNull(descrizione);
        this.dataPubblicazione = Objects.requireNonNull(dataPubblicazione);
        this.codImpiegato = Objects.requireNonNull(codImpiegato) ;
    }

    public int getCodComunicazione() {
        return codComunicazione;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Date getDataPubblicazione() {
        return dataPubblicazione;
    }

    public int getCodImpiegato() {
        return codImpiegato;
    }

    public static final class DAO {

        public static void insertComunicazione(Connection connection, String titolo, String descrizione, String data, int codImpiegato) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_COMUNICAZIONE, null, titolo, 
                    descrizione, data, codImpiegato);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void updateComunicazione(Connection connection, int code, String titolo, String descrizione) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.UPDATE_COMUNICAZIONE, titolo, descrizione, code);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static List<Comunicazione> getComunicazioni(Connection connection) {
            List<Comunicazione> comunicazioni = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_COMUNICAZIONI);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var codAnnuncio = resultSet.getInt("CodComunicazione");
                    var titolo = resultSet.getString("Titolo");
                    var dataPubblicazione = resultSet.getDate("DataPubblicazione");
                    var descrizione = resultSet.getString("Descrizione");
                    var codImpiegato = resultSet.getInt("CodImpiegato");

                    var comunicazione = new Comunicazione(codAnnuncio, titolo, descrizione, 
                            dataPubblicazione, codImpiegato);
                    comunicazioni.add(comunicazione);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return comunicazioni;
        }

        public static void deleteComunicazione(Connection connection, int code) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.DELETE_COMUNICAZIONE, code);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }
    
}
