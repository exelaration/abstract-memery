import React from "react";

interface ContentInterface {
  image: string;
  topText: String;
  bottomText: String;
  memeContentRef: React.RefObject<any>;
}

const MemeContent = (props: ContentInterface) => {
  return (
    <div className="content" id="textWrapping" ref={props.memeContentRef}>
      <img id="image" src={props.image} alt="" />
      <p className="textFormatting" id="topText">
        {props.topText}
      </p>
      <p className="textFormatting" id="bottomText">
        {props.bottomText}
      </p>
    </div>
  );
};

export default MemeContent;
