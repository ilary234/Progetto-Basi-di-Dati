package model;

import java.util.Objects;

public class Volanda {

    private final int numeroVolanda; //integer not null,
    private final int codServizio; //integer not null,
    private String note; //varchar(200) not null,
    private String fornitore; //varchar(50),
    private float prezzo; // float not null check (`Prezzo` > 0),
    private int km; //integer not null check (`Km` > 0),

    private String autista;
    private int mezzo;
    private int committente;

    public Volanda(int numeroVolanda, int codServizio, String note, String fornitore, float prezzo, int km) {
        this.numeroVolanda = Objects.requireNonNull(numeroVolanda);
        this.codServizio = Objects.requireNonNull(codServizio);
        this.note = Objects.requireNonNull(note);
        this.fornitore = fornitore;
        this.prezzo = Objects.requireNonNull(prezzo);
        this.km = Objects.requireNonNull(km);
    }

    public void setAutista(final String cF) {
        this.autista = cF;
    }

    public void setMezzo(final int mezzo) {
        this.mezzo = mezzo;
    }

    public void setCommittente(final int committente) {
        this.committente = committente;
    }

    

}
