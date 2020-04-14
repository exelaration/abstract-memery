import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MemeGallery.css";
import Button from "react-bootstrap/Button";

function MemeGallery(props: any) {
  const [memeResponses, setMemeResponses] = useState([]);

  useEffect(() => {
    function getMemeURLs() {
      axios.get("http://localhost:8080/meme").then((response) => {
        const memeResponses = response.data;
        setMemeResponses(memeResponses);
      });
    }
    getMemeURLs();
  }, [props.match.params.id]);

  const memes = memeResponses.map(function (meme) {
    return (
      <div key={meme["id"]}>
        {" "}
        <a href={"/memes/" + meme["id"]}>
          <img
            src={"data:image/jpeg;base64," + meme["memeUrl"]}
            alt="Meme Not Found"
          />
        </a>{" "}
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
