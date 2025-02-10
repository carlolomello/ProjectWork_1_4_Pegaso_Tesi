@RestResource(urlMapping='/api/available_rooms')
global with sharing class GetStanzeDisponibili {

    @HttpGet
    global static ResponseWrapper getStanzeDisponibili() {
        RestRequest req = RestContext.request;
        String dataInizio = req.params.get('data_inizio');
        String dataFine = req.params.get('data_fine');
        String postiLettoStr = req.params.get('posti_letto');
        String tipologiaStanza = req.params.get('tipologia_stanza');

        if (String.isBlank(dataInizio) || String.isBlank(dataFine)) {
            throw new AuraHandledException('I parametri data_inizio e data_fine sono obbligatori.');
        }

        Integer postiLetto = String.isBlank(postiLettoStr) ? null : Integer.valueOf(postiLettoStr);
        Date startDate = Date.valueOf(dataInizio);
        Date endDate = Date.valueOf(dataFine);

        // Recupera tutte le stanze
        List<Stanza__c> listaStanze = [
            SELECT 
                Id,
                Name,
                Descrizione__c,
                Num_Letti_Singoli__c,
                Num_Letti_Matrimoniali__c,
                Costo_Per_Notte__c,
                Tipologia_Stanza__c
            FROM Stanza__c
        ];

        // Recupera prenotazioni che si sovrappongono al periodo fornito
        List<Prenotazione__c> listaPrenotazioni = [
            SELECT Stanza__c 
            FROM Prenotazione__c 
            WHERE Data_Inizio__c <= :endDate AND Data_Fine__c >= :startDate AND (Stato_Pagamento__c != 'annullato_Verifica' AND Stato_Pagamento__c != 'annullato_utente')
        ];

        // Determina l'insieme delle stanze occupate
        Set<Id> stanzeOccupate = new Set<Id>();
        for (Prenotazione__c prenotazione : listaPrenotazioni) {
            if (prenotazione.Stanza__c != null) {
                stanzeOccupate.add(prenotazione.Stanza__c);
            }
        }

        // Filtra le stanze disponibili e utilizza il DTO
        List<GetStanzeDisponibiliDTO> stanzeDisponibili = new List<GetStanzeDisponibiliDTO>();
        for (Stanza__c stanza : listaStanze) {
            if (stanzeOccupate.contains(stanza.Id)) {
                continue;
            }
        
            Integer capacita = (Integer)stanza.Num_Letti_Singoli__c + (Integer)(stanza.Num_Letti_Matrimoniali__c * 2);
            
            // logica per la restrizione in base ai posti letto
            if (postiLetto != null) {
                if (postiLetto == 0) {
                    // Non applica restrizioni se postiLetto è 0
                    // Continua senza restrizioni sulla capacità
                } else if (postiLetto >= 1 && postiLetto <= 4) {
                    // Applica restrizione sulla capacità se postiLetto è tra 1 e 4
                    if (capacita != postiLetto) {
                        continue;
                    }
                } else if (postiLetto > 4) {
                    // Applica restrizione per stanze con capacità maggiore di 4
                    if (capacita <= 4) {
                        continue;
                    }
                }
            }
        
            if (String.isNotBlank(tipologiaStanza) && stanza.Tipologia_Stanza__c != tipologiaStanza) {
                continue;
            }
        
            Integer numeroNotti = (startDate.daysBetween(endDate) > 0) ? startDate.daysBetween(endDate) : 1;
            stanzeDisponibili.add(new GetStanzeDisponibiliDTO(stanza, numeroNotti));
        }

        // Costruisci la risposta
        ResponseWrapper respWrapper = new ResponseWrapper();
        respWrapper.stanze = stanzeDisponibili;
        return respWrapper;
    }

    // Wrapper per la risposta
    global class ResponseWrapper {
        public List<GetStanzeDisponibiliDTO> stanze;
    }
}