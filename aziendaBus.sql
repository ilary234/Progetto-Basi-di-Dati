-- Database Section
-- ________________ 
DROP DATABASE IF EXISTS AgenziaBus;

CREATE DATABASE IF NOT EXISTS AgenziaBus;

USE AgenziaBus;

# ---------------------------------------------------------------------- #
# Tables                                                                 #
# ---------------------------------------------------------------------- #

create table `AnnunciServizi` (
     `CodAnnuncio` integer not null auto_increment,
     `CodServizio` integer not null,
     `Titolo` varchar(50) not null,
     `Descrizione` mediumtext not null,
     `DataPubblicazione` datetime not null,
     `PrezzoBase` float not null,
     `Visibile` boolean not null,
     `BigliettiDisponibili` smallint not null,
     `CodImpiegato` integer not null,
     constraint  `Pk_AnnunciServizi` primary key (`CodAnnuncio`),
     constraint `Fk_AnnunciServizi` unique (`CodServizio`)
);

create table `Assicurazioni` (
     `NumeroPolizza` varchar(20) not null,
     `DataInizioValidità` date not null,
     `Tipologia` varchar(20) not null,
     `DurataMesi` smallint not null,
     constraint `Pk_Assicurazioni` primary key (`NumeroPolizza`)
);

create table `Autisti` (
     `CF` char(16) not null,
     `NumeroPatente` char(10) not null,
     `Nome` varchar(20) not null,
     `Cognome` varchar(20) not null,
     `DataNascita` date not null,
     `LuogoNascita` varchar(20) not null,
     `Residenza` varchar(15) not null,
     `Telefono` varchar(15) not null,
     `ScadenzaCQC` date not null,
     constraint `Pk_Autisti` primary key (`CF`),
     constraint `Fk_Autisti` unique (`NumeroPatente`)
);

create table `Biglietti` (
     `NumeroBiglietto` integer not null auto_increment,
     `Costo` float not null check (`Costo` >= 0),
     `CodAnnuncio` integer not null,
     `CodOrdine` integer not null,
     constraint `Pk_Biglietti` primary key (`NumeroBiglietto`)
);

create table `CategorieServizi` (
     `CodServizio` integer not null,
     `NomeLinea` varchar(20) not null,
     constraint `Pk_CategorieServizi` primary key (`CodServizio`)
);

create table `ClassiServizi` (
     `CodServizio` integer not null,
     `NomeTransfer` varchar(15) not null,
     constraint `Pk_ClassiServizi` primary key (`CodServizio`)
);

create table `Collegamenti` (
	 `FermataPartenza` smallint not null,
     `FermataArrivo` smallint not null,
     `TempoPercorrenza` smallint not null,
     constraint `Pk_Collegamenti` primary key (`FermataPartenza`, `FermataArrivo`)
);

create table `Commissioni` (
     `Data` date not null,
     `NumeroVolanda` integer not null,
     `CodCommittente` integer not null,
     constraint `Pk_Commissioni` primary key (`Data`, `NumeroVolanda`));

create table `Committenti` (
     `CodCommittente` integer not null auto_increment,
     `Nominativo` varchar(25) not null,
     `Telefono` varchar(15) not null,
     `Tipo` enum('Singolo', 'Agenzia') not null,
     `P_Iva` char(11),
     `Città` varchar(15),
     `CAP` integer(5),
     `Provincia` char(2),
     `SD` char(7),
     `Email` varchar(50),
     `Pec` varchar(330),
     constraint `Pk_Committenti` primary key (`CodCommittente`)
);

create table `Comunicazioni` (
     `CodComunicazione` integer not null auto_increment,
     `Titolo` varchar(50) not null,
     `Descrizione` mediumtext not null,
     `DataPubblicazione` datetime not null,
     `CodImpiegato` integer not null,
     constraint `Pk_Comunicazioni` primary key (`CodComunicazione`)
);

create table `Corse` (
     `CodPercorso` integer not null,
     `OrarioPartenza` char(5) not null,
     `NomeLinea` varchar(20) not null,
     constraint `Pk_Corse` primary key (`CodPercorso`, `OrarioPartenza`)
);

create table `Fermate` (
     `NumeroFermata` smallint not null auto_increment,
     `Via` varchar(30) not null,
     `NumeroCivico` smallint not null,
     constraint `Pk_Fermate` primary key (`NumeroFermata`)
);

create table `Giornaliere` (
     `Data` date not null,
     `CodImpiegato` integer not null,
     constraint `Pk_Giornaliere` primary key (`Data`)
);

create table `Guida` (
     `Data` date not null,
     `NumeroVolanda` integer not null,
     `Autista` char(16) not null,
     constraint `Pk_Guida` primary key (`Data`,`NumeroVolanda`)
);

create table `Impiegati` (
     `CF` char(16) not null,
     `Nome` varchar(20) not null,
     `Cognome` varchar(20) not null,
     `DataNascita` date not null,
     `LuogoNascita` varchar(20) not null,
     `Residenza` varchar(15) not null,
     `Telefono` varchar(15) not null,
     `CodImpiegato` integer not null auto_increment,
     `Password` varchar(50) not null,
     constraint `Pk_Impiegati` primary key (`CodImpiegato`),
     constraint `K_CF` unique (`CF`)
);

create table `KB` (
     `NumeroKB` integer not null,
     `Proprietario` char(16) not null,
     `Scadenza` date not null,
     constraint `Pk_KB` primary key (`NumeroKB`),
     constraint `Fk_KB` unique (`Proprietario`)
);

create table `Linee` (
     `NomeLinea` varchar(20) not null,
     constraint `Pk_Linee` primary key (`NomeLinea`)
);

create table `Mezzi` (
     `NumeroMezzo` integer not null,
     `Targa` char(7) not null,
     `Assicurazione` varchar(20) not null,
     `Euro` integer not null,
     `AnnoImmatricolazione` smallint not null,
     `DataRevisione` date not null,
     `PAX` smallint not null,
     `KmTotali` integer not null,
     `CDPD` boolean not null,
     `Tipo` enum('Pullman', 'Vettura') not null, 
     `Carrozzeria` varchar(20),
     `Modello` varchar(50),
     `Telaio` varchar(50),
     `NumeroLicenzaEuropea` char(10),
     constraint `Pk_Mezzi` primary key (`NumeroMezzo`),
     constraint `K_Targa` unique (`Targa`),
     constraint `Fk_Mezzi` unique (`Assicurazione`)
);

create table `Ordini` (
     `CodOrdine` integer not null auto_increment,
     `OraAcquisto` time not null,
     `DataAcquisto` date not null,
     `TipoPagamento` varchar(20) not null,
     `CostoTotale` float not null,
     `Acquirente` varchar(25) not null,
     constraint `Pk_Ordini` primary key (`CodOrdine`)
);

create table `Patenti` (
     `NumeroPatente` char(10) not null,
     `Tipologia` varchar(2) not null,
     `Scadenza` date not null,
     constraint `Pk_Patenti` primary key (`NumeroPatente`)
);

create table `Percorsi` (
     `CodPercorso` integer not null auto_increment,
     constraint `Pk_Percorsi` primary key (`CodPercorso`)
);

create table `Recensioni` (
     `Utente` varchar(25) not null,
     `CodAnnuncio` integer not null,
     `Valutazione` smallint not null,
     `Commento` varchar(200),
     `Data` date not null,
     constraint `Pk_Recensioni` primary key (`Utente`, `CodAnnuncio`, `Data`)
);

create table `Sconti` (
     `TipoBiglietto` varchar(15) not null,
     `Sconto` float not null,
     `Quantità` smallint not null,
     constraint `Pk_Sconti` primary key (`TipoBiglietto`, `Quantità`)
);

create table `Sequenza` (
     `CodPercorso` integer not null,
     `NumeroFermata` smallint not null,
     `Ordine` smallint not null,
     constraint `Pk_Sequenza` primary key (`CodPercorso`, `NumeroFermata`)
);

create table `Servizi` (
     `CodServizio` integer not null auto_increment, 
     `Partenza` varchar(20) not null,
     `Destinazione` varchar(20) not null,
     `OrarioPartenza` char(5) not null,
     `NumeroBigliettiVenduti` smallint not null,
     constraint `Pk_Servizi` primary key (`CodServizio`)
);

create table `Tipo` (
     `NumeroBiglietto` integer not null,
     `NomeTipo` varchar(15) not null,
     constraint `Pk_Tipo` primary key (`NumeroBiglietto`)
);

