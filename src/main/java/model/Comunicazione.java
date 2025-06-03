package model;

import java.util.Date;
import java.util.Objects;

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
    
}
