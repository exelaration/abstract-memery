import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MemePage.css";
import NavBar from "./NavBar";
import { useCookies } from "react-cookie";
import { useHistory } from "react-router-dom";
import Button from "react-bootstrap/Button";

function MemePage(props: any) {
  const [imageData, setImageData] = useState("");
  const [displayMessage, setDisplayMessage] = useState("");
  const [cookies] = useCookies(["userToken"]);
  const history = useHistory();

  useEffect(() => {
    function getMeme() {
      const response = axios
        .get("http://localhost:8080/meme/?id=" + props.match.params.memeID)
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
  }, [props.match.params.memeID]);

  function goToCreateMeme() {
    if (cookies.userToken === "not logged in") {
      alert("You must be logged in to access this page");
      history.push("/login");
    } else {
      history.push("/create-meme");
    }
  }

  return (
    <div className="memepage">
      <NavBar></NavBar>
      <header className="memepageHeader">
        <h1>{displayMessage}</h1>
        <img src={"data:image/jpeg;base64," + imageData} alt="404 Not Found" />
        <Button onClick={goToCreateMeme}>
          Press here to make another meme!
        </Button>
      </header>
    </div>
  );
}

export default MemePage;
