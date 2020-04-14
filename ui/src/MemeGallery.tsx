import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MemeGallery.css";
import Button from "react-bootstrap/Button";

function MemeGallery(props: any) {
  const [memeURLs, setMemeURLs] = useState([]);

  useEffect(() => {
    function getMemeURLs() {
      axios.get("http://localhost:8080/meme").then((response) => {
        const memeURLs = response.data;
        setMemeURLs(memeURLs);
      });
    }
    getMemeURLs();
  }, [props.match.params.id]);

  const memes = memeURLs.map(function (meme) {
    return (
      <div key={meme}>
        {" "}
        <img src={"data:image/jpeg;base64," + meme} alt="Meme Not Found" />{" "}
      </div>
    );
  });
  return (
    <div className="gallery">
      <header className="gallery-header">
        <h1>Welcome to Abstract Memery</h1>
        <Button type="submit" href="/create-meme" variant="primary">
          Meme Creation
        </Button>
        <ul>{memes}</ul>
      </header>
    </div>
  );
}

export default MemeGallery;
