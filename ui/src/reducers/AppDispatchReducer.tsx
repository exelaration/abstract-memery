import React from 'react';

export interface AppDispatchActions {
    type: string;
    value: string;
}

export interface AppState {
    topText: string;
    bottomText: string;
    memeName: string;
}

export const AppActions = {
    updateTopText: 'updateTopText',
    updateBottomText: 'updateBottomText',
    updateMemeName: 'updateMemeName'
}

export const initialState: AppState = {
    topText: '',
    bottomText: '',
    memeName: ''
}

export function AppDispatchReducer(state: AppState, action: AppDispatchActions) {
    switch (action.type) {
        case AppActions.updateTopText:
            return {...state, topText: action.value};
        case AppActions.updateBottomText:
            return {...state, bottomText: action.value};
        case AppActions.updateMemeName:
            return {...state, memeName: action.value};
        default:
            return state;
    }
}

interface AppContext {
    state: AppState;
    dispatch: React.Dispatch<AppDispatchActions>;
}
  
export const AppDispatchContext = React.createContext({} as AppContext);