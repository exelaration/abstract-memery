import React from "react";

export interface AppDispatchActions {
  type: string;
  value: any;
}

export interface AppState {
  topText: string;
  bottomText: string;
  memeName: string;
  dataUrl: string;
  imageID: number;
  memeContentRef: React.RefObject<any>;
  memeResultRef: React.RefObject<any>;
}

export const AppActions = {
  updateTopText: "updateTopText",
  updateBottomText: "updateBottomText",
  updateMemeName: "updateMemeName",
  updateDataUrl: "updateDataUrl",
  updateImageID: "updateImageID",
  updateMemeContentRef: "updateMemeContent",
  updateMemeResultRef: "updateMemeResult",
};

export const initialState: AppState = {
  topText: "",
  bottomText: "",
  memeName: "",
  dataUrl: "",
  imageID: 0,
  memeContentRef: React.createRef<HTMLElement | null>(),
  memeResultRef: React.createRef<HTMLElement | null>(),
};

export function AppDispatchReducer(
  state: AppState,
  action: AppDispatchActions
) {
  switch (action.type) {
    case AppActions.updateTopText:
      return { ...state, topText: action.value };
    case AppActions.updateBottomText:
      return { ...state, bottomText: action.value };
    case AppActions.updateMemeName:
      return { ...state, memeName: action.value };
    case AppActions.updateDataUrl:
      return {...state, dataUrl: action.value};
    case AppActions.updateImageID:
      return { ...state, imageID: action.value };
    case AppActions.updateMemeContentRef:
      return { ...state, memeContentRef: action.value };
    case AppActions.updateMemeResultRef:
      return { ...state, memeResultRef: action.value };
    default:
      return state;
  }
}

interface AppContext {
  state: AppState;
  dispatch: React.Dispatch<AppDispatchActions>;
}

export const AppDispatchContext = React.createContext({} as AppContext);
