import React from 'react';
import './HomePage.css';
import Carousel from '../../components/carousel/Carousel';
const HomePage = () => {

  const imagesData = [
    {
      label: 'Hotel Esterno',
      description: 'Vista esterna del nostro hotel, dove l\'architettura gotica si incontra con la modernità.',
      developerName: '1_hotel'
    },
    {
      label: 'Interni Hotel',
      description: 'Interni lussuosi con arredi eleganti e spazi ampi per un soggiorno indimenticabile.',
      developerName: '2_interni'
    },
    {
      label: 'Giardini',
      description: 'I nostri giardini sono un angolo di tranquillità dove rilassarsi all\'aria aperta.',
      developerName: '3_giardini'
    },
    {
      label: 'Piscina',
      description: 'La piscina all\'aperto è il luogo perfetto per rilassarsi e godere del sole.',
      developerName: '4_piscina'
    }
  ];
  

  return (
    <div className="home-page">
      <h1 className="page-title">Welcome to our Hotel!</h1>
  
      
      <section className="sarcastic-info">
        <p>
          Il nostro hotel, costruito con un’architettura gotica che non passerà inosservata, è un luogo dove la storia incontra
          la modernità. Non preoccuparti, anche se l'estetica storica potrebbe farti pensare a un castello medievale, siamo
          equipaggiati con tutte le comodità moderne per rendere il tuo soggiorno il più confortevole possibile.
        </p>
        <div className="carousel-container">

        {imagesData.length > 0 ? (
          <Carousel imagesPath='/images/hotel/' items={imagesData} />
          ) : (
            <p>Caricamento in corso...</p>
          )}
        </div>
        
        <p>
          Con stanze spaziose e una vista mozzafiato dalle vetrate, avrai sempre una buona ragione per non lasciare la tua
          camera (anche se il Wi-Fi potrebbe incoraggiarti a esplorare il resto dell’hotel). E se pensi che sia solo una
          facciata, aspetta di scoprire i nostri ampi spazi e il servizio che farà sentire ogni ospite come a casa.
        </p>
        <p>
          La nostra piscina ti aspetta per un tuffo di relax, mentre il ristorante stellato offre piatti raffinati per
          soddisfare anche i palati più esigenti. Un perfetto equilibrio tra tradizione e innovazione!
        </p>
      </section>
    </div>
  );
};

export default HomePage;
