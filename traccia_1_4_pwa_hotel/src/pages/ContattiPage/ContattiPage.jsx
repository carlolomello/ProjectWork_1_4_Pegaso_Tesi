import React, { useState } from "react";
import "./ContattiPage.css";
import { inviaEmailContatto } from "../../services/contatti_service/ContattiService"; // Aggiungi il percorso corretto

const ContactPage = () => {
  // Stato per i campi del modulo
  const [name, setName] = useState("");
  const [contact, setContact] = useState("");
  const [selectedSubject, setSelectedSubject] = useState("");
  const [message, setMessage] = useState("");
  const [feedback, setFeedback] = useState(""); // Stato per il feedback (successo o errore)

  // Lista degli oggetti predefiniti per il picklist (modificata per il contesto dell'albergo)
  const subjects = [
    "Richiesta informazioni generali",
    "Disponibilità camere",
    "Richiesta preventivo",
    "Problemi con la prenotazione",
    "Servizi extra",
    "Feedback sul soggiorno",
    "Altro"
  ];

  // Funzione di submit del modulo
  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Controlla che tutti i campi siano compilati
    if (!name || !contact || !selectedSubject || !message) {
      setFeedback("Tutti i campi sono obbligatori.");
      return;
    }

    try {
      const response = await inviaEmailContatto(name, contact, selectedSubject, message);
      
      // Se la risposta è positiva, mostra un messaggio di successo
      setFeedback("Messaggio inviato con successo! Ti risponderemo al più presto.");
      
      // Resetta i campi del modulo
      setName("");
      setContact("");
      setSelectedSubject("");
      setMessage("");
      console.log("email response",response)
    } catch (error) {
      // Se c'è un errore, mostra un messaggio di errore
      setFeedback(`Errore: ${error.message}`);
    }
  };

  return (
    <div className="contactpage">
      <h1 className="page-title">Contattaci</h1>
      <div className="contact-container">

        <p className="contact-description">
          Per qualsiasi informazione, contattaci tramite il form o i seguenti recapiti:
        </p>

        {/* Inizio modulo di contatto */}
        <div className="contact-form">
          <h2>Inviaci un messaggio</h2>
          <form onSubmit={handleSubmit}>
            <div>
              <label>Nome:</label>
              <input
                type="text"
                placeholder="Inserisci il tuo nome"
                value={name}
                onChange={(e) => setName(e.target.value)}
              />
            </div>

            <div>
              <label>Contatto (Email o Cellulare):</label>
              <input
                type="text"
                placeholder="Email o Cellulare"
                value={contact}
                onChange={(e) => setContact(e.target.value)}
              />
            </div>

            <div>
              <label>Oggetto del messaggio:</label>
              <select
                value={selectedSubject}
                onChange={(e) => setSelectedSubject(e.target.value)}
              >
                <option value="">Seleziona un oggetto</option>
                {subjects.map((subject, index) => (
                  <option key={index} value={subject}>
                    {subject}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label>Messaggio:</label>
              <textarea
                placeholder="Scrivi il tuo messaggio"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
              />
            </div>

            <button type="submit">Invia</button>
          </form>

          {/* Mostra il feedback */}
          {feedback && <p className="feedback-message">{feedback}</p>}
        </div>
        {/* Fine modulo di contatto */}

        <div className="contact-info">
          <p><span className="contact-label">Email:</span> carlo.lomello@yahoo.it</p>
          <p><span className="contact-label">Telefono:</span> +39 320 90 58 480</p>
          <p><span className="contact-label">Indirizzo:</span> Via Zuppetta, 4 - 80055, Portici (NA)</p>
        </div>
      </div>
    </div>
  );
};

export default ContactPage;
