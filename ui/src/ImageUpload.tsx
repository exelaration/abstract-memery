import * as React from 'react';
import axios from 'axios';

type ImageProps = {}

type ImageState = {
    imageData: String,
    file: File | null
}

interface ImageResponse {
    fileData: String,
    fileName: String
}

class ImageUpload extends React.Component<ImageProps, ImageState> {
    constructor(props: any) {
        super(props);
            this.state = { imageData: "", file: null};
        }

    handleSubmit = (e: any) => {
        e.preventDefault();
        if (this.state.file != null) {
            const formData = new FormData();
            formData.append('file',this.state.file);
            axios.post<ImageResponse>('http://localhost:8080/upload/', formData)
                .then((response) => {
                    this.setState({imageData: response.data.fileData});
                }).catch((error) => {
                    console.log(error)});
        }
      }
      onChangeHandler = (e: any) => {
        this.setState({file: e.target.files[0]});
    }
      render() {
        return (
            <div>
                <img src={"data:image/jpeg;base64," + this.state.imageData} alt=""></img>
                <form onSubmit={this.handleSubmit}>
                    <input  type="file" id="image" name="image" onChange={this.onChangeHandler}/>
                    <button type="submit">Upload Image</button>
                </form>
            </div>
        );
      }
}
export default ImageUpload;