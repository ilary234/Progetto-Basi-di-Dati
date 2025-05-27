package model.utility;

public final class Queries {

    public static final String GET_DATES =
        """
            select Data
            from giornaliere
        """;

    public static final String FIND_IMPIEGATO =
        """
            select *
            from impiegati as i
            where i.CodImpiegato = ?
            and i.Password = ?
        """;

    public static final String GET_TITLES =
        """
            select Titolo
            from annunciservizi
        """;
}
