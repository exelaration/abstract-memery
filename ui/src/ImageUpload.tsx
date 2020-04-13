import React, { useContext, useState } from "react";
import axios from "axios";
import { AppDispatchContext, AppActions } from "./reducers/AppDispatchReducer";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import "./ImageUpload.css";
import MemeContent from "./MemeContent";

type ImageProps = {
  topText: string;
  bottomText: string;
  memeContentRef: React.RefObject<any>;
};

interface ImageResponse {
  fileData: string;
  fileName: string;
  id: number;
}

function ImageUpload(props: ImageProps) {
  const { dispatch } = useContext(AppDispatchContext);

  const [imageData, setImageData] = useState("");
  const [file, setFile] = useState<File | null>(null);

  function handleSubmit(e: any) {
    e.preventDefault();
    if (file != null) {
      const formData = new FormData();
      formData.append("file", file);
      axios
        .post<ImageResponse>("http://localhost:8080/upload/", formData)
        .then((response) => {
          dispatch({ type: AppActions.updateImageID, value: response.data.id });
          setImageData(response.data.fileData);
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }

  function onChangeHandler(e: any) {
    setFile(e.target.files[0]);
    dispatch({
      type: AppActions.updateMemeName,
      value: e.target.files[0].name,
    });
    dispatch({
      type: AppActions.updateMemeContentRef,
      value: props.memeContentRef,
    });
  }

  return (
    <div className="upload">
      <MemeContent
        image={"data:image/jpeg;base64," + imageData}
        memeContentRef={props.memeContentRef}
        bottomText={props.bottomText}
        topText={props.topText}
      />
      <Form onSubmit={handleSubmit}>
        <InputGroup>
          <Form.File
            id="custom-file"
            label="Choose file"
            onChange={onChangeHandler}
            name="image"
            custom
          />
          <InputGroup.Append>
            <Button type="submit" variant="primary" size="lg">
              Upload
            </Button>
          </InputGroup.Append>
        </InputGroup>
      </Form>
    </div>
  );
}
export default ImageUpload;
