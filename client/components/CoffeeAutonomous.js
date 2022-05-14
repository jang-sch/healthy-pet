//do not change this 
import React, { useState, useEffect } from "react";
import { Box, FlatList, Center, NativeBaseProvider, Text } from "native-base";

export default function CoffeeAutonomous() {
  const [data, setData] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchData = async () => {
	const formData = new FormData();
	formData.append("ahooooga", "true");
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

  const renderItem = ({ item }) => {
    return (
      <Box px={5} py={2} rounded="md" bg="primary.300" my={2}>
        {item.name}
			{item.price}
      </Box>
	  
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
          keyExtractor={(item) => item.id.toString()}
        />
      )}
    </Center>
  </NativeBaseProvider>
);
} 