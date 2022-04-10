CREATE VIEW pet_profile 
  AS SELECT petName, species.speciesName, sex, 
    coalesce(birthDate, 'Unknown') birthDate, 
    coalesce(genHealth.aboutMe, 'No About Me found') as aboutMe , 
    coalesce(genHealth. weight, 'No Weight') as weight, 
    coalesce(microchipNum, 'None') as microchip, 
    coalesce(genHealth.foodNote, 'None') as foodNote, pet.petID 
  FROM pet 
INNER JOIN species ON species.speciesID = pet.speciesID 
LEFT JOIN genHealth ON genHealth.petID = pet.petID;
