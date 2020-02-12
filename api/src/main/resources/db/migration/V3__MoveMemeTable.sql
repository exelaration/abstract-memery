DROP TABLE memes;

CREATE TABLE AbstractMemery.memes(
    ID SERIAL NOT NULL PRIMARY KEY,
    memeName VARCHAR(200) UNIQUE,
    topText VARCHAR(200),
    bottomText VARCHAR(200)
);
