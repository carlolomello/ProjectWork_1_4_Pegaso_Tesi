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
public class Servizio {
	@JsonProperty("Id")
	private String id;
	@JsonProperty("Name")
	private String nomeServizio;
	@JsonProperty("costo_persona_giorno__c")
	private String costoPersonaGiorno;
	@JsonProperty("descrizione__c")
	private String descrizione;

	public static Servizio deserializeServizio(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		Servizio servizio = null;
		try {
			servizio = objectMapper.readValue(response, new TypeReference<Servizio>() {
			});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return servizio;
	}

	public static List<Servizio> deserializeServizioListSOQL(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<Servizio> servizioList = null;

		try {
			JsonNode jsonNode = objectMapper.readTree(response);
			JsonNode recordsNode = jsonNode.get("records");

			try {
				servizioList = objectMapper.readValue(recordsNode.traverse(),
						new TypeReference<List<Servizio>>() {
						});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return servizioList;
	}
}
