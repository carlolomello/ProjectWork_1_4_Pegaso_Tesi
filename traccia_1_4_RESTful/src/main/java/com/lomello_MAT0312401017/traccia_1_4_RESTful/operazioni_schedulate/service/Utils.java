package com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.connection.SalesforceConnection;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.email.Mail;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.PrenotazioneAnnullataResponse;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service.email.EmailService;

@Component
public class Utils {
	
	@Autowired
    private EmailService emailService;	
	
    public static String getPrenotazioniAnnullateNonNotificate() {
        String queryUrl = SalesforceConnection.getInstanceUrl() + "/services/data/v52.0/query";
        UriComponents builder = UriComponentsBuilder.fromUriString(queryUrl)
                .queryParam("q", "SELECT Id, Name, data_inizio__c, data_fine__c, metodo_pagamento__c, stato_pagamento__c, stato_pagamento_user_notified__c, Utenza_Cliente__r.Name , Utenza_Cliente__r.email__c from Prenotazione__c where stato_pagamento__c = 'annullato_verifica' and stato_pagamento_user_notified__c = false").buildAndExpand();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        String finalUrl = builder.toUriString();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(finalUrl, HttpMethod.GET, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.out.println("Errore durante la richiesta al servizio REST");
            return null;
        }
    }
    
    public void inviaEmailPrenotazioneAnnullata(PrenotazioneAnnullataResponse.PrenotazioneAnnullata prenotazione) {
        String emailTemplate = "<html>..."
                + "<h2>Ciao " + prenotazione.getUtenzaCliente().getNome() + ",</h2>"
                + "<p>Ti informiamo che la tua prenotazione è stata <strong>annullata</strong>.</p>"
                + "<ul>"
                + "<li><strong>Data Inizio:</strong> " + prenotazione.getDataInizio() + "</li>"
                + "<li><strong>Data Fine:</strong> " + prenotazione.getDataFine() + "</li>"
                + "<li><strong>Metodo di Pagamento:</strong> " + prenotazione.getMetodoPagamento() + "</li>"
                + "</ul>"
                + "<p>Se hai domande, non esitare a contattarci.</p>"
                + "<p>Grazie,<br>Il team dell'hotel</p>"
                + "</html>";

        Mail mail = new Mail();
        mail.setRecipient(prenotazione.getUtenzaCliente().getEmail());
        mail.setSubject("Prenotazione Annullata - " + prenotazione.getName());
        mail.setMessage(emailTemplate);
        
        emailService.sendHtmlMail(mail);
    }
}
