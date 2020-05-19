import React, { useState } from "react";
import axios from "axios";
import { Button, Modal, Spinner } from "react-bootstrap";
import "./ExistingImages.css";

type ExistingImagesProps = {
  handleImageData: CallableFunction;
  handleImageId: CallableFunction;
};
interface ImageResponse {
  fileData: string;
  fileName: string;
  id: number;
}

function ExistingImages(props: ExistingImagesProps) {
  const [show, setShow] = useState(false);
  const [loading, setLoading] = useState(true);
  const [imageResponses, setImageResponses] = useState([]);

  const handleClose = () => setShow(false);
  const handleShow = () => {
    axios.get("http://localhost:8080/upload").then((response) => {
      const imageResponses = response.data;
      setImageResponses(imageResponses);
      setLoading(false);
    });
    setShow(true);
  };

  function imageClick(image: ImageResponse) {
    props.handleImageId(image["id"]);
    props.handleImageData(image["fileData"]);
    handleClose();
  }

  const loadingState = (
    <Spinner variant="primary" animation="border" role="status">
      <span className="sr-only">Loading...</span>
    </Spinner>
  );

  const images = imageResponses.map(function (image) {
    return (
      <div key={image["id"]}>
        {" "}
        <img
          className="modalImage"
          src={"data:image/jpeg;base64," + image["fileData"]}
          alt="Not Found"
          onClick={() => imageClick(image)}
        />
      </div>
    );
  });

  return (
    <>
      <Button variant="secondary" onClick={handleShow} size="lg">
        Browse Existing Images
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Modal.Title>Select An Image As A Meme Template</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {loading && loadingState}
          <ul>{images}</ul>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="outline-primary" onClick={handleClose}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
}

export default ExistingImages;
