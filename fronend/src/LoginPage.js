import React, { useState } from 'react';
import './style.css';

function LoginPage({ setPage, successMessage, onLoginSuccess }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleLogin = async () => {
        try {
            const response = await fetch('http://localhost:8080/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                throw new Error('Неверный логин или пароль');
            }

            const data = await response.json();
            localStorage.setItem('token', data.token);
            onLoginSuccess();
        } catch (err) {
            setError(err.message);
        }
    };

    return React.createElement(
        'div',
        { className: 'form-container' },
        React.createElement('h2', null, 'Вход'),
        successMessage && React.createElement('p', { className: 'success' }, successMessage),
        React.createElement('input', {
            type: 'text',
            placeholder: 'Логин',
            value: username,
            onChange: e => setUsername(e.target.value)
        }),
        React.createElement('input', {
            type: 'password',
            placeholder: 'Пароль',
            value: password,
            onChange: e => setPassword(e.target.value)
        }),
        React.createElement('button', { onClick: handleLogin }, 'Войти'),
        error && React.createElement('p', { className: 'error' }, error),
        React.createElement(
            'p',
            { className: 'small-text' },
            'Нет аккаунта? ',
            React.createElement('span', {
                className: 'link',
                onClick: () => setPage('register')
            }, 'Зарегистрироваться')
        )
    );
}

export default LoginPage;
