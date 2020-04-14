import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MemePage.css";

function MemePage(props: any) {
  const [imageData, setImageData] = useState("");
  const [displayMessage, setDisplayMessage] = useState("");

  useEffect(() => {
    function getMeme() {
      const response = axios
        .get("http://localhost:8080/meme/?id=" + props.match.params.imageID)
        .then((res) => {
          setImageData(res.data);
          setDisplayMessage("Share Your Meme");
        })
        .catch((error) => {
          setDisplayMessage("Image does not exist");
        });
      return response;
    }
    getMeme();
  }, [props.match.params.imageID]);

  return (
    <div className="memepage">
      <header className="memepageHeader">
        <h1>{displayMessage}</h1>
        <img src={"data:image/jpeg;base64," + imageData} alt="404 Not Found" />
      </header>
    </div>
  );
}

export default MemePage;
