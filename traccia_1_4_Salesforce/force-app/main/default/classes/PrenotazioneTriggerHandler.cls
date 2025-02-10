public class PrenotazioneTriggerHandler implements TriggerHandlerInterface {
    private OverlappingValidatorInterface overlappingValidator;

    //Factory per ottenere il validatore
    public PrenotazioneTriggerHandler() {
        this.overlappingValidator = OverlappingValidatorFactory.getValidator('Prenotazione');
    }

    public void beforeInsert(List<SObject> newRecords) {
        overlappingValidator.validate(newRecords);
    }

    public void beforeUpdate(Map<Id, SObject> oldRecords, List<SObject> newRecords) {
        overlappingValidator.validate(newRecords);
    }

    public void beforeDelete(Map<Id, SObject> oldRecords) {
        // Logica per prima di eliminare una prenotazione (se necessaria)
    }

    public void afterInsert(List<SObject> newRecords) {
        // Eventuale logica post-inserimento
    }

    public void afterUpdate(Map<Id, SObject> oldRecords, List<SObject> newRecords) {
        // Eventuale logica post-aggiornamento
    }

    public void afterDelete(Map<Id, SObject> oldRecords) {
        // Eventuale logica post-eliminazione
    }
}