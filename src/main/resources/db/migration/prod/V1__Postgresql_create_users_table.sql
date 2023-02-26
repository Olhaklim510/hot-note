CREATE TABLE IF NOT EXISTS users(
    id SERIAL PRIMARY KEY,
    username VARCHAR (50) UNIQUE CHECK (LENGTH(username) >= 5) NOT NULL,
    password VARCHAR (100) CHECK (LENGTH(password) >= 8) NOT NULL
    );