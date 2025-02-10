package com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobRequest {
    
	public static class OpenJob{
		
		private String operation;
	    private String object;
	    private String contentType;

	    // Costruttore
	    public OpenJob(String operation, String object, String contentType) {
	        this.operation = operation;
	        this.object = object;
	        this.contentType = contentType;
	    }

	    // Getters e Setters
	    public String getOperation() {
	        return operation;
	    }

	    public void setOperation(String operation) {
	        this.operation = operation;
	    }

	    public String getObject() {
	        return object;
	    }

	    public void setObject(String object) {
	        this.object = object;
	    }

	    public String getContentType() {
	        return contentType;
	    }

	    public void setContentType(String contentType) {
	        this.contentType = contentType;
	    }
		
	}
	
public static class CloseJob{
		
		private String state;
	   
	    public CloseJob(String state) {
	        this.state = state;
	        
	    }

	    // Getters e Setters
	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }

		
	}
	
}
