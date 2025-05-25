package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Comunicazione {

    private final int codComunicazione;
    private final String titolo;
    private final String descrizione;
    private final SimpleDateFormat dateFormat;
    private final Date dataPubblicazione;
    private final int codImpiegato;

    public Comunicazione(int codComunicazione, String titolo, String descrizione, Date dataPubblicazione, int codImpiegato) {
        this.codComunicazione = Objects.requireNonNull(codComunicazione);
        this.titolo = Objects.requireNonNull(titolo);
        this.descrizione = Objects.requireNonNull(descrizione);
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.dataPubblicazione = Objects.requireNonNull(dataPubblicazione);
        this.codImpiegato = Objects.requireNonNull(codImpiegato) ;
    }
}
