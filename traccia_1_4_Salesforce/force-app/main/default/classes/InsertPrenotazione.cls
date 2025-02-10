@RestResource(urlMapping='/api/insert_prenotazione')
global with sharing class InsertPrenotazione {
    @HttpPost
    global static PrenotazioneDTO.InsertPrenotazioneResponseDTO creaPrenotazione() {
        try {
            // Parsing del body JSON in un oggetto DTO
            RestRequest req = RestContext.request;
            PrenotazioneDTO.InsertPrenotazioneRequestDTO requestData =
                (PrenotazioneDTO.InsertPrenotazioneRequestDTO) JSON.deserialize(req.requestBody.toString(), PrenotazioneDTO.InsertPrenotazioneRequestDTO.class);

            // Ottenere lo stato di pagamento dalla strategia
            String statoPagamento = PaymentStatusStrategyWrapper.getPaymentStatus(requestData.idMetodoPagamento);

            // Creazione della prenotazione con lo stato pagamento valorizzato
            Prenotazione__c prenotazione = new Prenotazione__c(
                Utenza_Cliente__c = requestData.idUser,
                Stanza__c = requestData.idStanza,
                Data_Inizio__c = Date.valueOf(requestData.dataInizio),
                Data_Fine__c = Date.valueOf(requestData.dataFine),
                Metodo_Pagamento__c = requestData.idMetodoPagamento,
                Stato_Pagamento__c = statoPagamento
            );
            insert prenotazione;

            // Associazione servizi
            PrenotazioneUtils.associaServiziPrenotazione(prenotazione.Id, requestData.serviziIds);

            // Creazione della risposta con il DTO
            return new PrenotazioneDTO.InsertPrenotazioneResponseDTO(true, 'Prenotazione creata con successo', prenotazione.Id);

        } catch (Exception e) {
            return new PrenotazioneDTO.InsertPrenotazioneResponseDTO(false, 'Errore durante la creazione: ' + e.getMessage(), null);
        }
    }
}