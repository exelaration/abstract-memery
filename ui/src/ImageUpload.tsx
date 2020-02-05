import * as React from 'react';

class ImageUpload extends React.Component {

      handleSubmit(e: any) {
        e.preventDefault();
        const data = new FormData();
        data.append('image', e.target.file);
        fetch('', {
            method: 'POST',
            body: data,
        });
      }
    
      render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <input  type="file" id="image" name="image"/>
                    <button>Upload Image</button>
                </form>
            </div>
        );
      }
}
export default ImageUpload;