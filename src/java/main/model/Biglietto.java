package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Biglietto {

    private final int numeroBiglietto; // integer not null auto_increment,
    private final SimpleDateFormat dateFormat;
    private final Date dataOraAcquisto; // datetime not null,
    private final String tipoPagamento; // varchar(15) not null,
    private final float costo; // float not null check (`Costo` > 0),       
    private final String acquirente; // varchar(25) not null,
    private final int codAnnuncio; // integer not null,

    public Biglietto(int numeroBiglietto, Date dataOraAcquisto, String tipoPagamento, float costo, String acquirente, int codAnnuncio) {
        this.numeroBiglietto = Objects.requireNonNull(numeroBiglietto);
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.dataOraAcquisto = Objects.requireNonNull(dataOraAcquisto);
        this.tipoPagamento = Objects.requireNonNull(tipoPagamento);
        this.costo = Objects.requireNonNull(costo);
        this.acquirente = Objects.requireNonNull(acquirente);
        this.codAnnuncio = Objects.requireNonNull(codAnnuncio);
    }
}