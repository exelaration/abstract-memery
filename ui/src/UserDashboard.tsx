import React, { useState, useEffect } from "react";
import axios from "axios";
import { useCookies } from "react-cookie";
import "./UserDashboard.css";
import NavBar from "./NavBar";

function UserDashboard(props: any) {
  const [memeResponses, setMemeResponses] = useState([]);
  const [memeResponseLength, setMemeResponseLength] = useState([]);
  const [cookies] = useCookies(["userToken", "userId"]);

  useEffect(() => {
    function getMemeURLs() {
      axios
        .get("http://localhost:8080/meme/?userId=" + cookies.userId, {
          headers: { Authorization: cookies.userToken },
        })
        .then((response) => {
          const memeResponses = response.data;
          setMemeResponses(memeResponses);
          setMemeResponseLength(memeResponses.length);
        });
    }
    getMemeURLs();
  }, [props.match.params.id, cookies.userToken, cookies.userId]);

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
        <h1>User Dashboard</h1>
        <hr />
        <h1>{memeResponseLength}</h1>
        <p>memes uploaded to date</p>
        <br />
        <h3>Memes Created</h3>
        <ul>{memes}</ul>
      </header>
    </div>
  );
}

export default UserDashboard;