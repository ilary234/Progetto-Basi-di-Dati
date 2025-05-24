package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Giornaliera {

    private final SimpleDateFormat dateFormat;
    private final Date data; //date not null,
    private final int codImp; //integer not null,
    private final List<Volanda> volande;

    public Giornaliera(Date data, int codImp, List<Volanda> volande) {
        this.dateFormat = (SimpleDateFormat)SimpleDateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        this.dateFormat.applyPattern("yyyy.MM.dd");
        this.data = Objects.requireNonNull(data);
        this.codImp = Objects.requireNonNull(codImp);
        this.volande = new ArrayList<>(volande);
    }

}
