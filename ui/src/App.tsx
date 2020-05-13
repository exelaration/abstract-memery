import React from "react";
import { Route, Switch, BrowserRouter as Router } from "react-router-dom";
import RouterError from "./RouterError";
import "bootstrap/dist/css/bootstrap.min.css";
import CreateMeme from "./CreateMeme";
import MemePage from "./MemePage";
import Login from "./Login";
import SignUp from "./SignUp";
import Gallery from "./MemeGallery";
import SearchResults from "./SearchResults";
import UserSettings from "./UserSettings";
import UserDashboard from "./UserDashboard";

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/" component={Gallery} exact />
        <Route path="/sign-up" component={SignUp} exact />
        <Route path="/login" component={Login} exact />
        <Route path="/memes/:memeID" component={MemePage} exact />
        <Route path="/create-meme" component={CreateMeme} exact />
        <Route path="/user-dashboard" component={UserDashboard} exact />
        <Route path="/search/:text" component={SearchResults} exact />
        <Route path="/user/settings" component={UserSettings} exact />
        <Route component={RouterError} />
      </Switch>
    </Router>
  );
}

export default App;
