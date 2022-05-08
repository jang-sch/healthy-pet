import React, { useState } from 'react';
import {View, Text, StyleSheet, TextInput, Button} from 'react-native';


const PetPostForm = ( { onSubmit, initialValues } ) => {
    const [title, setTitle] = useState (initialValues.title);
    const [content, setContent] = useState(initialValues.content);
    return (
        <View>
            <Text style={styles.label}>
                Create a Pet:
            </Text>
            <TextInput 
                style={styles.input}value={title} 
                onChangeText = {(text) => setTitle(text)} 
            />
            <Text style={styles.label}>
                Enter pet info:
            </Text>
            <TextInput 
                style={styles.input}
                value = {content} 
                onChangeText={(text) => setContent(text)} 
            />
            <Button 
                title = "save Add Pet"
                onPress={() => onSubmit(title, content)}
                
            />
        </View>
        
    );
};

PetPostForm.defaultProps = {
    initialValues: {
        title: '',
        content: ''
    }
};

const styles = StyleSheet.create({
    input: {
        fontSize: 18,
        borderWidth: 1,
        borderColor: 'black',
        marginBottom: 15,
        padding: 5,
        margin: 5
    },
    label: {
        fontSize:20,
        marginBottom: 5,
        marginLeft: 5
    }
});

export default PetPostForm;