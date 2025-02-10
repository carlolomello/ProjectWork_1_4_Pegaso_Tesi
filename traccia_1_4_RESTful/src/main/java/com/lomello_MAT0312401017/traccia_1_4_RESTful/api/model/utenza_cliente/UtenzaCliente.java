package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UtenzaCliente {
	@JsonProperty("Id")
    private String id;
	@JsonProperty("nome__c")
    private String nome;
	@JsonProperty("cognome__c")
    private String cognome;
	@JsonProperty("email__c")
    private String email;
	@JsonProperty("cellulare__c")
    private String cellulare;
	@JsonProperty("password__c")
    private String password;
	@JsonProperty("activation_token__c")
	private String activationToken;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getActivationToken() {
		return activationToken;
	}

	public void setActivationToken(String activationToken) {
		this.activationToken = activationToken;
	}

	public static UtenzaCliente deserializeUtenzaCliente(String response){
	ObjectMapper objectMapper = new ObjectMapper();
	UtenzaCliente utenzaCliente = null;
	try {
			utenzaCliente = objectMapper.readValue(response, new TypeReference<UtenzaCliente>() {});
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return utenzaCliente;
	}
    
    public static List<UtenzaCliente> deserializeUtenzaClienteListSOQL(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<UtenzaCliente> utenzaClientetList = null;

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode recordsNode = jsonNode.get("records");

            try {
            	utenzaClientetList = objectMapper.readValue(recordsNode.traverse(), new TypeReference<List<UtenzaCliente>>() {});
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return utenzaClientetList;
    }
}
