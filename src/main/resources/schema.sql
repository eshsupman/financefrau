create table users
(
    id       serial
        primary key,
    username varchar(255),
    password varchar(255)
);

create table expenses
(
    id           serial
        primary key,
    user_id      integer        not null
        references users,
    amount       numeric(10, 2) not null,
    category     varchar(255),
    description  text,
    expense_date date           not null,
    created_at   timestamp default now(),
    currency     varchar(3)
);
