@RestResource(urlMapping='/api/get_prenotazioni_utente')
global with sharing class GetPrenotazioniCliente {
    @HttpGet
    global static PrenotazioneDTO.ListaPrenotazioniUtenteResponseDTO getPrenotazioniUtente() {
      

        RestRequest req = RestContext.request;
        String userId = req.params.get('utenza_cliente');
       
        if (String.isBlank(userId)) {
            throw new AuraHandledException('Il parametro dutenza_cliente è obbligatorio.');
        }

        List<PrenotazioneDTO.PrenotazioneUtenteDTO> prenotazioniList = new List<PrenotazioneDTO.PrenotazioneUtenteDTO>();

        try {
            // Recuperiamo tutte le prenotazioni dell'utente con una sola query
            List<Prenotazione__c> prenotazioni = [
                SELECT Id, Data_Inizio__c, Data_Fine__c, Stato_Pagamento__c, 
                    Stanza__c, Stanza__r.Name, Stanza__r.Descrizione__c, 
                    Stanza__r.Num_Letti_Singoli__c, Stanza__r.Num_Letti_Matrimoniali__c,
                    Stanza__r.Costo_Per_Notte__c, Stanza__r.Tipologia_Stanza__c, Metodo_Pagamento__c
                FROM Prenotazione__c
                WHERE Utenza_Cliente__c = :userId
            ];

            if (prenotazioni.isEmpty()) {
                return new PrenotazioneDTO.ListaPrenotazioniUtenteResponseDTO(null, 'Nessuna prenotazione trovata.');
            }

            // Recuperiamo tutti i servizi associati alle prenotazioni con una sola query
            Map<Id, List<Prenotazione_Servizio__c>> serviziPerPrenotazione = new Map<Id, List<Prenotazione_Servizio__c>>();

            List<Prenotazione_Servizio__c> servizi = [
                SELECT Prenotazione__c, Id, Servizio__r.Id, Servizio__r.Name, Servizio__r.Descrizione__c, Servizio__r.Costo_Persona_Giorno__c
                FROM Prenotazione_Servizio__c
                WHERE Prenotazione__c IN :prenotazioni
            ];

            // Raggruppiamo i servizi per prenotazione
            for (Prenotazione_Servizio__c ps : servizi) {
                if (!serviziPerPrenotazione.containsKey(ps.Prenotazione__c)) {
                    serviziPerPrenotazione.put(ps.Prenotazione__c, new List<Prenotazione_Servizio__c>());
                }
                serviziPerPrenotazione.get(ps.Prenotazione__c).add(ps);
            }

            // Elaboriamo le prenotazioni
            for (Prenotazione__c p : prenotazioni) {
                List<PrenotazioneDTO.ServizioDTO> serviziList = new List<PrenotazioneDTO.ServizioDTO>();
                Decimal sommaCostiServizi = 0;

                if (serviziPerPrenotazione.containsKey(p.Id)) {
                    for (Prenotazione_Servizio__c ps : serviziPerPrenotazione.get(p.Id)) {
                        serviziList.add(new PrenotazioneDTO.ServizioDTO(
                            ps.Servizio__r.Id, ps.Servizio__r.Name, ps.Servizio__r.Descrizione__c, ps.Servizio__r.Costo_Persona_Giorno__c
                        ));
                        sommaCostiServizi += ps.Servizio__r.Costo_Persona_Giorno__c;
                    }
                }

                // Calcolo del costo totale della prenotazione
                Integer giorniDifferenza = p.Data_Inizio__c.daysBetween(p.Data_Fine__c);

                Integer numLettiSingoli = p.Stanza__r.Num_Letti_Singoli__c != null ? (Integer)p.Stanza__r.Num_Letti_Singoli__c : 0;
                Integer numLettiMatrimoniali = p.Stanza__r.Num_Letti_Matrimoniali__c != null ? (Integer)p.Stanza__r.Num_Letti_Matrimoniali__c : 0;

                Integer numLettiTotali = numLettiSingoli + (numLettiMatrimoniali * 2);

                Decimal costoStanzaTotale = p.Stanza__r.Costo_Per_Notte__c * giorniDifferenza;
                Decimal costoServiziTotale = sommaCostiServizi * giorniDifferenza * numLettiTotali;
                Decimal costoTotale = costoStanzaTotale + costoServiziTotale;

                // Creiamo l'oggetto DTO della prenotazione
                prenotazioniList.add(new PrenotazioneDTO.PrenotazioneUtenteDTO(
                    p.Id, p.Data_Inizio__c, p.Data_Fine__c, p.Stato_Pagamento__c,
                    p.Stanza__c, p.Stanza__r.Name, p.Stanza__r.Descrizione__c, numLettiSingoli, 
                    numLettiMatrimoniali, p.Stanza__r.Costo_Per_Notte__c, p.Stanza__r.Tipologia_Stanza__c, 
                    p.Metodo_Pagamento__c, serviziList, costoTotale
                ));
            }

            return new PrenotazioneDTO.ListaPrenotazioniUtenteResponseDTO(prenotazioniList, 'Successo');

        } catch (Exception e) {
            return new PrenotazioneDTO.ListaPrenotazioniUtenteResponseDTO(null, 'Errore: ' + e.getMessage());
        }
    }
}