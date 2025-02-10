public with sharing class PrenotazioneServizioOverlappingValidator implements OverlappingValidatorInterface {

    public void validate(List<SObject> records) {
        List<Prenotazione_Servizio__c> serviziToCheck = new List<Prenotazione_Servizio__c>();
        
        // Converti i record SObject in Prenotazione_Servizio__c
        for (SObject record : records) {
            if (record instanceof Prenotazione_Servizio__c) {
                serviziToCheck.add((Prenotazione_Servizio__c) record);
            }
        }

        // Verifica se esistono coppie duplicate tra la prenotazione e il servizio
        for (Prenotazione_Servizio__c servizio : serviziToCheck) {
            // Query per trovare servizi già associati alla stessa prenotazione
            List<Prenotazione_Servizio__c> existingServices = [
                SELECT Id, Servizio__c, Prenotazione__c
                FROM Prenotazione_Servizio__c
                WHERE Servizio__c = :servizio.Servizio__c
                AND Prenotazione__c = :servizio.Prenotazione__c
                AND Id != :servizio.Id
            ];

            // Se esiste già un record che lega lo stesso servizio alla stessa prenotazione
            if (!existingServices.isEmpty()) {
                // Aggiungi un errore al record per impedire l'inserimento
                servizio.Servizio__c.addError('Il servizio è già stato associato a questa prenotazione.');
            }
        }
    }
}