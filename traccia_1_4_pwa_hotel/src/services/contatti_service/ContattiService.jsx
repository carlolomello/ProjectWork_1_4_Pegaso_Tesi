import { API_BASE_URL } from "../../config/Config.js"; // Importa l'URL di base delle API

// Funzione per inviare una richiesta di email di contatto
export const inviaEmailContatto = async (nominativo, contatto, oggetto, messaggio) => {
  try {
    const response = await fetch(`${API_BASE_URL}/utility/invia_email_contatto`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        nominativo,
        contatto,
        oggetto,
        messaggio,
      }),
    });

    // Se la risposta non è ok (200), gestisci l'errore
    if (!response.ok) {
      const errorText = await response.text(); // Leggi la risposta come testo
      throw new Error(errorText); // Mostra il messaggio di errore
    }

    // Se la risposta è ok, prova a restituirla come testo
    const responseText = await response.text(); // Leggi la risposta come testo
    return responseText; // La risposta è una stringa, non un JSON

  } catch (error) {
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};
