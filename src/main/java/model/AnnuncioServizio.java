package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AnnuncioServizio extends Comunicazione {

    private final SimpleDateFormat dateFormat;
    private final int codServizio;
    private final float prezzoBase;
    private boolean visibile;
    private final int bigliettiDisponibili;
 
    public AnnuncioServizio(int codAnnuncio, int codServizio, String titolo, String descrizione, Date dataPubblicazione, float prezzoBase, boolean visibile, int bigliettiDisponibili, int codImpiegato) {
        super(codAnnuncio, titolo, descrizione, dataPubblicazione, codImpiegato);
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.codServizio = Objects.requireNonNull(codServizio);
        this.prezzoBase = Objects.requireNonNull(prezzoBase);
        this.visibile = Objects.requireNonNull(visibile);
        this.bigliettiDisponibili = Objects.requireNonNull(bigliettiDisponibili);
    }

}
