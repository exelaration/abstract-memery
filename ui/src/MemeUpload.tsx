import React, { useContext, useState } from "react";
import axios from "axios";
import InputGroup from "react-bootstrap/InputGroup";
import Form from "react-bootstrap/Form";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import { AppActions, AppDispatchContext } from "./reducers/AppDispatchReducer";
import "./MemeUpload.css";
import MemeResult from "./MemeResult";
import domtoimage from "dom-to-image-more";
import { useHistory } from "react-router-dom";
import { useCookies } from "react-cookie";

type MemeProps = {
  memeName: String;
  imageID: number;
  memeContentRef: React.RefObject<any>;
  memeResultRef: React.RefObject<any>;
};

interface MemeResponse {
  id: number;
  memeName: string;
  memeUrl: string;
  topText: string;
  bottomText: string;
  imageId: number;
}

function MemeUpload(props: MemeProps) {
  const { dispatch } = useContext(AppDispatchContext);
  const [topText, setTopText] = useState("");
  const [bottomText, setBottomText] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [cookies] = useCookies(["userToken", "userId"]);

  const history = useHistory();

  function generateMeme() {
    return domtoimage.toPng(props.memeContentRef.current);
  }

  function topTextChangeHandler(event: any) {
    let val = event.target.value;
    setTopText(val);
    dispatch({ type: AppActions.updateTopText, value: val });
  }

  function bottomTextChangeHandler(event: any) {
    let val = event.target.value;
    setBottomText(val);
    dispatch({ type: AppActions.updateBottomText, value: val });
  }

  function formSubmitHandler(event: any) {
    event.preventDefault();
    let error = "";
    if (
      (topText.length === 0 && bottomText.length === 0) ||
      props.imageID === 0
    ) {
      error =
        "You must add either a top or bottom caption or both with an uploaded image.";
      setErrorMessage(error);
    } else {
      generateMeme().then((dataUrl: string) => {
        let timestamp = Date.now();
        axios
          .post<MemeResponse>(
            "http://localhost:8080/meme/",
            {
              topText: topText,
              bottomText: bottomText,
              memeName: timestamp,
              imageId: props.imageID,
              memeUrl: dataUrl,
            },
            { headers: { Authorization: cookies.userToken } }
          )
          .then((res) => {
            history.push(`/memes/${res.data.id}`);
          });
      });
    }
  }

  return (
    <div>
      <MemeResult memeResultRef={props.memeResultRef} />
      <Form onSubmit={formSubmitHandler}>
        <InputGroup>
          <InputGroup.Prepend>
            <InputGroup.Text>Top Text</InputGroup.Text>
          </InputGroup.Prepend>
          <FormControl
            type="text"
            name="topText"
            placeholder="Top Text..."
            onChange={topTextChangeHandler}
          />
        </InputGroup>
        <br />
        <InputGroup>
          <InputGroup.Prepend>
            <InputGroup.Text>Bottom Text</InputGroup.Text>
          </InputGroup.Prepend>
          <FormControl
            type="text"
            name="bottomText"
            placeholder="Bottom Text..."
            onChange={bottomTextChangeHandler}
          />
        </InputGroup>
        <br />
        {errorMessage}
        <br />
        <Button type="submit" variant="primary">
          Create Meme
        </Button>
      </Form>
    </div>
  );
}

export default MemeUpload;
