package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Corse {

    private final int codPercorso; //integer not null,
    private final String orarioPartenza; //char(5) not null,
    private final String nomeLinea; // varchar(20) not null,

    private final List<Fermata> fermate;
    //Mappa dei collegamenti?

    public Corse(int codPercorso, String orarioPartenza, String nomeLinea, List<Fermata> fermate) {
        this.codPercorso = Objects.requireNonNull(codPercorso);
        this.orarioPartenza = Objects.requireNonNull(orarioPartenza);
        this.nomeLinea = Objects.requireNonNull(nomeLinea);
        this.fermate = new ArrayList<>(fermate);
    }

    

}