create table `TipoLinea` (
     `NomeTipo` varchar(15) not null,
     `Durata` varchar(15) not null,
     `PercentualeDaPagare` float not null,
     `CategoriaServizio` varchar(20) not null,
     constraint `Pk_TipoLinea` primary key (`NomeTipo`)
);

create table `TipoTransfer` (
     `NomeTipologia` varchar(15) not null,
     `EtàMinima` smallint not null,
     `EtàMassima` smallint not null,
     `PercentualeDaPagare` float not null,
     `CategoriaTransfer` varchar(20) not null,
     constraint `Pk_TipoTransfer` primary key (`NomeTipologia`)
);

create table `Tipologia` (
     `NumeroBiglietto` integer not null,
     `NomeTipologia` varchar(15) not null,
     constraint `Pk_Tipologia` primary key (`NumeroBiglietto`)
);

create table `Transfer` (
     `NomeTransfer` varchar(20) not null,
     constraint `Pk_Transfer` primary key (`NomeTransfer`)
);

create table `Utenti` (
     `Username` varchar(25) not null,
     `Password` varchar(50) not null,
     `Nome` varchar(20) not null,
     `Cognome` varchar(20) not null,
     `Email` varchar(50),
     constraint `Pk_Utenti` primary key (`Username`)
);

create table `VeicoloVolanda` (
     `Data` date not null,
     `NumeroVolanda` integer not null,
     `NumeroMezzo` integer not null,
     constraint `Pk_VeicoloVolanda` primary key (`Data`, `NumeroVolanda`)
);

create table `Volande` (
     `Data` date not null,
     `NumeroVolanda` integer not null,
     `CodServizio` integer not null,
     `Note` varchar(200) not null,
     `Fornitore` varchar(50),
     `Prezzo` float not null check (`Prezzo` > 0),
     `Km` integer not null check (`Km` > 0),
     constraint `Pk_Volande` primary key (`Data`, `NumeroVolanda`)
);

# ---------------------------------------------------------------------- #
# Foreign key constraints                                                #
# ---------------------------------------------------------------------- #

alter table `AnnunciServizi` add constraint `Fk_AnnunciServiziImpiegato`
     foreign key (`CodImpiegato`)
     references `Impiegati` (`CodImpiegato`);

alter table `AnnunciServizi` add constraint `Fk_AnnunciServiziServizio`
     foreign key (`CodServizio`)
     references `Servizi` (`CodServizio`);

alter table `Autisti` add constraint `Fk_AutistiPatente`
     foreign key (`NumeroPatente`)
     references `Patenti` (`NumeroPatente`);

alter table `Biglietti` add constraint `Fk_BigliettiAnnuncio`
     foreign key (`CodAnnuncio`)
     references `AnnunciServizi` (`CodAnnuncio`);

alter table `Biglietti` add constraint `Fk_BigliettiOrdine`
     foreign key (`CodOrdine`)
     references `Ordini` (`CodOrdine`);

alter table `CategorieServizi` add constraint `Fk_CategorieServiziServizio`
     foreign key (`CodServizio`)
     references `Servizi` (`CodServizio`);

alter table `CategorieServizi` add constraint `Fk_CategorieServiziLinea`
     foreign key (`NomeLinea`)
     references `Linee` (`NomeLinea`);

alter table `ClassiServizi` add constraint `Fk_ClassiServiziServizio`
     foreign key (`CodServizio`)
     references `Servizi` (`CodServizio`);

alter table `ClassiServizi` add constraint `Fk_ClassiServiziTransfer`
     foreign key (`NomeTransfer`)
     references `Transfer` (`NomeTransfer`);

alter table `Collegamenti` add constraint `Fk_CollegamentiPartenza`
     foreign key (`FermataPartenza`)
     references `Fermate` (`NumeroFermata`);

alter table `Collegamenti` add constraint `Fk_CollegamentiArrivo`
     foreign key (`FermataArrivo`)
     references `Fermate` (`NumeroFermata`);

alter table `Commissioni` add constraint `Fk_Commissioni`
     foreign key (`Data`, `NumeroVolanda`)
     references `Volande` (`Data`, `NumeroVolanda`)
     on delete cascade;

alter table `Commissioni` add constraint `Fk_CommissioniCommittente`
     foreign key (`CodCommittente`)
     references `Committenti` (`CodCommittente`);

alter table `Comunicazioni` add constraint `Fk_ComunicazioniImpiegato`
     foreign key (`CodImpiegato`)
     references `Impiegati` (`CodImpiegato`);

alter table `Corse` add constraint `Fk_CorseLinea`
     foreign key (`NomeLinea`)
     references `Linee` (`NomeLinea`);

alter table `Corse` add constraint `Fk_CorsePercorso`
     foreign key (`CodPercorso`)
     references `Percorsi` (`CodPercorso`);

alter table `Giornaliere` add constraint `Fk_GiornaliereImpiegato`
     foreign key (`CodImpiegato`)
     references `Impiegati` (`CodImpiegato`);

alter table `Guida` add constraint `FKDataEVolanda`
     foreign key (`Data`,`NumeroVolanda`)
     references `Volande` (`Data`,`NumeroVolanda`)
     on delete cascade;

alter table `Guida` add constraint `Fk_GuidaAutista`
     foreign key (`Autista`)
     references `Autisti` (`CF`);

alter table `KB` add constraint `Fk_KBProprietario`
     foreign key (`Proprietario`)
     references `Autisti` (`CF`);

alter table `Mezzi` add constraint `Fk_MezziAssicurazione`
     foreign key (`Assicurazione`)
     references `Assicurazioni` (`NumeroPolizza`);

alter table `Ordini` add constraint `Fk_OrdiniAcquirente`
     foreign key (`Acquirente`)
     references `Utenti` (`Username`);

alter table `Recensioni` add constraint `Fk_RecensioniAnnuncio`
     foreign key (`CodAnnuncio`)
     references `AnnunciServizi` (`CodAnnuncio`);

alter table `Recensioni` add constraint `Fk_RecensioniUtente`
     foreign key (`Utente`)
     references  `Utenti` (`Username`);

alter table `Sconti` add constraint `Fk_ScontiBiglietto`
     foreign key (`TipoBiglietto`)
     references `TipoTransfer` (`NomeTipologia`);

alter table `Sequenza` add constraint `Fk_SequenzaFermata`
     foreign key (`NumeroFermata`)
     references `Fermate` (`NumeroFermata`);

alter table `Sequenza` add constraint `Fk_SequenzaPercorso`
     foreign key (`CodPercorso`)
     references `Percorsi` (`CodPercorso`);

alter table `Tipo` add constraint `Fk_TipoNome`
     foreign key (`NomeTipo`)
     references `TipoLinea` (`NomeTipo`);

alter table `Tipo` add constraint `Fk_TipoBiglietto`
     foreign key (`NumeroBiglietto`)
     references `Biglietti` (`NumeroBiglietto`);

alter table `TipoLinea` add constraint `Fk_TipoLineaCategoria`
     foreign key (`CategoriaServizio`)
     references `Linee` (`NomeLinea`);

alter table `TipoTransfer` add constraint `Fk_TipoTransferCategoria`
     foreign key (`CategoriaTransfer`)
     references `Transfer` (`NomeTransfer`);

alter table `Tipologia` add constraint `Fk_TipologiaBiglietto`
     foreign key (`NumeroBiglietto`)
     references `Biglietti` (`NumeroBiglietto`);

alter table `Tipologia` add constraint `Fk_TipologiaNome`
     foreign key (`NomeTipologia`)
     references `TipoTransfer` (`NomeTipologia`);

alter table `VeicoloVolanda` add constraint `Fk_VeicoloVolandaData`
     foreign key (`Data`, `NumeroVolanda`)
     references `Volande` (`Data`, `NumeroVolanda`)
     on delete cascade;

alter table `VeicoloVolanda` add constraint `Fk_VeicoloVolandaMezzo`
     foreign key (`NumeroMezzo`)
     references `Mezzi` (`NumeroMezzo`);

alter table `Volande` add constraint `Fk_VolandeServizio`
     foreign key (`CodServizio`)
     references `Servizi` (`CodServizio`);

alter table `Volande` add constraint `Fk_VolandeData`
     foreign key (`Data`)
     references `Giornaliere` (`Data`);
     
# ---------------------------------------------------------------------- #
# Checks constraints                                                #
# ---------------------------------------------------------------------- #

alter table Mezzi add constraint `Ck_Mezzi`
check( (Tipo = 'Pullman' and Carrozzeria is not null and Modello is not null and Telaio is not null and NumeroLicenzaEuropea is not null)
		or (Tipo = 'Vettura' and Carrozzeria is null and Modello is null and Telaio is null and NumeroLicenzaEuropea is null));
        
