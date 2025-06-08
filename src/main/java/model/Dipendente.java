package model;

import java.util.Date;
import java.util.Objects;

public abstract class Dipendente {

    protected final String CF;
    protected final String nome;
    protected final String cognome;
    protected final Date dataNascita;
    protected final String luogoNascita;
    protected String residenza;
    protected String telefono;


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

    public String getCF() {
        return CF;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public String getResidenza() {
        return residenza;
    }

    public String getTelefono() {
        return telefono;
    }
    
}
