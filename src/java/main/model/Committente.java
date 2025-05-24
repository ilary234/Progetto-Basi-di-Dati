package model;

import java.util.Objects;

public class Committente {

    private final int codice; //integer not null auto_increment,
    private final String nominativo; //varchar(25) not null,
    private final String telefono; //varchar(15) not null,
    private final String tipo; // not null,
    private final int p_Iva; //integer(11),
    private final String città; //varchar(15),
    private final int CAP; //integer(5),
    private final String provincia; //char(2),
    private final String SD; //char(7),
    private final String email; //varchar(50),
    private final String pec; //varchar(330),

    public Committente(int codice, String nominativo, String telefono, String tipo, int p_Iva, String città, int cAP,
            String provincia, String sD, String email, String pec) {
        this.codice =  Objects.requireNonNull(codice);
        this.nominativo =  Objects.requireNonNull(nominativo);
        this.telefono =  Objects.requireNonNull(telefono);
        this.tipo = tipo;
        this.p_Iva = p_Iva;
        this.città = città;
        CAP = cAP;
        this.provincia = provincia;
        SD = sD;
        this.email = email;
        this.pec = pec;
    }

}
