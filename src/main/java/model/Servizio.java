package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Servizio {

    private final int codServizio; //integer not null auto_increment, 
    private final String partenza; //varchar(20) not null,
    private final String destinazione; //varchar(20) not null,
    private final String orarioPartenza; //char(5) not null,
    private int bigliettiVenduti; //smallint not null,
    private final String categoriaServizio; 
    private final List<Corse> corse;

    public Servizio(int codServizio, String partenza, String destinazione, String orarioPartenza, int bigliettiVenduti,
            List<Corse> corse, String categoriaServizio) {
        this.codServizio = Objects.requireNonNull(codServizio);
        this.partenza = Objects.requireNonNull(partenza);
        this.destinazione = Objects.requireNonNull(destinazione);
        this.orarioPartenza = Objects.requireNonNull(orarioPartenza);
        this.bigliettiVenduti = Objects.requireNonNull(bigliettiVenduti);
        this.categoriaServizio = Objects.requireNonNull(categoriaServizio);
        this.corse = new ArrayList<>(corse);
    }

    public int getCodServizio() {
        return codServizio;
    }

    public String getCategoriaServizio() {
        return categoriaServizio;
    }

    

}
