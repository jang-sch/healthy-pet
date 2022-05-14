import React, { useState, useEffect } from "react";
import { Box, FlatList, Center, NativeBaseProvider, Text, View, TouchableOpacity, TextInput } from "native-base";



export default function hardcodedsignup() {
  const [data, setData] = useState('');
  const [loading, setLoading] = useState(true);

	const fetchData = async () => {
	const formData = new FormData();
	//this is the request from the server how its displayed
	
	
	formData.append("createAccount", "true");
	formData.append("username", "plaboycarti");//probably use setState/this.state to set for username and emailto give us rendered values 
	formData.append("email", "aolaol1222222223@aol.com");
	formData.append("password", "99933399");

    const request = new Request("https://artemis.cs.csub.edu/~hmmc/api_hmmc.php", {
		method: "POST", 
		headers: {
			Accept: "application/json", 
			"Content-Type": "multipart/form-data"
		}, 
		body: formData
	});
	
const resp = await fetch(request);
	// will need method post to set actions in the database to true such as tostada 
	
    const data = await resp.json();
    setData(data);
    setLoading(false);
  };

/*	
	handles response from api and what it sends bacc
	const resp = await fetch(request);
	// will need method post to set actions in the database to true such as tostada 
	
    const data = await resp.json();
    setData(data);
    setLoading(false);
	//data should have property error or userdata 
  };
  */
//these are for displaying artist and songnames
  const renderItem = ({ item, onPress }) => {
    return (
		<View>
			<Box px={5} py={2} rounded="md" bg="primary.300" my={2}>
			{item.username}
			{item.password}
			{item.artist}
			{item.located}
		</Box>
		</View>
	);
  };
  
 useEffect(() => {
  fetchData();
}, []);

return (
  <NativeBaseProvider>
    <Center flex={1}>
    <Box> Fetch API</Box>
      {loading && <Box>Loading..</Box>}
      {data && (
        <FlatList
          data={data}
          renderItem={renderItem}
          keyExtractor={(item) => item.song_id.toString()}
        />
      )}
    </Center>
  </NativeBaseProvider>
);
} 
