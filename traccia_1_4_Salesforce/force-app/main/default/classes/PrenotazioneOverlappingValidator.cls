public class PrenotazioneOverlappingValidator implements OverlappingValidatorInterface {

    public void validate(List<SObject> newRecords) {
        List<Prenotazione__c> newPrenotazioni = (List<Prenotazione__c>) newRecords;
        Set<Id> stanzaIds = new Set<Id>();

        for (Prenotazione__c p : newPrenotazioni) {
            stanzaIds.add(p.Stanza__c);
        }

        // Recuperiamo le prenotazioni esistenti
        Map<Id, List<Prenotazione__c>> existingBookingsMap = new Map<Id, List<Prenotazione__c>>();
        List<Prenotazione__c> existingBookings = [
            SELECT Id, Stanza__c, Data_Inizio__c, Data_Fine__c 
            FROM Prenotazione__c 
            WHERE Stanza__c IN :stanzaIds 
        ];

        for (Prenotazione__c p : existingBookings) {
            if (!existingBookingsMap.containsKey(p.Stanza__c)) {
                existingBookingsMap.put(p.Stanza__c, new List<Prenotazione__c>());
            }
            existingBookingsMap.get(p.Stanza__c).add(p);
        }

        // Verifica sovrapposizioni
        for (Prenotazione__c newP : newPrenotazioni) {
            if (existingBookingsMap.containsKey(newP.Stanza__c)) {
                for (Prenotazione__c existingP : existingBookingsMap.get(newP.Stanza__c)) {
                    if (newP.Id == existingP.Id) continue; // Evitiamo di confrontare la stessa prenotazione
                    System.debug('Entra');
                    if (!(newP.Data_Fine__c < existingP.Data_Inizio__c || newP.Data_Inizio__c > existingP.Data_Fine__c)) {
                        newP.addError('La stanza è già prenotata in queste date.');
                    }
                }
            }
        }
    }
}