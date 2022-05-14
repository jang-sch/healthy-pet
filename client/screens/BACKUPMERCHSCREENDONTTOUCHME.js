import React from "react";
import { Text, StyleSheet, View, TouchableOpacity, Button, ScrollView } from "react-native";
//import Formatter from "../components/Formatter";

const HomeScreen = ({ navigation }) => {
  return (
	
	<View>

    <Text style={styles.textHeader}>Home Screen</Text>
    <Text style={styles.text}>New Releases!     Updated 12/06/2021</Text>
	
	
		<Button
			onPress={() => navigation.navigate('Catalog')}
			title="Catalog"
		/>
	
		<Button
			onPress={() => navigation.navigate('Playlist')}
			title="My Purchases"
		/>
	
		<Button
			onPress={() => navigation.navigate('Merch')}
			title="Merch Store"
		/>
	
		<Button
			onPress={() => navigation.navigate('Profile')}
			title="My Profile"
		/>
	
	
	
		<Button
			onPress={() => navigation.navigate('art')}
			title="My Cart"
		/>
	
	
	
		<Button
			onPress={() => navigation.navigate('About')}
			title="About Us"
		/>
	
	
	
		<Button
			onPress={() => navigation.navigate('SignUp')}
			title="User Signup"
		/>
	
	
	
		<Button
			onPress={() => navigation.navigate('userlogin')}
			title="User Login"
		/>
	
	
	  
	</View>
    );
};

const styles = StyleSheet.create({
  textHeader: {
    fontSize: 40
  },
  text: {
    fontSize: 20
  }
});

export default HomeScreen;
