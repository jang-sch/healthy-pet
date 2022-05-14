import React from "react";
import { Text, StyleSheet, View, TouchableOpacity, Button, ScrollView } from "react-native";
import Formatter from "../components/Formatter";
import global from "../global/global";

const HomeScreen = ({ navigation }) => {
  return (
	
  <View>
	
    <Text style={styles.textHeader}>{global.userData}</Text>
	<Text style={styles.textHeader}>{global.userName}</Text>
    <Text style={styles.text}>Healthy Pet</Text>
	
	<Formatter>
			<Button
				onPress={() => navigation.navigate('MainHome')}
				title="Home Screen"
			/>
	</Formatter>
	
	<Formatter>
			<Button
				onPress={() => navigation.navigate('Playlist')}
				title="My Playlist"
			/>
	</Formatter>
	
	<Formatter>
			<Button
				onPress={() => navigation.navigate('Merch')}
				title="Merch Store"
			/>
	</Formatter>
	
	<Formatter>
			<Button
				onPress={() => navigation.navigate('Profile')}
				title="My Profile"
			/>
	</Formatter>
	
	<Formatter>
			<Button
				onPress={() => navigation.navigate('Cart')}
				title="My Cart"
			/>
	</Formatter>
	
	<Formatter>
			<Button
				onPress={() => navigation.navigate('About')}
				title="About Us"
			/>
	</Formatter>
	
	<Formatter>
			<Button
				onPress={() => navigation.navigate('SignUp')}
				title="User Signup"
			/>
	</Formatter>
	
	
	  
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
