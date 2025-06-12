import React, { useState, useEffect } from 'react';
import LoginPage from './LoginPage';
import RegisterPage from './RegisterPage';
import MainPage from './MainPage';
import './style.css';

function App() {
  const [page, setPage] = useState('login');
  const [successMessage, setSuccessMessage] = useState('');

  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setPage('main');
    }
  }, []);

  const handleLoginSuccess = () => {
    setPage('main');
  };

  const handleRegistrationSuccess = () => {
    setSuccessMessage('Вы успешно зарегистрированы. Теперь можете войти.');
    setPage('login');
  };

  return React.createElement(
      'div',
      null,
      page === 'login' && React.createElement(LoginPage, { setPage, successMessage, onLoginSuccess: handleLoginSuccess }),
      page === 'register' && React.createElement(RegisterPage, { onSuccess: handleRegistrationSuccess, setPage }),
      page === 'main' && React.createElement(MainPage, { setPage })
  );
}

export default App;
