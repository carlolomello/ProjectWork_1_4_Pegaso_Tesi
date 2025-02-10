global class PrenotazioneVerificaPagamentoScheduler implements Schedulable{
    global void execute(SchedulableContext ctx) {

        Date today = Date.today();

        // Query per ottenere le prenotazioni che rispettano i criteri
        List<Prenotazione__c> prenotazioniDaAggiornare = [
            SELECT Id, Data_Inizio__c, Metodo_Pagamento__c, Stato_Pagamento__c, Stato_Pagamento_User_Notified__c
            FROM Prenotazione__c
            WHERE 
                Data_Inizio__c >= :today
                AND Data_Inizio__c <= :today.addDays(3)
                AND Metodo_Pagamento__c = 'Bonifico'
                AND Stato_Pagamento__c = 'in_verifica'
                AND Stato_Pagamento_User_Notified__c = false
        ];

        // Aggiornamento dello stato pagamento
        if (!prenotazioniDaAggiornare.isEmpty()) {
            for (Prenotazione__c p : prenotazioniDaAggiornare) {
                p.Stato_Pagamento__c = 'Annullato_Verifica';
            }
            update prenotazioniDaAggiornare;
        }
    }
}