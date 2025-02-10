import React, { useState } from 'react';
import { login, register } from '../../services/auth_service/AuthService';  
import './AuthModal.css';
import { X } from 'lucide-react';

const AuthModal = ({ onClose, onSuccess }) => {
  const [isLoginMode, setIsLoginMode] = useState(true);
  const [formData, setFormData] = useState({
    nome: '',
    cognome: '',
    email: '',
    cellulare: '',
    password: '',
    emailCellulare: '',
  });

  const [error, setError] = useState('');

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const validateForm = () => {
    // Se siamo in modalità registrazione, eseguiamo la validazione sui campi
    if (!isLoginMode) {
      // Validazione di nome e cognome (nessun numero o carattere speciale)
      if (/[^a-zA-Zà-ùÀ-Ù\s]/.test(formData.nome) || formData.nome.length < 2) {
        setError('Nome non valido');
        return false;
      }
      if (/[^a-zA-Zà-ùÀ-Ù\s]/.test(formData.cognome) || formData.cognome.length < 2) {
        setError('Cognome non valido');
        return false;
      }
      // Validazione dell'email (formato corretto)
      if (!/\S+@\S+\.\S+/.test(formData.email)) {
        setError('Email non valida');
        return false;
      }
      // Validazione del numero di cellulare (formato italiano)
      if (!/^3\d{9}$/.test(formData.cellulare)) {
        setError('Numero di cellulare non valido. Deve iniziare con 3 e avere 10 cifre.');
        return false;
      }
      // Validazione della password (minimo 6 caratteri)
      if (formData.password.length < 6) {
        setError('La password deve contenere almeno 6 caratteri');
        return false;
      }
    }

    // Se tutte le validazioni passano, resetta l'errore
    setError('');
    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return; // Solo se la validazione passa procedi

    try {
      let user;
      if (isLoginMode) {
        user = await login(formData.emailCellulare, formData.password);
      } else {
        user = await register(formData.nome, formData.cognome, formData.email, formData.cellulare, formData.password);
      }

      sessionStorage.setItem("user", JSON.stringify(user));
      onSuccess(user);  // Passa l'utente alla navbar
      onClose();
    } catch (err) {
      setError(err.message);
    }
  };

  return (
    <div className="auth-modal-overlay" onClick={onClose}>
      <div className="auth-modal-content" onClick={(e) => e.stopPropagation()}>
        <button className="auth-modal-close" onClick={onClose}>
          <X size={20} />
        </button>
        <h2>{isLoginMode ? 'Login' : 'Registrazione'}</h2>
        {error && <p className="auth-modal-error">{error}</p>}
        <form onSubmit={handleSubmit} className="auth-modal-form">
          {!isLoginMode && (
            <>
              <input className="auth-modal-input" type="text" name="nome" value={formData.nome} onChange={handleChange} placeholder="Nome" required />
              <input className="auth-modal-input" type="text" name="cognome" value={formData.cognome} onChange={handleChange} placeholder="Cognome" required />
              <input className="auth-modal-input" type="email" name="email" value={formData.email} onChange={handleChange} placeholder="Email" required />
              <input className="auth-modal-input" type="text" name="cellulare" value={formData.cellulare} onChange={handleChange} placeholder="Cellulare" required />
            </>
          )}
          {isLoginMode && (
            <input className="auth-modal-input" type="text" name="emailCellulare" value={formData.emailCellulare} onChange={handleChange} placeholder="Email o Cellulare" required />
          )}
          <input className="auth-modal-input" type="password" name="password" value={formData.password} onChange={handleChange} placeholder="Password" required />
          <button type="submit" className="auth-modal-submit">{isLoginMode ? 'Accedi' : 'Registrati'}</button>
        </form>
        <p className="auth-modal-switch">
          {isLoginMode ? "Non hai un account? " : "Hai già un account? "}
          <span className="auth-modal-switch-link" onClick={() => setIsLoginMode(!isLoginMode)}>
            {isLoginMode ? "Registrati qui" : "Accedi qui"}
          </span>
        </p>
      </div>
    </div>
  );
};

export default AuthModal;
