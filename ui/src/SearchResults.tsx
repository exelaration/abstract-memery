import React, { useState, useEffect } from "react";
import axios from "axios";
import NavBar from "./NavBar";
import "./SearchResults.css";

function SearchPage(props: any) {
  const [memeResponses, setMemeResponses] = useState([]);
  const [displayMessage, setDisplayMessage] = useState("");
  const [isValid, setIsValid] = useState(true);

  useEffect(() => {
    function getSearchURLs() {
      const response = axios
        .get("http://localhost:8080/meme/?text=" + props.match.params.text)
        .then((response) => {
          const memeResponses = response.data;
          if (memeResponses) {
            setIsValid(true);
            setMemeResponses(memeResponses);
          } else {
            setIsValid(false);
          }
        })
        .catch((error) => {
          setDisplayMessage("There are no images with this search criteria");
        });
      return response;
    }
    getSearchURLs();
  }, [props.match.params.text]);

  if (!isValid) {
    return (
      <div className="search">
        <NavBar></NavBar>
        <header className="searchHeader">
          <h1>Search Results</h1>
          <div>Search query must contain more than one character</div>
        </header>
      </div>
    );
  } else if (memeResponses.length === 0) {
    return (
      <div className="search">
        <NavBar></NavBar>
        <header className="searchHeader">
          <h1>Search Results</h1>
          <div>There are no images with this search criteria</div>
        </header>
      </div>
    );
  } else {
    const memes = memeResponses.map(function (meme) {
      return (
        <div key={meme["id"]}>
          {" "}
          <a href={"/memes/" + meme["id"]}>
            <img
              src={"data:image/jpeg;base64," + meme["memeUrl"]}
              alt={displayMessage}
            />{" "}
          </a>
        </div>
      );
    });

    return (
      <div className="search">
        <NavBar></NavBar>
        <header className="searchHeader">
          <h1>Search Results</h1>
          <ul>{memes}</ul>
        </header>
      </div>
    );
  }
}
export default SearchPage;
