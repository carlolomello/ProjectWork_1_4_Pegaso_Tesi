import React from 'react';
import './ServiziCard.css';

const ServiziCard = ({ service, numDays, capacita, isChecked, onChange }) => {
  // Converte il costo per giorno in numero
  const costoGiornaliero = parseFloat(service.costo_persona_giorno__c) || 0;
  const costoTotale = numDays * capacita * costoGiornaliero;

  return (
    <div className="prenotazione-modal-service-card">
      <input 
        type="checkbox" 
        checked={isChecked} 
        onChange={() => onChange(service.Id)} 
        className="prenotazione-modal-service-checkbox"
      />
      <div className="prenotazione-modal-service-details">
        <h3 className="prenotazione-modal-service-title">{service.Name}</h3>
        <p className="prenotazione-modal-service-cost">
          <strong>Costo per giorno:</strong> €{costoGiornaliero.toFixed(2)}
        </p>
        <p className="prenotazione-modal-service-description">{service.descrizione__c}</p>
        <p className="prenotazione-modal-service-total">
          <strong>Costo totale:</strong> €{costoTotale.toFixed(2)}
        </p>
      </div>
    </div>
  );
};

export default ServiziCard;
