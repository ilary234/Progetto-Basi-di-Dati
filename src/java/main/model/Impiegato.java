package model;

import java.util.Objects;

public class Impiegato extends Dipendente {

    private final int codImpiegato; //integer not null auto_increment,
    private String password; //varchar(50) not null,

    public Impiegato(String cF, String nome, String cognome, String dataNascita, String luogoNascita, String residenza,
            String telefono, int codImpiegato, String password) {

        super(cF, nome, cognome, dataNascita, luogoNascita, residenza, telefono);
        this.codImpiegato = Objects.requireNonNull(codImpiegato);
        this.password = Objects.requireNonNull(password);
    }

}
