CREATE TABLE IF NOT EXISTS Memes (
    id SERIAL NOT NULL PRIMARY KEY,
    meme_name VARCHAR(200),
    top_text VARCHAR(200),
    bottom_text VARCHAR(200)
);