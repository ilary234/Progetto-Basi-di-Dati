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
            select monthname(DataAcquisto) as Mese, count(*) as `Biglietti venduti`
            from biglietti b, ordini o
            where b.CodOrdine = o.CodOrdine
            and year(DataAcquisto) = ?
            group by month(DataAcquisto)
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
            SELECT CodServizio, Titolo 
            FROM AnnunciServizi 
            WHERE CodServizio IN (SELECT CodServizio 
                                  FROM CategorieServizi) 
        """;

    public static final String GET_GP = 
        """
            SELECT CodServizio, Titolo 
            FROM AnnunciServizi 
            WHERE CodServizio IN (SELECT CodServizio 
                                  FROM ClassiServizi 
                                  WHERE NomeTransfer = 'Giro panoramico') 
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
            WHERE CodAnnuncio = ?
        """;

    public static final String GET_REVIEWS =
        """
            SELECT Utente, Valutazione, Commento, Data
            FROM Recensioni
            WHERE CodAnnuncio = ?
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
            INSERT INTO Recensioni
            VALUES (?, ?, ?, ?, ?)
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
            SELECT NomeTipo
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

    public static final String INSERT_USER =
        """
            INSERT INTO Utenti
            VALUES (?, ?, ?, ?, ?)
        """;

    public static final String GET_ORDINE_APERTO =
        """
            SELECT CodOrdine 
            FROM Ordini 
            WHERE Acquirente = ? 
            AND TipoPagamento = ?
        """;

    public static final String GET_ORDINI =
        """
            SELECT CodOrdine, DataAcquisto, CostoTotale 
            FROM Ordini 
            WHERE Acquirente = ? 
            AND TipoPagamento <> '' 
            ORDER BY DataAcquisto DESC  
        """;

    public static final String INSERT_ORDINE =
        """
            INSERT INTO Ordini
            VALUES (?, ?, ?, ?, ?, ?)
        """;

    public static final String UPDATE_COSTO_TOTALE =
        """
            UPDATE Ordini 
            SET CostoTotale = CostoTotale + ? 
            WHERE CodOrdine = ?
        """;

    public static final String UPDATE_DATI_ORDINE =
        """
            UPDATE Ordini 
            SET OraAcquisto = ?, DataAcquisto = ?, TipoPagamento = ?, CostoTotale = ?
            WHERE Acquirente = ?
            AND CodOrdine = ?
        """;

    public static final String GET_PERCENTUALE_TRANSFERS =
        """
            SELECT PercentualeDaPagare
            FROM TipoTransfer
            WHERE NomeTipologia = ?
        """;

    public static final String GET_PERCENTUALE_LINES =
        """
            SELECT PercentualeDaPagare
            FROM TipoLinea
            WHERE NomeTipo = ?
        """;

    public static final String GET_PREZZO_BASE =
        """
            SELECT PrezzoBase
            FROM AnnunciServizi
            WHERE CodAnnuncio = ?
        """;

    public static final String INSERT_BIGLIETTO =
        """
            INSERT INTO Biglietti
            VALUES (?, ?, ?, ?)
        """;

    public static final String INSERT_TIPO =
        """
            INSERT INTO Tipo
            VALUES (?, ?)
        """;
    
    public static final String INSERT_TIPOLOGIA =
        """
            INSERT INTO Tipologia
            VALUES (?, ?)
        """;

    public static final String GET_DETTAGLI_ORDINE_APERTO =
        """
            SELECT a.Titolo AS Titolo, tp.NomeTipo AS Categoria, COUNT(*) AS Quantita
            FROM Biglietti b
            JOIN AnnunciServizi a ON b.CodAnnuncio = a.CodAnnuncio
            JOIN Tipo tp ON b.NumeroBiglietto = tp.NumeroBiglietto
            WHERE b.CodOrdine = ?
            GROUP BY a.Titolo, Categoria
            UNION
            SELECT a.Titolo AS Titolo, tpl.NomeTipologia AS Categoria, COUNT(*) AS Quantita
            FROM Biglietti b
            JOIN AnnunciServizi a ON b.CodAnnuncio = a.CodAnnuncio
            JOIN Tipologia tpl ON b.NumeroBiglietto = tpl.NumeroBiglietto
            WHERE b.CodOrdine = ?
            GROUP BY a.Titolo, Categoria
        """;

    public static final String GET_ORDINI_PRECEDENTI =
        """
            SELECT CodOrdine, DataAcquisto, CostoTotale
            FROM Ordini
            WHERE TipoPagamento <> ?
            AND Acquirente = ?
        """;

    public static final String GET_COSTO_TOTALE =
        """
            SELECT CostoTotale
            FROM Ordini
            WHERE CodOrdine = ?
        """;

    public static final String GET_CATEGORIE_PREZZI =
        """
            SELECT tt.NomeTipologia AS Categoria, SUM(b.Costo) AS TotaleCategoria
            FROM TipoTransfer tt
            JOIN Tipologia t ON t.NomeTipologia = tt.NomeTipologia
            JOIN Biglietti b ON b.NumeroBiglietto = t.NumeroBiglietto
            WHERE b.CodOrdine = ?
            GROUP BY tt.NomeTipologia
            UNION 
            SELECT tl.NomeTipo AS Categoria, SUM(b.Costo) AS TotaleCategoria
            FROM TipoLinea tl
            JOIN Tipo t ON t.NomeTipo = tl.NomeTipo
            JOIN Biglietti b ON b.NumeroBiglietto = t.NumeroBiglietto
            WHERE b.CodOrdine = ?
            GROUP BY tl.NomeTipo
            ORDER BY Categoria
        """;

    public static final String GET_SCONTI =
        """
            SELECT tt.NomeTipologia AS Categoria, s.Sconto
            FROM Sconti s
            JOIN TipoTransfer tt ON tt.NomeTipologia = s.TipoBiglietto
            JOIN Tipologia t ON t.NomeTipologia = tt.NomeTipologia
            JOIN Biglietti b ON b.NumeroBiglietto = t.NumeroBiglietto
            WHERE b.CodOrdine = ?
            GROUP BY tt.NomeTipologia, s.Sconto, s.Quantità
            HAVING COUNT(*) >= s.Quantità
            ORDER BY Categoria
        """;

    public static final String GET_ANNUNCI_QUANTITA =
        """
            SELECT a.Titolo AS Titolo, COUNT(*) AS Quantita
            FROM Biglietti b
            JOIN AnnunciServizi a ON b.CodAnnuncio = a.CodAnnuncio
            WHERE b.CodOrdine = ?
            GROUP BY a.Titolo
            UNION
            SELECT a.Titolo AS Titolo, COUNT(*) AS Quantita
            FROM Biglietti b
            JOIN AnnunciServizi a ON b.CodAnnuncio = a.CodAnnuncio
            WHERE b.CodOrdine = ?
            GROUP BY a.Titolo
        """;

    public static final String UPDATE_BIGLIETTI_DISPONIBILI =
        """
            UPDATE AnnunciServizi 
            SET BigliettiDisponibili = BigliettiDisponibili - ? 
            WHERE Titolo = ?
        """;

    public static final String UPDATE_BIGLIETTI_VENDUTI =
        """
            UPDATE Servizi 
            SET NumeroBigliettiVenduti = NumeroBigliettiVenduti + ? 
            WHERE CodServizio = (SELECT CodServizio
                                FROM AnnunciServizi
                                WHERE Titolo = ?)
        """;

    public static final String GET_BIGLIETTI_DISPONIBILI = 
        """
            SELECT BigliettiDisponibili
            FROM AnnunciServizi
            WHERE CodServizio = ?
        """;

    public static final String GET_CODICE_SERVIZIO =
        """
            SELECT CodServizio
            FROM AnnunciServizi
            WHERE Titolo = ?
                
        """;

    public static final String DELETE_TICKETS_TRANSFER =
        """
            DELETE FROM Tipologia
            WHERE NomeTipologia = ?
            AND NumeroBiglietto = ?
        """;
    
    public static final String DELETE_TICKETS_LINEA =
        """
            DELETE FROM Tipo
            WHERE NomeTipo = ?
            AND NumeroBiglietto = ?
        """;
    
    public static final String GET_BIGLIETTI_DA_RIMUOVERE =
        """
            SELECT b.NumeroBiglietto
            FROM Biglietti b
            JOIN Tipologia t on b.NumeroBiglietto = t.NumeroBiglietto
            WHERE t.NomeTipologia = ?
            AND b.CodOrdine = ?
            UNION
            SELECT b.NumeroBiglietto
            FROM Biglietti b
            JOIN Tipo t on b.NumeroBiglietto = t.NumeroBiglietto
            WHERE t.NomeTipo = ?
            AND b.CodOrdine = ?
        """;

    public static final String DELETE_TICKET =
        """
            DELETE FROM Biglietti
            WHERE NumeroBiglietto = ?
        """;
    
    public static final String GET_COSTO_BIGLIETTO =
        """
            SELECT Costo
            FROM Biglietti 
            WHERE NumeroBiglietto = ?
        """;
    
    public static final String UPDATE_COSTO_ORDINE =
        """
            UPDATE Ordini 
            SET CostoTotale = CostoTotale - ? 
            WHERE CodOrdine = ?
        """;
}