alter table Committenti add constraint `Ck_Committenti`
check( (Tipo = 'Agenzia' and P_Iva is not null and Città is not null and CAP is not null and Provincia is not null and SD is not null and Email is not null)
		or (Tipo = 'Singolo' and P_Iva is null and Città is null and CAP is null and Provincia is null and SD is null and Email is null and Pec is null));


# ---------------------------------------------------------------------- #
# Views                                                                  #
# ---------------------------------------------------------------------- #
create view VolandeRelazioni (Data, NumeroVolanda, CodServizio, Note, Fornitore, Prezzo, Km, Mezzo, Cognome, Nome, Committente) as
select v.*, NumeroMezzo, Cognome, Nome, Nominativo
from volande v left join veicolovolanda vv on (v.Data = vv.Data and v.NumeroVolanda = vv.NumeroVolanda) 
	left join (select g.*, Nome, Cognome from guida g, autisti a where a.CF = g.Autista) as a 
		on (v.Data = a.Data and v.NumeroVolanda = a.NumeroVolanda) 
	left join (select c.*, Nominativo from commissioni c, committenti cc where c.CodCommittente = cc.CodCommittente) as c 
		on (v.Data = c.Data and v.NumeroVolanda = c.NumeroVolanda);
        
create view ServiziCategorie(CodServizio, Partenza, Destinazione, OrarioPartenza, NumeroBigliettiVenduti, Categoria) as
select s.*, NomeLinea as Categoria
from Servizi s, CategorieServizi c
where s.CodServizio = c.CodServizio
union
select s.*, NomeTransfer as Categoria
from Servizi s, Classiservizi c
where s.CodServizio = c.CodServizio
order by CodServizio;

# ---------------------------------------------------------------------- #
# Add info into "Patenti"                                                #
# ---------------------------------------------------------------------- #
insert into Patenti values ('U1A65R987Q', 'D', '2020-03-23');
insert into Patenti values ('U1B54I876F', 'D', '2021-05-22');
insert into Patenti values ('U1C54H075N', 'D', '2018-09-17');
insert into Patenti values ('U1N23L654M', 'D', '2024-12-04');
insert into Patenti values ('U1A09B765J', 'D', '2025-01-10');
insert into Patenti values ('U1S23N548G', 'D', '2021-08-20');
insert into Patenti values ('U1L12M230G', 'D', '2020-11-16');
insert into Patenti values ('U1A62K037F', 'D', '2019-02-05');
insert into Patenti values ('U1B12D609S', 'D', '2018-06-13');
insert into Patenti values ('U1P74T267A', 'D', '2023-07-08');

# ---------------------------------------------------------------------- #
# Add info into "Autisti"                                                #
# ---------------------------------------------------------------------- #
insert into Autisti values ('FLPLRT85C23D969Y', 'U1A65R987Q', 'Alberto', 'Filippini', '1985-03-23', 'Genova', 'Alghero', '+391234567891', '2027-12-14');
insert into Autisti values ('RSSRRT86L21H294N', 'U1B54I876F', 'Roberto', 'Rossi', '1986-07-21', 'Rimini', 'Alghero', '+391765489143', '2026-10-05');
insert into Autisti values ('GRNMTT04R13D704R', 'U1C54H075N', 'Matteo', 'Grandini', '2004-10-13', 'Forli', 'Alghero', '+399998887776', '2030-04-05');
insert into Autisti values ('SNTMTT04S07C573R', 'U1N23L654M', 'Matteo', 'Santarelli', '2004-11-07', 'Cesena', 'Alghero', '+396543210987', '2027-06-07');
insert into Autisti values ('CVNMTT95R25D704N', 'U1A09B765J', 'Mattia', 'Cavina', '1995-10-25', 'Forli', 'Alghero', '+391234567765', '2032-01-12');
insert into Autisti values ('CSDVNC04E43C573X', 'U1S23N548G', 'Veronica', 'Casadei', '2004-05-03', 'Cesena', 'Alghero', '+391234345291', '2026-09-15');
insert into Autisti values ('CHRCHR72H41D704V', 'U1L12M230G', 'Chiara', 'Chiarucci', '1972-06-01', 'Forli', 'Alghero', '+391999888891', '2031-02-06');
insert into Autisti values ('MRNMSM68E27H199X', 'U1A62K037F', 'Massimo', 'Mariani', '1968-05-11', 'Ravenna', 'Alghero', '+391231112224', '2035-04-27');
insert into Autisti values ('PRSGNN62M20A192T', 'U1B12D609S', 'Giovanni', 'Piras', '1962-08-20', 'Alghero', 'Alghero', '+391233355591', '2028-09-26');
insert into Autisti values ('CTTSLV69R60A192E', 'U1P74T267A', 'Silvia', 'Cattogno', '1969-10-20', 'Alghero', 'Alghero', '+391234433665', '2027-12-14');

# ---------------------------------------------------------------------- #
# Add info into "KB"                                                     #
# ---------------------------------------------------------------------- #
insert into KB values (12345, 'CTTSLV69R60A192E', '2030-03-23');
insert into KB values (65432, 'PRSGNN62M20A192T', '2026-05-22');
insert into KB values (89076, 'MRNMSM68E27H199X', '2028-09-17');
insert into KB values (65452, 'CHRCHR72H41D704V', '2031-12-04');
insert into KB values (11223, 'CVNMTT95R25D704N', '2035-01-10');

# ---------------------------------------------------------------------- #
# Add info into "Impiegati"                                              #
# ---------------------------------------------------------------------- #
insert into Impiegati values ('MRNMGH06L47C573U', 'Margherita', 'Mariani', '2006-07-07', 'Cesena', 'Alghero', '+391111222891', null, 'margherita');
insert into Impiegati values ('MRNDRO08A67C573M', 'Dora', 'Mariani', '2008-01-27', 'Cesena', 'Alghero', '+391234887791',  null, 'dora');
insert into Impiegati values ('PRSGLI99C56A192P', 'Giulia', 'Piras', '1999-03-16', 'Alghero', 'Alghero', '+391234567891',  null, 'giulia');
insert into Impiegati values ('CNTCSL86S49A192D', 'Consuelo', 'Contine', '1986-11-09', 'Alghero', 'Alghero', '+391234567891',  null, 'consuelo');
insert into Impiegati values ('BTTFNC51T65D704G', 'Francesca', 'Battista', '1951-12-25', 'Forli', 'Alghero', '+391234567891',  null, 'francesca');

# ---------------------------------------------------------------------- #
# Add info into "Comunicazioni"                                          #
# ---------------------------------------------------------------------- #
insert into Comunicazioni values (null, 'Sospensione servizio di Linea della città', 'A causa di lavori in via Collinello, la linea sarà sospesa dalle ore 13:00 alle ore 15:00 del giorno 15 Giugno 2025', '2025-05-22', 5);
insert into Comunicazioni values (null, 'Sospensione servizio di Linea Beach bus', 'Si comunica che la linea sarà sospesa dalle ore 09:00 alle ore 12:00 del giorno 6 Giugno 2025', '2025-05-28', 2);
insert into Comunicazioni values (null, 'Deviazione servizio di Linea della città', 'A causa di lavori in via Mazzini, la fermata non verrà effettuata dal 10 al 13 Giugno 2025', '2025-05-27', 1);
insert into Comunicazioni values (null, 'Sciopero 18 Giugno 2025', "Si comunica che aderiremo allo sciopero del giorno 18 Giugno e il servizio di linea Beach bus sarà quindi sospeso per l'intera giornata", '2025-06-05', 5);
insert into Comunicazioni values (null, 'Sospensione servizio di Linea Beach bus', 'Si comunica che la linea sarà sospesa dalle ore 10:00 alle ore 12:00 del giorno 22 Maggio 2025', '2025-05-28', 2);
insert into Comunicazioni values (null, 'Sospensione servizio di Linea della città', 'A causa di lavori in via Garibaldi, la linea sarà sospesa dalle ore 16:00 alle ore 17:00 del giorno 12 Giugno 2025', '2025-06-01', 4);
insert into Comunicazioni values (null, 'Deviazione servizio di Linea Beach bus', 'A causa di lavori in corso Italia, la fermata non verrà effettuata dal 12 al 16 Giugno 2025', '2025-05-22', 3);
insert into Comunicazioni values (null, 'Deviazione servizio di Linea della città', 'A causa di lavori in via Verdi, la fermata non verrà effettuata dal 5 al 7 Giugno 2025', '2025-05-18', 4);
insert into Comunicazioni values (null, 'Festa di san Giovanni', 'A causa della festa di san Giovanni, il giorno 24 Giugno 2025 la linea cittadina partirà dalle ore 13:00', '2025-05-12', 3);

