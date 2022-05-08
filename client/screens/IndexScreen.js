import React, { useContext, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList, Button, TouchableOpacity } from 'react-native';
//later on use Context as namehere if we get more context variables
import { Context } from '../context/PetContext';
import { Feather } from '@expo/vector-icons';



const IndexScreen = ({ navigation }) => {
    
    //gives us whatever info we passed into value prop from
    //context provider
    //do not call getPetPosts directly!!!!!!!!AWOOOOOGA
    const { state, deletePetPost, getPetPosts } = useContext(Context);

    useEffect(() =>{
        getPetPosts();

    }, []); 

    return (
        <View>
            <Text>Main pet page temp</Text>
            <FlatList 
                data={state}
                keyExtractor={ petPost => petPost.title}
                renderItem={({ item }) => {
                    return ( 
                        <TouchableOpacity onPress = {() => navigation.navigate('Show', { id: item.id})}>
                            <View style = {styles.row}>
                                <Text style = {styles.title}>{item.title} - {item.id}</Text>
                                <TouchableOpacity onPress ={() => deletePetPost(item.id)}>
                                    <Feather style = {styles.icon} name="trash" />
                                </TouchableOpacity>    
                            </View>
                        </TouchableOpacity>
                    );
                }}
            />
        </View>
    );
};
//when destructuring ADD FUCKING CURLY BRACES OR REACT LOSES ITS SHIT ({navigation})
IndexScreen.navigationOptions = ( {navigation} ) => {
    return {
        headerRight: () => ( 
                <TouchableOpacity onPress={() => navigation.navigate('Create')}>  
                    <Feather name="plus" size={50} />
                </TouchableOpacity>
        ),
    };
};



const styles = StyleSheet.create({
    row: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        paddingVertical: 20,
        paddingHorizontal: 10,
        borderTopWidth: 1,
        borderColor: 'gray'
    },
    title: {
        fontSize: 18,
        margin: 100
    },
    icon: {
        fontSize: 24
    },
    
});

export default IndexScreen; 