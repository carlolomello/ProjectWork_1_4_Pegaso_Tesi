import { API_BASE_URL } from "../../config/Config.js"; // Importa l'URL di base delle API

// Funzione per ottenere le tipologie di stanze
export const getTipologieStanze = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/utility/tipologia_stanze`);
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || "Errore durante il recupero delle tipologie di stanze.");
      }
  
      const data = await response.json(); // Parsea la risposta JSON
      return data;
    } catch (error) {
      throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
    }
};

// Funzione per ottenere i vari servizi
export const getServizi = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/utility/servizi`);
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || "Errore durante il recupero dei servizi.");
    }

    const data = await response.json(); // Parsea la risposta JSON
    return data;
  } catch (error) {
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};

// Funzione per ottenere i vari servizi
export const getMetodiPagamento = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/utility/metodi_pagamento`);
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || "Errore durante il recupero dei metodi di pagamento.");
    }

    const data = await response.json(); // Parsea la risposta JSON
    return data;
  } catch (error) {
    throw new Error(error.message || "Errore di connessione. Riprova più tardi.");
  }
};

export const getStanzeDisponibili = async ({ dataInizio, dataFine, postiLetto, tipologiaStanza }) => {
  try {
    console.log("effettuo getStanzeDisponibili");
    let url = `${API_BASE_URL}/utility/stanze_disponibili?dataInizio=${dataInizio}&dataFine=${dataFine}`;

    if (postiLetto) {
      url += `&postiLetto=${postiLetto}`;
    }

    // Aggiungi il parametro tipologiaStanza solo se è diverso da una stringa vuota
    if (tipologiaStanza && tipologiaStanza !== "") {
      url += `&tipologiaStanza=${tipologiaStanza}`;
    }

    const response = await fetch(url);

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(errorText || 'Errore durante il recupero delle stanze disponibili.');
    }

    const data = await response.json(); // Parsea la risposta JSON
    return data;
  } catch (error) {
    throw new Error(error.message || 'Errore di connessione. Riprova più tardi.');
  }
};

