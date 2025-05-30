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

    public static final String GET_GP = 
        """
            SELECT *
            FROM ClassiServizi
            WHERE NomeTransfer = 'Giro Panoramico'
        """;
    
    public static final String GET_CONCERTS = 
        """
            SELECT CodServizio, Titolo
            FROM AnnunciServizi
            WHERE CodServizio IN (SELECT CodServizio
                                  FROM ClassiServizi
                                  WHERE NomeTransfer = 'Concerto')
        """;

    public static final String GET_TRIPS = 
        """
            SELECT CodServizio, Titolo
            FROM AnnunciServizi
            WHERE CodServizio IN (SELECT CodServizio
                                  FROM ClassiServizi
                                  WHERE NomeTransfer = 'Escursione')
        """;

    public static final String FIND_SERVIZIO =
        """
            SELECT CodAnnuncio, Titolo, Descrizione, PrezzoBase
            FROM AnnunciServizi
            WHERE CodServizio = ?
        """;

    public static final String AVG_RATING =
        """
            SELECT AVG(Valutazione) AS MediaValutazioni
            FROM Recensioni
            WHERE CodAnnuncio = ?;
        """;

    public static final String GET_REVIEWS =
        """
            SELECT Utente, Valutazione, Commento, Data
            FROM Recensioni
            WHERE CodAnnuncio = ?;
        """;
    
    public static final String FIND_UTENTE =
        """
            SELECT COUNT(*)
            FROM Utenti
            WHERE Username = ?
            and Password = ?
        """;
    
    public static final String INSERT_REVIEW =
        """
            INSERT INTO Recensioni (Utente, CodAnnuncio, Valutazione, Commento, Data)
            VALUES (?, ?, ?, ?, ?);
        """;

}
