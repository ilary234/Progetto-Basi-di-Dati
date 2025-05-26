package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Mezzo {

    private final int numeroMezzo; //integer not null,
    private final String targa; //char(7) not null,
    private final int euro; //integer not null,
    private final int annoImmatricolazione; //smallint not null,
    private final SimpleDateFormat dateFormat;
    private final Date dataRevisione; //date not null, 
    private final int PAX; //smallint not null,
    private int kmTotali; //integer not null,
    private boolean CDPD; //boolean not null,
    private final String tipo; //enum('Pullman', 'Vettura') not null, 
    private final String carrozzeria; //varchar(20),
    private final String modello; //varchar(50),
    private final String telaio; //varchar(50),
    private final String numeroLicenzaEuropea; //char(10),

    private String assicurazione; //varchar(20) not null,
    private Date dataInizioValidità; //date not null,
    private String tipologiaAss; //varchar(20) not null,
    private int durataMesi; //smallint not null,

    public Mezzo(int numeroMezzo, String targa, String assicurazione, int euro, int annoImmatricolazione,
            Date dataRevisione, int pAX, int kmTotali, boolean cDPD, String tipo,
            String carrozzeria, String modello, String telaio, String numeroLicenzaEuropea,
            Date dataInizioValidità, String tipologiaAss, int durataMesi) {
                
        this.numeroMezzo = Objects.requireNonNull(numeroMezzo);
        this.targa = Objects.requireNonNull(targa);
        this.euro = Objects.requireNonNull(euro);
        this.annoImmatricolazione = Objects.requireNonNull(annoImmatricolazione);
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.dataRevisione = Objects.requireNonNull(dataRevisione);
        this.PAX = Objects.requireNonNull(pAX);
        this.kmTotali = Objects.requireNonNull(kmTotali);
        this.CDPD = Objects.requireNonNull(cDPD);
        this.tipo = tipo;
        this.carrozzeria = carrozzeria;
        this.modello = modello;
        this.telaio = telaio;
        this.numeroLicenzaEuropea = numeroLicenzaEuropea;

        this.setAssicurazione(assicurazione, dataInizioValidità, tipologiaAss, durataMesi);
    }

    public void setAssicurazione(String assicurazione, Date dataInizioValidità, String tipologiaAss, int durataMesi) {
        this.assicurazione = Objects.requireNonNull(assicurazione);
        this.dataInizioValidità = Objects.requireNonNull(dataInizioValidità);
        this.tipologiaAss = Objects.requireNonNull(tipologiaAss);
        this.durataMesi = Objects.requireNonNull(durataMesi);
    }

}
