import React from 'react';

class TextUpload extends React.Component {
    render() {
        return (
            <form>
                <h1>Upload Your Meme</h1>
                <p>Enter your top text caption:</p>
                <input type='text' />
                <p>Enter your bottom text caption:</p>
                <input type='text' />
            </form>
        );
    }
}

export default TextUpload;