# ---------------------------------------------------------------------- #
# Add info into "Committenti"                                            #
# ---------------------------------------------------------------------- #
insert into Committenti values (null, 'Chiarucci&co', '+392223334445', 'Singolo', null, null, null, null, null, null, null);
insert into Committenti values (null, 'OMA', '+392229876545', 'Agenzia', '12345678901', 'Bertinoro', 47032, 'FC', 'A32BC65', 'chiarucci.alberto@gmail.com', null);
insert into Committenti values (null, 'Piras&co', '+392211198745', 'Singolo', null, null, null, null, null, null, null);
insert into Committenti values (null, 'Mariani&co', '+392324354658', 'Singolo', null, null, null, null, null, null, null);
insert into Committenti values (null, 'CattognoViaggi', '+390998877665', 'Agenzia', '12342223301', 'Alghero', 07041, 'SS', 'G44UC65', 'cattogno.viaggi@gmail.com', 'cattognoViaggi@pec.com');

# ---------------------------------------------------------------------- #
# Add info into "Assicurazioni"                                          #
# ---------------------------------------------------------------------- #
insert into Assicurazioni values ('534821152', '2025-03-23', 'Kasko', 9);
insert into Assicurazioni values ('534516533', '2025-05-22', 'Kasko', 12);
insert into Assicurazioni values ('534931224', '2024-09-17', 'Kasko', 9);
insert into Assicurazioni values ('555450792', '2024-12-04', 'Assistenza Stradale', 10);
insert into Assicurazioni values ('555035661', '2025-01-10', 'Assistenza Stradale', 12);
insert into Assicurazioni values ('533892891', '2024-08-20', 'Infortuni Conducente', 12);
insert into Assicurazioni values ('534821149', '2024-11-16', 'Infortuni Conducente', 10);
insert into Assicurazioni values ('534821148', '2025-02-05', 'Assistenza Stradale', 6);
insert into Assicurazioni values ('534931999', '2025-06-13', 'Infortuni Conducente', 6);

# ---------------------------------------------------------------------- #
# Add info into "Mezzi"                                                  #
# ---------------------------------------------------------------------- #
insert into Mezzi values (25, 'FD066HJ', '534821152', 6, 2024, '2025-03-22', 25, 5000, true, 'Pullman', 'Mercedes', 'Tourismo', 'WEB63203613262981', '15165');
insert into Mezzi values (48, 'DE234MM', '534516533', 6, 2022, '2025-05-21', 48, 25000, true, 'Pullman', 'Setra', 'S14', 'WDB9066571P172490', '15165');
insert into Mezzi values (18, 'TY765KO', '534931224', 6, 2023, '2024-09-15', 17, 13000, true, 'Pullman', 'Setra', 'S14', 'WEB632410132747788', '15165');
insert into Mezzi values (12, 'TR876JU','555450792', 6, 2025, '2024-12-12', 12, 3000, false, 'Vettura', null, null, null, null);
insert into Mezzi values (8, 'PO999HU', '555035661', 6, 2020, '2025-01-05', 8, 30000, false, 'Vettura', null, null, null, null);
insert into Mezzi values (50, 'FR234NN', '533892891', 6, 2021, '2024-08-20', 50, 21000, true, 'Pullman', 'Mercedes', 'Tourismo', 'WKK63213113105599', '15165');
insert into Mezzi values (52, 'RQ500BV', '534821149', 6, 2022, '2024-11-23', 52, 17000, false, 'Pullman', 'Mercedes', 'Sprinter', 'ZCFC70C1105966474', '15165');
insert into Mezzi values (32, 'LK775XF', '534821148', 6, 2023, '2025-02-16', 90, 16000, true, 'Pullman', 'Mercedes', 'O530Citaro', 'WEB62808313118405', '15165');
insert into Mezzi values (28, 'CA129KM', '534931999', 6, 2024, '2025-06-07', 28, 8000, false, 'Pullman', 'Mercedes', 'O530Citaro', 'WEB62808313118404', '15165');

# ---------------------------------------------------------------------- #
# Add info into "Utenti"                                                 #
# ---------------------------------------------------------------------- #
insert into Utenti values ('Filiberto', 'Filiberto', 'Filiberto', 'Canu', null);
insert into Utenti values ('Angelina', 'Angelina', 'Angelina', 'Porcu', null);
insert into Utenti values ('Gelsomina', 'Gelsomina', 'Gelsomina', 'Sanna', 'gelsomina23@gmail.com');
insert into Utenti values ('Gavina', 'Gavina', 'Gavina', 'Meloni', null);
insert into Utenti values ('Salvatore', 'Salvatore', 'Salvatore', 'Pischedda', null);
insert into Utenti values ('Bobore', 'Bobore', 'Bobore', 'Pinna', 'bobore65@gmail.com');
insert into Utenti values ('Eugenio', 'Eugenio', 'Eugenio', 'Carta', null);
insert into Utenti values ('Fernando', 'Fernando', 'Fernando', 'Lai', 'fernando@gmail.com');
insert into Utenti values ('Gianfranco', 'Gianfranco', 'Gianfranco', 'Serra', 'gianfranco43@gmail.com');
insert into Utenti values ('Roberto', 'Roberto', 'Roberto', 'Garau', null);

# ---------------------------------------------------------------------- #
# Add info into "Transfer"                                               #
# ---------------------------------------------------------------------- #
insert into Transfer values ('Concerto');
insert into Transfer values ('Escursione');
insert into Transfer values ('Giro Panoramico');

# ---------------------------------------------------------------------- #
# Add info into "TipoTransfer"                                           #
# ---------------------------------------------------------------------- #
insert into TipoTransfer values ('Infante', 0, 3, 0, 'Escursione');
insert into TipoTransfer values ('Bambino', 4, 15, 0.50, 'Escursione');
insert into TipoTransfer values ('Adulti', 16, 64, 1.00, 'Escursione');
insert into TipoTransfer values ('Senior', 65, 100, 0.50, 'Escursione');
insert into TipoTransfer values ('Ridotto', 0, 15, 0.60, 'Giro Panoramico');
insert into TipoTransfer values ('Normale', 16, 100, 1.00, 'Giro Panoramico');
insert into TipoTransfer values ('Unico', 0, 100, 1.00, 'Concerto');

# ---------------------------------------------------------------------- #
# Add info into "Sconti"                                                 #
# ---------------------------------------------------------------------- #
insert into Sconti values('Unico', 0.8, 10);
insert into Sconti values('Adulti', 0.8, 5);

# ---------------------------------------------------------------------- #
# Add info into "Linee"                                                  #
# ---------------------------------------------------------------------- #
INSERT INTO `Linee` VALUES ('Beachbus mattina');
INSERT INTO `Linee` VALUES ('Beachbus sera');
INSERT INTO `Linee` VALUES ('Città');

# ---------------------------------------------------------------------- #
# Add info into "TipoLinea"                                              #
# ---------------------------------------------------------------------- #
insert into TipoLinea values ('Una corsa', 'Una corsa', 1.00, 'Beachbus mattina');
insert into TipoLinea values ('Un giorno', 'Un giorno', 1.50, 'Beachbus mattina');
insert into TipoLinea values ('Quattro giorni', 'Quattro giorni', 2.70, 'Beachbus mattina');
insert into TipoLinea values ('1 corsa', 'Una corsa', 1.00, 'Beachbus sera');
insert into TipoLinea values ('1 giorno', 'Un giorno', 1.50, 'Beachbus sera');
insert into TipoLinea values ('4 giorni', 'Quattro giorni', 2.70, 'Beachbus sera');
insert into TipoLinea values ('Una tratta', 'Una tratta', 1.00, 'Città');
insert into TipoLinea values ('Due tratte', 'Due tratte', 1.50, 'Città');
insert into TipoLinea values ('Quattro tratte', 'Quattro tratte', 3.00, 'Città');

