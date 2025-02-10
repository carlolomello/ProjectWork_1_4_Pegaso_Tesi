package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.connection.SalesforceConnection;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility.GetStanzeDisponibiliDTO;

public class UtilityService {

    // Metodo per recuperare le stanze disponibili in base ai parametri di ricerca
	public static List<GetStanzeDisponibiliDTO.GetStanzeDisponibiliResponseDTO> getStanzeDisponibili(String dataInizio,
			String dataFine, Integer postiLetto, String tipologiaStanza) {

		// Crea la URL di richiesta per l'API REST di Salesforce
		StringBuilder urlBuilder = new StringBuilder(SalesforceConnection.getInstanceUrl())
		        .append("/services/apexrest/api/available_rooms")
		        .append("?data_inizio=").append(dataInizio)
		        .append("&data_fine=").append(dataFine);

		// Aggiunge i parametri opzionali alla URL
		if (postiLetto != null) {
		    urlBuilder.append("&posti_letto=").append(postiLetto);
		}

		if (tipologiaStanza != null && !tipologiaStanza.isEmpty()) {
		    urlBuilder.append("&tipologia_stanza=").append(tipologiaStanza);
		}

		// Costruisce la URL finale
		String url = urlBuilder.toString();

		// Crea gli header della richiesta HTTP, incluso il token di accesso
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Crea la richiesta HTTP con gli header
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);

		// Gestisce la risposta e decodifica il JSON in un oggetto DTO
		if (response.getStatusCode() == HttpStatus.OK) {
			try {
				GetStanzeDisponibiliDTO.GetStanzeDisponibiliWrapperDTO wrapper = GetStanzeDisponibiliDTO.GetStanzeDisponibiliWrapperDTO
						.fromJson(response.getBody());
				return wrapper.getStanze(); // Restituisce la lista delle stanze disponibili
			} catch (IOException e) {
				e.printStackTrace();
				return new ArrayList<>(); // Restituisce una lista vuota in caso di errore di parsing
			}
		} else {
			System.out.println("Errore durante la richiesta al servizio REST: " + response.getStatusCode());
			return null; // Restituisce null in caso di errore
		}
	}
	
    // Metodo per ottenere il metodo di pagamento disponibile per una prenotazione
    public static String getMetodoPagamentoPrenotazione() {
        // Costruisce la URL per eseguire la query per i metodi di pagamento
        String queryUrl = SalesforceConnection.getInstanceUrl() + "/services/data/v52.0/query";
        UriComponents builder = UriComponentsBuilder.fromUriString(queryUrl)
                .queryParam("q", "SELECT Id, DeveloperName, Label, descrizione__c FROM Metodo_Pagamento_Prenotazione__mdt").buildAndExpand();

        // Crea gli header della richiesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Costruisce la URL finale
        String finalUrl = builder.toUriString();

        // Crea la richiesta HTTP
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(finalUrl, HttpMethod.GET, request, String.class);

        // Gestisce la risposta
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // Restituisce il corpo della risposta (metodo di pagamento)
        } else {
            System.out.println("Errore durante la richiesta al servizio REST");
            return null; // Restituisce null in caso di errore
        }
    }
    
    // Metodo per ottenere la tipologia delle stanze disponibili
    public static String getTipologiaStanze() {
        // Costruisce la URL per eseguire la query per le tipologie di stanze
        String queryUrl = SalesforceConnection.getInstanceUrl() + "/services/data/v52.0/query";
        UriComponents builder = UriComponentsBuilder.fromUriString(queryUrl)
                .queryParam("q", "SELECT Id, DeveloperName, Label, descrizione__c FROM Tipologia_Stanza__mdt").buildAndExpand();

        // Crea gli header della richiesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Costruisce la URL finale
        String finalUrl = builder.toUriString();

        // Crea la richiesta HTTP
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(finalUrl, HttpMethod.GET, request, String.class);

        // Gestisce la risposta
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody(); // Restituisce il corpo della risposta (tipologia stanze)
        } else {
            System.out.println("Errore durante la richiesta al servizio REST");
            return null; // Restituisce null in caso di errore
        }
    }
    
    // Metodo per ottenere i servizi disponibili per le prenotazioni
    public static String getServizi() {
        // Costruisce la URL per eseguire la query per i servizi
        String queryUrl = SalesforceConnection.getInstanceUrl() + "/services/data/v52.0/query";
        UriComponents builder = UriComponentsBuilder.fromUriString(queryUrl)
                .queryParam("q", "SELECT Id, Name, costo_persona_giorno__c, descrizione__c FROM Servizio__c").buildAndExpand();

        // Crea gli header della richiesta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Costruisce la URL finale
        String finalUrl = builder.toUriString();

        // Crea la richiesta HTTP
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(finalUrl, HttpMethod.GET, request, String.class);

        // Gestisce la risposta
        if (response.getStatusCode() == HttpStatus.OK) {
        	System.out.println("Servizi :"+response.getBody());
            return response.getBody(); // Restituisce il corpo della risposta (servizi)
        } else {
            System.out.println("Errore durante la richiesta al servizio REST");
            return null; // Restituisce null in caso di errore
        }
    }
}
