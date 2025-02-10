import React from 'react';
import './LogoutModal.css'; // Stili per la modale di conferma logout

const LogoutModal = ({ onConfirm, onCancel }) => {
  return (
    <div className="logout-modal">
      <div className="logout-modal-content">
        <h2>Sei sicuro di voler effettuare il logout?</h2>
        <div className="logout-modal-actions">
          <button className="logout-modal-btn" onClick={onCancel}>Annulla</button>
          <button className="logout-modal-btn" onClick={onConfirm}>Conferma</button>
        </div>
      </div>
    </div>
  );
};

export default LogoutModal;
