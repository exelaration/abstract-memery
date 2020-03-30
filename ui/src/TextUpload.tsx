import React, { useContext, useState } from 'react';
import axios from 'axios';
import { AppActions, AppDispatchContext } from './reducers/AppDispatchReducer';
import './TextUpload.css'

type TextProps = {
    memeName: string
}

type TextState = {
    topText: String,
    bottomText: String,
    errMsg: String
}


function TextUpload(props: TextProps) {
    const {dispatch} = useContext(AppDispatchContext);

    const [topText, setTopText] = useState('');
    const [bottomText, setBottomText] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

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
            axios.post('http://localhost:8080/meme/', ({
                topText: topText, 
                bottomText: bottomText,
                memeName: props.memeName
            })).then(res => {
                console.log(res);
                console.log(res.data);
            })
        }
    }

    return (
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
    );
}

export default TextUpload;