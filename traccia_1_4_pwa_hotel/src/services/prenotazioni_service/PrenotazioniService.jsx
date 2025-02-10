import { API_BASE_URL } from "../../config/Config.js"; // Importa l'URL di base delle API

// Funzione per inserire una prenotazione
export const insertPrenotazione = async (
  idUser,
  idStanza,
  dataInizio,
  dataFine,
  idMetodoPagamento,
  serviziIds
) => {
  try {
    const response = await fetch(`${API_BASE_URL}/utenza_cliente/insert_prenotazione`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        idUser,
        idStanza,
        dataInizio,
        dataFine,
        idMetodoPagamento, // Qui invii il campo DeveloperName come idMetodoPagamento
        serviziIds,       // Array di id dei servizi selezionati
      }),
    });

    let errorMessage = "Inserimento prenotazione fallito. Riprova.";

    if (!response.ok) {
      // Legge la risposta come testo
      const errorText = await response.text();
      try {
        // Prova a fare il parsing della risposta come JSON
        const errorData = JSON.parse(errorText);
        errorMessage = errorData.message || errorMessage;
      } catch (e) {
        errorMessage = errorText || errorMessage;
      }
      throw new Error(errorMessage);
    }

    // Se la risposta è ok, restituisce i dati
    const data = await response.json();
    return data;

  } catch (error) {
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};

// Funzione per ottenere le prenotazioni di un utente
export const getPrenotazioniUtente = async (idUser) => {
  try {
    const response = await fetch(
      `${API_BASE_URL}/utenza_cliente/prenotazioni_utente?utenzaCliente=${idUser}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    let errorMessage = "Errore nel recupero delle prenotazioni. Riprova.";

    if (!response.ok) {
      const errorText = await response.text();
      try {
        const errorData = JSON.parse(errorText);
        errorMessage = errorData.message || errorMessage;
      } catch (e) {
        errorMessage = errorText || errorMessage;
      }
      throw new Error(errorMessage);
    }

    // Se la risposta è ok, restituisce i dati
    const data = await response.json();
    return data;
  } catch (error) {
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};

// Funzione per aggiornare una prenotazione
export const updatePrenotazione = async (
  prenotazioneId,
  dataInizio,
  dataFine,
  serviziIds
) => {
  try {
    const response = await fetch(`${API_BASE_URL}/utenza_cliente/update_prenotazione`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        prenotazioneId, // ID della prenotazione da modificare
        dataInizio,     // Nuova data di inizio
        dataFine,       // Nuova data di fine
        serviziIds,     // Array di id dei servizi aggiornati
      }),
    });

    let errorMessage = "Aggiornamento prenotazione fallito. Riprova.";

    if (!response.ok) {
      const errorText = await response.text();
      try {
        const errorData = JSON.parse(errorText);
        errorMessage = errorData.message || errorMessage;
      } catch (e) {
        errorMessage = errorText || errorMessage;
      }
      throw new Error(errorMessage);
    }

    // Se la risposta è ok, restituisce i dati
    const data = await response.json();
    return data;

  } catch (error) {
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};

// Funzione per cancellare una prenotazione
export const deletePrenotazione = async (prenotazioneId) => {
  try {
    const response = await fetch(`${API_BASE_URL}/utenza_cliente/delete_prenotazione/${prenotazioneId}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
      },
    });

    let errorMessage = "Cancellazione prenotazione fallita. Riprova.";

    if (!response.ok) {
      const errorText = await response.text();
      try {
        const errorData = JSON.parse(errorText);
        errorMessage = errorData.message || errorMessage;
      } catch (e) {
        errorMessage = errorText || errorMessage;
      }
      throw new Error(errorMessage);
    }

    // Se la risposta è ok, restituisce i dati
    const data = await response.json();
    return data;

  } catch (error) {
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};