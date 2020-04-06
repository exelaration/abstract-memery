import React from 'react';
import {Route, Switch, BrowserRouter as Router } from "react-router-dom";
import RouterError from './RouterError';
import 'bootstrap/dist/css/bootstrap.min.css';
import CreateMeme from './CreateMeme';
import SignUp from './SignUp';

function App() {

  return (
    <Router>
      <Switch>
        <Route path = "/" component = {CreateMeme} exact />
        <Route path = "/sign-up" component = {SignUp} exact/>
        <Route component = {RouterError} />
      </Switch>
    </Router>
  );

}

export default App;