package com.lomello_MAT0312401017.traccia_1_4_RESTful.operazioni_schedulate.salesforce_batch.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JobResponse {

    @JsonProperty("apexProcessingTime")
    private int apexProcessingTime;

    @JsonProperty("apiActiveProcessingTime")
    private int apiActiveProcessingTime;

    @JsonProperty("apiVersion")
    private String apiVersion;

    @JsonProperty("assignmentRuleId")
    private String assignmentRuleId;

    @JsonProperty("concurrencyMode")
    private String concurrencyMode;

    @JsonProperty("contentType")
    private String contentType;

    @JsonProperty("createdById")
    private String createdById;

    @JsonProperty("createdDate")
    private Date createdDate;

    @JsonProperty("externalIdFieldName")
    private String externalIdFieldName;

    @JsonProperty("fastPathEnabled")
    private boolean fastPathEnabled;

    @JsonProperty("id")
    private String id;

    @JsonProperty("numberBatchesCompleted")
    private int numberBatchesCompleted;

    @JsonProperty("numberBatchesFailed")
    private int numberBatchesFailed;

    @JsonProperty("numberBatchesInProgress")
    private int numberBatchesInProgress;

    @JsonProperty("numberBatchesQueued")
    private int numberBatchesQueued;

    @JsonProperty("numberBatchesTotal")
    private int numberBatchesTotal;

    @JsonProperty("numberRecordsFailed")
    private int numberRecordsFailed;

    @JsonProperty("numberRecordsProcessed")
    private int numberRecordsProcessed;

    @JsonProperty("numberRetries")
    private int numberRetries;

    @JsonProperty("object")
    private String object;

    @JsonProperty("operation")
    private String operation;

    @JsonProperty("state")
    private String state;

    @JsonProperty("systemModstamp")
    private Date systemModstamp;

    @JsonProperty("totalProcessingTime")
    private int totalProcessingTime;

    // Getters and Setters
    public int getApexProcessingTime() {
        return apexProcessingTime;
    }

    public void setApexProcessingTime(int apexProcessingTime) {
        this.apexProcessingTime = apexProcessingTime;
    }

    public int getApiActiveProcessingTime() {
        return apiActiveProcessingTime;
    }

    public void setApiActiveProcessingTime(int apiActiveProcessingTime) {
        this.apiActiveProcessingTime = apiActiveProcessingTime;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getAssignmentRuleId() {
        return assignmentRuleId;
    }

    public void setAssignmentRuleId(String assignmentRuleId) {
        this.assignmentRuleId = assignmentRuleId;
    }

    public String getConcurrencyMode() {
        return concurrencyMode;
    }

    public void setConcurrencyMode(String concurrencyMode) {
        this.concurrencyMode = concurrencyMode;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getExternalIdFieldName() {
        return externalIdFieldName;
    }

    public void setExternalIdFieldName(String externalIdFieldName) {
        this.externalIdFieldName = externalIdFieldName;
    }

    public boolean isFastPathEnabled() {
        return fastPathEnabled;
    }

    public void setFastPathEnabled(boolean fastPathEnabled) {
        this.fastPathEnabled = fastPathEnabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberBatchesCompleted() {
        return numberBatchesCompleted;
    }

    public void setNumberBatchesCompleted(int numberBatchesCompleted) {
        this.numberBatchesCompleted = numberBatchesCompleted;
    }

    public int getNumberBatchesFailed() {
        return numberBatchesFailed;
    }

    public void setNumberBatchesFailed(int numberBatchesFailed) {
        this.numberBatchesFailed = numberBatchesFailed;
    }

    public int getNumberBatchesInProgress() {
        return numberBatchesInProgress;
    }

    public void setNumberBatchesInProgress(int numberBatchesInProgress) {
        this.numberBatchesInProgress = numberBatchesInProgress;
    }

    public int getNumberBatchesQueued() {
        return numberBatchesQueued;
    }

    public void setNumberBatchesQueued(int numberBatchesQueued) {
        this.numberBatchesQueued = numberBatchesQueued;
    }

    public int getNumberBatchesTotal() {
        return numberBatchesTotal;
    }

    public void setNumberBatchesTotal(int numberBatchesTotal) {
        this.numberBatchesTotal = numberBatchesTotal;
    }

    public int getNumberRecordsFailed() {
        return numberRecordsFailed;
    }

    public void setNumberRecordsFailed(int numberRecordsFailed) {
        this.numberRecordsFailed = numberRecordsFailed;
    }

    public int getNumberRecordsProcessed() {
        return numberRecordsProcessed;
    }

    public void setNumberRecordsProcessed(int numberRecordsProcessed) {
        this.numberRecordsProcessed = numberRecordsProcessed;
    }

    public int getNumberRetries() {
        return numberRetries;
    }

    public void setNumberRetries(int numberRetries) {
        this.numberRetries = numberRetries;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getSystemModstamp() {
        return systemModstamp;
    }

    public void setSystemModstamp(Date systemModstamp) {
        this.systemModstamp = systemModstamp;
    }

    public int getTotalProcessingTime() {
        return totalProcessingTime;
    }

    public void setTotalProcessingTime(int totalProcessingTime) {
        this.totalProcessingTime = totalProcessingTime;
    }
    
    public static JobResponse deserializeJobResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Deserializza la risposta JSON in un oggetto JobResponse
            return objectMapper.readValue(jsonResponse, JobResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

