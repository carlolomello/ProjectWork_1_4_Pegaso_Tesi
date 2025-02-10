package com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BatchRecord{
	@JsonProperty("Id")
    private String id;

    @JsonProperty("stato_pagamento_user_notified__c")
    private boolean statoPagamentoUserNotified;

    // Costruttore
    public BatchRecord(String id, boolean statoPagamentoUserNotified) {
        this.id = id;
        this.statoPagamentoUserNotified = statoPagamentoUserNotified;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatoPagamentoUserNotified() {
        return statoPagamentoUserNotified;
    }

    public void setStatoPagamentoUserNotified(boolean statoPagamentoUserNotified) {
        this.statoPagamentoUserNotified = statoPagamentoUserNotified;
    }
}