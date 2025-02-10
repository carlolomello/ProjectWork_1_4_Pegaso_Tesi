import React from 'react';
import './AnnullaPrenotazioneModal.css'; // Assicurati di avere lo stile per la modale

const AnnullaPrenotazioneModal = ({ isOpen, onClose, onConfirm, prenotazioneId }) => {
  // Se il modal non è aperto, non viene renderizzato nulla
  if (!isOpen) return null;

  // Gestione della conferma dell'annullamento
  const handleConfirm = () => {
    onConfirm(prenotazioneId); // Passa l'ID della prenotazione alla funzione di conferma
    onClose(); // Chiudi il modal dopo la conferma
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Conferma Cancellazione</h2>
        <p>Sei sicuro di voler annullare questa prenotazione?</p>
        <div className="modal-buttons">
          <button className="annulla-modal-btn" onClick={onClose}>Annulla</button>
          <button className="annulla-modal-btn" onClick={handleConfirm}>Conferma</button>
        </div>
      </div>
    </div>
  );
};

export default AnnullaPrenotazioneModal;
