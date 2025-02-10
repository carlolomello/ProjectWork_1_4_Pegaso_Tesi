package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetodoPagamentoPrenotazione {

	@JsonProperty("Id")
	private String id;
	@JsonProperty("DeveloperName")
	private String developerName;
	@JsonProperty("Label")
	private String label;
	@JsonProperty("descrizione__c")
	private String descrizione;

	public static MetodoPagamentoPrenotazione deserializeTMetodoPagamentoPrenotazione(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		MetodoPagamentoPrenotazione metodoPagamentoPrenotazione = null;
		try {
			metodoPagamentoPrenotazione = objectMapper.readValue(response, new TypeReference<MetodoPagamentoPrenotazione>() {
			});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return metodoPagamentoPrenotazione;
	}

	public static List<MetodoPagamentoPrenotazione> deserializeMetodoPagamentoPrenotazioneListSOQL(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<MetodoPagamentoPrenotazione> metodoPagamentoPrenotazioneList = null;

		try {
			JsonNode jsonNode = objectMapper.readTree(response);
			JsonNode recordsNode = jsonNode.get("records");

			try {
				metodoPagamentoPrenotazioneList = objectMapper.readValue(recordsNode.traverse(),
						new TypeReference<List<MetodoPagamentoPrenotazione>>() {
						});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return metodoPagamentoPrenotazioneList;
	}
}
