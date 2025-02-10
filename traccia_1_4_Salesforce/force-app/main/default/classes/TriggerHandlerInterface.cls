public interface TriggerHandlerInterface {
    void beforeInsert(List<SObject> newRecords);
    void beforeUpdate(Map<Id, SObject> oldRecords, List<SObject> newRecords);
    void beforeDelete(Map<Id, SObject> oldRecords);
    void afterInsert(List<SObject> newRecords);
    void afterUpdate(Map<Id, SObject> oldRecords, List<SObject> newRecords);
    void afterDelete(Map<Id, SObject> oldRecords);
}