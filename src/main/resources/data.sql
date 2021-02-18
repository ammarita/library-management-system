DROP TABLE IF EXISTS books;

CREATE TABLE books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(250) NOT NULL,
  author VARCHAR(250) NOT NULL,
  language VARCHAR(250) NOT NULL,
  publication_year YEAR NOT NULL,
  available BOOLEAN NOT NULL
);

INSERT INTO books (id, title, author, language, publication_year, available) VALUES
  ('1', 'Nordisk kokbok', 'Magnus Nilsson', 'Norwegian', '2017', 'false'),
  ('2', 'Grace Hopper: Queen of Computer Code', 'Laurie Wallmark', 'English', '2017', 'true'),
  ('3', 'A Mind for Numbers: How to Excel at Math and Science (Even If You Flunked Algebra)', 'Barbara Oakley', 'English', '2014', 'true'),
  ('4', 'Retinātā gaisā: Personisks vēstījums par traģēdiju Everesta nogāzē', 'Džons Krakauers', 'Latvian', '1998', 'true'),
  ('5', 'Vilko valanda', 'Andrius B. Tapinas', 'Lithuanian', '2013', 'false');