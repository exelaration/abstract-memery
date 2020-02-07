import React from 'react';
import logo from './logo.svg';
import './App.css';
import TextUpload from './TextUpload';

const App = () => {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <TextUpload />
      </header>
    </div>
  );
}

export default App;
