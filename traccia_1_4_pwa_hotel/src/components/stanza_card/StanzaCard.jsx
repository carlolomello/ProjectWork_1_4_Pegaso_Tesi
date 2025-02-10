import React from 'react';
import { useAuth } from '../../context/AuthContext';
import './StanzaCard.css';

const StanzaCard = ({ room, refresh, onPrenota, onAnnulla, onModifica, isFrom, metodiPagamento }) => {
  const { user } = useAuth();

  // Funzione per formattare la data
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const day = String(date.getDate()).padStart(2, '0');  // Aggiunge uno zero davanti se il giorno è inferiore a 10
    const month = String(date.getMonth() + 1).padStart(2, '0');  // I mesi partono da 0, quindi aggiungiamo 1
    const year = date.getFullYear();
    return `${day}/${month}/${year}`;  // Restituisce la data nel formato dd/mm/aaaa
  };

  // Trova il nome del metodo di pagamento usando l'id
  const metodoPagamentoSelezionato = (metodoPagamentoId) => {
    const metodo = metodiPagamento.find(
      (metodoPagamento) => metodoPagamento.DeveloperName === metodoPagamentoId
    );
    return metodo ? metodo.Label : null;
  };

  const isAnnullaDisabled = () => {
    console.log("room.metodoPagamentoNome", room.metodoPagamentoNome)
    if (room.metodoPagamentoNome === "Carta_di_credito") {
      return false; // Sempre abilitato se il metodo è carta di credito
    }
    const now = new Date();
    const startDate = new Date(room.dataInizio);
    const diffHours = (startDate - now) / (1000 * 60 * 60); // Converte la differenza in ore
  
    return diffHours < 72; // Disabilita se mancano meno di 72 ore
  };

  const isModificaDisabled = () => {
    return room.metodoPagamentoNome !== "Carta_di_credito"; // Disabilita se non è carta di credito
  };
  

  return (
    <div className="roomCard">
      {isFrom === 'StanzePage' && (
        <>
          <h3>{room.Nome}</h3>
          <p><strong>Descrizione:</strong> {room.Descrizione}</p>
          <p><strong>Letti singoli:</strong> {room.NumLettiSingoli}</p>
          <p><strong>Letti matrimoniali:</strong> {room.NumLettiMatrimoniali}</p>
          <p><strong>Capacità:</strong> {room.Capacita} persone</p>
          <p><strong>Costo per notte:</strong> €{room.CostoPerNotte}</p>
          <p><strong>Costo totale:</strong> €{room.CostoTotale}</p>
          <p><strong>Tipologia di stanza:</strong> {room.TipologiaStanza}</p>

          {user ? (
            <button className="stanza-card-btn" onClick={() => onPrenota(room, room.Capacita)}>Prenota</button>
          ) : (
            <p className="not-logged-message">Effettua il login per prenotare</p>
          )}
        </>
      )}

      {isFrom === 'PrenotazioniPage' && (
        <>
          <h3>{room.stanzaNome}</h3>
          <p><strong>Descrizione:</strong> {room.stanzaDescrizione}</p>
          <p><strong>Data Inizio:</strong> {formatDate(room.dataInizio)}</p>
          <p><strong>Data Fine:</strong> {formatDate(room.dataFine)}</p>
          <p><strong>Letti singoli:</strong> {room.numLettiSingoli}</p>
          <p><strong>Letti matrimoniali:</strong> {room.numLettiMatrimoniali}</p>
          <p><strong>Metodo di Pagamento:</strong> {metodoPagamentoSelezionato(room.metodoPagamentoNome) ? metodoPagamentoSelezionato(room.metodoPagamentoNome) : 'Non specificato'}</p>
          <p><strong>Stato Pagamento:</strong> {room.statoPagamento}</p>
          <p><strong>Costo per notte:</strong> €{room.costoPerNotte}</p>
          <p><strong>Costo Totale:</strong> €{room.costoTotale}</p>
          <button className="stanza-card-btn" onClick={() => onModifica(room)} disabled={isModificaDisabled()}>Modifica Prenotazione</button>
          <button className="stanza-card-btn" onClick={() => onAnnulla(room)} disabled={isAnnullaDisabled()}>Annulla Prenotazione</button>
        </>
      )}
    </div>
  );
};

export default StanzaCard;
