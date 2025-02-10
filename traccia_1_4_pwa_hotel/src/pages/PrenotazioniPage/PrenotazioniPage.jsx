import React, { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext'; // Hook per ottenere i dati dell'utente loggato
import { getPrenotazioniUtente, deletePrenotazione } from '../../services/prenotazioni_service/PrenotazioniService'; // Funzione per ottenere le prenotazioni dell'utente
import { getServizi, getMetodiPagamento } from '../../services/stanze_service/StanzeService'; // Funzioni per ottenere i servizi e i metodi di pagamento
import StanzaCard from '../../components/stanza_card/StanzaCard'; // Componente per visualizzare le informazioni della stanza
import PrenotazioneModal from '../../components/prenotazione_modal/PrenotazioneModal'; // Modal per la modifica della prenotazione
import AnnullaPrenotazioneModal from '../../components/annulla_prenotazione_modal/AnnullaPrenotazioneModal'; // Modal per annullare la prenotazione
import './PrenotazioniPage.css'; // Import del foglio di stile per la pagina

const PrenotazioniPage = () => {
  // Stato per memorizzare i dati
  const { user } = useAuth(); // Ottieni i dati dell'utente loggato tramite il context
  const [prenotazioni, setPrenotazioni] = useState([]); // Stato per memorizzare le prenotazioni
  const [servizi, setServizi] = useState([]); // Stato per memorizzare i servizi disponibili
  const [metodiPagamento, setMetodiPagamento] = useState([]); // Stato per memorizzare i metodi di pagamento
  const [modalOpen, setModalOpen] = useState(false); // Stato per il modal di modifica
  const [annullaModalOpen, setAnnullaModalOpen] = useState(false); // Stato per il modal di annullamento
  const [selectedRoom, setSelectedRoom] = useState(null); // Stato per memorizzare la stanza selezionata
  const [startDate, setStartDate] = useState(null); // Stato per la data di inizio
  const [endDate, setEndDate] = useState(null); // Stato per la data di fine

  // Carica le prenotazioni dell'utente quando l'utente è loggato
  useEffect(() => {
    const fetchPrenotazioni = async () => {
      try {
        const data = await getPrenotazioniUtente(user.id); // Recupera le prenotazioni dall'API
        setPrenotazioni(data.prenotazioni || []); // Salva le prenotazioni nello stato
      } catch (error) {
        console.error('Errore nel recupero delle prenotazioni:', error.message);
        setPrenotazioni([]); // Imposta un array vuoto in caso di errore
      }
    };

    if (user) {
      fetchPrenotazioni(); // Se l'utente è loggato, carica le prenotazioni
    }
  }, [user]);

  // Carica i servizi e i metodi di pagamento disponibili
  useEffect(() => {
    const fetchUtilityData = async () => {
      try {
        const serviziData = await getServizi(); // Recupera i servizi disponibili
        const metodiData = await getMetodiPagamento(); // Recupera i metodi di pagamento disponibili
        setServizi(serviziData); // Salva i servizi nello stato
        setMetodiPagamento(metodiData); // Salva i metodi di pagamento nello stato
        console.log("metodiData ",metodiData)
      } catch (error) {
        console.error('Errore durante il recupero dei dati utili:', error.message);
      }
    };

    fetchUtilityData(); // Carica i dati all'inizializzazione del componente
  }, []);

  // Funzione per aprire il modal di modifica prenotazione
  const openModal = (room, start, end) => {
    setSelectedRoom(room); // Imposta la stanza selezionata
    setStartDate(start); // Imposta la data di inizio
    setEndDate(end); // Imposta la data di fine
    setModalOpen(true); // Apre il modal di modifica
  };

  // Funzione per chiudere il modal di modifica prenotazione
  const closeModal = () => {
    setModalOpen(false); // Chiudi il modal di modifica
  };

  // Funzione per aprire il modal di annullamento prenotazione
  const openAnnullaModal = (room) => {
    setSelectedRoom(room); // Imposta la stanza selezionata per l'annullamento
    setAnnullaModalOpen(true); // Apre il modal di annullamento
  };

  // Funzione per chiudere il modal di annullamento prenotazione
  const closeAnnullaModal = () => {
    setAnnullaModalOpen(false); // Chiudi il modal di annullamento
  };

  // Funzione per gestire l'annullamento della prenotazione
  const handleAnnullaPrenotazione = async (room) => {
    try {
      // Chiamata al servizio per annullare la prenotazione
      console.log("room to delete ", room);
      await deletePrenotazione(room.prenotazioneId); // Chiamata al servizio di annullamento prenotazione

      // Aggiorna lo stato dopo l'annullamento della prenotazione
      setPrenotazioni(prenotazioni.filter(p => p.prenotazioneId !== room.prenotazioneId));
      closeAnnullaModal(); // Chiudi il modal dopo aver annullato
    } catch (error) {
      console.error("Errore durante l'annullamento della prenotazione:", error);
    }
  };

  return (
    <div className="prenotazionipage">
      {/* Titolo della pagina */}
      <h1 className="page-title">Le Tue Prenotazioni</h1>

      <div className="rooms-list">
        {/* Se ci sono prenotazioni, visualizzale */}
        {prenotazioni.length > 0 ? (
          prenotazioni.map(prenotazione => (
            <StanzaCard 
              key={prenotazione.prenotazioneId} // Identificatore unico per la stanza
              room={prenotazione} // Passa i dati della prenotazione
              isPrenotazionePage={true} // Indica che siamo nella pagina delle prenotazioni
              onAnnulla={() => openAnnullaModal(prenotazione)} // Apre il modal di annullamento
              onModifica={() => openModal(prenotazione, prenotazione.dataInizio, prenotazione.dataFine)} // Apre il modal di modifica
              metodiPagamento={metodiPagamento}
              isFrom='PrenotazioniPage' // Passa un identificatore per sapere da dove proviene
            />
          ))
        ) : (
          <p>Nessuna prenotazione trovata.</p> // Messaggio se non ci sono prenotazioni
        )}
      </div>

      {/* Modal per la modifica della prenotazione */}
      <PrenotazioneModal 
        isOpen={modalOpen} // Stato di apertura del modal
        onClose={closeModal} // Funzione per chiudere il modal
        room={selectedRoom} // Passa la stanza selezionata
        startDate={startDate} // Passa la data di inizio
        endDate={endDate} // Passa la data di fine
        capacita={selectedRoom ? (selectedRoom.numLettiSingoli + (selectedRoom.numLettiMatrimoniali * 2)) : 0} // Passa la capacità della stanza
        isFrom='PrenotazioniPage' // Passa l'origine della pagina
        servizi={servizi} // Passa i servizi disponibili
        serviziSelezionati={selectedRoom ? selectedRoom.servizi : []} // Passa i servizi selezionati
        metodiPagamento={metodiPagamento} // Passa i metodi di pagamento disponibili
      />

      {/* Modal per annullare la prenotazione */}
      <AnnullaPrenotazioneModal 
        isOpen={annullaModalOpen}
        onClose={closeAnnullaModal}
        onConfirm={() => handleAnnullaPrenotazione(selectedRoom)} // Usa onConfirm qui
        prenotazione={selectedRoom}
      />
    </div>
  );
};

export default PrenotazioniPage;
