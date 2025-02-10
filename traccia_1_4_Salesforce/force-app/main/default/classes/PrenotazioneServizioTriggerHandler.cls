public with sharing class PrenotazioneServizioTriggerHandler implements TriggerHandlerInterface {
    private OverlappingValidatorInterface overlappingValidator;

    // Costruttore: istanzia il validatore tramite la factory
    public PrenotazioneServizioTriggerHandler() {
        this.overlappingValidator = OverlappingValidatorFactory.getValidator('PrenotazioneServizio'); // Istanzia il validatore specifico
    }

    // Prima dell'inserimento di un nuovo servizio per la prenotazione
    public void beforeInsert(List<SObject> newRecords) {
        // Chiama il validatore per eseguire la verifica di overlapping
        overlappingValidator.validate(newRecords); // Passa i nuovi record al validatore
    }

    // Prima dell'aggiornamento di un servizio per la prenotazione
    public void beforeUpdate(Map<Id, SObject> oldRecords, List<SObject> newRecords) {
        // Chiama il validatore per eseguire la verifica di overlapping
        overlappingValidator.validate(newRecords); // Passa i nuovi record al validatore
    }

    // Metodi vuoti per gli altri trigger
    public void beforeDelete(Map<Id, SObject> oldRecords) {
        // Logica per prima della cancellazione, se necessaria
    }

    public void afterInsert(List<SObject> newRecords) {
        // Eventuale logica post-inserimento, se necessaria
    }

    public void afterUpdate(Map<Id, SObject> oldRecords, List<SObject> newRecords) {
        // Eventuale logica post-aggiornamento, se necessaria
    }

    public void afterDelete(Map<Id, SObject> oldRecords) {
        // Eventuale logica post-eliminazione, se necessaria
    }
}