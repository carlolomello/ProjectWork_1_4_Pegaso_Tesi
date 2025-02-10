package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente;

public class UtenzaClienteControllerDTO {

    public static class UtenzaClienteLoginRequestDTO {
        private String emailCellulare;
        private String password;

        // Getter e Setter
        public String getEmailCellulare() {
            return emailCellulare;
        }

        public void setEmailCellulare(String emailCellulare) {
            this.emailCellulare = emailCellulare;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public static UtenzaClienteLoginRequestDTO fromEntity(UtenzaCliente utenza) {
            UtenzaClienteLoginRequestDTO dto = new UtenzaClienteLoginRequestDTO();
            dto.setEmailCellulare(utenza.getEmail() != null ? utenza.getEmail() : utenza.getCellulare());
            dto.setPassword(utenza.getPassword());
            return dto;
        }
    }

    public static class UtenzaClienteLoginResponseDTO {
        private String id;
        private String nome;
        private String cognome;
        private String email;
        private String cellulare;

        // Getter e Setter
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

        public static UtenzaClienteLoginResponseDTO fromEntity(UtenzaCliente utenza) {
            UtenzaClienteLoginResponseDTO dto = new UtenzaClienteLoginResponseDTO();
            dto.setId(utenza.getId());
            dto.setNome(utenza.getNome());
            dto.setCognome(utenza.getCognome());
            dto.setEmail(utenza.getEmail());
            dto.setCellulare(utenza.getCellulare());
            return dto;
        }
    }

    public static class UtenzaClienteRegistrazioneRequestDTO {
        private String nome;
        private String cognome;
        private String email;
        private String cellulare;
        private String password;

        // Getter e Setter
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

        public static UtenzaClienteRegistrazioneRequestDTO fromEntity(UtenzaCliente utenza) {
            UtenzaClienteRegistrazioneRequestDTO dto = new UtenzaClienteRegistrazioneRequestDTO();
            dto.setNome(utenza.getNome());
            dto.setCognome(utenza.getCognome());
            dto.setEmail(utenza.getEmail());
            dto.setCellulare(utenza.getCellulare());
            dto.setPassword(utenza.getPassword());
            return dto;
        }

        public UtenzaCliente toEntity() {
            UtenzaCliente utenza = new UtenzaCliente();
            utenza.setNome(this.getNome());
            utenza.setCognome(this.getCognome());
            utenza.setEmail(this.getEmail());
            utenza.setCellulare(this.getCellulare());
            utenza.setPassword(this.getPassword());
            return utenza;
        }
    }
}
