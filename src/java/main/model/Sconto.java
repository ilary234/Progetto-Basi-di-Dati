package model;

import java.util.Objects;

public class Sconto {

    private final String tipoBiglietto;
    private final float sconto;
    private final int quantita;

    public Sconto(String tipoBiglietto, float sconto, int quantita) {
        this.tipoBiglietto = Objects.requireNonNull(tipoBiglietto);
        this.sconto = Objects.requireNonNull(sconto);
        this.quantita = Objects.requireNonNull(quantita);
    }

}