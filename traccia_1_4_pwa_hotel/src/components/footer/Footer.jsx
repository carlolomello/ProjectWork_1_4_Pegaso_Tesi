import React, { useState } from "react";
import "./Footer.css";

const Footer = () => {
  const [isFooterVisible, setIsFooterVisible] = useState(false);

  const toggleFooter = () => {
    setIsFooterVisible(prevState => !prevState);
  };

  return (
    <div>
      {/* Pulsante laterale con freccia */}
      <button 
        className={`footer-toggle-btn ${isFooterVisible ? "show" : ""}`} 
        onClick={toggleFooter}
      >
        {/* Icona freccia */}
      </button>

      {/* Footer */}
      <footer className={`footer ${isFooterVisible ? "show" : ""}`}>
        <p>&copy; {new Date().getFullYear()} Hotel Management. Tutti i diritti riservati.</p>
        <section className="footer-description">
          <h3>Informazioni sul progetto</h3>
          <p>
            <strong>La digitalizzazione dell’impresa</strong>
            <br />
            Traccia del PW n. 1.4: Sviluppo di una pagina web per un servizio di prenotazione online di un’impresa del settore terziario.
          </p>
          <p>
            Le imprese nel settore terziario, come hotel, ristoranti o servizi di trasporto, richiedono sistemi di prenotazione
            efficienti per gestire la clientela. Il sistema sviluppato consente di prenotare, modificare e cancellare prenotazioni,
            ed è progettato per essere intuitivo e facile da usare.
          </p>
        </section>
      </footer>
    </div>
  );
};

export default Footer;
