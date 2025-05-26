package model;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import com.mysql.cj.conf.ConnectionUrlParser.Pair;

public class Autista extends Dipendente{

    private String numeroPatente; //char(10) not null,
    private String tipologiaPatente; //varchar(2) not null,
    private String scadenzaPatente; //date not null,
    private String scadenzaCQC; //date not null,

    private Optional<Pair<Integer, String>> Kb = Optional.empty();

    public Autista(String cF, String nome, String cognome, Date dataNascita, String luogoNascita, String residenza,
            String telefono, String numeroPatente, String tipoPatente, String scadenzaPatente, String scadenzaCQC) {

        super(cF, nome, cognome, dataNascita, luogoNascita, residenza, telefono);
        this.scadenzaCQC = Objects.requireNonNull(scadenzaCQC);
        this.setPatente(numeroPatente, tipoPatente, scadenzaPatente);
    }

    public Autista(String cF, String nome, String cognome, Date dataNascita, String luogoNascita, String residenza,
            String telefono, String numeroPatente, String tipoPatente, String scadenzaPatente, String scadenzaCQC, 
            int numeroKb, String scadenzaKb) {

        this(cF, nome, cognome, dataNascita, luogoNascita, residenza, telefono, 
                numeroPatente, tipoPatente, scadenzaPatente, scadenzaCQC);
        this.setKb(numeroKb, scadenzaKb);
    }

    public void setPatente(String numeroPatente, String tipoPatente, String scadenzaPatente) {
        this.numeroPatente = Objects.requireNonNull(numeroPatente);
        this.tipologiaPatente = Objects.requireNonNull(tipoPatente);
        this.scadenzaPatente = Objects.requireNonNull(scadenzaPatente);
    }

    public void setKb(int numeroKb, String scadenzaKb) {
        this.Kb = Optional.of(new Pair<>(numeroKb, scadenzaKb));
    }


}
