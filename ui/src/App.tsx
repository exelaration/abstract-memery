import React from 'react';
import {Route, Switch, BrowserRouter as Router } from "react-router-dom";
import Upload from './CreateMeme';
import RouterError from './RouterError';

function App() {

  return (
    <Router>
      <Switch>
        <Route path = "/" component = {Upload} exact />
        <Route component = {RouterError} />
      </Switch>
    </Router>
  );

}

export default App;