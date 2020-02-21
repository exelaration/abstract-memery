import React from 'react';
import './App.css';
import TextUpload from './TextUpload';
import ImageUpload from './ImageUpload';

type Props = {
}

type State = {
  topText: String,
  bottomText: String,
}

class App extends React.Component<Props, State> {
  constructor(props: any) {
    super(props);
    this.state = {topText: '', bottomText: ''}
  }

  setTopText = (text: String) => {
    this.setState({topText: text});
  }

  setBottomText = (text: String) => {
    this.setState({bottomText: text});
  }

  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="inputPrompt">Create Your Meme</h1>
          <div className="memeCreation">
            <ImageUpload topText={this.state.topText} bottomText={this.state.bottomText}/>
            <TextUpload sendTopText={this.setTopText.bind(this)} sendBottomText={this.setBottomText.bind(this)}/>
          </div>
        </header>
      </div>
    );
  }
}

export default App;
