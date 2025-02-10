import React from 'react';
import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Navbar from './components/navbar/Navbar';
import Footer from './components/footer/Footer';
import HomePage from './pages/HomePage';
import StanzePage from './pages/StanzePage';
import PrenotazioniPage from './pages/PrenotazioniPage';
import ContattiPage from './pages/ContattiPage';
import AuthModal from './components/auth_modal/AuthModal';
import { AuthProvider, useAuth } from './context/AuthContext';

const AppContent = () => {
  const { user, login, logout } = useAuth();
  const [modalOpen, setModalOpen] = React.useState(false);

  const handleOpenModal = () => {
    setModalOpen(true);
  };

  const handleCloseModal = () => {
    setModalOpen(false);
  };

  // In AuthModal, una volta che il login va a buon fine, chiama direttamente login(userData)
  const handleSuccessLogin = (userData) => {
    login(userData);
  };

  return (
    <>
      <Navbar user={user} onOpenModal={handleOpenModal} onLogout={logout} />
      <div className="main-content">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/stanze" element={<StanzePage />} />
          <Route path="/prenotazioni" element={user ? <PrenotazioniPage /> : <Navigate to="/" />} />
          <Route path="/contatti" element={<ContattiPage />} />
        </Routes>
      </div>
      <Footer />
      {modalOpen && (
        <AuthModal onClose={handleCloseModal} onSuccess={handleSuccessLogin} />
      )}
    </>
  );
};

const App = () => {
  return (
    <AuthProvider>
      <Router>
        <AppContent />
      </Router>
    </AuthProvider>
  );
};

export default App;
