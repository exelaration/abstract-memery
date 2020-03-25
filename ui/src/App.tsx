import React, { useReducer } from 'react';
import { AppDispatchContext, AppDispatchReducer, initialState } from './reducers/AppDispatchReducer';
import './App.css';
import MemeUpload from './MemeUpload';
import ImageUpload from './ImageUpload';

function App() {
  const [appState, dispatch] = useReducer(AppDispatchReducer, initialState);
  const value = {state: appState, dispatch};

  return (
    <div className="App">
      <header className="App-header">
        <h1 className="inputPrompt">Create Your Meme</h1>
        <div className="memeCreation">
          <AppDispatchContext.Provider value={value}>
            <ImageUpload topText={appState.topText} bottomText={appState.bottomText} memeContentRef={appState.memeContentRef}/>
            <MemeUpload memeName={appState.memeName} memeContentRef={appState.memeContentRef} memeResultRef={appState.memeResultRef}/>
          </AppDispatchContext.Provider>
        </div>
      </header>
    </div>
  );

}

export default App;