# ---------------------------------------------------------------------- #
# Add info into "Fermate"                                                #
# ---------------------------------------------------------------------- #
INSERT INTO `Fermate` VALUES (NULL, "Via Collinello", 461); 
INSERT INTO `Fermate` VALUES (NULL, "Via Garibaldi", 12); 
INSERT INTO `Fermate` VALUES (NULL, "Via Mazzini", 14);
INSERT INTO `Fermate` VALUES (NULL, "Corso Italia", 87);
INSERT INTO `Fermate` VALUES (NULL, "Via Verdi", 5);
INSERT INTO `Fermate` VALUES (NULL, "Viale Europa", 21);
INSERT INTO `Fermate` VALUES (NULL, "Via Dante", 42);
INSERT INTO `Fermate` VALUES (NULL, "Via Manzoni", 3);
INSERT INTO `Fermate` VALUES (NULL, "Via Leopardi", 19);
INSERT INTO `Fermate` VALUES (NULL, "Piazza Garibaldi", 1);
INSERT INTO `Fermate` VALUES (NULL, "Via Cavour", 33);
INSERT INTO `Fermate` VALUES (NULL, "Via della Libertà", 55);
INSERT INTO `Fermate` VALUES (NULL, "Via Foscolo", 10);
INSERT INTO `Fermate` VALUES (NULL, "Viale dei Mille", 8);
INSERT INTO `Fermate` VALUES (NULL, "Via Marconi", 70);
INSERT INTO `Fermate` VALUES (NULL, "Via Aldo Moro", 16);
INSERT INTO `Fermate` VALUES (NULL, "Via Monti", 27);
INSERT INTO `Fermate` VALUES (NULL, "Via Roma", 4);
INSERT INTO `Fermate` VALUES (NULL, "Via della Repubblica", 11);

# ---------------------------------------------------------------------- #
# Add info into "Collegamenti"                                           #
# ---------------------------------------------------------------------- #
INSERT INTO `Collegamenti` VALUES (1, 2, 5);
INSERT INTO `Collegamenti` VALUES (2, 17, 5);
INSERT INTO `Collegamenti` VALUES (17, 15, 5);
INSERT INTO `Collegamenti` VALUES (15, 7, 5);
INSERT INTO `Collegamenti` VALUES (7, 5, 5);
INSERT INTO `Collegamenti` VALUES (5, 18, 5);
INSERT INTO `Collegamenti` VALUES (18, 8, 5);
INSERT INTO `Collegamenti` VALUES (8, 4, 5);
INSERT INTO `Collegamenti` VALUES (4, 9, 5);
INSERT INTO `Collegamenti` VALUES (9, 14, 5);
INSERT INTO `Collegamenti` VALUES (14, 16, 5);
INSERT INTO `Collegamenti` VALUES (16, 3, 5);
INSERT INTO `Collegamenti` VALUES (3, 12, 5);
INSERT INTO `Collegamenti` VALUES (12, 6, 5);
INSERT INTO `Collegamenti` VALUES (6, 13, 5);
INSERT INTO `Collegamenti` VALUES (2, 1, 5);
INSERT INTO `Collegamenti` VALUES (17, 2, 5);
INSERT INTO `Collegamenti` VALUES (15, 17, 5);
INSERT INTO `Collegamenti` VALUES (7, 15, 5);
INSERT INTO `Collegamenti` VALUES (5, 7, 5);
INSERT INTO `Collegamenti` VALUES (18, 5, 5);
INSERT INTO `Collegamenti` VALUES (8, 18, 5);
INSERT INTO `Collegamenti` VALUES (4, 8, 5);
INSERT INTO `Collegamenti` VALUES (9, 4, 5);
INSERT INTO `Collegamenti` VALUES (14, 9, 5);
INSERT INTO `Collegamenti` VALUES (16, 14, 5);
INSERT INTO `Collegamenti` VALUES (3, 16, 5);
INSERT INTO `Collegamenti` VALUES (12, 3, 5);
INSERT INTO `Collegamenti` VALUES (6, 12, 5);
INSERT INTO `Collegamenti` VALUES (13, 6, 5);
INSERT INTO `Collegamenti` VALUES (19, 3, 5);
INSERT INTO `Collegamenti` VALUES (3, 11, 5);
INSERT INTO `Collegamenti` VALUES (11, 12, 5);
INSERT INTO `Collegamenti` VALUES (12, 13, 5);
INSERT INTO `Collegamenti` VALUES (6, 1, 5);
INSERT INTO `Collegamenti` VALUES (15, 10, 5);
INSERT INTO `Collegamenti` VALUES (10, 5, 5);
INSERT INTO `Collegamenti` VALUES (3, 19, 5);
INSERT INTO `Collegamenti` VALUES (11, 3, 5);
INSERT INTO `Collegamenti` VALUES (12, 11, 5);
INSERT INTO `Collegamenti` VALUES (13, 12, 5);
INSERT INTO `Collegamenti` VALUES (1, 6, 5);
INSERT INTO `Collegamenti` VALUES (10, 15, 5);
INSERT INTO `Collegamenti` VALUES (5, 10, 5);
INSERT INTO `Collegamenti` VALUES (19, 11, 5);
INSERT INTO `Collegamenti` VALUES (1, 15, 5);
INSERT INTO `Collegamenti` VALUES (11, 19, 5);
INSERT INTO `Collegamenti` VALUES (15, 1, 5);

# ---------------------------------------------------------------------- #
# Add info into "Percorsi"                                               #
# ---------------------------------------------------------------------- #
INSERT INTO `Percorsi` VALUES (NULL);
INSERT INTO `Percorsi` VALUES (NULL);
INSERT INTO `Percorsi` VALUES (NULL);
INSERT INTO `Percorsi` VALUES (NULL);
INSERT INTO `Percorsi` VALUES (NULL);
INSERT INTO `Percorsi` VALUES (NULL);

# ---------------------------------------------------------------------- #
# Add info into "Sequenza"                                               #
# ---------------------------------------------------------------------- #
INSERT INTO `Sequenza` VALUES (1, 19, 1);
INSERT INTO `Sequenza` VALUES (1, 3, 2);
INSERT INTO `Sequenza` VALUES (1, 11, 3);
INSERT INTO `Sequenza` VALUES (1, 12, 4);
INSERT INTO `Sequenza` VALUES (1, 13, 5);
INSERT INTO `Sequenza` VALUES (1, 6, 6);
INSERT INTO `Sequenza` VALUES (1, 1, 7);
INSERT INTO `Sequenza` VALUES (1, 2, 8);
INSERT INTO `Sequenza` VALUES (1, 17, 9);
INSERT INTO `Sequenza` VALUES (1, 15, 10);
INSERT INTO `Sequenza` VALUES (1, 10, 11);
INSERT INTO `Sequenza` VALUES (1, 5, 12);
INSERT INTO `Sequenza` VALUES (2, 19, 12);
INSERT INTO `Sequenza` VALUES (2, 3, 11);
INSERT INTO `Sequenza` VALUES (2, 11, 10);
INSERT INTO `Sequenza` VALUES (2, 12, 9);
INSERT INTO `Sequenza` VALUES (2, 13, 8);
INSERT INTO `Sequenza` VALUES (2, 6, 7);
INSERT INTO `Sequenza` VALUES (2, 1, 6);
INSERT INTO `Sequenza` VALUES (2, 2, 5);
INSERT INTO `Sequenza` VALUES (2, 17, 4);
INSERT INTO `Sequenza` VALUES (2, 15, 3);
INSERT INTO `Sequenza` VALUES (2, 10, 2);
INSERT INTO `Sequenza` VALUES (2, 5, 1);
INSERT INTO `Sequenza` VALUES (3, 19, 1);
INSERT INTO `Sequenza` VALUES (3, 11, 2);
INSERT INTO `Sequenza` VALUES (3, 12, 3);
INSERT INTO `Sequenza` VALUES (3, 6, 4);
INSERT INTO `Sequenza` VALUES (3, 1, 5);
INSERT INTO `Sequenza` VALUES (3, 15, 6);
INSERT INTO `Sequenza` VALUES (3, 10, 7);
INSERT INTO `Sequenza` VALUES (3, 5, 8);
INSERT INTO `Sequenza` VALUES (4, 19, 8);
INSERT INTO `Sequenza` VALUES (4, 11, 7);
INSERT INTO `Sequenza` VALUES (4, 12, 6);
INSERT INTO `Sequenza` VALUES (4, 6, 5);
INSERT INTO `Sequenza` VALUES (4, 1, 4);
INSERT INTO `Sequenza` VALUES (4, 15, 3);
INSERT INTO `Sequenza` VALUES (4, 10, 2);
INSERT INTO `Sequenza` VALUES (4, 5, 1);
INSERT INTO `Sequenza` VALUES (5, 1, 1);
INSERT INTO `Sequenza` VALUES (5, 2, 2);
INSERT INTO `Sequenza` VALUES (5, 17, 3);
INSERT INTO `Sequenza` VALUES (5, 15, 4);
INSERT INTO `Sequenza` VALUES (5, 7, 5);
INSERT INTO `Sequenza` VALUES (5, 5, 6);
INSERT INTO `Sequenza` VALUES (5, 18, 7);
INSERT INTO `Sequenza` VALUES (5, 8, 8);
INSERT INTO `Sequenza` VALUES (5, 4, 9);
INSERT INTO `Sequenza` VALUES (5, 9, 10);
INSERT INTO `Sequenza` VALUES (5, 14, 11);
INSERT INTO `Sequenza` VALUES (5, 16, 12);
INSERT INTO `Sequenza` VALUES (5, 3, 13);
INSERT INTO `Sequenza` VALUES (5, 12, 14);
INSERT INTO `Sequenza` VALUES (5, 6, 15);
INSERT INTO `Sequenza` VALUES (5, 13, 16);
INSERT INTO `Sequenza` VALUES (6, 1, 16);
INSERT INTO `Sequenza` VALUES (6, 2, 15);
INSERT INTO `Sequenza` VALUES (6, 17, 14);
INSERT INTO `Sequenza` VALUES (6, 15, 13);
INSERT INTO `Sequenza` VALUES (6, 7, 12);
INSERT INTO `Sequenza` VALUES (6, 5, 11);
INSERT INTO `Sequenza` VALUES (6, 18, 10);
INSERT INTO `Sequenza` VALUES (6, 8, 9);
INSERT INTO `Sequenza` VALUES (6, 4, 8);
INSERT INTO `Sequenza` VALUES (6, 9, 7);
INSERT INTO `Sequenza` VALUES (6, 14, 6);
INSERT INTO `Sequenza` VALUES (6, 16, 5);
INSERT INTO `Sequenza` VALUES (6, 3, 4);
INSERT INTO `Sequenza` VALUES (6, 12, 3);
INSERT INTO `Sequenza` VALUES (6, 6, 2);
INSERT INTO `Sequenza` VALUES (6, 13, 1);

