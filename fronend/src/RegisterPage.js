import React, { useState } from 'react';
import './style.css';

function RegisterPage({ onSuccess, setPage }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleRegister = async () => {
        try {
            const response = await fetch('http://localhost:8080/auth/register', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });

            if (!response.ok) {
                throw new Error('Ошибка регистрации');
            }

            onSuccess();
        } catch (err) {
            setError(err.message);
        }
    };

    return React.createElement(
        'div',
        { className: 'form-container' },
        React.createElement('h2', null, 'Регистрация'),
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
        React.createElement('button', { onClick: handleRegister }, 'Зарегистрироваться'),
        error && React.createElement('p', { className: 'error' }, error),
        React.createElement(
            'p',
            { className: 'small-text' },
            'Уже есть аккаунт? ',
            React.createElement('span', {
                className: 'link',
                onClick: () => setPage('login')
            }, 'Войти')
        )
    );
}

export default RegisterPage;
