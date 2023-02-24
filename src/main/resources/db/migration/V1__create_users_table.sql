CREATE TABLE IF NOT EXISTS Users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE,
    password VARCHAR(100),
    CONSTRAINT user_name_length_ck CHECK (LENGTH(username) >= 5),
    CONSTRAINT password_length_ck CHECK (LENGTH(password) >= 8)
    );