import React, { useContext, useState } from 'react';
import axios from 'axios';
import { AppActions, AppDispatchContext } from './reducers/AppDispatchReducer';
import './MemeUpload.css';
import MemeResult from './MemeResult';
import domtoimage from 'dom-to-image-more';

type MemeProps = {
    memeName: String,
    imageID: number,
    memeContentRef: React.RefObject<any>
    memeResultRef: React.RefObject<any>
}

function MemeUpload(props: MemeProps) {
    const {dispatch} = useContext(AppDispatchContext);
    const [topText, setTopText] = useState('');
    const [bottomText, setBottomText] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    function generateMeme() {
        return domtoimage.toPng(props.memeContentRef.current);
    }

    function topTextChangeHandler(event: any) {
        let val = event.target.value;
        setTopText(val);
        dispatch({type: AppActions.updateTopText, value: val});
    }

    function bottomTextChangeHandler(event: any) {
        let val = event.target.value;
        setBottomText(val);
        dispatch({type: AppActions.updateBottomText, value: val});
    }

    function formSubmitHandler(event: any) {
        event.preventDefault();
        let error = '';
        if(topText.length === 0 && bottomText.length === 0){
            error = 'You must add either a top or bottom caption or both.';
            setErrorMessage(error);
        }
        else{
            generateMeme().then((dataUrl: string) => {
                let timestamp = Date.now()
                axios.post('http://localhost:8080/meme/', ({
                    topText: topText, 
                    bottomText: bottomText,
                    memeName: timestamp,
                    imageId: props.imageID,
                    memeUrl: dataUrl
                })).then(res => {
                    console.log(res);
                })
            });
        }
    }

    return (
        <div>
            <MemeResult memeResultRef={props.memeResultRef} />
            <form onSubmit={formSubmitHandler}>
                <p className='inputPrompt'>Enter your top text caption:</p>
                <input 
                    type='text'
                    name='topText'
                    placeholder="Top Text..."
                    onChange={topTextChangeHandler} />
                <p className="inputPrompt">Enter your bottom text caption:</p>
                <input 
                    type='text'
                    name='bottomText'
                    placeholder="Bottom Text..."
                    onChange={bottomTextChangeHandler} />
                <br/>
                {errorMessage}
                <br/>
                <input className="button"
                    type='submit'
                />
            </form>
        </div>
    );
}

export default MemeUpload;