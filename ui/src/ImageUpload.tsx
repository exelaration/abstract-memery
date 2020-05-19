import React, { useContext, useState } from "react";
import axios from "axios";
import { AppDispatchContext, AppActions } from "./reducers/AppDispatchReducer";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import "./ImageUpload.css";
import MemeContent from "./MemeContent";
import { useCookies } from "react-cookie";
import ExistingImages from "./ExistingImages";

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
  const [cookies] = useCookies(["userToken"]);

  function handleSubmit(e: any) {
    e.preventDefault();
    if (file != null) {
      const formData = new FormData();
      formData.append("file", file);
      axios
        .post<ImageResponse>("http://localhost:8080/upload/", formData, {
          headers: { Authorization: cookies.userToken },
        })
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
    if (isImage(e.target.files[0])) {
      setFile(e.target.files[0]);
      dispatch({
        type: AppActions.updateMemeName,
        value: e.target.files[0].name,
      });
      dispatch({
        type: AppActions.updateMemeContentRef,
        value: props.memeContentRef,
      });
    } else {
      alert("Please upload an image file");
    }
  }

  function isImage(file: File) {
    return file["type"].split("/")[0] === "image"; //returns true or false
  }

  function sendImageData(imageData: string) {
    setImageData(imageData);
  }

  function sendImageId(id: number) {
    dispatch({ type: AppActions.updateImageID, value: id });
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
            accept="image/*"
            custom
          />
          <InputGroup.Append>
            <Button type="submit" variant="primary" size="lg">
              Upload
            </Button>
          </InputGroup.Append>
        </InputGroup>
        <ExistingImages
          handleImageData={sendImageData}
          handleImageId={sendImageId}
        ></ExistingImages>
      </Form>
    </div>
  );
}
export default ImageUpload;
