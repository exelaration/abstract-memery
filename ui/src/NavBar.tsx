import React, { useState } from "react";
import {
  Nav,
  Navbar,
  NavDropdown,
  Form,
  FormControl,
  Button,
} from "react-bootstrap";
import { useHistory } from "react-router-dom";
import { useCookies } from "react-cookie";

function NavBar() {
  const [searchCriteria, setSearchCriteria] = useState("");
  const history = useHistory();
  const [cookies, setCookie] = useCookies(["userToken", "username"]);

  let displayUser =
    cookies.username !== undefined && cookies.username !== "username"
      ? cookies.username
      : "Login";

  function submitHandler(event: any) {
    event.preventDefault();
    if (searchCriteria !== "") {
      history.push("/search/" + searchCriteria);
    }
  }
  function onChangeHandler(e: any) {
    setSearchCriteria(e.target.value);
  }

  function onLogout() {
    setCookie("userToken", "not logged in", { path: "/" });
    setCookie("username", "username", { path: "/" });
    history.push("/login");
  }

  function goToCreateMeme() {
    if (cookies.userToken === "not logged in") {
      alert("You must be logged in to access this page");
      history.push("/login");
    } else {
      history.push("/create-meme");
    }
  }

  function goToSettings() {
    if (cookies.userToken === "not logged in") {
      alert("You must be logged in to access this page");
      history.push("/login");
    } else {
      history.push("/user/settings");
    }
  }

  return (
    <Navbar bg="white" expand="lg">
      <Navbar.Brand href="/">Abstract Memery</Navbar.Brand>
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="basic-navbar-nav">
        <Nav className="mr-auto">
          <Nav.Link href="/">Meme Gallery</Nav.Link>
          <Nav.Link onClick={goToCreateMeme}>Create a Meme</Nav.Link>
        </Nav>

        <Form inline onSubmit={submitHandler}>
          <FormControl
            type="text"
            placeholder="Search For Memes"
            className="mr-sm-2"
            onChange={onChangeHandler}
          />
          <Button type="submit" variant="primary">
            Search
          </Button>
        </Form>

        <NavDropdown title={displayUser} id="basic-nav-dropdown">
          <NavDropdown.Item href="/login">Login</NavDropdown.Item>
          <NavDropdown.Item href="/sign-up">Sign-Up</NavDropdown.Item>
          <NavDropdown.Item onClick={onLogout}>Log-Out</NavDropdown.Item>
          <NavDropdown.Item onClick={goToSettings}>Settings</NavDropdown.Item>
        </NavDropdown>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default NavBar;
