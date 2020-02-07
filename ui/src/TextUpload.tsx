import React from 'react';
import axios from 'axios';

class TextUpload extends React.Component<any, any> {
    constructor(props: any) {
        super(props);
            this.state = { topText: '', bottomText: '', errMsg: '' };
        }
        myChangeHandler = (event: any) => {
            let nam = event.target.name;
            let val = event.target.value;
            this.setState({[nam]: val});
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
                axios.post('http://localhost:8080/', ({
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
                    onChange={this.myChangeHandler} />
                <p>Enter your bottom text caption:</p>
                <input 
                    type='text'
                    name='bottomText'
                    onChange={this.myChangeHandler} />
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