# ---------------------------------------------------------------------- #
# Add info into "Corse"                                                  #
# ---------------------------------------------------------------------- #
INSERT INTO `Corse` VALUES (1, '09:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (1, '11:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (1, '13:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (1, '15:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (1, '17:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (1, '19:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (2, '10:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (2, '12:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (2, '14:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (2, '16:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (2, '18:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (2, '20:00', 'Beachbus mattina');
INSERT INTO `Corse` VALUES (3, '21:00', 'Beachbus sera');
INSERT INTO `Corse` VALUES (3, '22:20', 'Beachbus sera');
INSERT INTO `Corse` VALUES (4, '21:40', 'Beachbus sera');
INSERT INTO `Corse` VALUES (4, '23:00', 'Beachbus sera');
INSERT INTO `Corse` VALUES (5, '08:00', 'Città');
INSERT INTO `Corse` VALUES (5, '08:30', 'Città');
INSERT INTO `Corse` VALUES (5, '09:00', 'Città');
INSERT INTO `Corse` VALUES (5, '09:30', 'Città');
INSERT INTO `Corse` VALUES (5, '10:00', 'Città');
INSERT INTO `Corse` VALUES (5, '10:30', 'Città');
INSERT INTO `Corse` VALUES (5, '11:00', 'Città');
INSERT INTO `Corse` VALUES (5, '11:30', 'Città');
INSERT INTO `Corse` VALUES (6, '09:04', 'Città');
INSERT INTO `Corse` VALUES (6, '09:34', 'Città');
INSERT INTO `Corse` VALUES (6, '10:04', 'Città');
INSERT INTO `Corse` VALUES (6, '10:34', 'Città');
INSERT INTO `Corse` VALUES (6, '11:04', 'Città');
INSERT INTO `Corse` VALUES (6, '11:34', 'Città');
INSERT INTO `Corse` VALUES (6, '12:04', 'Città');
INSERT INTO `Corse` VALUES (6, '12:34', 'Città');

# ---------------------------------------------------------------------- #
# Add info into "Servizi"                                                #
# ---------------------------------------------------------------------- #
INSERT INTO `Servizi` VALUES (NULL, 'Alghero', 'Nuoro', '08:30', 20);
INSERT INTO `Servizi` VALUES (NULL, 'Alghero', 'Oliena', '07:30', 35);
INSERT INTO `Servizi` VALUES (NULL, 'Alghero', 'Seulo', '06:30', 17);
INSERT INTO `Servizi` VALUES (NULL, 'Alghero', 'Busachi', '09:00', 45);
INSERT INTO `Servizi` VALUES (NULL, 'Alghero', 'Orosei', '15:30', 75);
INSERT INTO `Servizi` VALUES (NULL, 'Alghero', 'Olbia', '17:00', 67);
INSERT INTO `Servizi` VALUES (NULL, 'Via Marconi', 'Via Foscolo', '11:15', 40);
INSERT INTO `Servizi` VALUES (NULL, 'Via Collinello', 'Via Verdi', '09:00', 27);
INSERT INTO `Servizi` VALUES (NULL, 'Corso Italia', 'Viale dei Mille', '15:00', 60);
INSERT INTO `Servizi` VALUES (NULL, 'Via della Repubblica', 'Piazza Garibaldi', '21:00', 40);
INSERT INTO `Servizi` VALUES (NULL, 'Via Verdi', 'Piazza Sulis', '17:00', 20);

# ---------------------------------------------------------------------- #
# Add info into "AnnunciServizi"                                         #
# ---------------------------------------------------------------------- #
INSERT INTO `AnnunciServizi` Values (NULL, 1, 'Escursione Nuoro', 'Sabato con prenotazione. \nPartenza/Start at h6:00 da Alghero.\nRientro/Return h20:00 ad Alghero', now(), 60.00, True, 60, 2);
INSERT INTO `AnnunciServizi` Values (NULL, 2, 'Autunno in barbagia: visita ad Oliena', 'Sabato con prenotazione.\nPartenza/Start at h7:00 da Alghero.\nRientro/Return h19:30 ad Alghero', now(), 65.00, True, 40, 3);
INSERT INTO `AnnunciServizi` Values (NULL, 3, 'Escursione alla diga di Seulo', 'Venerdì 11.07.2025 con prenotazione. \nPartenza/Start at h7:00 da Alghero. \nRientro/Return h20:00 ad Alghero', now(), 60.00, True, 50, 3);
INSERT INTO `AnnunciServizi` Values (NULL, 4, 'Escursione alla cascata di Busachi', 'Lunedì 14.07.2025 con prenotazione. \nPartenza/Start at h7:00 da Alghero. \nRientro/Return h19:00 ad Alghero', now(), 45.00, True, 55, 2);
INSERT INTO `AnnunciServizi` Values (NULL, 6, 'Concerto di Ligabue', "Sabato 12.07.25  \nPartenza/Start at h18:30 da Alghero. \nRientro/Return h1:00 ad Alghero", now(), 45.00, True, 50, 5);
INSERT INTO `AnnunciServizi` Values (NULL, 5, 'Concerto di Annalisa', 'Martedì 08.07.25. \nPartenza/Start at h18:00 da Alghero. \nRientro/Return h1:30 ad Alghero', now(), 55.00, True, 35, 1);
INSERT INTO `AnnunciServizi` Values (NULL, 8, 'Beachbus mattina', 'Tutti i giorni. \nPartenza/Start at h9:00 da Via della Repubblica. \nUltima fermata Via Verdi', now(), 5.00, True, 30, 4);
INSERT INTO `AnnunciServizi` Values (NULL, 9, 'Linea standard', 'Tutti i giorni. \nPartenza/Start at h8:00 da Via Collinello. \nUltima fermata Via Foscolo', now(), 2.00, True, 40, 3);
INSERT INTO `AnnunciServizi` Values (NULL, 7, 'Giro panoramico', 'Ogni martedì, venerdì e domenica. \nPartenza/Start at h15:00 dal porto. \nArrivo a Fertilia', now(), 6.00, True, 55, 1);
INSERT INTO `AnnunciServizi` Values (NULL, 10, 'Beachbus sera', 'Tutti i giorni. \nPartenza/Start at h21:00 da Via della Repubblica. \nUltima fermata Via Verdi', now(), 5.00, True, 30, 4);

# ---------------------------------------------------------------------- #
# Add info into "Recensioni"                                             #
# ---------------------------------------------------------------------- #
INSERT INTO `Recensioni` Values ('Fernando', 5, 5, NULL, '2025-05-08');
INSERT INTO `Recensioni` Values ('Angelina', 5, 4, NULL, '2025-03-23');
INSERT INTO `Recensioni` Values ('Salvatore', 5, 3, 'La linea è comoda e copre tratte utili, ma a volte si verificano ritardi. Nel complesso, però, un’opzione valida per chi viaggia spesso.', '2025-05-03');
INSERT INTO `Recensioni` Values ('Fernando', 7, 5, NULL, '2025-05-19');
INSERT INTO `Recensioni` Values ('Fernando', 6, 5, 'Ottimo servizio, puntuali e tutti molto cordiali.', '2025-01-20');
INSERT INTO `Recensioni` Values ('Angelina', 6, 4, NULL, '2025-02-18');
INSERT INTO `Recensioni` Values ('Angelina', 7, 4, NULL, '2025-04-17');
INSERT INTO `Recensioni` Values ('Salvatore', 6, 5, 'Utilizzo spesso la linea per spostarmie devo dire che il servizio è sempre stato impeccabile. Autobus puliti, personale cortese e partenze puntuali. Consigliatissimo!', '2025-04-30');
INSERT INTO `Recensioni` Values ('Salvatore', 7, 5, 'Bellissimo giro panoramico ad Alghero! Autobus puntuale, comodo e con vista mozzafiato. Ottima audioguida e staff cordiale. Consigliatissimo per scoprire la città!', '2025-05-13');

# ---------------------------------------------------------------------- #
# Add info into "CategorieServizi"                                       #
# ---------------------------------------------------------------------- #
INSERT INTO `CategorieServizi` VALUES (9, 'Città');
INSERT INTO `CategorieServizi` VALUES (10, 'Beachbus sera');
INSERT INTO `CategorieServizi` VALUES (8, 'Beachbus mattina');
# ---------------------------------------------------------------------- #
# Add info into "ClassiServizi"                                          #
# ---------------------------------------------------------------------- #
INSERT INTO `ClassiServizi` VALUES (1, 'Escursione');
INSERT INTO `ClassiServizi` VALUES (2, 'Escursione');
INSERT INTO `ClassiServizi` VALUES (3, 'Escursione');
INSERT INTO `ClassiServizi` VALUES (4, 'Escursione');
INSERT INTO `ClassiServizi` VALUES (5, 'Concerto');
INSERT INTO `ClassiServizi` VALUES (6, 'Concerto');
INSERT INTO `ClassiServizi` VALUES (7, 'Giro panoramico');
INSERT INTO `ClassiServizi` VALUES (11, 'Giro panoramico');

# ---------------------------------------------------------------------- #
# Add info into "Giornaliere"                                            #
# ---------------------------------------------------------------------- #
INSERT INTO `Giornaliere` VALUES ('2025-10-18', 5);
INSERT INTO `Giornaliere` VALUES ('2025-07-08', 4);
INSERT INTO `Giornaliere` VALUES ('2025-07-09', 3);
INSERT INTO `Giornaliere` VALUES ('2025-07-10', 2);
INSERT INTO `Giornaliere` VALUES ('2025-07-11', 1);
INSERT INTO `Giornaliere` VALUES ('2025-07-12', 1);
INSERT INTO `Giornaliere` VALUES ('2025-07-13', 3);
INSERT INTO `Giornaliere` VALUES ('2025-07-14', 5);
INSERT INTO `Giornaliere` VALUES ('2025-07-15', 2);
INSERT INTO `Giornaliere` VALUES ('2025-07-16', 2);

# ---------------------------------------------------------------------- #
# Add info into "Volande"                                                #
# ---------------------------------------------------------------------- #
INSERT INTO `Volande` VALUES ('2025-10-18', 1, 1, 'Escursione Alghero -> Nuoro', 'Chiarucci&co', 450, 155);
INSERT INTO `Volande` VALUES ('2025-10-18', 2, 2, 'Autunno in Barbagia: Alghero -> Oliena', 'Piras&co', 475, 165);
INSERT INTO `Volande` VALUES ('2025-10-18', 3, 7, 'Giro panoramico Alghero', NULL, 200, 20);
INSERT INTO `Volande` VALUES ('2025-07-08', 1, 5, 'Concerto Annalisa: Alghero -> Orosei', 'Mariani&co', 500, 190);
INSERT INTO `Volande` VALUES ('2025-07-08', 2, 8, 'Beachbus mattina', NULL, 220, 45);
INSERT INTO `Volande` VALUES ('2025-07-08', 3, 7, 'Giro panoramico Alghero', NULL, 200, 20);
INSERT INTO `Volande` VALUES ('2025-07-08', 4, 10, 'Beachbus sera', NULL, 180, 35);
INSERT INTO `Volande` VALUES ('2025-07-09', 1, 9, 'Percorso standard', NULL, 240, 60);
INSERT INTO `Volande` VALUES ('2025-07-09', 2, 8, 'Beachbus mattina', NULL, 220, 45);
INSERT INTO `Volande` VALUES ('2025-07-09', 3, 10, 'Beachbus sera', NULL, 180, 35);
INSERT INTO `Volande` VALUES ('2025-07-10', 1, 8, 'Beachbus mattina', NULL, 220, 45);
INSERT INTO `Volande` VALUES ('2025-07-10', 2, 9, 'Percorso standard', NULL, 240, 60);
INSERT INTO `Volande` VALUES ('2025-07-10', 3, 10, 'Beachbus sera', NULL, 180, 35);
INSERT INTO `Volande` VALUES ('2025-07-11', 1, 8, 'Beachbus mattina', NULL, 220, 45);
INSERT INTO `Volande` VALUES ('2025-07-11', 2, 10, 'Beachbus sera', NULL, 180, 35);
INSERT INTO `Volande` VALUES ('2025-07-11', 3, 7, 'Giro panoramico Alghero', NULL, 200, 20);
INSERT INTO `Volande` VALUES ('2025-07-11', 4, 3, 'Escursione diga di Seulo', 'OMA', 240, 60);
INSERT INTO `Volande` VALUES ('2025-07-11', 5, 9, 'Percorso standard', NULL, 240, 60);
INSERT INTO `Volande` VALUES ('2025-07-12', 1, 8, 'Beachbus mattina', NULL, 220, 45);
INSERT INTO `Volande` VALUES ('2025-07-12', 2, 10, 'Beachbus sera', NULL, 180, 35);
INSERT INTO `Volande` VALUES ('2025-07-12', 3, 6, 'Concerto Ligabue: Alghero -> Olbia', 'CattognoViaggi', 400, 140);
INSERT INTO `Volande` VALUES ('2025-07-13', 1, 8, 'Beachbus mattina', NULL, 220, 45);
INSERT INTO `Volande` VALUES ('2025-07-13', 2, 10, 'Beachbus sera', NULL, 180, 35);
INSERT INTO `Volande` VALUES ('2025-07-13', 3, 7, 'Giro panoramico Alghero', NULL, 200, 20);
INSERT INTO `Volande` VALUES ('2025-07-14', 1, 8, 'Beachbus mattina', NULL, 220, 45);
INSERT INTO `Volande` VALUES ('2025-07-14', 2, 10, 'Beachbus sera', NULL, 180, 35);
INSERT INTO `Volande` VALUES ('2025-07-14', 3, 9, 'Percorso standard', NULL, 240, 60);
INSERT INTO `Volande` VALUES ('2025-07-14', 4, 4, 'Escursione cascata Mularza Noa a Busachi', 'CattognoViaggi', 375, 140);

# ---------------------------------------------------------------------- #
# Add info into "Ordini"                                              #
# ---------------------------------------------------------------------- #
INSERT INTO `Ordini` VALUES (NULL, '08:00:00', '2025-01-08', 'Carta di credito', 9.60, 'Fernando'); 
INSERT INTO `Ordini` VALUES (NULL, '08:00:00', '2025-03-23', 'Carta di credito', 90.00, 'Fernando'); 
INSERT INTO `Ordini` VALUES (NULL, '08:30:00', '2025-07-12', 'Bancomat', 6.00, 'Angelina'); 
INSERT INTO `Ordini` VALUES (NULL, '11:00:00', '2025-02-20', 'Carta di credito', 45.00, 'Salvatore'); 
INSERT INTO `Ordini` VALUES (NULL, '15:30:00', '2025-02-27', 'Bancomat', 22.50, 'Fernando'); 
INSERT INTO `Ordini` VALUES (NULL, '12:30:00', '2025-02-28', 'Bancomat', 11.00, 'Angelina'); 
INSERT INTO `Ordini` VALUES (NULL, '16:00:00', '2025-08-08', 'Carta di credito', 6.00, 'Angelina'); 
INSERT INTO `Ordini` VALUES (NULL, '15:24:00', '2025-08-08', 'Carta di credito', 90.00, 'Salvatore'); 
INSERT INTO `Ordini` VALUES (NULL, '19:57:00', '2025-08-08', 'Carta di credito', 45.00, 'Fernando'); 
INSERT INTO `Ordini` VALUES (NULL, '11:33:00', '2025-09-15', 'Bancomat', 165.00, 'Angelina'); 
INSERT INTO `Ordini` VALUES (NULL, '10:43:00', '2025-09-16', 'Bancomat', 110.00, 'Angelina'); 
INSERT INTO `Ordini` VALUES (NULL, '12:17:00', '2025-11-09', 'Bancomat', 9.60, 'Salvatore'); 
INSERT INTO `Ordini` VALUES (NULL, '10:34:00', '2025-11-09', 'Bancomat', 90.00, 'Salvatore'); 
INSERT INTO `Ordini` VALUES (NULL, '08:00:00', '2025-10-08', 'Carta di credito', 10.00, 'Fernando'); 
INSERT INTO `Ordini` VALUES (NULL, '20:20:00', '2025-10-08', 'Carta di credito', 21.00, 'Salvatore');

# ---------------------------------------------------------------------- #
# Add info into "Biglietti"                                              #
# ---------------------------------------------------------------------- #
INSERT INTO `Biglietti` VALUES (NULL, 6.00, 7, 1);
INSERT INTO `Biglietti` VALUES (NULL, 3.60, 7, 1);
INSERT INTO `Biglietti` VALUES (NULL, 30.00, 3, 2);
INSERT INTO `Biglietti` VALUES (NULL, 60.00, 3, 2);
INSERT INTO `Biglietti` VALUES (NULL, 6.00, 7, 3);
INSERT INTO `Biglietti` VALUES (NULL, 45.00, 4, 4);
INSERT INTO `Biglietti` VALUES (NULL, 22.50, 4, 5);
INSERT INTO `Biglietti` VALUES (NULL, 2.00, 9, 6);
INSERT INTO `Biglietti` VALUES (NULL, 3.00, 9, 6);
INSERT INTO `Biglietti` VALUES (NULL, 6.00, 9, 6);
INSERT INTO `Biglietti` VALUES (NULL, 6.00, 7, 7);
INSERT INTO `Biglietti` VALUES (NULL, 45.00, 6, 8);
INSERT INTO `Biglietti` VALUES (NULL, 45.00, 6, 8);
INSERT INTO `Biglietti` VALUES (NULL, 45.00, 6, 9);
INSERT INTO `Biglietti` VALUES (NULL, 55.00, 5, 10);
INSERT INTO `Biglietti` VALUES (NULL, 55.00, 5, 10);
INSERT INTO `Biglietti` VALUES (NULL, 55.00, 5, 10);
INSERT INTO `Biglietti` VALUES (NULL, 55.00, 5, 11);
INSERT INTO `Biglietti` VALUES (NULL, 55.00, 5, 11);
INSERT INTO `Biglietti` VALUES (NULL, 6.00, 7, 12);
INSERT INTO `Biglietti` VALUES (NULL, 3.60, 7, 12);
INSERT INTO `Biglietti` VALUES (NULL, 45.00, 6, 13);
INSERT INTO `Biglietti` VALUES (NULL, 45.00, 6, 13);
INSERT INTO `Biglietti` VALUES (NULL, 5.00, 8, 14);
INSERT INTO `Biglietti` VALUES (NULL, 5.00, 8, 14);
INSERT INTO `Biglietti` VALUES (NULL, 7.50, 10, 15);
INSERT INTO `Biglietti` VALUES (NULL, 13.50, 10, 15);

# ---------------------------------------------------------------------- #
# Add info into "VeicoloVolanda"                                         #
# ---------------------------------------------------------------------- #
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-08', 1, 25); 
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-08', 2, 50); 
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-08', 3, 32);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-08', 4, 48); 
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-09', 1, 25);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-09', 3, 48);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-09', 2, 18);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-10', 1, 25);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-10', 2, 18);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-11', 1, 25);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-11', 2, 48);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-11', 3, 52);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-11', 5, 18);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-12', 1, 25);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-12', 2, 48);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-12', 3, 50);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-13', 2, 48);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-13', 3, 52);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-14', 2, 48);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-14', 3, 18);
INSERT INTO `VeicoloVolanda` VALUES ('2025-07-14', 4, 32);

