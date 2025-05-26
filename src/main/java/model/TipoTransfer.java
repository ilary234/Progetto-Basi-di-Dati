package model;

import java.util.Objects;

public class TipoTransfer {

    private final String nomeTipologia;
    private final int etaMinima;
    private final int etaMassima;
    private final float percentualeDaPagare;
    private final String categoriaTransfer;

    public TipoTransfer(String nomeTipologia, int etaMinima, int etaMassima, float percentualeDaPagare, String categoriaTransfer) {
        this.nomeTipologia = Objects.requireNonNull(nomeTipologia);
        this.etaMinima = Objects.requireNonNull(etaMinima);
        this.etaMassima = Objects.requireNonNull(etaMassima);
        this.percentualeDaPagare = Objects.requireNonNull(percentualeDaPagare);
        this.categoriaTransfer = Objects.requireNonNull(categoriaTransfer);
    }
}
