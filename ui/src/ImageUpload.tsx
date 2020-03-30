import React, { useContext, useState } from 'react';
import axios from 'axios';
import { AppDispatchContext, AppActions } from './reducers/AppDispatchReducer';
import './ImageUpload.css';

type ImageProps = {
    topText: string,
    bottomText: string
}

interface ImageResponse {
    fileData: string,
    fileName: string
}

function ImageUpload(props: ImageProps) {
    const {dispatch} = useContext(AppDispatchContext);

    const [imageData, setImageData] = useState('');
    const [file, setFile] = useState<File | null>(null);

    function handleSubmit(e: any) {
        e.preventDefault();
        if (file != null) {
            const formData = new FormData();
            formData.append('file', file);
            axios.post<ImageResponse>('http://localhost:8080/upload/', formData)
                .then((response) => {
                    setImageData(response.data.fileData);
                })
                .catch((error) => {
                    console.log(error)
                });
        }
    }
      
    function onChangeHandler(e: any) {
        setFile(e.target.files[0]);
        dispatch({type: AppActions.updateMemeName, value: e.target.files[0].name});
    }

    return (
        <div className='upload'>
            <div id="textWrapping">
                <img id="image" src={"data:image/jpeg;base64," + imageData} alt=""></img>
                <p className='textFormatting' id="topText">{props.topText}</p>
                <p className='textFormatting' id="bottomText">{props.bottomText}</p>
            </div>
            <form onSubmit={handleSubmit}>
                <label className="button" htmlFor="browsePhoto">Browse...</label>
                <input type="file" name="image" className="button" id="browsePhoto" onChange={onChangeHandler}/>
                <button className="button" type="submit">Upload Image</button>
            </form>
        </div>
    );
}
export default ImageUpload;