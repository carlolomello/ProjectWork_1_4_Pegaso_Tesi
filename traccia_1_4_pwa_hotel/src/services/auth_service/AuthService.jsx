import { API_BASE_URL } from "../../config/Config.js"; // Importa l'URL di base delle API

// Funzione per il login
export const login = async (emailCellulare, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/utenza_cliente/login`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ emailCellulare, password }),
    });

    let errorMessage = "Login fallito. Riprova.";
    
    if (!response.ok) {
      // Legge la risposta come testo
      const errorText = await response.text();

      try {
        // Prova a fare il parsing della risposta come JSON, se possibile
        const errorData = JSON.parse(errorText);
        errorMessage = errorData.message || errorMessage;
      } catch (e) {
        // Se il parsing fallisce, usa la risposta come testo
        errorMessage = errorText || errorMessage;
      }

      throw new Error(errorMessage);
    }

    // Se la risposta è ok, restituiamo i dati dell'utente
    const data = await response.json();
    sessionStorage.setItem("user", JSON.stringify(data)); // Salva i dati in sessionStorage
    return data;

  } catch (error) {
    // Cattura qualsiasi errore (anche di rete o altri errori)
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};

// Funzione per la registrazione
export const register = async (nome, cognome, email, cellulare, password) => {
  try {
    const response = await fetch(`${API_BASE_URL}/utenza_cliente/registrazione`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ nome, cognome, email, cellulare, password }),
    });

    let errorMessage = "Registrazione fallita. Riprova.";

    if (!response.ok) {
      // Legge la risposta come testo
      const errorText = await response.text();

      try {
        // Prova a fare il parsing della risposta come JSON, se possibile
        const errorData = JSON.parse(errorText);
        errorMessage = errorData.message || errorMessage;
      } catch (e) {
        // Se il parsing fallisce, usa la risposta come testo
        errorMessage = errorText || errorMessage;
      }

      // Aggiungi logica per personalizzare il messaggio se la risposta riguarda un duplicato
      if (response.status === 400) {
        throw new Error(errorMessage);
      }
      throw new Error(errorMessage);
    }
    console.log("RESPONSE OK")
    // Se la risposta è ok, restituiamo i dati
    const data = await response.json();
    return data;

  } catch (error) {
    // Cattura qualsiasi errore (anche di rete o altri errori)
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};
