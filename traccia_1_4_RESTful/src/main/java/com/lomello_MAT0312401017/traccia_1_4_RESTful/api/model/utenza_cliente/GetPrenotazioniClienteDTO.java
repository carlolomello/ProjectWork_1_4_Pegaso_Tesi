package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetPrenotazioniClienteDTO {

    // DTO per rappresentare una singola prenotazione dell'utente
    public static class GetPrenotazioniClienteResponseDTO {

        @JsonProperty("prenotazioni")
        private List<PrenotazioneDTO> prenotazioni;
        @JsonProperty("message")
        private String message;

        // Getters e Setters
        public List<PrenotazioneDTO> getPrenotazioni() {
            return prenotazioni;
        }

        public void setPrenotazioni(List<PrenotazioneDTO> prenotazioni) {
            this.prenotazioni = prenotazioni;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        // Metodo statico per deserializzare la risposta JSON
        public static GetPrenotazioniClienteResponseDTO fromJson(String json) throws IOException {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, GetPrenotazioniClienteResponseDTO.class);
        }
    }

    // DTO per rappresentare una singola prenotazione
    public static class PrenotazioneDTO {

        @JsonProperty("prenotazioneId")
        private String prenotazioneId;
        @JsonProperty("dataInizio")
        private String dataInizio;
        @JsonProperty("dataFine")
        private String dataFine;
        @JsonProperty("statoPagamento")
        private String statoPagamento;
        @JsonProperty("stanzaId")
        private String stanzaId;
        @JsonProperty("stanzaNome")
        private String stanzaNome;
        @JsonProperty("stanzaDescrizione")
        private String stanzaDescrizione;
        @JsonProperty("numLettiSingoli")
        private Integer numLettiSingoli;
        @JsonProperty("numLettiMatrimoniali")
        private Integer numLettiMatrimoniali;
        @JsonProperty("costoPerNotte")
        private Double costoPerNotte;
        @JsonProperty("tipologiaNome")
        private String tipologiaNome;
        @JsonProperty("metodoPagamentoNome")
        private String metodoPagamentoNome;
        @JsonProperty("servizi")
        private List<ServizioResponseDTO> servizi;
        @JsonProperty("costoTotale")
        private Double costoTotale;

        // Getters e Setters
        public String getPrenotazioneId() {
            return prenotazioneId;
        }

        public void setPrenotazioneId(String prenotazioneId) {
            this.prenotazioneId = prenotazioneId;
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

        public String getStatoPagamento() {
            return statoPagamento;
        }

        public void setStatoPagamento(String statoPagamento) {
            this.statoPagamento = statoPagamento;
        }

        public String getStanzaId() {
            return stanzaId;
        }

        public void setStanzaId(String stanzaId) {
            this.stanzaId = stanzaId;
        }

        public String getStanzaNome() {
            return stanzaNome;
        }

        public void setStanzaNome(String stanzaNome) {
            this.stanzaNome = stanzaNome;
        }

        public String getStanzaDescrizione() {
            return stanzaDescrizione;
        }

        public void setStanzaDescrizione(String stanzaDescrizione) {
            this.stanzaDescrizione = stanzaDescrizione;
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

        public Double getCostoPerNotte() {
            return costoPerNotte;
        }

        public void setCostoPerNotte(Double costoPerNotte) {
            this.costoPerNotte = costoPerNotte;
        }

        public String getTipologiaNome() {
            return tipologiaNome;
        }

        public void setTipologiaNome(String tipologiaNome) {
            this.tipologiaNome = tipologiaNome;
        }

        public String getMetodoPagamentoNome() {
            return metodoPagamentoNome;
        }

        public void setMetodoPagamentoNome(String metodoPagamentoNome) {
            this.metodoPagamentoNome = metodoPagamentoNome;
        }

        public List<ServizioResponseDTO> getServizi() {
            return servizi;
        }

        public void setServizi(List<ServizioResponseDTO> servizi) {
            this.servizi = servizi;
        }

        public Double getCostoTotale() {
            return costoTotale;
        }

        public void setCostoTotale(Double costoTotale) {
            this.costoTotale = costoTotale;
        }
    }

    // DTO per rappresentare un singolo servizio
    public static class ServizioResponseDTO {

        @JsonProperty("servizioId")
        private String servizioId;
        @JsonProperty("nomeServizio")
        private String nomeServizio;
        @JsonProperty("descrizione")
        private String descrizione;
        @JsonProperty("costoPersonaGiorno")
        private Double costoPersonaGiorno;

        // Getters e Setters
        public String getServizioId() {
            return servizioId;
        }

        public void setServizioId(String servizioId) {
            this.servizioId = servizioId;
        }

        public String getNomeServizio() {
            return nomeServizio;
        }

        public void setNomeServizio(String nomeServizio) {
            this.nomeServizio = nomeServizio;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }

        public Double getCostoPersonaGiorno() {
            return costoPersonaGiorno;
        }

        public void setCostoPersonaGiorno(Double costoPersonaGiorno) {
            this.costoPersonaGiorno = costoPersonaGiorno;
        }
    }
}

