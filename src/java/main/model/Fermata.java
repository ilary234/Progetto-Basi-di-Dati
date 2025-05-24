package model;

import java.util.Objects;

public class Fermata {

    private final int numeroFermata; //smallint not null auto_increment,
    private final String via;  //varchar(30) not null,
    private final int numeroCivico; //smallint not null,

    public Fermata(int numeroFermata, String via, int numeroCivico) {
        this.numeroFermata = Objects.requireNonNull(numeroFermata);
        this.via = Objects.requireNonNull(via);
        this.numeroCivico = Objects.requireNonNull(numeroCivico);
    }

}
