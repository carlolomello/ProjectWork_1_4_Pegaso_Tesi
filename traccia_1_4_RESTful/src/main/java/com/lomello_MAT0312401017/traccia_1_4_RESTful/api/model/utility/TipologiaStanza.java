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
public class TipologiaStanza {

	@JsonProperty("Id")
	private String id;
	@JsonProperty("DeveloperName")
	private String developerName;
	@JsonProperty("Label")
	private String label;
	@JsonProperty("descrizione__c")
	private String descrizione;

	public static TipologiaStanza deserializeTipologiaStanza(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		TipologiaStanza tipologiaStanza = null;
		try {
			tipologiaStanza = objectMapper.readValue(response, new TypeReference<TipologiaStanza>() {
			});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tipologiaStanza;
	}

	public static List<TipologiaStanza> deserializeTipologiaStanzaListSOQL(String response) {
		ObjectMapper objectMapper = new ObjectMapper();
		List<TipologiaStanza> tipologiaStanzaList = null;

		try {
			JsonNode jsonNode = objectMapper.readTree(response);
			JsonNode recordsNode = jsonNode.get("records");

			try {
				tipologiaStanzaList = objectMapper.readValue(recordsNode.traverse(),
						new TypeReference<List<TipologiaStanza>>() {
						});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return tipologiaStanzaList;
	}
}
