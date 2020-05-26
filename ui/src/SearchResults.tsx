import React, { useState, useEffect } from "react";
import axios from "axios";
import NavBar from "./NavBar";
import "./SearchResults.css";

function SearchPage(props: any) {
  const [memeResponses, setMemeResponses] = useState([]);
  const [displayMessage, setDisplayMessage] = useState("");
  const [isValid, setIsValid] = useState(true);
  const [usernamesResponse, setUsernames] = useState([]);

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

  useEffect(() => {
    function getUsernames() {
      const response = axios
        .get("http://localhost:8080/users/?text=" + props.match.params.text)
        .then((response) => {
          const usernamesResponse = response.data;
          setUsernames(usernamesResponse);
        })
        .catch((error) => {
          setDisplayMessage("There are no Users with this search criteria");
        });
      return response;
    }
    getUsernames();
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
  }
  let memes;
  if (memeResponses.length === 0) {
    memes = <div>No image exists with this search criteria</div>;
  } else {
    memes = memeResponses.map(function (meme) {
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
  }

  let usernames;
  if (usernamesResponse.length === 0) {
    usernames = <div>No users exist with this search criteria</div>;
  } else {
    usernames = usernamesResponse.map(function (username) {
      return (
        <div>
          {" "}
          <h3>{username}</h3>
        </div>
      );
    });
  }

  return (
    <div className="search">
      <NavBar></NavBar>
      <header className="searchHeader">
        <h1>Search Results</h1>
        <h2>Users</h2>
        <ul>{usernames}</ul>
        <h2>Memes</h2>
        <ul>{memes}</ul>
      </header>
    </div>
  );
}
export default SearchPage;
