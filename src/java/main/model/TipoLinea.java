package model;

import java.util.Objects;

public class TipoLinea {

    private final String nomeTipo;
    private final String durata;
    private final float percentualeDaPagare;
    private final String categoriaServizio;

    public TipoLinea(String nomeTipo, String durata, float percentualeDaPagare, String categoriaServizio) {
        this.nomeTipo = Objects.requireNonNull(nomeTipo);
        this.durata = Objects.requireNonNull(durata);
        this.percentualeDaPagare = Objects.requireNonNull(percentualeDaPagare);
        this.categoriaServizio = Objects.requireNonNull(categoriaServizio);
    }
}