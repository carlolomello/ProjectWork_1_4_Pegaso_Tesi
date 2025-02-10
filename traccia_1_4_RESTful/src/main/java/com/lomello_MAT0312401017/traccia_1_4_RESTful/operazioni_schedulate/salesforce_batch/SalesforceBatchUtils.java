package com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.connection.SalesforceConnection;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.model.JobRequest;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.model.JobResponse;

public class SalesforceBatchUtils {
    public static JobResponse openJob(String operation, String object) {
        // Costruzione dell'URL per creare il job
        String url = UriComponentsBuilder.fromUriString(SalesforceConnection.getInstanceUrl())
                .path("/services/async/55/job")
                .toUriString();

        // Crea un'istanza di JobRequest
        JobRequest.OpenJob jobRequest = new JobRequest.OpenJob(operation, object, "JSON");

        // Serializza l'oggetto JobRequest in JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jobBody = "";
        try {
            jobBody = objectMapper.writeValueAsString(jobRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Impostazioni delle intestazioni della richiesta
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-SFDC-Session", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crea la richiesta HTTP
        HttpEntity<String> request = new HttpEntity<>(jobBody, headers);
        
        // Esegui la richiesta per creare il job
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        // Verifica la risposta
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return JobResponse.deserializeJobResponse(response.getBody());
        } else {
            System.out.println("Errore durante la creazione del job: " + response.getStatusCode());
            return null;
        }
    }
    
    public static JobResponse closeJob(JobResponse job) {
        // Costruzione dell'URL per chiudere il job, utilizzando il jobId dinamicamente
        String url = UriComponentsBuilder.fromUriString(SalesforceConnection.getInstanceUrl())
                .path("/services/async/55/job/{jobId}")
                .buildAndExpand(job.getId())
                .toUriString();

        // Crea un'istanza di JobRequest
        JobRequest.CloseJob jobRequest = new JobRequest.CloseJob("Closed");

        // Serializza l'oggetto JobRequest in JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jobBody = "";
        try {
            jobBody = objectMapper.writeValueAsString(jobRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // Impostazioni delle intestazioni della richiesta
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-SFDC-Session", "Bearer " + SalesforceConnection.getAccessToken());
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Crea la richiesta HTTP
        HttpEntity<String> request = new HttpEntity<>(jobBody, headers);
        
        // Esegui la richiesta per creare il job
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        // Verifica la risposta
        if (response.getStatusCode() == HttpStatus.OK) {
            return JobResponse.deserializeJobResponse(response.getBody());
        } else {
            System.out.println("Errore durante la chiusura del job: " + response.getStatusCode());
            return null;
        }
    }
    
    
    public static <T> String addBatchToJob(String jobId, List<T> batchRecords) {
        // Costruzione dell'URL per aggiungere il batch al job
        String url = UriComponentsBuilder.fromUriString(SalesforceConnection.getInstanceUrl())
                .path("/services/async/55/job/{jobId}/batch")
                .buildAndExpand(jobId)
                .toUriString();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Serializza la lista di oggetti BatchRecord in JSON
            String batchBody = objectMapper.writeValueAsString(batchRecords);

            // Impostazioni delle intestazioni della richiesta
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-SFDC-Session", "Bearer " + SalesforceConnection.getAccessToken());
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Crea la richiesta HTTP
            HttpEntity<String> request = new HttpEntity<>(batchBody, headers);

            // Esegui la richiesta per aggiungere il batch al job
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            // Verifica la risposta
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return "Batch aggiunto al job con successo.";
            } else {
                System.out.println("Errore durante l'aggiunta del batch al job: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Errore durante la serializzazione del corpo della richiesta: " + e.getMessage());
            return null;
        }
    }
    
}
