import createDataContext from './createDataContext';
import jsonServer from '../api/jsonServer';
//reducer by convention gets state and action params
const petReducer = (state, action) => {
    switch (action.type) {
        case 'get_petposts':
            return action.payload;

        case 'edit_petpost':
            return state.map(petPost => {
                return petPost.id === action.payload.id ? action.payload : petPost;
        });
    
        case 'delete_petpost':
            return state.filter((petPost) => petPost.id !== action.payload);

        case 'add_petpost':
            //take all new petposts and add to an array
            return [...state, 
                { id: Math.floor(Math.random() * 99999),
                    title: action.payload.title,
                    content: action.payload.content
                }
            ];
        default: 
            return state;
    }
};

const getPetPosts = dispatch => {
    return async () => {
        const response = await jsonServer.get('/petposts');

        dispatch({type: 'get_petposts', payload: response.data});
    };
};

const addPetPost = (dispatch) => {
    //this gets title and content from createpet
    return async (title, content, callback) => {

        await jsonServer.post('/petposts', {title, content});
        //dispatch ({ type: 'add_petpost', payload: {title: title, content: content} });
        if(callback){
            callback();
        }
    };
};
const deletePetPost = dispatch => {
    return (id) => {
        //convention is to use type and payload. the actual names dont matter
        dispatch({type: 'delete_petpost', payload: id})
    };
};

const editPetPost = dispatch => {
    return (id, title, content, callback) => {
        dispatch({
            type: 'edit_petpost', 
            payload: { id, title, content }
        });
        if (callback){
            callback();
        }

    };
};

export const { Context, Provider } = createDataContext( 
    petReducer, 
    { addPetPost, deletePetPost, editPetPost, getPetPosts }, 
    []
);


    
    


    

    



