//lowercase c is cause we export a plain function from this file

//this function can automate context and provider setup for new objects
import React, { useReducer } from 'react';


export default (reducer, actions, initialState) => {
    const Context = React.createContext();

    const Provider = ({ children }) => {
        const [state, dispatch] = useReducer(reducer, initialState);
        
        //actions === { addPetPost: (dispatch) => {return () => {} }}
        const boundActions = {};
        for (let key in actions){
            //key === 'addPetPost:
            boundActions[key] = actions[key](dispatch);
        }

        return <Context.Provider value = {{ state: state, ...boundActions }}>
            {children}
        </Context.Provider>
    }

    return { Context, Provider };
};