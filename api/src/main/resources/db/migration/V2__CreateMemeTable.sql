CREATE TABLE memes (
    ID SERIAL NOT NULL PRIMARY KEY,
    memeName TEXT UNIQUE,
    topText TEXT,
    bottomText TEXT
);