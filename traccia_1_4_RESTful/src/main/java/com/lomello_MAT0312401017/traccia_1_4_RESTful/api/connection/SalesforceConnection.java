package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.connection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration.AccessTokenResponse;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration.SalesforceOrgAuth;

import jakarta.annotation.PostConstruct;

@Configuration
public class SalesforceConnection {
    private static SalesforceOrgAuth salesforceOrgAuth;
    private static String accessToken;
    private static String instanceUrl;
    private static long tokenExpirationTime = 0; // Timestamp di scadenza del token

    // Iniezione della configurazione di autenticazione di Salesforce
    @Autowired
    private void setSalesforceConfiguration(SalesforceOrgAuth salesforceOrgAuth) {
        SalesforceConnection.salesforceOrgAuth = salesforceOrgAuth;
    }

    // Metodo eseguito dopo la costruzione del bean per ottenere il token iniziale
    @PostConstruct
    private void initializer() {
        refreshAccessToken();
    }

    /**
     * Metodo sincronizzato per ottenere un nuovo token di accesso da Salesforce.
     * Verifica le credenziali, invia una richiesta HTTP e aggiorna le variabili di accesso.
     */
    private static synchronized void refreshAccessToken() {
        try {
            // Verifica che le credenziali non siano vuote
            if (salesforceOrgAuth.getUsername() == null || salesforceOrgAuth.getUsername().isEmpty()
                    || salesforceOrgAuth.getPassword() == null || salesforceOrgAuth.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Username e password non possono essere vuoti");
            }

            // Creazione dell'header della richiesta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // Costruzione del corpo della richiesta con i parametri di autenticazione
            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("grant_type", salesforceOrgAuth.getGrantType());
            requestBody.add("client_id", salesforceOrgAuth.getClientId());
            requestBody.add("client_secret", salesforceOrgAuth.getClientSecret());
            requestBody.add("username", salesforceOrgAuth.getUsername());
            requestBody.add("password", salesforceOrgAuth.getPassword());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

            // Invio della richiesta HTTP a Salesforce per ottenere il token di accesso
            ResponseEntity<String> response = new RestTemplate().exchange(
                salesforceOrgAuth.getSalesforceAuthUrl(), 
                HttpMethod.POST, 
                request, 
                String.class
            );

            // Verifica se la risposta è valida e aggiorna le credenziali
            if (response.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                AccessTokenResponse tokenResponse = objectMapper.readValue(response.getBody(), AccessTokenResponse.class);
                
                accessToken = tokenResponse.getAccessToken();
                instanceUrl = tokenResponse.getInstanceUrl();

                long issuedAt = tokenResponse.getIssuedAt();
                tokenExpirationTime = issuedAt + (2 * 60 * 60 * 1000); // Token valido per 2 ore

                System.out.println("\ud83d\udd11 Nuovo token ricevuto! Scadrà alle: " + convertDateToString(new Date(tokenExpirationTime), "dd/MM/yyyy HH:mm:ss.SSS"));

            } else {
                throw new RuntimeException("Errore nel recupero del token. Codice: " + response.getStatusCode().value());
            }

        } catch (Exception e) {
            System.out.println("Errore durante il recupero del token di accesso da Salesforce");
            e.printStackTrace();
            throw new RuntimeException("Errore durante il recupero del token di accesso da Salesforce", e);
        }
    }

    /**
     * Restituisce il token di accesso valido. Se il token è scaduto, ne richiede uno nuovo.
     * @return Il token di accesso attuale.
     */
    public static String getAccessToken() {
        if (isTokenExpired()) {
            refreshAccessToken();
        }
        return accessToken;
    }

    /**
     * Restituisce l'URL dell'istanza Salesforce attuale. Se il token è scaduto, lo rinnova.
     * @return L'URL dell'istanza Salesforce.
     */
    public static String getInstanceUrl() {
        if (isTokenExpired()) {
            refreshAccessToken();
        }
        return instanceUrl;
    }
    
    /**
     * Converte una data in una stringa formattata secondo il pattern specificato.
     * @param date La data da convertire.
     * @param pattern Il pattern del formato.
     * @return La data formattata come stringa.
     */
    public static String convertDateToString(Date date, String pattern) {
        if (date == null)
            return null;
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * Verifica se il token di accesso è scaduto.
     * @return true se il token è scaduto, altrimenti false.
     */
    private static boolean isTokenExpired() {
        return System.currentTimeMillis() >= tokenExpirationTime;
    }
}
