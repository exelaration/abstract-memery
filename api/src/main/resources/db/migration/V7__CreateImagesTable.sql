CREATE TABLE IF NOT EXISTS Images (
    id SERIAL NOT NULL PRIMARY KEY,
    file_name VARCHAR(200),
    file_location VARCHAR(200)
    -- memes_id SERIAL REFERENCES memes (id)
);