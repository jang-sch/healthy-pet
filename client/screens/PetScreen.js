import React, {useContext} from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { Context} from '../context/PetContext';
import { EvilIcons} from '@expo/vector-icons';

const PetScreen = ( { navigation } ) => {
    const { state } = useContext(Context);

    const petPost = state.find((petPost) => petPost.id === navigation.getParam('id'));

    return (
        <View>
            <Text>{petPost.title}</Text>
            <Text>{petPost.content}</Text>
            
        </View>
    )
};

PetScreen.navigationOptions = ({ navigation }) => {
    return {
        headerRight: () => (
            <TouchableOpacity 
                onPress={() => 
                    navigation.navigate('Edit', { id: navigation.getParam('id') })
                }
            >
                <EvilIcons name="pencil" size={35} />
            </TouchableOpacity>
        ),
    };
};

const styles = StyleSheet.create({});

export default PetScreen;