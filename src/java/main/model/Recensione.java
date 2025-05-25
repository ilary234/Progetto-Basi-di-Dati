package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Recensione {

    private final String utente; 
    private final int codAnnuncio; 
    private final int valutazione; 
    private final String commento; 
    private final SimpleDateFormat dateFormat;
    private final Date data; 

    public Recensione(String utente, int codAnnuncio, int valutazione, String commento, Date data) {
        this.utente = Objects.requireNonNull(utente);
        this.codAnnuncio = Objects.requireNonNull(codAnnuncio);
        this.valutazione = Objects.requireNonNull(valutazione);
        this.commento = commento;
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.data = Objects.requireNonNull(data);
    }

    // Getters and Setters can be added here as needed
}