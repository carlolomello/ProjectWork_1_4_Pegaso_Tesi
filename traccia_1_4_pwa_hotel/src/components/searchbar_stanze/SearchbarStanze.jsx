import React, { useState, useEffect } from 'react';
import { getTipologieStanze } from '../../services/stanze_service/StanzeService'; 
import './SearchbarStanze.css'; // Importa il CSS specifico per il componente

const SearchbarStanze = ({ onSearch }) => {
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [numBeds, setNumBeds] = useState('0'); // Imposta il valore di default su "0"
  const [roomTypes, setRoomTypes] = useState([]);
  const [selectedRoomType, setSelectedRoomType] = useState('');

  useEffect(() => {
    // Carica le tipologie di stanze
    const fetchRoomTypes = async () => {
      try {
        const types = await getTipologieStanze();
        setRoomTypes(types);
      } catch (error) {
        console.error("Errore nel recupero delle tipologie stanze:", error);
      }
    };

    fetchRoomTypes();
  }, []);

  // Gestione delle date
  const handleStartDateChange = (e) => {
    const selectedDate = e.target.value;
    if (new Date(selectedDate) >= new Date().setHours(0, 0, 0, 0)) {
      setStartDate(selectedDate);
      // Se la data di fine è precedente alla nuova data di inizio, resettala
      if (new Date(endDate) <= new Date(selectedDate)) {
        setEndDate('');
      }
    } else {
      alert("La data di inizio deve essere odierna o futura.");
    }
  };

  const getMinEndDate = () => {
    if (!startDate) return '';
    const start = new Date(startDate);
    start.setDate(start.getDate() + 1);
    return start.toISOString().split('T')[0];
  };

  const handleEndDateChange = (e) => {
    const selectedEndDate = e.target.value;
    if (new Date(selectedEndDate) > new Date(startDate)) {
      setEndDate(selectedEndDate);
    } else {
      alert("La data di fine deve essere successiva alla data di inizio.");
    }
  };

  const handleSearch = async () => {
    const searchData = {
      startDate,
      endDate,
      numBeds: numBeds !== "" ? numBeds : undefined, 
      // Imposta selectedRoomType a null se è vuoto
      selectedRoomType: selectedRoomType !== "" ? selectedRoomType : null
    };
  
    // Assicurati che almeno le date siano presenti
    if (!searchData.startDate || !searchData.endDate) {
      console.log("Dati di ricerca mancanti:", searchData);
      return; // Non eseguire la ricerca se le date non sono definite
    }
  
    // Passa i dati al padre
    onSearch(searchData);
  };

  return (
    <div className="search-bar">
      <div>
        <label>Data Inizio:</label>
        <input 
          type="date" 
          value={startDate} 
          onChange={handleStartDateChange} 
          min={new Date().toISOString().split('T')[0]} 
          required 
        />
      </div>
      
      <div>
        <label>Data Fine:</label>
        <input 
          type="date" 
          value={endDate} 
          onChange={handleEndDateChange} 
          min={getMinEndDate()} 
          required 
        />
      </div>
      
      <div>
        <label>Numero di Letti:</label>
        <select 
          value={numBeds} 
          onChange={(e) => setNumBeds(e.target.value)} 
        >
          <option value="0">Numero di letti (facoltativo)</option>
          {[1, 2, 3, 4].map((num) => (
            <option key={num} value={num}>
              {num}
            </option>
          ))}
          <option value="5+">5+</option>
        </select>
      </div>

      <div>
        <label>Tipologia Stanza:</label>
        <select 
          value={selectedRoomType} 
          onChange={(e) => setSelectedRoomType(e.target.value)} 
        >
          <option value="">Tipologia (facoltativo)</option>
          {roomTypes.map((room) => (
            <option key={room.Id} value={room.DeveloperName}>
              {room.Label}
            </option>
          ))}
        </select>
      </div>

      <button onClick={handleSearch}>Cerca</button>
    </div>
  );
};

export default SearchbarStanze;
