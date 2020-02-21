import React from 'react';
import axios from 'axios';

type TextProps = {}

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
        }
        bottomTextChangeHandler = (event: any) => {
            let val = event.target.value;
            this.setState({bottomText: val});
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
                <h1>Upload Your Meme</h1>
                <p>Enter your top text caption:</p>
                <input 
                    type='text'
                    name='topText'
                    onChange={this.topTextChangeHandler} />
                <p>Enter your bottom text caption:</p>
                <input 
                    type='text'
                    name='bottomText'
                    onChange={this.bottomTextChangeHandler} />
                <br/>
                {this.state.errMsg}
                <br/>
                <input
                    type='submit'
                />
            </form>
        );
    }
}

export default TextUpload;