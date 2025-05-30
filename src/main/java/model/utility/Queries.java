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

    public static final String GET_SERVIZI = 
        """
            select s.*, NomeLinea as Categoria
            from Servizi s, CategorieServizi c
            where s.CodServizio = c.CodServizio
            union
            select s.*, NomeTransfer as Categoria
            from Servizi s, Classiservizi c
            where s.CodServizio = c.CodServizio
            order by CodServizio;
        """;

    public static final String GET_CATEGORIE =
        """
            select NomeLinea as Categoria
            from Categorieservizi
            union
            select NomeTransfer as Categoria
            from Classiservizi;
        """;

    public static final String INSERT_SERVIZIO = 
        """
            insert into servizi
            values (?, ?, ?, ?, ?)
        """;

    public static final String INSERT_CATEGORIA = 
        """
            insert into CategorieServizi
            values (?, ?)
        """;

    public static final String INSERT_CLASSE = 
        """
            insert into ClassiServizi
            values (?, ?)
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
            select CodCommittente, Nominativo
            from Committenti
        """;
    
    public static final String UPDATE_GUIDA = 
        """
            update guida
            set Autista = ?
            where Data = ?
            and NumeroVolanda = ?
        """;

    public static final String DELETE_GUIDA = 
        """
            delete from guida
            where Data = ?
            and NumeroVolanda = ?
        """;

    public static final String INSERT_GUIDA = 
        """
            insert into guida
            values (?, ?, ?)
        """;
    
    public static final String UPDATE_VEICOLOVOLANDA = 
        """
            update veicoloVolanda
            set NumeroMezzo = ?
            where Data = ?
            and NumeroVolanda = ?
        """;

    public static final String INSERT_VEICOLOVOLANDA = 
        """
            insert into veicoloVolanda
            values (?, ?, ?)
        """;

    public static final String DELETE_VEICOLOVOLANDA = 
        """
            delete from veicoloVolanda
            where Data = ?
            and NumeroVolanda = ?
        """;

    public static final String UPDATE_COMMITTENTE = 
        """
            update commissioni
            set CodCommittente = ?
            where Data = ?
            and NumeroVolanda = ?
        """;

    public static final String INSERT_COMMITTENTE = 
        """
            insert into commissioni
            values (?, ?, ?)
        """;

    public static final String DELETE_COMMITTENTE = 
        """
            delete from commissioni
            where Data = ?
            and NumeroVolanda = ?
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
}
