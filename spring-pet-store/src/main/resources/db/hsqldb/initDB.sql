DROP TABLE pets IF EXISTS;

CREATE TABLE pets (
  id         INTEGER IDENTITY PRIMARY KEY,
  name       VARCHAR(30),
  birth_date DATE,
  race		 VARCHAR(50),
  gender	 VARCHAR(1),
  deleted	 BOOLEAN DEFAULT FALSE
);

CREATE INDEX pets_id ON pets (id);
