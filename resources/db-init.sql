DROP TABLE IF EXISTS books;
CREATE TABLE books (
  id INTEGER PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255) NOT NULL,
  genre VARCHAR(255) NOT NULL,
  price DECIMAL(5,2) NOT NULL,
  stock INTEGER NOT NULL
)
