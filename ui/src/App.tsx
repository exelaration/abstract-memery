import React from 'react';
import {Route, Switch, BrowserRouter as Router } from "react-router-dom";
import RouterError from './RouterError';
import 'bootstrap/dist/css/bootstrap.min.css';
import CreateMeme from './CreateMeme';
import Login from './Login';
import SignUp from './SignUp';

function App() {

  return (
    <Router>
      <Switch>
        <Route path = "/" component = {CreateMeme} exact />
        <Route path = "/sign-up" component = {SignUp} exact/>
        <Route path = "/login" component = {Login} exact/>
        <Route component = {RouterError} />
      </Switch>
    </Router>
  );

}

export default App;