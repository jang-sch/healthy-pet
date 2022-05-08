import React, {useContext} from 'react';
import { StyleSheet } from 'react-native';
import { Context } from '../context/PetContext';
import PetPostForm from '../components/PetPostForm';
//controlled input
const CreatePet = ( { navigation } ) => {
    
    const { addPetPost } = useContext(Context);

    return <PetPostForm onSubmit={(title, content) => {
        addPetPost(title, content, () => navigation.navigate('Index'))
    }} />;
    
};

const styles = StyleSheet.create({
   
});

export default CreatePet;