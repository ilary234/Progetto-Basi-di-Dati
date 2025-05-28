package model.utility;

public final class Queries {

    public static final String REMOVE_DATE = 
        """
            delete from giornaliere
            where Data = ?
        """;

    public static final String INSERT_DATE = 
        """
            insert into giornaliere
            values (?, ?)
        """;

    public static final String REMOVE_VOLANDA = 
        """
            delete from volande
            where Data = ?
            and NumeroVolanda = ?
        """;

    public static final String INSERT_VOLANDA = 
        """
            insert into volande
            values (?, ?, ?, ?, ?, ?, ?)
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
    
    public static final String GET_SERVIZI_CODES = 
        """
            select CodServizio
            from Servizi
            order by CodServizio
        """;

    public static final String GET_AUTISTI_NAMES = 
        """
            select CF, Nome, Cognome
            from Autisti
        """;

    public static final String GET_MEZZI_NUMBERS = 
        """
            select NumeroMezzo
            from Mezzi
            order by NumeroMezzo
        """;

    public static final String GET_COMMITTENTI_NAMES = 
        """
            select Nominativo
            from Committenti
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
