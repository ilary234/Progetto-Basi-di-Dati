package model;

import java.util.Date;
import java.util.Objects;

public abstract class Dipendente {

    protected final String CF; //char(16) not null,
    protected final String nome; //varchar(20) not null,
    protected final String cognome; //varchar(20) not null,
    protected final Date dataNascita; //date not null,
    protected final String luogoNascita; //varchar(20) not null,
    protected String residenza; //varchar(15) not null,
    protected String telefono; //varchar(15) not null,


    public Dipendente(String cF, String nome, String cognome, Date dataNascita, String luogoNascita, String residenza,
            String telefono) {
        this.CF = Objects.requireNonNull(cF);
        this.nome = Objects.requireNonNull(nome);
        this.cognome = Objects.requireNonNull(cognome);
        this.dataNascita = Objects.requireNonNull(dataNascita);
        this.luogoNascita = Objects.requireNonNull(luogoNascita);
        this.residenza = Objects.requireNonNull(residenza);
        this.telefono = Objects.requireNonNull(telefono);
    }

}
