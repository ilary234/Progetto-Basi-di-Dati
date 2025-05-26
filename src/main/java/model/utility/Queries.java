package model.utility;

public final class Queries {

    public static final String GET_VOLANDE = 
        """
            select * 
            from VolandeRelazioni 
            where Data = ?
        """;

    public static final String GET_DATES =
        """
            select Data
            from giornaliere
        """;

    public static final String FIND_IMPIEGATO =
        """
            select *
            from impiegati
            where CodImpiegato = ?
            and Password = ?
        """;
}
