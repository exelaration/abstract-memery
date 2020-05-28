import React, { useState, useEffect } from "react";
import axios from "axios";
import NavBar from "./NavBar";

function ProfilePage(props: any) {
  const [memeResponses, setMemeResponses] = useState([]);
  const [memeResponseLength, setMemeResponseLength] = useState([]);

  useEffect(() => {
    function getMemeURLs() {
      axios
        .get("http://localhost:8080/meme/?userId=" + props.match.params.username)
        .then((response) => {
          const memeResponses = response.data;
          setMemeResponses(memeResponses);
          setMemeResponseLength(memeResponses.length);
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
    <div className="dashboard">
      <NavBar></NavBar>
      <header className="dashboardHeader">
        <h1>{props.match.params.username}</h1>
        <br />
        <h3>Memes Created</h3>
        <ul>{memes}</ul>
      </header>
    </div>
  );
}

export default ProfilePage;
