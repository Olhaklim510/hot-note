CREATE TABLE note (
    id IDENTITY PRIMARY KEY,
    title VARCHAR (100) CHECK (LENGTH(title) >= 5) NOT NULL,
    content VARCHAR (10000) CHECK (LENGTH(content) >= 5) NOT NULL
);