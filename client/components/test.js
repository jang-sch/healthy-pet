import React, { useEffect, useState } from 'react';
import { ActivityIndicator, FlatList, Text, View } from 'react-native';


fetch('https://artemis.cs.csub.edu/~hmmc/baby_api.php', {
  method: 'POST',
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({
    firstParam: 'tostada',
    //secondParam: 'yourOtherValue'
  })
});

const getBabyApi = async () => {
    let response = await fetch(
      'https://artemis.cs.csub.edu/~hmmc/baby_api.php'
    );
    let json = await response.json();
    return json.;
  }
};