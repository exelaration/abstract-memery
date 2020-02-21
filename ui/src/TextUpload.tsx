import React from 'react';
import axios from 'axios';
import './TextUpload.css'

type TextProps = {
    sendTopText: CallableFunction,
    sendBottomText: CallableFunction
}

type TextState = {
    topText: String,
    bottomText: String,
    errMsg: String
}


class TextUpload extends React.Component<TextProps, TextState> {
    constructor(props: any) {
        super(props);
            this.state = { topText: '', bottomText: '', errMsg: '' };
        }
        topTextChangeHandler = (event: any) => {
            let val = event.target.value;
            this.setState({topText: val});
            this.props.sendTopText(val);
        }
        bottomTextChangeHandler = (event: any) => {
            let val = event.target.value;
            this.setState({bottomText: val});
            this.props.sendBottomText(val);
        }
        mySubmitHandler = (event: any) => {
            event.preventDefault();
            let topText = this.state.topText;
            let bottomText = this.state.bottomText;
            let err = '';
            if(topText.length === 0 && bottomText.length === 0){
                err = 'You must add either a top or bottom caption or both.';
                this.setState({errMsg: err});
            }
            else{
                axios.post('http://localhost:8080/meme/', ({
                    topText: topText, 
                    bottomText: bottomText
                })).then(res => {
                    console.log(res);
                    console.log(res.data);
                })
            }
        }
    render() {
        return (
            <form onSubmit={this.mySubmitHandler}>
                <p className='inputPrompt'>Enter your top text caption:</p>
                <input 
                    type='text'
                    name='topText'
                    placeholder="Top Text..."
                    onChange={this.topTextChangeHandler} />
                <p className="inputPrompt">Enter your bottom text caption:</p>
                <input 
                    type='text'
                    name='bottomText'
                    placeholder="Bottom Text..."
                    onChange={this.bottomTextChangeHandler} />
                <br/>
                {this.state.errMsg}
                <br/>
                <input className="button"
                    type='submit'
                />
            </form>
        );
    }
}

export default TextUpload;