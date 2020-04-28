import React, { useReducer } from "react";
import {
  AppDispatchContext,
  AppDispatchReducer,
  initialState,
} from "./reducers/AppDispatchReducer";
import "./CreateMeme.css";
import MemeUpload from "./MemeUpload";
import ImageUpload from "./ImageUpload";
import NavBar from "./NavBar";

function CreateMeme() {
  const [appState, dispatch] = useReducer(AppDispatchReducer, initialState);
  const value = { state: appState, dispatch };


  return (
    <div className="Create">
      <NavBar></NavBar>
      <header className="Create-header">
        <h1 className="inputPrompt">Create Your Meme</h1>
        <div className="memeCreation">
          <AppDispatchContext.Provider value={value}>
            <ImageUpload
              topText={appState.topText}
              bottomText={appState.bottomText}
              memeContentRef={appState.memeContentRef}
            />
            <MemeUpload
              memeName={appState.memeName}
              imageID={appState.imageID}
              memeContentRef={appState.memeContentRef}
              memeResultRef={appState.memeResultRef}
            />
          </AppDispatchContext.Provider>
        </div>
      </header>
    </div>
  );
}

export default CreateMeme;
