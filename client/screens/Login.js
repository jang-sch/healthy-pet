import React, {Component} from "react";
import { Text, View,StyleSheet,Button,Modal,Pressable ,Icon} from 'react-native';
import { Provider ,Appbar, Avatar,TextInput } from 'react-native-paper';
import axios from 'axios';
const Login = () => {
  const [username, setFirstNameText] = React.useState('');
  const [passWord, setLastNameText] = React.useState('');
  const [email, setEmailText] = React.useState('');
  

  const [errorMessage, setErrorMessage] = React.useState('');

    const handleClick = async () => {
      try{
      axios.post('https://artemis.cs.csub.edu/~jguarcasgarc/myhealthypet/mhpAPI.php', {username:username,password:passWord,email:email})
      .then(response => console.log(response.data));
      } catch (e){
        setErrorMessage('something went wrong dawg')
      }
    };
  
  const _goBack = () => console.log('Went back');
  const _handleSearch = () => console.log('Searching');
  const _handleMore = () => console.log('Shown more');
  return (
    <Provider>
    <Appbar.Header style={styles.header}>
        <Appbar.BackAction onPress={_goBack} />
        <Appbar.Content title="My Healthy Pet" subtitle="Subtitle" />
        <Appbar.Action icon="magnify" onPress={_handleSearch} />
        <Appbar.Action icon="dots-vertical" onPress={_handleMore} />
    </Appbar.Header>
        <View style={styles.mainbox}>
            <Text style={styles.labelText}>User's Name:</Text>
            <TextInput
                style={styles.inputText}
                placeholder="Enter User's Name"
                value={username}
                onChangeText={username => setFirstNameText(username)}
            />
            <Text style={styles.labelText}>Password:</Text>
            <TextInput
                style={styles.inputText}
                placeholder="Enter Password"
                onChangeText={password => setLastNameText(password)}
            />
            <Text style={styles.labelText}>Email:</Text>
            
            <TextInput
                style={styles.inputText}
                placeholder="Enter Email"
                onChangeText={email => setEmailText(email)}
            />
            <Button
                title="Submit"
                onPress={() => handleClick(this)} 
                style={styles.buttonstyle}
                color="#6200EE"
            />
            {errorMessage ? <Text>{errorMessage}</Text> : null}
        </View>
    </Provider>
  );
};
const styles = StyleSheet.create({
  title:{
    margin: 10,
    fontSize: 15,
    fontSize: 35
  },
  mainbox:{
    textAlign:'center',
    margin: 15,
  },
  textstyle:{
    fontSize: 18,
    marginBottom: 20,
  },
  labelText:{
    marginTop: 10,
    marginBottom: 5,
  },
  inputText:{
    height:45,
    marginBottom: 15,
  },
  buttonstyle:{
    marginTop: 10,
  },
    centeredView: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    marginTop: 22
  },
  modalView: {
    margin: 20,
    backgroundColor: "#ffffff",
    borderRadius: 20,
    padding: 20 ,
    alignItems: "center",
    shadowColor: "#000",
    shadowOffset: {
      width: 0,
      height: 2
  },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 6
  },
  button: {
    borderRadius: 4,
    padding: 8,
    elevation: 2
  },
  buttonClose: {
    backgroundColor: "#C82333",
  },
  textStyle: {
    color: "white",
    fontWeight: "bold",
    textAlign: "center"
  },
  modalText: {
    marginBottom: 15,
    textAlign: "center",
  }
});
export default Login;