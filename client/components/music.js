import React, { useState, useEffect } from "react";
import { Box, FlatList, Center, NativeBaseProvider, Text, View, TouchableOpacity } from "native-base";
//look at post request to php react native.
//look into how to send parameters with post request.

export default function music() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
	const formData = new FormData();
	//this is the request from the server how its displayed
	formData.append("bpmAsc", "true");
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
//these are for displaying artist and songnames
  const renderItem = ({ item, onPress }) => {
    return (
		<View>
			<Box px={5} py={2} rounded="md" bg="primary.300" my={2}>
			<Text>Artist - {item.artist} </Text>
			<Text>Song Name - {item.song_name} </Text>
			<Text>Genre - {item.genre} </Text>
			<Text>Signature - {item.key_sig} </Text>
			<Text>Track BPM - {item.bpm} </Text>
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