package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utility;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetStanzeDisponibiliDTO {

    public static class GetStanzeDisponibiliRequestDTO {
        private String dataInizio;
        private String dataFine;
        private Integer postiLetto;
        private String tipologiaStanza;

        // Getters e Setters
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

        public Integer getPostiLetto() {
            return postiLetto;
        }

        public void setPostiLetto(Integer postiLetto) {
            this.postiLetto = postiLetto;
        }

        public String getTipologiaStanza() {
            return tipologiaStanza;
        }

        public void setTipologiaStanza(String tipologiaStanza) {
            this.tipologiaStanza = tipologiaStanza;
        }
    }

    public static class GetStanzeDisponibiliResponseDTO {
    	@JsonProperty("Id")
        private String id;
    	@JsonProperty("Nome")
        private String nome;
    	@JsonProperty("Descrizione")
        private String descrizione;
    	@JsonProperty("NumLettiSingoli")
        private Integer numLettiSingoli;
    	@JsonProperty("NumLettiMatrimoniali")
        private Integer numLettiMatrimoniali;
    	@JsonProperty("Capacita")
        private Integer capacita;
    	@JsonProperty("CostoPerNotte")
        private Double costoPerNotte;
    	@JsonProperty("CostoTotale")
        private Double costoTotale;
    	@JsonProperty("TipologiaStanza")
        private String tipologiaStanza;

        // Getters e Setters
        public String getId() {
            return id;
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

        public String getDescrizione() {
            return descrizione;
        }

        public void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }

        public Integer getNumLettiSingoli() {
            return numLettiSingoli;
        }

        public void setNumLettiSingoli(Integer numLettiSingoli) {
            this.numLettiSingoli = numLettiSingoli;
        }

        public Integer getNumLettiMatrimoniali() {
            return numLettiMatrimoniali;
        }

        public void setNumLettiMatrimoniali(Integer numLettiMatrimoniali) {
            this.numLettiMatrimoniali = numLettiMatrimoniali;
        }

        public Integer getCapacita() {
            return capacita;
        }

        public void setCapacita(Integer capacita) {
            this.capacita = capacita;
        }

        public Double getCostoPerNotte() {
            return costoPerNotte;
        }

        public void setCostoPerNotte(Double costoPerNotte) {
            this.costoPerNotte = costoPerNotte;
        }

        public Double getCostoTotale() {
            return costoTotale;
        }

        public void setCostoTotale(Double costoTotale) {
            this.costoTotale = costoTotale;
        }

        public String getTipologiaStanza() {
            return tipologiaStanza;
        }

        public void setTipologiaStanza(String tipologiaStanza) {
            this.tipologiaStanza = tipologiaStanza;
        }

        // Metodo statico per mappare la risposta JSON a un oggetto
        public static GetStanzeDisponibiliResponseDTO fromJson(String json) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, GetStanzeDisponibiliResponseDTO.class);
        }
    }

    // Classe wrapper per gestire una risposta JSON con oggetto contenente lista di stanze
    public static class GetStanzeDisponibiliWrapperDTO {
        private List<GetStanzeDisponibiliResponseDTO> stanze;

        // Getters e Setters
        public List<GetStanzeDisponibiliResponseDTO> getStanze() {
            return stanze;
        }

        public void setStanze(List<GetStanzeDisponibiliResponseDTO> stanze) {
            this.stanze = stanze;
        }

        // Metodo statico per deserializzare una risposta che contiene un oggetto con lista
        public static GetStanzeDisponibiliWrapperDTO fromJson(String json) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, GetStanzeDisponibiliWrapperDTO.class);
        }
    }
}
