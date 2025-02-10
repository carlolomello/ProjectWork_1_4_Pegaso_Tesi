import React, { useState, useEffect } from 'react';
import Carousel from '../../components/carousel/Carousel';
import { 
  getTipologieStanze, 
  getStanzeDisponibili, 
  getServizi, 
  getMetodiPagamento 
} from '../../services/stanze_service/StanzeService';
import SearchbarStanze from '../../components/searchbar_stanze/SearchbarStanze';
import StanzaCard from '../../components/stanza_card/StanzaCard';
import PrenotaModal from '../../components/prenotazione_modal/PrenotazioneModal';
import { useAuth } from '../../context/AuthContext';

const StanzePage = () => {
  const { user } = useAuth();
  const [tipologie, setTipologie] = useState([]);
  const [response, setResponse] = useState(null);
  const [refresh, setRefresh] = useState(false);
  const [servizi, setServizi] = useState(null);
  const [metodiPagamento, setMetodiPagamento] = useState(null);
  const [searchData, setSearchData] = useState(null);
  const [modalOpen, setModalOpen] = useState(false);
  const [selectedCapacity, setSelectedCapacity] = useState(null);
  const [selectedRoom, setSelectedRoom] = useState(null);

  // Carica le tipologie delle stanze
  useEffect(() => {
    const fetchTipologie = async () => {
      try {
        const data = await getTipologieStanze();
        const formattedData = data.map(item => ({
          label: item.Label,
          developerName: item.DeveloperName,
          description: item.descrizione__c,
          image: '',
        }));
        setTipologie(formattedData);
      } catch (error) {
        console.error('Errore durante il recupero delle tipologie di stanze:', error.message);
      }
    };

    fetchTipologie();
  }, []);

  // Carica servizi e metodi di pagamento all'avvio della pagina
  useEffect(() => {
    const fetchUtilityData = async () => {
      try {
        const serviziData = await getServizi();
        const metodiData = await getMetodiPagamento();
        setServizi(serviziData);
        setMetodiPagamento(metodiData);
      } catch (error) {
        console.error('Errore durante il recupero dei dati utili:', error.message);
      }
    };

    fetchUtilityData();
  }, []);

  // Forza il re-render quando cambia l'utente (login/logout)
  useEffect(() => {
    setRefresh(prev => !prev);
  }, [user]);

  // Gestione della ricerca: salviamo anche i dati della ricerca per usarli nel modal
  const handleSearch = async (data) => {
    if (!data.startDate || !data.endDate || !data.numBeds || data.selectedRoomType === "") {
      console.error('Dati di ricerca mancanti:', data);
      return;
    }
    setSearchData(data);
    try {
      const res = await getStanzeDisponibili({
        dataInizio: data.startDate,
        dataFine: data.endDate,
        postiLetto: data.numBeds,
        tipologiaStanza: data.selectedRoomType,
      });
      setResponse(res);
    } catch (error) {
      console.error('Errore durante la ricerca delle stanze:', error.message);
      setResponse([]);
    }
  };

  // Callback per aprire il modal, passando la capacità della stanza selezionata
  const handlePrenota = (room, capacita) => {
    setSelectedRoom(room);
    setSelectedCapacity(capacita);
    setModalOpen(true);
  };

  // Funzione per ottenere l'etichetta della tipologia
  const getRoomTypeLabel = (developerName) => {
    const roomType = tipologie.find(type => type.developerName === developerName);
    return roomType ? roomType.label : 'Tipo di stanza non disponibile';
  };

  return (
    <div className="stanzepage">
      <h1 className="page-title">Le Nostre Stanze</h1>

      <div className="page-container">
      <div className="carousel-container">
        {tipologie.length > 0 ? (
          <Carousel imagesPath='/images/tipologia_stanze/' items={tipologie} />
          ) : (
            <p>Caricamento in corso...</p>
          )}
        </div>
        <div className="searchbar-container">
          <SearchbarStanze roomTypes={tipologie} onSearch={handleSearch} />
        </div>
      </div>

      {response && (
        <div className="rooms-grid">
          <h3>Stanze Disponibili:</h3>
          <div className="rooms-list">
            {response.map(room => (
              <StanzaCard
                key={room.Id}
                room={{
                  ...room,
                  TipologiaStanza: getRoomTypeLabel(room.TipologiaStanza)
                }}
                refresh={refresh}
                onPrenota={handlePrenota}
                isFrom='StanzePage'
              />
            ))}
          </div>
        </div>
      )}

      {/* Modal di prenotazione: lo apriamo se sono presenti searchData e la capacità della stanza */}
      {modalOpen && searchData && selectedRoom && selectedCapacity && (
        <PrenotaModal
          isOpen={modalOpen}
          onClose={() => setModalOpen(false)}
          servizi={servizi}
          room={selectedRoom}
          metodiPagamento={metodiPagamento}
          startDate={searchData.startDate}
          endDate={searchData.endDate}
          capacita={selectedCapacity}
          isFrom='StanzePage'
        />
      )}
    </div>
  );
};

export default StanzePage;
