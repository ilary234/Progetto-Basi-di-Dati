package model.utility;

public final class Queries {

    public static final String REMOVE_DATE = 
        """
            delete from giornaliere
            where Data = ?
        """;

    public static final String REMOVE_VOLANDA = 
        """
            delete from volande
            where Data = ?
            and NumeroVolanda = ?
        """;

    public static final String GET_VOLANDE = 
        """
            select * 
            from VolandeRelazioni 
            where Data = ?
        """;

    public static final String FIND_VOLANDE = 
        """
            select NumeroVolanda 
            from VolandeRelazioni 
            where Data = ?
        """;

    public static final String GET_DATES =
        """
            select Data
            from giornaliere
            order by Data
        """;

    public static final String FIND_IMPIEGATO =
        """
            select *
            from impiegati
            where CodImpiegato = ?
            and Password = ?
        """;

    public static final String GET_TITLES =
        """
            SELECT Titolo
            FROM annunciservizi
        """;

    public static final String GET_LINES = 
        """
            SELECT *
            FROM CategorieServizi
        """;
}
