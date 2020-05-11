import React, { useState, useEffect } from "react";
import axios from "axios";
import "./MemeGallery.css";
import NavBar from "./NavBar";
import { Pagination } from "react-bootstrap";

function MemeGallery(props: any) {
  const [memeResponses, setMemeResponses] = useState([]);
  const [page, setPage] = useState(0);
  const [memeCount, setMemeCount] = useState(0);

  function getPageCount() {
    let numberOfPages = Math.ceil(memeCount / 10);
    return numberOfPages;
  }

  function onPageClick(e: any) {
    setPage(e.target.text - 1);
  }

  function getMemeCount() {
    axios.get("http://localhost:8080/meme/count").then((response) => {
      const memeCount = response.data;
      setMemeCount(memeCount);
    });
  }

  useEffect(() => {
    function getMemeURLs() {
      axios.get("http://localhost:8080/meme/?page=" + page).then((response) => {
        const memeResponses = response.data;
        setMemeResponses(memeResponses);
      });
    }
    getMemeURLs();
    getMemeCount();
  }, [props.match.params.id, page]);

  let pageCount = getPageCount();
  let items = Array.from(Array(pageCount).keys(), (number) => {
    let numberKey = number + 1;
    return (
      <Pagination.Item
        key={numberKey}
        active={numberKey === page + 1}
        onClick={onPageClick}
      >
        {numberKey}
      </Pagination.Item>
    );
  });

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
      <NavBar></NavBar>
      <header className="gallery-header">
        <h1>Welcome to Abstract Memery</h1>
        <ul>{memes}</ul>
      </header>
      <Pagination className="pages">{items}</Pagination>
    </div>
  );
}

export default MemeGallery;
