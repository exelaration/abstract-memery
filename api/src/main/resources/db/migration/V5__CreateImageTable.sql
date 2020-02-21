CREATE TABLE AbstractMemery.images (
    ID SERIAL NOT NULL PRIMARY KEY,
    memeID SERIAL REFERENCES AbstractMemery.memes(ID),
    fileLocation TEXT
);