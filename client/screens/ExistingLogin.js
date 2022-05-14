import React, {Component, useState} from "react";
import { Text, View,StyleSheet,Button,Modal,Pressable ,Icon} from 'react-native';
import { Provider ,Appbar, Avatar,TextInput } from 'react-native-paper';
import global from "../global/global";
const ExistingLogin = ( { navigation }) => {
  //const [userID, setUserID] = useState("");
  //const [userName, setUserName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

	const fetchData = async () => {
	const formData = new FormData();
	//this is the request from the server how its displayed
	
	formData.append("login", "true");
	//formData.append("username", userName);//probably use setState/this.state to set for username and emailto give us rendered values 
	formData.append("email", email);
	formData.append("password", password);

    const request = new Request("https://artemis.cs.csub.edu/~jguarcasgarc/myhealthypet/mhpAPI.php", {
		method: "POST", 
		headers: {
			Accept: "application/json", 
			"Content-Type": "multipart/form-data"
		}, 
		body: formData
	});
	
    const resp = await fetch(request);
	
    const responseJS = await resp.json();
    console.log(responseJS);
    if ("user_id" in responseJS) {
      // New user id storage somewhere
      console.log(`New user id: ${responseJS.user_id}`);

      // userData is part of global
      global.userData = responseJS.user_id;
      // userName is NOT, but can be added and accessed from other screens!
      global.userName = "SCREW IT";

    }
    else if ("error" in responseJS) {
      console.log(responseJS.error);
    }
    
    //setData(data);
    
  };
 
  return (
    <Provider>
    <Appbar.Header style={styles.header}>
        <Appbar.Content title="My Healthy Pet" />

    </Appbar.Header>
        <View style={styles.mainbox}>
            
            <Text style={styles.labelText}>email:</Text>
            <TextInput
                style={styles.inputText}
                placeholder="Enter email"
                onChangeText={email => setEmail(email)}
            />
            <Text style={styles.labelText}>pw:</Text>
            <TextInput
                style={styles.inputText}
                placeholder="Enter password"
                onChangeText={password => setPassword(password)}
            />
            <Button
                title="Submit"
                onPress={() => fetchData(this)} 
                style={styles.buttonstyle}
                color="#6200EE"
            />
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
export default ExistingLogin;