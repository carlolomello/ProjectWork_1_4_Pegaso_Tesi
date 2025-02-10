package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration.HotelManagement;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.email.ContattoRequest;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.email.Mail;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.GetStanzeDisponibiliDTO;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.MetodoPagamentoPrenotazione;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.Servizio;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.TipologiaStanza;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service.UtilityService;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service.email.EmailService;

@RestController // Definisce questa classe come un controller REST
@RequestMapping("/api/utility") // Imposta il prefisso dell'URL per le richieste
public class UtilityController {
	
	@Autowired
    private EmailService emailService; // Servizio per inviare email
	@Autowired
	private HotelManagement hotelManagement; // Gestione configurazioni hotel
	
    // Endpoint per ottenere le stanze disponibili in un determinato intervallo di date
    @GetMapping("/stanze_disponibili")
    public ResponseEntity<List<GetStanzeDisponibiliDTO.GetStanzeDisponibiliResponseDTO>> getStanzeDisponibili(
            @RequestParam String dataInizio, // Data di inizio per la ricerca
            @RequestParam String dataFine, // Data di fine per la ricerca
            @RequestParam(required = false) Integer postiLetto, // Opzionale, numero di letti
            @RequestParam(required = false) String tipologiaStanza) { // Opzionale, tipo di stanza
        try {
            // Chiama il servizio per ottenere le stanze disponibili
            List<GetStanzeDisponibiliDTO.GetStanzeDisponibiliResponseDTO> stanze = 
                UtilityService.getStanzeDisponibili(dataInizio, dataFine, postiLetto, tipologiaStanza);
            
            // Se vengono trovate stanze, le restituisce con codice 200 (OK)
            if (stanze != null && !stanze.isEmpty()) {
                return ResponseEntity.ok(stanze); 
            } else {
                // Se non ci sono stanze, restituisce codice 404 (Not Found)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>()); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore, restituisce codice 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
    
    // Endpoint per ottenere la lista delle tipologie di stanza
    @GetMapping("/tipologia_stanze")
    public ResponseEntity<List<TipologiaStanza>> getTipologiaStanze() {
        try {
            // Chiama il servizio per ottenere la lista delle tipologie di stanza
            List<TipologiaStanza> tipologiaStanzaList = TipologiaStanza.deserializeTipologiaStanzaListSOQL(UtilityService.getTipologiaStanze());
            
            // Se la lista non è vuota, restituisce il risultato con codice 200 (OK)
            if (tipologiaStanzaList != null && !tipologiaStanzaList.isEmpty()) {
                return ResponseEntity.ok(tipologiaStanzaList); 
            } else {
                // Se la lista è vuota, restituisce codice 404 (Not Found)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>()); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore, restituisce codice 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
    
    // Endpoint per ottenere i metodi di pagamento per le prenotazioni
    @GetMapping("/metodi_pagamento")
    public ResponseEntity<List<MetodoPagamentoPrenotazione>> getMetodiPagamentoPrenotazione() {
        try {
            // Chiama il servizio per ottenere i metodi di pagamento
            List<MetodoPagamentoPrenotazione> metodoPagamentoPrenotazioneList = MetodoPagamentoPrenotazione.deserializeMetodoPagamentoPrenotazioneListSOQL(UtilityService.getMetodoPagamentoPrenotazione());
            
            // Se la lista non è vuota, restituisce il risultato con codice 200 (OK)
            if (metodoPagamentoPrenotazioneList != null && !metodoPagamentoPrenotazioneList.isEmpty()) {
                return ResponseEntity.ok(metodoPagamentoPrenotazioneList); 
            } else {
                // Se la lista è vuota, restituisce codice 404 (Not Found)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>()); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore, restituisce codice 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
    
    // Endpoint per ottenere i servizi disponibili
    @GetMapping("/servizi")
    public ResponseEntity<List<Servizio>> getServizi() {
        try {
            // Chiama il servizio per ottenere la lista dei servizi
            List<Servizio> serviziList = Servizio.deserializeServizioListSOQL(UtilityService.getServizi());
            
            // Se la lista non è vuota, restituisce il risultato con codice 200 (OK)
            if (serviziList != null && !serviziList.isEmpty()) {
                return ResponseEntity.ok(serviziList); 
            } else {
                // Se la lista è vuota, restituisce codice 404 (Not Found)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>()); 
            }
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore, restituisce codice 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
    }
    
    // Endpoint per inviare un'email di contatto
    @PostMapping("/invia_email_contatto")
    public ResponseEntity<String> inviaEmailContatto(@RequestBody ContattoRequest contattoRequest) {
        try {
        	// Costruzione del template HTML dell'email
        	String emailTemplate = "<html>"
                    + "<head>"
                    + "<style>"
                    + "body { font-family: Arial, sans-serif; color: #333; }"
                    + ".container { max-width: 600px; margin: 0 auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; background-color: #f4f4f4; }"
                    + ".header { background-color: #6e0000; color: #fff; padding: 10px; text-align: center; font-size: 1.5rem; border-radius: 8px 8px 0 0; }"
                    + ".content { margin-top: 20px; font-size: 1rem; line-height: 1.5; }"
                    + ".footer { margin-top: 30px; font-size: 0.9rem; color: #888; text-align: center; }"
                    + ".highlight { color: #6e0000; font-weight: bold; }"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<div class='header'>"
                    + "<p>Messaggio di Contatto da " + contattoRequest.getNominativo() + "</p>"
                    + "</div>"
                    + "<div class='content'>"
                    + "<p><span class='highlight'>Contatto:</span> " + contattoRequest.getContatto() + "</p>"
                    + "<p><span class='highlight'>Oggetto:</span> " + contattoRequest.getOggetto() + "</p>"
                    + "<p><span class='highlight'>Messaggio:</span></p>"
                    + "<p>" + contattoRequest.getMessaggio() + "</p>"
                    + "</div>"
                    + "<div class='footer'>"
                    + "<p>Messaggio inviato da Hotel Management</p>"
                    + "</div>"
                    + "</div>"
                    + "</body>"
                    + "</html>";
        	
            // Crea un oggetto Mail e imposta i suoi campi
            Mail mail = new Mail();
            mail.setRecipient(hotelManagement.getRecepistEmail());
            mail.setSubject(contattoRequest.getOggetto());
            mail.setMessage(emailTemplate);
            
            // Invia l'email tramite il servizio email
            emailService.sendHtmlMail(mail);
            
            // Restituisce una risposta di successo con codice 200 (OK)
            return ResponseEntity.status(HttpStatus.OK).body("Email inviata correttamente.");
        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore, restituisce codice 500 (Internal Server Error)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'invio dell'email.");
        }
    }

}