# ---------------------------------------------------------------------- #
# Add info into "Commissioni"                                            #
# ---------------------------------------------------------------------- #
INSERT INTO `Commissioni` VALUES ('2025-10-18', 1, 1);
INSERT INTO `Commissioni` VALUES ('2025-10-18', 2, 4);
INSERT INTO `Commissioni` VALUES ('2025-07-08', 1, 4);
INSERT INTO `Commissioni` VALUES ('2025-07-11', 4, 2);
INSERT INTO `Commissioni` VALUES ('2025-07-12', 3, 5);
INSERT INTO `Commissioni` VALUES ('2025-07-14', 4, 5);

# ---------------------------------------------------------------------- #
# Add info into "Guida"                                                  #
# ---------------------------------------------------------------------- #
INSERT INTO `Guida` VALUES ('2025-07-08', 1, 'FLPLRT85C23D969Y');
INSERT INTO `Guida` VALUES ('2025-07-08', 2, 'CHRCHR72H41D704V');
INSERT INTO `Guida` VALUES ('2025-07-08', 3, 'CTTSLV69R60A192E');
INSERT INTO `Guida` VALUES ('2025-07-08', 4, 'MRNMSM68E27H199X');
INSERT INTO `Guida` VALUES ('2025-07-09', 1, 'FLPLRT85C23D969Y');
INSERT INTO `Guida` VALUES ('2025-07-09', 2, 'PRSGNN62M20A192T');
INSERT INTO `Guida` VALUES ('2025-07-09', 3, 'FLPLRT85C23D969Y');
INSERT INTO `Guida` VALUES ('2025-07-10', 1, 'RSSRRT86L21H294N');
INSERT INTO `Guida` VALUES ('2025-07-10', 2, 'CVNMTT95R25D704N');
INSERT INTO `Guida` VALUES ('2025-07-10', 3, 'FLPLRT85C23D969Y');
INSERT INTO `Guida` VALUES ('2025-07-12', 2, 'FLPLRT85C23D969Y');
INSERT INTO `Guida` VALUES ('2025-07-13', 3, 'FLPLRT85C23D969Y');
INSERT INTO `Guida` VALUES ('2025-07-14', 1, 'FLPLRT85C23D969Y');

