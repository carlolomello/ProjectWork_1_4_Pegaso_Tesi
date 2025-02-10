package com.lomello_MAT0312401017.traccia_1_4_RESTful.api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.configuration.PasswordEncoder;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.email.Mail;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente.GetPrenotazioniClienteDTO;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente.PrenotazioneDTO;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente.UtenzaCliente;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.model.utenza_cliente.UtenzaClienteControllerDTO;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service.UtenzaClienteService;
import com.lomello_MAT0312401017.traccia_1_4_RESTful.api.service.email.EmailService;


@RestController
@RequestMapping("/api/utenza_cliente")
public class UtenzaClienteController {

	// URL base per la generazione di link di conferma registrazione
	@Value("${app.base-url}")
	private String baseUrl;
	
	@Autowired
    private EmailService emailService;

	// Metodo per il login dell'utente
    @PostMapping("/login")
    public ResponseEntity<UtenzaClienteControllerDTO.UtenzaClienteLoginResponseDTO> login(
            @RequestBody UtenzaClienteControllerDTO.UtenzaClienteLoginRequestDTO loginRequest) {
        try {
            // Recupera la risposta dal servizio di login
            String response = UtenzaClienteService.utenzaClienteLogin(loginRequest.getEmailCellulare());
            List<UtenzaCliente> utenti = UtenzaCliente.deserializeUtenzaClienteListSOQL(response);

            // Verifica se l'utente esiste e se la password è corretta
            if (!utenti.isEmpty() && PasswordEncoder.encoder().matches(loginRequest.getPassword(), utenti.get(0).getPassword())) {
                UtenzaCliente utente = utenti.get(0);
                // Crea un DTO con i dati dell'utente e restituiscilo come risposta
                UtenzaClienteControllerDTO.UtenzaClienteLoginResponseDTO dto = UtenzaClienteControllerDTO.UtenzaClienteLoginResponseDTO.fromEntity(utente);
                return ResponseEntity.ok(dto);
            } else {
                // Se la password non è corretta, restituisci un errore di autorizzazione
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            // Gestione degli errori
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
	// Metodo per la registrazione di un nuovo utente
    @PostMapping("/registrazione")
    public ResponseEntity<String> registrazione(
            @RequestBody UtenzaClienteControllerDTO.UtenzaClienteRegistrazioneRequestDTO registrazioneRequest) {
        try {
            // Crea l'oggetto utenza dal DTO
            UtenzaCliente utenza = registrazioneRequest.toEntity();
            // Genera un token di attivazione unico
            String token = UUID.randomUUID().toString();
            utenza.setActivationToken(token);
            String response = UtenzaClienteService.utenzaClienteRegistrazione(utenza);

            // Se la registrazione è avvenuta con successo, invia un'email di conferma
            if (response != null) {
            	// Crea il link di conferma
                StringBuilder confirmationLink = new StringBuilder();
                confirmationLink.append(this.baseUrl).append("/api/utenza_cliente/conferma_registrazione/").append(utenza.getActivationToken());
                String msg = """
                    <html>
                    <head><title>Conferma Registrazione</title></head>
                    <body>
                        <p>Gentile %s %s,</p>
                        <p>Per confermare l'attivazione del tuo account, clicca sul link qui sotto:</p>
                        <a href="%s">Conferma la registrazione</a>
                    </body>
                    </html>
                """.formatted(utenza.getNome(), utenza.getCognome(), confirmationLink);
                
            	// Invia l'email di conferma
            	Mail htmlMail = new Mail(utenza.getEmail(),"Conferma registrazione utenza", msg );
            	emailService.sendHtmlMail(htmlMail);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                // Se qualcosa va storto, restituisci un errore di registrazione
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore durante la registrazione.");
            }
        } catch (Exception e) {
            // Gestione degli errori
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno al server.");
        }
    }
    
	// Metodo per confermare la registrazione tramite il token
    @GetMapping("/conferma_registrazione/{token}")
    public ResponseEntity<String> confermaRegistrazione(@PathVariable String token) {
        try {
            // Verifica se il token è valido e attiva l'account
            boolean isActivated = UtenzaClienteService.confermaRegistrazione(token);
            if (isActivated) {
                return ResponseEntity.ok("Account attivato con successo!");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token non valido o scaduto.");
            }
        } catch (Exception e) {
            // Gestione degli errori
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore interno al server.");
        }
    }
    
	// Metodo per ottenere le prenotazioni di un utente
    @GetMapping("/prenotazioni_utente")
    public ResponseEntity<GetPrenotazioniClienteDTO.GetPrenotazioniClienteResponseDTO> getPrenotazioniCliente(
            @RequestParam String utenzaCliente) {
        try {
            // Ottieni la risposta dal service
            GetPrenotazioniClienteDTO.GetPrenotazioniClienteResponseDTO responseDTO = UtenzaClienteService.getPrenotazioniCliente(utenzaCliente);
            
            // Se la risposta contiene prenotazioni, restituisci un OK con il DTO
            if (responseDTO != null && responseDTO.getPrenotazioni() != null && !responseDTO.getPrenotazioni().isEmpty()) {
                return ResponseEntity.ok(responseDTO); // Restituisce il DTO con la lista di prenotazioni
            } else {
                // Se non ci sono prenotazioni, restituisci un 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GetPrenotazioniClienteDTO.GetPrenotazioniClienteResponseDTO());
            }
        } catch (Exception e) {
            // Gestione degli errori
            e.printStackTrace();
            // In caso di errore restituisci un 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @PostMapping("/insert_prenotazione")
    public ResponseEntity<PrenotazioneDTO.InsertPrenotazioneResponseDTO> creaPrenotazione(
            @RequestBody PrenotazioneDTO.InsertPrenotazioneRequestDTO requestData) {
        try {
            // Chiamata al servizio per creare la prenotazione
            PrenotazioneDTO.InsertPrenotazioneResponseDTO response = PrenotazioneDTO.InsertPrenotazioneResponseDTO.fromJsonToDTO(UtenzaClienteService.creaPrenotazione(requestData));

            // Se la risposta contiene un ID prenotazione, restituiamo un codice di stato 200
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                // In caso di errore, restituiamo un codice di stato 400 con un messaggio
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new PrenotazioneDTO.InsertPrenotazioneResponseDTO(false, "Errore durante la creazione della prenotazione", null));
            }

        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore imprevisto, restituiamo un codice di stato 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PrenotazioneDTO.InsertPrenotazioneResponseDTO(false, "Errore interno del server", null));
        }
    }
    
    @PutMapping("/update_prenotazione")
    public ResponseEntity<PrenotazioneDTO.UpdatePrenotazioneResponseDTO> aggiornaPrenotazione(
            @RequestBody PrenotazioneDTO.UpdatePrenotazioneRequestDTO requestData) {
        try {
            // Chiamata al servizio per creare la prenotazione
            PrenotazioneDTO.UpdatePrenotazioneResponseDTO response = PrenotazioneDTO.UpdatePrenotazioneResponseDTO.fromJsonToDTO(UtenzaClienteService.aggiornaPrenotazione(requestData));

            // Se la risposta contiene un ID prenotazione, restituiamo un codice di stato 200
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                // In caso di errore, restituiamo un codice di stato 400 con un messaggio
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new PrenotazioneDTO.UpdatePrenotazioneResponseDTO(false,"Errore durante la creazione della prenotazione"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            // In caso di errore imprevisto, restituiamo un codice di stato 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PrenotazioneDTO.UpdatePrenotazioneResponseDTO(false,"Errore interno del server"));
        }
    }
    
    @DeleteMapping("/delete_prenotazione/{prenotazioneId}")
    public ResponseEntity<PrenotazioneDTO.DeletePrenotazioneResponseDTO> deletePrenotazione(
            @PathVariable String prenotazioneId) {
        try {
            // Controlla che l'ID non sia nullo o vuoto
            if (prenotazioneId == null || prenotazioneId.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new PrenotazioneDTO.DeletePrenotazioneResponseDTO("ID prenotazione non valido"));
            }

            // Chiamata al servizio per eliminare la prenotazione
            String result = UtenzaClienteService.deletePrenotazione(prenotazioneId);

            if (result != null) {
                // Restituisce un codice 200 OK se l'eliminazione ha avuto successo
                return ResponseEntity.ok(new PrenotazioneDTO.DeletePrenotazioneResponseDTO(result));
            } else {
                // Restituisce un codice 400 Bad Request in caso di errore
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new PrenotazioneDTO.DeletePrenotazioneResponseDTO("Errore durante l'eliminazione della prenotazione"));
            }
        } catch (Exception e) {
            // Gestisce eccezioni generiche
            e.printStackTrace();
            // Restituisce un codice 500 Internal Server Error per errori non previsti
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PrenotazioneDTO.DeletePrenotazioneResponseDTO("Errore interno del server"));
        }
    }
    
}
