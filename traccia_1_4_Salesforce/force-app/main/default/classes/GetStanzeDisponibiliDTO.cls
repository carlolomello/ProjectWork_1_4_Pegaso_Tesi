public with sharing class GetStanzeDisponibiliDTO {
    public Id Id { get; set; }
    public String Nome { get; set; }
    public String Descrizione { get; set; }
    public Integer NumLettiSingoli { get; set; }
    public Integer NumLettiMatrimoniali { get; set; }
    public Integer Capacita { get; set; }
    public Decimal CostoPerNotte { get; set; }
    public Decimal CostoTotale { get; set; }
    public String TipologiaStanza { get; set; }

    // Costruttore per inizializzare i dati da un oggetto Stanza__c
    public GetStanzeDisponibiliDTO(Stanza__c stanza, Integer numeroNotti) {
        this.Id = stanza.Id;
        this.Nome = stanza.Name;
        this.Descrizione = stanza.Descrizione__c;
        this.NumLettiSingoli = (Integer)stanza.Num_Letti_Singoli__c;
        this.NumLettiMatrimoniali = (Integer)stanza.Num_Letti_Matrimoniali__c;
        this.Capacita = (Integer)stanza.Num_Letti_Singoli__c + (Integer)(stanza.Num_Letti_Matrimoniali__c * 2);
        this.CostoPerNotte = stanza.Costo_Per_Notte__c;
        this.CostoTotale = numeroNotti * stanza.Costo_Per_Notte__c;
        this.TipologiaStanza = stanza.Tipologia_Stanza__c;
    }
}