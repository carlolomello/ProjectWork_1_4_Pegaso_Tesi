package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PrenotazioneDTO {

    // DTO per la richiesta di creazione di una prenotazione
    public static class InsertPrenotazioneRequestDTO {

        @JsonProperty("idUser")
        public String idUser;

        @JsonProperty("idStanza")
        public String idStanza;

        @JsonProperty("dataInizio")
        public String dataInizio;

        @JsonProperty("dataFine")
        public String dataFine;

        @JsonProperty("idMetodoPagamento")
        public String idMetodoPagamento;

        @JsonProperty("serviziIds")
        public List<String> serviziIds;
    }
    
    // DTO per la richiesta di creazione di una prenotazione
    public static class UpdatePrenotazioneRequestDTO {

    	@JsonProperty("prenotazioneId")
        public String prenotazioneId;

        @JsonProperty("dataInizio")
        public String dataInizio;

        @JsonProperty("dataFine")
        public String dataFine;

        @JsonProperty("serviziIds")
        public List<String> serviziIds;
    }

    // DTO per la risposta di creazione di una prenotazione
    public static class InsertPrenotazioneResponseDTO {
    	@JsonProperty("success")
        public Boolean success;

        @JsonProperty("message")
        public String message;

        @JsonProperty("prenotazioneId")
        public String prenotazioneId;
        
        public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getPrenotazioneId() {
			return prenotazioneId;
		}

		public void setPrenotazioneId(String prenotazioneId) {
			this.prenotazioneId = prenotazioneId;
		}

		// Costruttore senza argomenti per Jackson
        public InsertPrenotazioneResponseDTO() {
        }

        // Costruttore con argomenti
        public InsertPrenotazioneResponseDTO(Boolean success, String message, String prenotazioneId) {
        	this.success = success;
            this.message = message;
            this.prenotazioneId = prenotazioneId;
        }

        // Metodo per deserializzare il JSON in un oggetto DTO
        public static InsertPrenotazioneResponseDTO fromJsonToDTO(String json) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Usa il costruttore senza argomenti per la deserializzazione
                return objectMapper.readValue(json, InsertPrenotazioneResponseDTO.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    
 // DTO per la risposta di creazione di una prenotazione
    public static class UpdatePrenotazioneResponseDTO {
    	@JsonProperty("success")
        public Boolean success;
    	
        @JsonProperty("message")
        public String message;

        public String getMessage() {
			return message;
		}
        
        public Boolean getSuccess() {
			return success;
		}

		public void setSuccess(Boolean success) {
			this.success = success;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		// Costruttore senza argomenti per Jackson
        public UpdatePrenotazioneResponseDTO() {
        }

        // Costruttore con argomenti
        public UpdatePrenotazioneResponseDTO(Boolean success, String message) {
        	
        	this.success = success;
            this.message = message;
        }

        // Metodo per deserializzare il JSON in un oggetto DTO
        public static UpdatePrenotazioneResponseDTO fromJsonToDTO(String json) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                // Usa il costruttore senza argomenti per la deserializzazione
                return objectMapper.readValue(json, UpdatePrenotazioneResponseDTO.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    
    // DTO per la risposta di eliminazione di una prenotazione
    public static class DeletePrenotazioneResponseDTO {


        @JsonProperty("message")
        public String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		// Costruttore senza argomenti per Jackson
        public DeletePrenotazioneResponseDTO() {
        }

        // Costruttore con argomenti
        public DeletePrenotazioneResponseDTO(String message) {
            this.message = message;
        }
    }
}
