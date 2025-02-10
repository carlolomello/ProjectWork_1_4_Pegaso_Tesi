global with sharing class PrenotazioneDTO {
    // DTO per la richiesta di creazione di una prenotazione
    public class InsertPrenotazioneRequestDTO {
        public String idUser;
        public String idStanza;
        public String dataInizio;
        public String dataFine;
        public String idMetodoPagamento;
        public List<String> serviziIds;
    }

    // DTO per la risposta di creazione di una prenotazione
    global class InsertPrenotazioneResponseDTO {
        public Boolean success;
        public String message;
        public String prenotazioneId;

        public InsertPrenotazioneResponseDTO(Boolean success, String message, String prenotazioneId) {
            this.success = success;
            this.message = message;
            this.prenotazioneId = prenotazioneId;
        }
    }

    // DTO per la richiesta di modifica di una prenotazione
    public class UpdatePrenotazioneRequestDTO {
        public String prenotazioneId;
        public String dataInizio;
        public String dataFine;
        public List<String> serviziIds;
    }

    // DTO per la risposta di modifica di una prenotazione
    global class UpdatePrenotazioneResponseDTO {
        public Boolean success;
        public String message;

        public UpdatePrenotazioneResponseDTO(Boolean success,String message) {
            this.success = success;
            this.message = message;
        }
    }
     
    // DTO per rappresentare un singolo servizio associato alla prenotazione
    public class ServizioDTO {
        public String servizioId;
        public String nomeServizio;
        public String descrizione;
        public Decimal costoPersonaGiorno;

        public ServizioDTO(String servizioId, String nomeServizio, String descrizione, Decimal costoPersonaGiorno) {
            this.servizioId = servizioId;
            this.nomeServizio = nomeServizio;
            this.descrizione = descrizione;
            this.costoPersonaGiorno = costoPersonaGiorno;
        }
    }

    // DTO per rappresentare una singola prenotazione dell'utente
    public class PrenotazioneUtenteDTO {
        public String prenotazioneId;
        public Date dataInizio;
        public Date dataFine;
        public String statoPagamento;
        public String stanzaId;
        public String stanzaNome;
        public String stanzaDescrizione;
        public Integer numLettiSingoli;
        public Integer numLettiMatrimoniali;
        public Decimal costoPerNotte;
        public String tipologiaNome;
        public String metodoPagamentoNome;
        public List<ServizioDTO> servizi;
        public Decimal costoTotale;
    
        // Aggiungi il costruttore con i parametri corretti
        public PrenotazioneUtenteDTO(String prenotazioneId, Date dataInizio, Date dataFine, String statoPagamento, 
                                     String stanzaId, String stanzaNome, String stanzaDescrizione, Integer numLettiSingoli, Integer numLettiMatrimoniali, 
                                     Decimal costoPerNotte, String tipologiaNome, String metodoPagamentoNome,  List<ServizioDTO> servizi, Decimal costoTotale) {
            this.prenotazioneId = prenotazioneId;
            this.dataInizio = dataInizio;
            this.dataFine = dataFine;
            this.statoPagamento = statoPagamento;
            this.stanzaId = stanzaId;
            this.stanzaNome = stanzaNome;
            this.stanzaDescrizione = stanzaDescrizione;
            this.numLettiSingoli = numLettiSingoli;
            this.numLettiMatrimoniali = numLettiMatrimoniali;
            this.costoPerNotte = costoPerNotte;
            this.tipologiaNome = tipologiaNome;
            this.metodoPagamentoNome = metodoPagamentoNome;
            this.servizi = servizi;
            this.costoTotale = costoTotale;
        }
    }

    // DTO per la risposta della lista delle prenotazioni dell'utente
    global class ListaPrenotazioniUtenteResponseDTO {
        public List<PrenotazioneUtenteDTO> prenotazioni;
        public String message;

        public ListaPrenotazioniUtenteResponseDTO(List<PrenotazioneUtenteDTO> prenotazioni, String message) {
            this.prenotazioni = prenotazioni;
            this.message = message;
        }
    }
}