# ---------------------------------------------------------------------- #
# Add info into "Tipo"                                                   #
# ---------------------------------------------------------------------- #
INSERT INTO `Tipo` VALUES (8, 'Una tratta');
INSERT INTO `Tipo` VALUES (9, 'Due tratte');
INSERT INTO `Tipo` VALUES (10, 'Quattro tratte');
INSERT INTO `Tipo` VALUES (24, 'Una corsa');
INSERT INTO `Tipo` VALUES (25, 'Una corsa');
INSERT INTO `Tipo` VALUES (26, '1 giorno');
INSERT INTO `Tipo` VALUES (27, '4 giorni');

# ---------------------------------------------------------------------- #
# Add info into "Tipologia"                                              #
# ---------------------------------------------------------------------- #
INSERT INTO `Tipologia` VALUES (1, 'Normale');
INSERT INTO `Tipologia` VALUES (2, 'Ridotto');
INSERT INTO `Tipologia` VALUES (3, 'Senior');
INSERT INTO `Tipologia` VALUES (4, 'Adulti');
INSERT INTO `Tipologia` VALUES (5, 'Normale');
INSERT INTO `Tipologia` VALUES (6, 'Adulti');
INSERT INTO `Tipologia` VALUES (7, 'Bambino');
INSERT INTO `Tipologia` VALUES (11, 'Normale');
INSERT INTO `Tipologia` VALUES (12, 'Unico');
INSERT INTO `Tipologia` VALUES (13, 'Unico');
INSERT INTO `Tipologia` VALUES (14, 'Unico');
INSERT INTO `Tipologia` VALUES (15, 'Unico');
INSERT INTO `Tipologia` VALUES (16, 'Unico');
INSERT INTO `Tipologia` VALUES (17, 'Unico');
INSERT INTO `Tipologia` VALUES (18, 'Unico');
INSERT INTO `Tipologia` VALUES (19, 'Unico');
INSERT INTO `Tipologia` VALUES (20, 'Normale');
INSERT INTO `Tipologia` VALUES (21, 'Ridotto');
INSERT INTO `Tipologia` VALUES (22, 'Unico');
INSERT INTO `Tipologia` VALUES (23, 'Unico');