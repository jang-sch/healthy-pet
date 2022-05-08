import React, { useContext } from 'react';
import { StyleSheet}  from 'react-native';
import { Context } from '../context/PetContext';
import PetPostForm from '../components/PetPostForm';

const EditScreen = ( { navigation } ) => {
    const id = navigation.getParam('id');
    const { state, editPetPost } = useContext(Context);

    const petPost = state.find(
        petPost => petPost.id === id
    );

    return (
        <PetPostForm  
            initialValues={{ title: petPost.title, content: petPost.content}}
            onSubmit={( title, content) => {
            editPetPost(id, title, content, () => navigation.pop());

            }}
        />
    );
};

const styles = StyleSheet.create({});

export default EditScreen;

