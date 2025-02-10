import React, { useState, useEffect } from 'react';
import './PrenotazioneModal.css';
import ServiceCard from '../service_card/ServiziCard';
import PaymentMethodCard from '../payment_method_card/PaymentMethodCard';
import { useAuth } from '../../context/AuthContext';
import { insertPrenotazione, updatePrenotazione } from '../../services/prenotazioni_service/PrenotazioniService';
import { X, ChevronUp, ChevronDown } from 'lucide-react';
import { useNavigate } from 'react-router-dom';

const PrenotazioneModal = ({
  isOpen,
  onClose,
  servizi,
  metodiPagamento,
  room,
  startDate,
  endDate,
  capacita,
  isFrom
}) => {
  const { user } = useAuth();
  const navigate = useNavigate();

  const [start, setStart] = useState(startDate);
  const [end, setEnd] = useState(endDate);
  const [selectedServices, setSelectedServices] = useState({});
  const [selectedPaymentMethod, setSelectedPaymentMethod] = useState(null);
  const [isServicesVisible, setIsServicesVisible] = useState(true);
  const [cardDetails, setCardDetails] = useState({ cardNumber: '', cvv: '', expiryDate: '' });
  const [isCardDetailsVisible, setIsCardDetailsVisible] = useState(false);

  const [message, setMessage] = useState('');
  const [messageColor, setMessageColor] = useState(''); // 'red' per errore, 'green' per successo


  useEffect(() => {
    setStart(startDate);
    setEnd(endDate);
    if (room && room.servizi) {
      const initialSelectedServices = {};
      room.servizi.forEach(service => {
        initialSelectedServices[service.servizioId] = true;
      });
      setSelectedServices(initialSelectedServices);
    }
  }, [startDate, endDate, room]);

  if (!isOpen) return null;

  const calculateDays = (start, end) => {
    const startObj = new Date(start);
    const endObj = new Date(end);
    return Math.ceil(Math.abs(endObj - startObj) / (1000 * 60 * 60 * 24));
  };

  const numDays = calculateDays(start, end);

  const handleServiceChange = (serviceId) => {
    setSelectedServices(prev => ({
      ...prev,
      [serviceId]: !prev[serviceId]
    }));
  };

  const handlePaymentSelect = (methodId) => {
    if (methodId !== selectedPaymentMethod) {
      setSelectedPaymentMethod(methodId);
    
    setSelectedPaymentMethod(methodId);

    // Nascondi i dettagli della carta per il metodo precedente
    setIsCardDetailsVisible(prev => ({
      ...prev,
      [selectedPaymentMethod]: false // Nasconde il metodo precedente
    }));
    // Resetta i dati della carta (numero, CVV, scadenza) quando si cambia metodo
    setCardDetails({
      cardNumber: '',
      cvv: '',
      expiryDate: ''
    });

    // Ottieni il metodo di pagamento selezionato
    const selectedMethod = metodiPagamento.find((method) => method.Id === methodId);

    // Verifica se il metodo selezionato è una carta di credito o carta di debito
    if (selectedMethod && (selectedMethod.DeveloperName === 'Carta_di_credito' || selectedMethod.DeveloperName === 'Carta_di_debito_Bancomat')) {
      setIsCardDetailsVisible(prev => ({ ...prev, [methodId]: true }));  // Mostra i dettagli della carta solo per il metodo selezionato
    }
    // Verifica se il metodo selezionato è Bonifico
    else if (selectedMethod && selectedMethod.DeveloperName === 'Bonifico') {
      setIsCardDetailsVisible(prev => ({ ...prev, [methodId]: true }));  // Mostra i dettagli per Bonifico
    } else {
      setIsCardDetailsVisible(prev => ({ ...prev, [methodId]: false }));  // Nascondi i dettagli della carta
    }
  }
  };
  const calcolaCostoServizi = (selectedServices) => {
    const serviziSelezionati = Object.keys(selectedServices).filter(id => selectedServices[id]);
    return serviziSelezionati.reduce((totale, serviceId) => {
      const service = servizi.find(s => s.Id === serviceId);
      if (service && service.costo_persona_giorno__c) {
        totale += parseFloat(service.costo_persona_giorno__c);
      }
      return totale;
    }, 0) * capacita;
  };

  const calcolaTotale = () => {
    const costoStanza = isFrom === 'StanzePage' ? room.CostoPerNotte : room.costoPerNotte;
    const costoServizi = calcolaCostoServizi(selectedServices);
    return (costoStanza + costoServizi) * numDays;
  };

  const totale = calcolaTotale();

  const handleConfirm = async () => {
    if (!validateForm()) return;

    const serviziIds = Object.keys(selectedServices).filter(id => selectedServices[id]);
    const selectedMethodObj = metodiPagamento.find(method => method.Id === selectedPaymentMethod);
    const idMetodoPagamento = selectedMethodObj ? selectedMethodObj.DeveloperName : "";

    try {
      if (room.prenotazioneId) {
        // Modifica prenotazione
        const response = await updatePrenotazione(
          room.prenotazioneId,
          start,
          end,
          serviziIds
        );
        if (response.success) {
          setMessage('Prenotazione modificata con successo!');
          setMessageColor('green');    
          setTimeout(() => {
            onClose();
            window.location.reload();
          }, 3000);      
          
        } else {
          setMessage('Errore durante la prenotazione. Stanza non disponibile');
          setMessageColor('red');
        }
      } else {
        // Nuova prenotazione
        const response = await insertPrenotazione(
          user.id,
          room.Id,
          start,
          end,
          idMetodoPagamento,
          serviziIds
        );
        if (response.success) {
          setMessage('Prenotazione effettuata con successo!');
          setMessageColor('green');   
          

          setTimeout(() => {
            onClose();
            navigate("/prenotazioni");
          }, 3000);
          
          
        } else {
          setMessage('Errore durante la prenotazione. Stanza non disponibile');
          setMessageColor('red');
        }
      }
    } catch (error) {
      console.error("Errore nella prenotazione:", error);
      setMessage('Errore durante la prenotazione.');
      setMessageColor('red');
    }
  };

  const validateCardDetails = () => {
    console.log("entro 2");
    console.log("selectedPaymentMethod", selectedPaymentMethod);
    console.log("metodiPagamento", metodiPagamento);

    // Cicla attraverso i metodi di pagamento
    for (let i = 0; i < metodiPagamento.length; i++) {
      const metodo = metodiPagamento[i];

      // Verifica se l'ID del metodo selezionato corrisponde
      if (metodo.Id === selectedPaymentMethod) {
        console.log("metodo selezionato:", metodo);

        // Se il metodo è Carta_di_credito o Carta_di_debito_Bancomat
        if (metodo.DeveloperName === 'Carta_di_credito' || metodo.DeveloperName === 'Carta_di_debito_Bancomat') {
          const { cardNumber, cvv, expiryDate } = cardDetails;
          const cardNumberPattern = /^[0-9]{16}$/;
          const cvvPattern = /^[0-9]{3}$/;
          const expiryDatePattern = /^\d{4}-\d{2}$/;

          // Controlla se i dati della carta sono vuoti
          if (!cardNumber || !cvv || !expiryDate) {
            return 'Tutti i campi della carta devono essere compilati';
          }

          if (!cardNumberPattern.test(cardNumber)) {
            return 'Numero carta non valido';
          }
          if (!cvvPattern.test(cvv)) {
            return 'CVV non valido';
          }
          if (!expiryDatePattern.test(expiryDate)) {
            return 'Scadenza non valida';
          }
        }
        // Se il metodo è Bonifico, non ci sono controlli da fare
        else if (metodo.DeveloperName === 'Bonifico') {
          console.log('Bonifico selezionato, nessun controllo necessario');
        }
      }
    }

    return null; // Se nessun errore è stato trovato, ritorna null
  };

const validateForm = () => {
  if (!selectedPaymentMethod && isFrom === 'StanzePage') {
    setMessage('Seleziona un metodo di pagamento.');
    setTimeout(() => {
      setMessage('');
    }, 3000);
    
    return false;
  }

  // Controlla i dettagli della carta solo se necessario
  const cardValidationError = validateCardDetails();
  if (cardValidationError) {
    setMessage(cardValidationError);
    setTimeout(() => {
      setMessage('');
    }, 3000);
    return false;
  }

  return true;
};

const handleCardDetailsChange = (field, value) => {
  setCardDetails(prev => ({
    ...prev, // Mantiene i valori precedenti
    [field]: value // Aggiorna solo il campo specifico
  }));
};

  return (
    <div className="prenotazione-modal-overlay">
      <div className="prenotazione-modal-content">
        <h2 className="prenotazione-modal-title">{isFrom === 'StanzePage' ? room.Nome : room.stanzaNome}</h2>
        <button className="prenotazione-modal-close-button" onClick={onClose}><X size={20} /></button>

        {isFrom === 'PrenotazioniPage' && (
          <>
            <div className="prenotazione-modal-date">
              <label>Data Inizio</label>
              <input
                type="date"
                value={start || ''}
                onChange={(e) => {
                  const newStart = e.target.value;
                  setStart(newStart);
                  if (end && newStart >= end) {
                    const newEnd = new Date(newStart);
                    newEnd.setDate(newEnd.getDate() + 1);
                    setEnd(newEnd.toISOString().split('T')[0]);
                  }
                }}
                min={new Date().toISOString().split('T')[0]}
              />
            </div>

            <div className="prenotazione-modal-date">
              <label>Data Fine</label>
              <input
                type="date"
                value={end || ''}
                onChange={(e) => setEnd(e.target.value)}
                min={start ? new Date(new Date(start).setDate(new Date(start).getDate() + 1)).toISOString().split('T')[0] : new Date().toISOString().split('T')[0]}
              />
            </div>
          </>
        )}

        <div className="prenotazione-modal-scrollable-content">
          <div className="prenotazione-modal-services-list">
            <div className="services-header">
              <h3 className="prenotazione-modal-payment-title-header">Servizi Disponibili</h3>
              <button
                className="toggle-services-circle"
                onClick={() => setIsServicesVisible(!isServicesVisible)}
              >
                {isServicesVisible ? <ChevronUp size={20} /> : <ChevronDown size={20} />}
              </button>
            </div>

            {isServicesVisible && (
              <div>
                {servizi && servizi.length > 0 ? (
                  servizi.map(service => (
                    <ServiceCard
                      key={service.Id}
                      service={service}
                      numDays={numDays}
                      capacita={capacita}
                      isChecked={!!selectedServices[service.Id]}
                      onChange={handleServiceChange}
                    />
                  ))
                ) : (
                  <p>Caricamento servizi...</p>
                )}
              </div>
            )}
          </div>

          {isFrom === 'StanzePage' && (
            <div className="prenotazione-modal-payment-methods">
              <h3 className="prenotazione-modal-payment-title-header">Metodi di Pagamento</h3>
              {metodiPagamento && metodiPagamento.length > 0 ? (
                metodiPagamento.map(method => (
                  <PaymentMethodCard
                    key={method.Id}
                    method={method}
                    isSelected={selectedPaymentMethod === method.Id}
                    onSelect={handlePaymentSelect}
                    isCardDetailsVisible={isCardDetailsVisible[method.Id] || false}
                    onCardDetailsChange={
                      handleCardDetailsChange
                    }
                    room={room}
                    startDate={startDate}
                    endDate={startDate}
                  />
                ))
              ) : (
                <p>Caricamento metodi di pagamento...</p>
              )}
            </div>
          )}

        </div>

        <div className="prenotazione-modal-total">
          <h3>Totale: €{totale}</h3>
        </div>
        <div className={`prenotazione-modal-status-message ${messageColor}`}>
          {message}
        </div>
        <div className="prenotazione-modal-footer">
          <button className="stanza-card-btn" onClick={handleConfirm}>
            {room.prenotazioneId ? 'Modifica Prenotazione' : 'Conferma Prenotazione'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default PrenotazioneModal;
