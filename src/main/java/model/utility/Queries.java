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

    public static final String GET_SERVIZI__NO_ANNUNCIO_CODES = 
        """
            select CodServizio
            from Servizi
            where CodServizio not in (select CodServizio
							            from annunciservizi)
            order by CodServizio
        """;

    public static final String GET_SERVIZI = 
        """
            select *
            from servizicategorie;
        """;

    public static final String GET_SERVIZI_STATISTICS = 
        """
            select CodServizio, Categoria, NumeroBigliettiVenduti
            from servizicategorie
            order by NumeroBigliettiVenduti desc
            limit 10
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
    
    public static final String GET_BIGLIETTI_STATISTICS = 
        """
            select monthname(DataOraAcquisto) as Mese, count(*) as `Biglietti venduti`
            from biglietti
            where year(DataOraAcquisto) = ?
            group by month(DataOraAcquisto)
            order by count(*) desc;
        """;
    
    public static final String GET_AUTISTI_STATISTICS = 
        """
            select v.CodServizio, Categoria
            from volande v, guida g, (select CodServizio, Categoria
                                        from servizicategorie) as s
            where v.NumeroVolanda = g.NumeroVolanda
            and v.Data = g.Data
            and v.CodServizio = s.CodServizio
            and g.Autista = ?
            group by v.CodServizio, Categoria
            order by count(*) desc
            limit 5;
        """;

    public static final String GET_AUTISTI_NAMES = 
        """
            select CF, Nome, Cognome
            from Autisti
        """;
    
    public static final String GET_AUTISTI = 
        """
            select a.*, NumeroKB
            from autisti a left join kb on (a.CF = kb.Proprietario)
        """;

    public static final String INSERT_AUTISTA = 
        """
            insert into autisti
            values (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String INSERT_PATENTE = 
        """
            insert into patenti
            values (?, ?, ?)
        """;

    public static final String INSERT_KB = 
        """
            insert into kb
            values (?, ?, ?)
        """;

    public static final String GET_MEZZI_NUMBERS = 
        """
            select NumeroMezzo
            from Mezzi
            order by NumeroMezzo
        """;

    public static final String GET_MEZZI = 
            """
            select *
            from Mezzi
            order by NumeroMezzo
        """;

    public static final String INSERT_MEZZO = 
        """
            insert into Mezzi
            values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String INSERT_ASSICURAZIONE = 
        """
            insert into Assicurazioni
            values (?, ?, ?, ?)
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
    
    public static final String GET_ANNUNCI = 
        """
            select *
            from annunciServizi
        """;

    public static final String INSERT_ANNUNCIO = 
        """
            insert into annunciServizi
            values (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
    
    public static final String UPDATE_ANNUNCIO = 
        """
            update annunciServizi
            set Titolo = ?,
            Descrizione = ?,
            PrezzoBase = ?,
            Visibile = ?,
            BigliettiDisponibili = ?
            where CodAnnuncio = ?
        """;

    public static final String GET_COMUNICAZIONI = 
        """
            select *
            from comunicazioni
        """;

    public static final String INSERT_COMUNICAZIONE = 
        """
            insert into comunicazioni
            values (?, ?, ?, ?, ?)
        """;

    public static final String UPDATE_COMUNICAZIONE = 
        """
            update comunicazioni
            set Titolo = ?,
            Descrizione = ?
            where CodComunicazione = ?
        """;

    public static final String DELETE_COMUNICAZIONE = 
        """
            delete from comunicazioni
            where CodComunicazione = ?
        """;

    public static final String GET_IMPIEGATI = 
        """
            select *
            from impiegati
        """;
    
    public static final String INSERT_IMPIEGATO = 
        """
            insert into impiegati
            values (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

    public static final String UPDATE_RESIDENZA_IMP =
        """
            update impiegati
            set Residenza = ?
            where CodImpiegato = ?
        """;
    
    public static final String UPDATE_TELEFONO_IMP =
        """
            update impiegati
            set Telefono = ?
            where CodImpiegato = ?
        """;

    public static final String UPDATE_PASSWORD_IMP =
        """
            update impiegati
            set Password = ?
            where CodImpiegato = ?
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
            SELECT CodServizio, Titolo
            FROM AnnunciServizi
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

    public static final String FIND_CATEGORIA =
        """
            SELECT COUNT(*)
            FROM ClassiServizi
            WHERE CodServizio = ?
        """;

    public static final String GET_NAME_TRANSFERS =
        """
            SELECT NomeTipologia, EtàMinima, EtàMassima
            FROM TipoTransfer
            WHERE CategoriaTransfer = (SELECT NomeTransfer
                                       FROM ClassiServizi
                                       WHERE CodServizio = ?)
        """;
    
    public static final String GET_NAME_LINES =
        """
            SELECT Durata
            FROM TipoLinea
            WHERE CategoriaServizio = (SELECT NomeLinea
                                       FROM CategorieServizi
                                       WHERE CodServizio = ?)
        """;

    public static final String GET_IMAGE_LINES =
        """
        SELECT tl.CategoriaServizio
        FROM TipoLinea tl
        JOIN CategorieServizi cs ON tl.CategoriaServizio = cs.NomeLinea
        WHERE cs.CodServizio = ?
        """;

    public static final String GET_IMAGE_TRANSFERS =
        """
        SELECT tt.CategoriaTransfer
        FROM TipoTransfer tt
        JOIN ClassiServizi cs ON tt.CategoriaTransfer = cs.NomeTransfer
        WHERE cs.CodServizio = ?
        """;

}
