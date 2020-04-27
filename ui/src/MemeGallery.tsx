import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MemeGallery.css";
import Button from "react-bootstrap/Button";
import { useCookies } from "react-cookie";
import { useHistory } from "react-router-dom";

function MemeGallery(props: any) {
  const [memeResponses, setMemeResponses] = useState([]);
  const [cookies] = useCookies(["userToken"]);
  const history = useHistory();

  useEffect(() => {
    function getMemeURLs() {
      axios.get("http://localhost:8080/meme").then((response) => {
        const memeResponses = response.data;
        setMemeResponses(memeResponses);
      });
    }
    getMemeURLs();
  }, [props.match.params.id]);

  function goToCreateMeme() {
    if (cookies.userToken === "not logged in") {
      alert("You must be logged in to access this page");
      history.push("/login");
    } else {
      history.push("/create-meme");
    }
  }

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
        <Button type="button" onClick={goToCreateMeme} variant="primary">
          Meme Creation
        </Button>
        <ul>{memes}</ul>
      </header>
    </div>
  );
}

export default MemeGallery;
