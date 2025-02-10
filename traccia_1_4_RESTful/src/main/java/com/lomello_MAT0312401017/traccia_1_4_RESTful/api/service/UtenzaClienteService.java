package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.connection.SalesforceConnection;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration.PasswordEncoder;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente.GetPrenotazioniClienteDTO;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente.PrenotazioneDTO;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente.UtenzaCliente;



public class UtenzaClienteService {

	// Metodo per effettuare il login di un cliente utilizzando email o cellulare
    public static String utenzaClienteLogin(String email_cellulare) {
        String queryUrl = SalesforceConnection.getInstanceUrl() + "/services/data/v62.0/query";
        // Costruisce la URL della query con parametri dinamici (email o cellulare)
        UriComponents builder = UriComponentsBuilder.fromUriString(queryUrl)
                .queryParam("q", "SELECT Id, Name, nome__c, cognome__c, email__c, cellulare__c, password__c "
                		+ "FROM Utenza_Cliente__c "
                		+ "WHERE is_active__c = true AND (email__c ='{?}' OR cellulare__c ='{?}') LIMIT 1")
                .buildAndExpand(email_cellulare, email_cellulare);    
        
        // Configura gli headers per l'autenticazione
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        String finalUrl = builder.toUriString();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(finalUrl, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {        	
            return response.getBody(); // Restituisce il corpo della risposta se la richiesta è riuscita
        } else {
            System.out.println("Errore durante la richiesta al servizio REST" + response.getStatusCode());
            return null;
        }
    }
    
    // Metodo per registrare un nuovo cliente, inclusa la codifica della password
    public static String utenzaClienteRegistrazione(UtenzaCliente utenzaCliente) {
    	// Costruisce l'URL di inserimento per la registrazione
    	StringBuilder insertUrlBuilder = new StringBuilder(SalesforceConnection.getInstanceUrl())
    	        .append("/services/data/v62.0/sobjects/Utenza_Cliente__c");
    	String insertUrl = insertUrlBuilder.toString();
        
        // Configura gli headers per l'autenticazione
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Codifica la password e crea il corpo JSON della richiesta
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = null;
        try {
        	utenzaCliente.setPassword(PasswordEncoder.encoder().encode(utenzaCliente.getPassword())); // Codifica la password
            jsonBody = objectMapper.writeValueAsString(utenzaCliente); // Converte l'oggetto in formato JSON
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }

        // Esegue la richiesta POST per creare il nuovo cliente
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        try {
            ResponseEntity<String> response = new RestTemplate().postForEntity(insertUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return response.getBody(); // Restituisce l'ID del nuovo cliente
            } else {
                System.out.println("Errore durante l'inserimento: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Metodo per confermare la registrazione di un cliente tramite token di attivazione
    public static boolean confermaRegistrazione(String token) {
    	// Costruisce l'URL per la query che cerca il token
        String queryUrl = SalesforceConnection.getInstanceUrl() + "/services/data/v62.0/query";
    	UriComponents builder = UriComponentsBuilder.fromUriString(queryUrl)
                .queryParam("q", "SELECT Id FROM Utenza_Cliente__c WHERE activation_token__c = {?} LIMIT 1")
                .buildAndExpand(token);    
    	
        String finalUrl = builder.toUriString();

        // Configura gli headers per l'autenticazione
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());

        HttpEntity<String> request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.exchange(finalUrl, HttpMethod.GET, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // Verifica se il token è valido e aggiorna lo stato dell'utente
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(response.getBody());
                JsonNode records = root.path("records");
                if (records.isArray() && records.size() > 0) {
                    String userId = records.get(0).get("Id").asText();
                    return aggiornaStatoAttivazione(userId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
 // Metodo per aggiornare lo stato di attivazione dell'utente
    public static boolean aggiornaStatoAttivazione(String userId) {
        // Costruzione dell'URL per l'aggiornamento tramite la REST API di Salesforce
        StringBuilder updateUrlBuilder = new StringBuilder(SalesforceConnection.getInstanceUrl())
                .append("/services/data/v62.0/composite/sobjects");

        String updateUrl = updateUrlBuilder.toString();

        // Creazione degli header per la richiesta HTTP, incluso il token di autorizzazione
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Creazione del corpo della richiesta con i dati di aggiornamento
        Map<String, Object> updateData = new HashMap<>();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("type", "Utenza_Cliente__c");

        updateData.put("attributes", attributes);
        updateData.put("Id", userId);
        updateData.put("activation_token__c", null);
        updateData.put("is_active__c", true);

        Map<String, Object> requestData = new HashMap<>();
        requestData.put("records", new Object[] { updateData });

        // Converte il corpo della richiesta in formato JSON
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonBody = objectMapper.writeValueAsString(requestData);
            HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

            // Esegue la richiesta HTTP di tipo PATCH per aggiornare i dati
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            RestTemplate restTemplate = new RestTemplate(factory);

            // Analizza la risposta e verifica se la richiesta ha avuto successo
            ResponseEntity<String> response = restTemplate.exchange(updateUrl, HttpMethod.PATCH, request, String.class);
            return response.getStatusCode().is2xxSuccessful(); // Restituisce true se la risposta è OK
        } catch (Exception e) {
            e.printStackTrace(); // Gestione degli errori
        }
        return false; // Restituisce false in caso di errore
    }

    // Metodo per creare una nuova prenotazione
    public static String creaPrenotazione(PrenotazioneDTO.InsertPrenotazioneRequestDTO requestData) {
        // Costruzione dell'URL per l'inserimento della prenotazione
        StringBuilder insertUrlBuilder = new StringBuilder(SalesforceConnection.getInstanceUrl())
                .append("/services/apexrest/api/insert_prenotazione");

        String insertUrl = insertUrlBuilder.toString();        

        // Creazione degli header per la richiesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Converte i dati della prenotazione (DTO) in formato JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = null;
        try {
            jsonBody = objectMapper.writeValueAsString(requestData);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Gestione degli errori
            return null; // Ritorna null se c'è un errore nella serializzazione
        }

        // Crea la richiesta HTTP con il corpo e gli header
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        // Esegue la richiesta POST per creare la prenotazione
        try {
            ResponseEntity<String> response = new RestTemplate().postForEntity(insertUrl, request, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                // Ritorna l'ID della prenotazione creata se la risposta è OK
                return response.getBody();
            } else {
                System.out.println("Errore durante la creazione della prenotazione: " + response.getStatusCode());
                return null; // Restituisce null in caso di errore
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gestione degli errori
            return null;
        }
    }

    // Metodo per aggiornare una prenotazione esistente
    public static String aggiornaPrenotazione(PrenotazioneDTO.UpdatePrenotazioneRequestDTO requestData) {
        // Costruzione dell'URL per l'aggiornamento della prenotazione
        StringBuilder updateUrlBuilder = new StringBuilder(SalesforceConnection.getInstanceUrl())
                .append("/services/apexrest/api/update_prenotazione");

        String updateUrl = updateUrlBuilder.toString();

        // Creazione degli header per la richiesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Converte il DTO di aggiornamento in formato JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = null;
        try {
            jsonBody = objectMapper.writeValueAsString(requestData);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Gestione degli errori
            return null;
        }

        // Crea la richiesta HTTP con il corpo e gli header
        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);

        // Esegue la richiesta PUT per aggiornare la prenotazione
        try {
            ResponseEntity<String> response = new RestTemplate().exchange(updateUrl, HttpMethod.PUT, request, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                // Restituisce la risposta dell'aggiornamento se la richiesta ha avuto successo
                return response.getBody();
            } else {
                System.out.println("Errore durante l'aggiornamento della prenotazione: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace(); // Gestione degli errori
            return null;
        }
    }

    // Metodo per ottenere le prenotazioni di un cliente
    public static GetPrenotazioniClienteDTO.GetPrenotazioniClienteResponseDTO getPrenotazioniCliente(String utenzaCliente) {
        // Costruzione dell'URL per recuperare le prenotazioni
        StringBuilder urlBuilder = new StringBuilder(SalesforceConnection.getInstanceUrl())
                .append("/services/apexrest/api/get_prenotazioni_utente")
                .append("?utenza_cliente=").append(utenzaCliente);

        String url = urlBuilder.toString();

        // Creazione degli header per la richiesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crea la richiesta GET
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                // Decodifica la risposta JSON e ritorna il DTO con le prenotazioni
                GetPrenotazioniClienteDTO.GetPrenotazioniClienteResponseDTO responseDTO = 
                    GetPrenotazioniClienteDTO.GetPrenotazioniClienteResponseDTO.fromJson(response.getBody());
                return responseDTO; 
            } catch (IOException e) {
                e.printStackTrace(); // Gestione degli errori
                return new GetPrenotazioniClienteDTO.GetPrenotazioniClienteResponseDTO(); // Restituisce un DTO vuoto in caso di errore
            }
        } else {
            System.out.println("Errore durante la richiesta al servizio REST: " + response.getStatusCode());
            return null; // Restituisce null in caso di errore
        }
    }

    // Metodo per eliminare una prenotazione
    public static String deletePrenotazione(String prenotazioneId) {
        // Costruzione dell'URL per eliminare la prenotazione specifica
        String url = UriComponentsBuilder.fromUriString(SalesforceConnection.getInstanceUrl())
                .path("/services/data/v52.0/sobjects/Prenotazione__c/{prenotazioneId}")
                .buildAndExpand(prenotazioneId)
                .toUriString();

        // Creazione degli header per la richiesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crea la richiesta DELETE
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.DELETE, request, String.class);

        if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
            // Restituisce un messaggio di successo se la richiesta è andata a buon fine
            return "Prenotazione eliminata con successo";
        } else {
            System.out.println("Errore durante la richiesta al servizio REST");
            return null; // Restituisce null in caso di errore
        }
    }

   
}
