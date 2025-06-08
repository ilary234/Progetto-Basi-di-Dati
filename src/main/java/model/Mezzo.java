package model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import model.utility.DAOException;
import model.utility.DAOUtils;
import model.utility.Queries;

public class Mezzo {

    private final int numeroMezzo;
    private final String targa;
    private final int euro;
    private final int annoImmatricolazione;
    private final Date dataRevisione;
    private final int PAX;
    private int kmTotali;
    private boolean CDPD;
    private final String carrozzeria;
    private final String modello;
    private final String telaio;
    private final String numeroLicenzaEuropea;

    private String assicurazione;

    public Mezzo(int numeroMezzo, String targa, String assicurazione, int euro, int annoImmatricolazione,
            Date dataRevisione, int pAX, int kmTotali, boolean cDPD, String carrozzeria, 
            String modello, String telaio, String numeroLicenzaEuropea) {
                
        this.numeroMezzo = Objects.requireNonNull(numeroMezzo);
        this.targa = Objects.requireNonNull(targa);
        this.euro = Objects.requireNonNull(euro);
        this.annoImmatricolazione = Objects.requireNonNull(annoImmatricolazione);
        this.dataRevisione = Objects.requireNonNull(dataRevisione);
        this.PAX = Objects.requireNonNull(pAX);
        this.kmTotali = Objects.requireNonNull(kmTotali);
        this.CDPD = Objects.requireNonNull(cDPD);
        this.carrozzeria = carrozzeria;
        this.modello = modello;
        this.telaio = telaio;
        this.numeroLicenzaEuropea = numeroLicenzaEuropea;
        this.assicurazione = Objects.requireNonNull(assicurazione);
    }

    public int getNumeroMezzo() {
        return numeroMezzo;
    }

    public String getTarga() {
        return targa;
    }

    public int getEuro() {
        return euro;
    }

    public int getAnnoImmatricolazione() {
        return annoImmatricolazione;
    }

    public Date getDataRevisione() {
        return dataRevisione;
    }

    public int getPAX() {
        return PAX;
    }

    public int getKmTotali() {
        return kmTotali;
    }

    public boolean isCDPD() {
        return CDPD;
    }

    public String getCarrozzeria() {
        return carrozzeria;
    }

    public String getModello() {
        return modello;
    }

    public String getTelaio() {
        return telaio;
    }

    public String getNumeroLicenzaEuropea() {
        return numeroLicenzaEuropea;
    }

    public String getAssicurazione() {
        return assicurazione;
    }

    public static List<String> getMezziTypes() {
        return List.of("Pullman", "Vettura");
    }

    public static final class DAO {

        public static List<Integer> getMezziNumbers(Connection connection) {
            List<Integer> mezzi = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_MEZZI_NUMBERS);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var number = resultSet.getInt("NumeroMezzo");
                    mezzi.add(number);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return mezzi;
        }

        public static List<Mezzo> getMezzi(Connection connection) {
            List<Mezzo> mezzi = new ArrayList<>();
            try (
                var statement = DAOUtils.prepare(connection, Queries.GET_MEZZI);
                var resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                    var number = resultSet.getInt("NumeroMezzo");
                    var targa = resultSet.getString("Targa");
                    var euro = resultSet.getInt("Euro");
                    var annoImmatricolazione = resultSet.getInt("AnnoImmatricolazione");
                    var dataRevisione = resultSet.getDate("DataRevisione");
                    var PAX = resultSet.getInt("PAX");
                    var kmTotali = resultSet.getInt("KmTotali");
                    var CDPD = resultSet.getBoolean("CDPD");
                    var carrozzeria = resultSet.getString("Carrozzeria");
                    var modello = resultSet.getString("Modello");
                    var telaio = resultSet.getString("Telaio");
                    var licenzaEuropea = resultSet.getString("NumeroLicenzaEuropea");
                    var assicurazione = resultSet.getString("Assicurazione");

                    var mezzo = new Mezzo(number, targa, assicurazione, euro, annoImmatricolazione, dataRevisione, 
                        PAX, kmTotali, CDPD, carrozzeria, modello, telaio, licenzaEuropea);

                    mezzi.add(mezzo);
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
            return mezzi;
        }

        public static void addMezzo(Connection connection, String numero, String targa, int euro, int immatricolazione,
                String data, String PAX, String kmTotali, boolean CDPD, String tipo, String carrozzeria,
                String modello, String telaio, String licenzaEuropea, String assicurazione) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_MEZZO, numero, targa, assicurazione, euro, immatricolazione, data,
                     PAX, kmTotali, CDPD, tipo, carrozzeria, modello, telaio, licenzaEuropea);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static void addAssicurazione(Connection connection, String numero, String data, String tipologia,
                String durata) {
            try (
                var statement = DAOUtils.prepare(connection, Queries.INSERT_ASSICURAZIONE, numero, data, tipologia, durata);
            ) {
                statement.executeUpdate();
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }
    }

}
