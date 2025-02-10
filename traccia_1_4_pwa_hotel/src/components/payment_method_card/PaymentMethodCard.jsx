import React, { useState } from 'react';
import './PaymentMethodCard.css';
import { useAuth } from '../../context/AuthContext';


const PaymentMethodCard = ({ method, isSelected, onSelect, onCardDetailsChange, isCardDetailsVisible, room, startDate, endDate
 }) => {
    
  const { user } = useAuth();
  const [cardNumber, setCardNumber] = useState("");
  const [errors, setErrors] = useState({
    cardNumber: "",
    cvv: "",
    expiryDate: "",
    iban: ""
  });

    // Funzione per formattare la data
    const formatDate = (dateString) => {
      const date = new Date(dateString);
      const day = String(date.getDate()).padStart(2, '0');  // Aggiunge uno zero davanti se il giorno è inferiore a 10
      const month = String(date.getMonth() + 1).padStart(2, '0');  // I mesi partono da 0, quindi aggiungiamo 1
      const year = date.getFullYear();
      return `${day}/${month}/${year}`;  // Restituisce la data nel formato dd/mm/aaaa
    };

  // Regex patterns
  const cardNumberPattern = /^[0-9]{16}$/;
  const cvvPattern = /^[0-9]{3}$/;
  const expiryDatePattern = /^\d{4}-\d{2}$/;
  const ibanPattern = /^IT\d{2}[A-Z0-9]{1,30}$/;

  // Validation function
  const validateField = (field, value) => {
    switch (field) {
      case "cardNumber":
        return cardNumberPattern.test(value.replace(/\s/g, '')) ? "" : "Numero carta non valido";
      case "cvv":
        return cvvPattern.test(value) ? "" : "CVV non valido";
      case "expiryDate":
        return expiryDatePattern.test(value) ? "" : "Scadenza non valida";
      case "iban":
        return ibanPattern.test(value) ? "" : "IBAN non valido";
      default:
        return "";
    }
  };

  const formatCardNumber = (value) => {
    return value.replace(/\D/g, '') // Rimuove caratteri non numerici
      .slice(0, 16) // Limita a 16 cifre
      .replace(/(\d{4})/g, '$1 ') // Aggiunge spazio ogni 4 cifre
      .trim(); // Rimuove eventuali spazi finali
  };

  const handleCardNumberChange = (e) => {
    const formattedValue = formatCardNumber(e.target.value);
    setCardNumber(formattedValue);
    onCardDetailsChange("cardNumber", formattedValue.replace(/\s/g, ''));
    setErrors(prev => ({ ...prev, cardNumber: validateField("cardNumber", formattedValue) }));
  };

  return (
    <div
      className="prenotazione-modal-payment-card"
      onClick={() => onSelect(method.Id)}
    >
      <input
        type="radio"
        name="paymentMethod"
        checked={isSelected}
        onChange={() => onSelect(method.Id)}
        className="prenotazione-modal-payment-radio"
      />
      <div className="prenotazione-modal-payment-details">
        <h3 className="prenotazione-modal-payment-title">{method.Label}</h3>
        <p className="prenotazione-modal-payment-description">{method.descrizione__c}</p>

        {isCardDetailsVisible && (method.DeveloperName === "Carta_di_credito" || method.DeveloperName === "Carta_di_debito_Bancomat") && (
          <div className="card-details">
            <label>
              Numero Carta
              <input
                className='card-number-input'
                type="text"
                name="cardNumber"
                value={cardNumber}
                onChange={handleCardNumberChange}
                maxLength="19" // 16 cifre + 3 spazi
                inputMode="numeric"
              />
              {errors.cardNumber && <p className="error-message">{errors.cardNumber}</p>}
            </label>
            <label>
              CVV
              <input
                type="text"
                name="cvv"
                onChange={e => onCardDetailsChange("cvv", e.target.value)}
                maxLength="3"
                inputMode="numeric"
              />
              {errors.cvv && <p className="error-message">{errors.cvv}</p>}
            </label>
            <label>
              Scadenza
              <input
                type="month"
                name="expiryDate"
                onChange={e => onCardDetailsChange("expiryDate", e.target.value)}
              />
              {errors.expiryDate && <p className="error-message">{errors.expiryDate}</p>}
            </label>
          </div>
        )}

        {isCardDetailsVisible && method.DeveloperName === "Bonifico" && (
          <div className="iban-details">
            <p>IBAN: IT60X0542811101000000123456</p>
            <p>Destinatario: Hotel Management</p>
            <p>Causale: Prenotazione Hotel - {room.Nome} - {user.nome} {user.cognome} - {formatDate(startDate)} - {formatDate(endDate)}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default PaymentMethodCard;
