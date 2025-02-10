trigger PrenotazioneServizioTrigger on Prenotazione_Servizio__c (
    before insert, before update, before delete, after insert, after update, after delete
) {
    PrenotazioneServizioTriggerHandler handler = new PrenotazioneServizioTriggerHandler();

    if (Trigger.isBefore) {
        if (Trigger.isInsert) handler.beforeInsert(Trigger.new);
        if (Trigger.isUpdate) handler.beforeUpdate(Trigger.oldMap, Trigger.new);
        if (Trigger.isDelete) handler.beforeDelete(Trigger.oldMap);
    }

    if (Trigger.isAfter) {
        if (Trigger.isInsert) handler.afterInsert(Trigger.new);
        if (Trigger.isUpdate) handler.afterUpdate(Trigger.oldMap, Trigger.new);
        if (Trigger.isDelete) handler.afterDelete(Trigger.oldMap);
    }
}