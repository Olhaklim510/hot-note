create TABLE IF NOT EXISTS note (
    id VARCHAR (36) PRIMARY KEY,
    owner VARCHAR (50) NOT NULL,
    title VARCHAR (100) CHECK (LENGTH(title) >= 5) NOT NULL,
    content VARCHAR (10000) CHECK (LENGTH(content) >= 5) NOT NULL,
    note_type VARCHAR (20) CHECK (note_type IN ('PRIVATE', 'PUBLIC')) NOT NULL,
    FOREIGN KEY (owner) REFERENCES users (username)
);