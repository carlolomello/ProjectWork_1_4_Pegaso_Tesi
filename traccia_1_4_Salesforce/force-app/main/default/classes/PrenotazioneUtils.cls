public with sharing class PrenotazioneUtils {
    public static void associaServiziPrenotazione(String idPrenotazione, List<String> serviziIds) {
        Savepoint sp = Database.setSavepoint(); // Creazione di un punto di ripristino per la transazione

        try {
            // Recupero i servizi già associati alla prenotazione
            Map<String, Id> serviziEsistenti = new Map<String, Id>();
            for (Prenotazione_Servizio__c s : [SELECT Id, Servizio__c FROM Prenotazione_Servizio__c WHERE Prenotazione__c = :idPrenotazione]) {
                serviziEsistenti.put(s.Servizio__c, s.Id);
            }

            List<Prenotazione_Servizio__c> daInserire = new List<Prenotazione_Servizio__c>();
            List<Prenotazione_Servizio__c> daEliminare = new List<Prenotazione_Servizio__c>();

            // Verifica i servizi da aggiungere
            for (String servizioId : serviziIds) {
                if (!serviziEsistenti.containsKey(servizioId)) {
                    daInserire.add(new Prenotazione_Servizio__c(Prenotazione__c = idPrenotazione, Servizio__c = servizioId));
                }
            }

            // Verifica i servizi da eliminare (quelli presenti a DB ma non in input)
            for (String servizioEsistenteId : serviziEsistenti.keySet()) {
                if (!serviziIds.contains(servizioEsistenteId)) {
                    daEliminare.add(new Prenotazione_Servizio__c(Id = serviziEsistenti.get(servizioEsistenteId)));
                }
            }

            // Eseguo le operazioni in batch per efficienza
            if (!daInserire.isEmpty()) {
                insert daInserire;
            }
            if (!daEliminare.isEmpty()) {
                delete daEliminare;
            }

        } catch (Exception e) {
            Database.rollback(sp); // Ripristina lo stato precedente in caso di errore
            throw new CalloutException('Errore durante l\'associazione dei servizi: ' + e.getMessage());
        }
    }
}