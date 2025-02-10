import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Navbar.css";
import LogoutModal from "../logout_modal/LogoutModal";

const Navbar = ({ user, onOpenModal, onLogout }) => {
  const [menuActive, setMenuActive] = useState(false);
  const [showLogoutModal, setShowLogoutModal] = useState(false);
  const navigate = useNavigate();

  const toggleMenu = () => {
    setMenuActive(!menuActive);
  };

  const closeMenu = () => {
    setMenuActive(false); // Chiude il menu
  };

  const handleLogout = () => {
    setShowLogoutModal(true);
  };

  const handleConfirmLogout = () => {
    onLogout();
    setShowLogoutModal(false);
    navigate('/'); // Reindirizza alla homepage
  };

  const handleCancelLogout = () => {
    setShowLogoutModal(false); // Chiudi la modale
  };

  return (
    <nav className="navbar">
      <div className="navbar__left">
        <Link to="/" className="navbar__logo">Hotel Management</Link>
        {user && (
          <span className="navbar__user">
            Benvenuto, {user.nome} {user.cognome}
          </span>
        )}
      </div>
      <div className="navbar__hamburger" onClick={toggleMenu}>
        <div></div>
        <div></div>
        <div></div>
      </div>
      <ul className={`navbar__menu ${menuActive ? "active" : ""}`}>
        <li className="navbar__list-item">
          <Link to="/" className="navbar__link" onClick={closeMenu}>Home Page</Link>
        </li>
        <li className="navbar__list-item">
          <Link to="/stanze" className="navbar__link" onClick={closeMenu}>Stanze</Link>
        </li>
        {user && (
          <li className="navbar__list-item">
            <Link to="/prenotazioni" className="navbar__link" onClick={closeMenu}>Le tue prenotazioni</Link>
          </li>
        )}
        <li className="navbar__list-item">
          <Link to="/contatti" className="navbar__link" onClick={closeMenu}>Contatti</Link>
        </li>
        {!user ? (
          <li className="navbar__list-item">
            <span className="navbar__auth-link" onClick={onOpenModal}>Accedi / Registrati</span>
          </li>
        ) : (
          <li className="navbar__list-item">
            <span className="navbar__auth-link" onClick={handleLogout}>Logout</span>
          </li>
        )}
        {/* Bottone per chiudere il menu (freccia verso l'alto) */}
        <button className="navbar__close-btn" onClick={closeMenu}>↑</button>
      </ul>

      {showLogoutModal && (
        <LogoutModal onConfirm={handleConfirmLogout} onCancel={handleCancelLogout} />
      )}
    </nav>
  );
};

export default Navbar;
