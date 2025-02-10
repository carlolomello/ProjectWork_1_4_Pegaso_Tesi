package com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.PrenotazioneAnnullataResponse;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.PrenotazioneAnnullataResponse.PrenotazioneAnnullata;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.SalesforceBatchUtils;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.model.BatchRecord;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.model.JobResponse;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.service.Utils;

@Component
public class BatchSchedulatoNotificaPrenotazioneAnnullata {
    
    @Autowired
    private Utils utils;  
    
    @Async  // La funzione sarà eseguita in un thread separato (esecuzione asincrona)
    @Scheduled(cron = "0 */5 * * * *")  // Esegue il metodo ogni 5 minuti, utilizzando la sintassi cron
    public void notificaPrenotazioneAnnullataMassiva() {
        
        // Recupera la lista delle prenotazioni annullate non ancora notificate (risposta in formato JSON)
        PrenotazioneAnnullataResponse prenotazioniAnnullateResponse = PrenotazioneAnnullataResponse.fromJson(Utils.getPrenotazioniAnnullateNonNotificate());
        
        // Se ci sono prenotazioni annullate da notificare, procedi
        if(!prenotazioniAnnullateResponse.getRecords().isEmpty()) {
            // Crea una lista per raccogliere i record di prenotazioni da aggiornare in batch
            List<BatchRecord> prenotazioniDaAggiornare = new ArrayList<BatchRecord>();
            
            // Per ogni prenotazione annullata, aggiungi il record alla lista e invia una email
            for(PrenotazioneAnnullata temp: prenotazioniAnnullateResponse.getRecords()) {
                prenotazioniDaAggiornare.add(new BatchRecord(temp.getId(),true));  // Aggiungi il record alla lista di aggiornamento batch
                utils.inviaEmailPrenotazioneAnnullata(temp);  // Invia l'email di notifica per la prenotazione annullata
            }
            
            // Avvia un job batch su Salesforce per aggiornare le prenotazioni annullate
            JobResponse jobResponse = SalesforceBatchUtils.openJob("update", "Prenotazione__c");
            SalesforceBatchUtils.addBatchToJob(jobResponse.getId(),prenotazioniDaAggiornare);  // Aggiungi i record da aggiornare al job
            jobResponse = SalesforceBatchUtils.closeJob(jobResponse);  // Chiudi il job dopo aver aggiunto i record
        }
    }
    
}
