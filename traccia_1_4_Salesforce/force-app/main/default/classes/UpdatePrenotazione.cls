@RestResource(urlMapping='/api/update_prenotazione')
global with sharing class UpdatePrenotazione {
    @HttpPut
    global static PrenotazioneDTO.UpdatePrenotazioneResponseDTO modificaPrenotazione() {
        try {
            // Parsing del body JSON in un oggetto DTO
            RestRequest req = RestContext.request;
            PrenotazioneDTO.UpdatePrenotazioneRequestDTO requestData =
                (PrenotazioneDTO.UpdatePrenotazioneRequestDTO) JSON.deserialize(req.requestBody.toString(), PrenotazioneDTO.UpdatePrenotazioneRequestDTO.class);

            // Recupero e aggiornamento della prenotazione
            Prenotazione__c prenotazione = [SELECT Id FROM Prenotazione__c WHERE Id = :requestData.prenotazioneId LIMIT 1];
            prenotazione.Data_Inizio__c = Date.valueOf(requestData.dataInizio);
            prenotazione.Data_Fine__c = Date.valueOf(requestData.dataFine);
            update prenotazione;

            // Aggiornamento servizi
            PrenotazioneUtils.associaServiziPrenotazione(requestData.prenotazioneId, requestData.serviziIds);

            // Creazione della risposta con il DTO
            return new PrenotazioneDTO.UpdatePrenotazioneResponseDTO(true,'Prenotazione aggiornata con successo');
        } catch (Exception e) {
            return  new PrenotazioneDTO.UpdatePrenotazioneResponseDTO(false,'Errore durante la modifica: ' + e.getMessage());
        }
    }
}