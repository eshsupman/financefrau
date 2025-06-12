import React, { useState, useEffect } from 'react'
import './style.css';

import { Pie } from 'react-chartjs-2';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
ChartJS.register(ArcElement, Tooltip, Legend);

function MainPage({ setPage }) {
    const [expenses, setExpenses] = useState([]);
    const [amount, setAmount] = useState('');
    const [description, setDescription] = useState('');
    const [currency, setCurrency] = useState('RUB');
    const [category, setCategory] = useState('Продукты');
    const [error, setError] = useState('');
    const [targetCurrency, setTargetCurrency] = useState('USD');
    const [conversionResults, setConversionResults] = useState({});

    const categories = ['Продукты', 'Транспорт', 'Развлечения', 'Здоровье', 'Образование', 'Путешествия', 'Коммунальные услуги'];
    const currencySymbols = { 'RUB': '₽', 'USD': '$', 'EUR': '€' };

    const fetchExpenses = async () => {
        try {
            const response = await fetch('/expenses', {
                method: 'GET',
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
            });

            if (!response.ok) throw new Error('Ошибка при загрузке трат');

            const data = await response.json();
            setExpenses(data);
        } catch (err) {
            setError(err.message);
        }
    };

    useEffect(() => { fetchExpenses(); }, []);

    const calculateTodayTotal = () => {
        const today = new Date().toISOString().split('T')[0];
        const todayExpenses = expenses.filter(e => e.expenseDate === today);
        return todayExpenses.reduce((sum, e) => sum + e.amount, 0);
    };

    const handleAddExpense = async () => {
        try {
            const expenseDate = new Date().toISOString().split('T')[0];
            const response = await fetch('/expenses', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
                },
                body: JSON.stringify({ amount: parseFloat(amount), description, category, currency, expenseDate })
            });

            if (!response.ok) throw new Error('Ошибка при добавлении траты');

            setAmount('');
            setDescription('');
            setCategory('Продукты');
            setCurrency('RUB');
            fetchExpenses();
        } catch (err) { setError(err.message); }
    };

    const handleDeleteExpense = async (id) => {
        try {
            const response = await fetch(`/expenses/${id}`, {
                method: 'DELETE',
                headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
            });

            if (!response.ok) throw new Error('Ошибка при удалении траты');
            fetchExpenses();
        } catch (err) { setError(err.message); }
    };

    const calculateCategoryData = () => {
        const categoryMap = {};
        expenses.forEach(e => {
            categoryMap[e.category] = (categoryMap[e.category] || 0) + e.amount;
        });

        const labels = Object.keys(categoryMap);
        const data = Object.values(categoryMap);

        return {
            labels,
            datasets: [{
                data,
                backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', '#9966FF', '#FF9F40', '#E7E9ED']
            }]
        };
    };

    const updateConversions = async () => {
        const newResults = {};
        for (let exp of expenses) {
            if (exp.currency === targetCurrency) {
                newResults[exp.id] = exp.amount;
                continue;
            }

            const response = await fetch('/cnv', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    from: exp.currency,
                    to: targetCurrency,
                    amount: exp.amount
                })
            });
            const data = await response.json();
            newResults[exp.id] = Number(data).toFixed(2);
        }
        setConversionResults(newResults);
    };
    const handleLogout = () => {
        localStorage.removeItem('token');
        setPage('login');
    };

    useEffect(() => {
        if (expenses.length > 0) updateConversions();
    }, [expenses, targetCurrency]);

    return React.createElement('div', { className: 'form-container' },
        React.createElement('h2', null, 'Ваши траты'),

        // Секция с траты + конверсией
        React.createElement('div', { style: { display: 'flex', gap: '30px' } },

            React.createElement('div', { style: { flex: 1 } },React.createElement('h3', null, `Траты за сегодня`),
                expenses.map(e =>
                    React.createElement('div', { key: e.id, className: 'expense-item' },
                        React.createElement('button', {
                            className: 'delete-btn',
                            onClick: () => handleDeleteExpense(e.id)
                        }, '❌'),
                        `${e.description}: ${e.amount} ${currencySymbols[e.currency]} [${e.category}]`
                    )
                )

            ),
            React.createElement('div', { style: { flex: 1 } },
                React.createElement('h3', null, `Конвертация в ${targetCurrency}`),
                expenses.map(e => React.createElement('div', { key: e.id, className: 'expense-item' },
                    conversionResults[e.id] ?
                        `${conversionResults[e.id]} ${currencySymbols[targetCurrency]}` :
                        'Загрузка...'
                )),
                React.createElement('select', {
                        value: targetCurrency,
                        onChange: e => setTargetCurrency(e.target.value)
                    },
                    React.createElement('option', { value: 'RUB' }, 'RUB'),
                    React.createElement('option', { value: 'USD' }, 'USD'),
                    React.createElement('option', { value: 'EUR' }, 'EUR')
                )
            )
        ),

        React.createElement('h3', null, 'Добавить трату'),
        React.createElement('input', { type: 'number', placeholder: 'Сумма', value: amount, onChange: e => setAmount(e.target.value) }),
        React.createElement('input', { type: 'text', placeholder: 'Описание', value: description, onChange: e => setDescription(e.target.value) }),
        React.createElement('select', { value: category, onChange: e => setCategory(e.target.value) },
            categories.map(cat => React.createElement('option', { key: cat, value: cat }, cat))
        ),
        React.createElement('select', { value: currency, onChange: e => setCurrency(e.target.value) },
            React.createElement('option', { value: 'RUB' }, 'RUB'),
            React.createElement('option', { value: 'USD' }, 'USD'),
            React.createElement('option', { value: 'EUR' }, 'EUR')
        ),
        React.createElement('button', { onClick: handleAddExpense }, 'Добавить'),

        error && React.createElement('p', { className: 'error' }, error),
        React.createElement('h3', null, `Сегодня потрачено: ${calculateTodayTotal()} ₽`),
        React.createElement('div', { className: 'chart-container' }, React.createElement(Pie, { data: calculateCategoryData() })),
        React.createElement('button', { onClick: handleLogout, className: 'logout-btn' }, 'Выйти')
    );
}

export default MainPage;
