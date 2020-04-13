import React from "react";

interface ResultInterface {
  memeResultRef: React.RefObject<any>;
}

const MemeResult = (props: ResultInterface) => {
  return <div ref={props.memeResultRef} className="result"></div>;
};
export default MemeResult;
