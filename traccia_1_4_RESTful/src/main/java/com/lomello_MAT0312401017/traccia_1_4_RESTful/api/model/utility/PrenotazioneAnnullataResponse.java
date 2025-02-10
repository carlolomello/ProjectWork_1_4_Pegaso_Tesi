package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;



@JsonIgnoreProperties(ignoreUnknown = true)
public class PrenotazioneAnnullataResponse {
    @JsonProperty("records")
    public List<PrenotazioneAnnullata> records;
    
    
    public List<PrenotazioneAnnullata> getRecords() {
		return records;
	}

	public void setRecords(List<PrenotazioneAnnullata> records) {
		this.records = records;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
    public static class PrenotazioneAnnullata {
    	
    	@JsonProperty("Id")
        private String id;
        
        @JsonProperty("Name")
        private String name;
        
        @JsonProperty("data_inizio__c")
        private String dataInizio;
        
        @JsonProperty("data_fine__c")
        private String dataFine;
        
        @JsonProperty("metodo_pagamento__c")
        private String metodoPagamento;
        
        @JsonProperty("stato_pagamento__c")
        private String statoPagamento;
        
        @JsonProperty("Utenza_Cliente__r")
        private UtenzaCliente utenzaCliente;
        
        @JsonProperty("stato_pagamento_user_notified__c")
        private Boolean statoPagamentoUserNotified;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDataInizio() {
			return dataInizio;
		}

		public void setDataInizio(String dataInizio) {
			this.dataInizio = dataInizio;
		}

		public String getDataFine() {
			return dataFine;
		}

		public void setDataFine(String dataFine) {
			this.dataFine = dataFine;
		}

		public String getMetodoPagamento() {
			return metodoPagamento;
		}

		public void setMetodoPagamento(String metodoPagamento) {
			this.metodoPagamento = metodoPagamento;
		}
		
		public String getStatoPagamento() {
			return statoPagamento;
		}

		public void setStatoPagamento(String statoPagamento) {
			this.statoPagamento = statoPagamento;
		}

		public UtenzaCliente getUtenzaCliente() {
			return utenzaCliente;
		}

		public void setUtenzaCliente(UtenzaCliente utenzaCliente) {
			this.utenzaCliente = utenzaCliente;
		}
        
        
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UtenzaCliente {
        @JsonProperty("Name")
        private String nome;
        
        @JsonProperty("email__c")
        private String email;

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
        
        
    }
    
    public static PrenotazioneAnnullataResponse fromJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        try {
            System.out.println("JSON ricevuto: " + json); // Debugging
            return objectMapper.readValue(json, PrenotazioneAnnullataResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
   
}