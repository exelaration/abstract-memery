import React, { useReducer } from "react";
import {
  AppDispatchContext,
  AppDispatchReducer,
  initialState,
} from "./reducers/AppDispatchReducer";
import "./CreateMeme.css";
import MemeUpload from "./MemeUpload";
import ImageUpload from "./ImageUpload";
import Button from "react-bootstrap/Button";
import { useCookies } from "react-cookie";
import { useHistory } from "react-router-dom";

function CreateMeme() {
  const [appState, dispatch] = useReducer(AppDispatchReducer, initialState);
  const value = { state: appState, dispatch };
  // eslint-disable-next-line
  const [cookies, setCookie] = useCookies(["userToken"]);
  const history = useHistory();
  function onLogout() {
    setCookie("userToken", "not logged in", { path: "/" });
    history.push("/login");
  }

  return (
    <div className="Create">
      <header className="Create-header">
        <div className="row">
          <Button type="submit" href="/" variant="primary">
            Gallery
          </Button>
          <Button type="submit" href="/sign-up" variant="primary">
            Sign Up
          </Button>
          <Button type="submit" href="/login" variant="primary">
            Login
          </Button>
          <Button type="button" onClick={onLogout} variant="primary">
            Logout
          </Button>
        </div>
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
