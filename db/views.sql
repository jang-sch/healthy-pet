-- view user's profile info
CREATE VIEW user_profile 
  AS SELECT userName, userID, email 
FROM user; 

-- a view for the pets header(banner)
CREATE OR REPLACE VIEW pet_header 
  AS SELECT petID, petName, 
    coalesce(FLOOR(datediff(NOW(), birthDate)/365), 'unknown') as `ageYears`, 
    species.speciesName, 
    coalesce(sex, 'Unknown') as sex 
FROM pet 
INNER JOIN species ON species.speciesID = pet.speciesID; 

-- view information from pet's profile
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

-- a view for pet's profile picture 
CREATE OR REPLACE VIEW pet_pic 
  AS SELECT petID, petPic 
FROM pet; 

-- a view to look at the pet's health for the day
CREATE OR REPLACE VIEW todays_health_view 
  AS SELECT petID, logday, eatingHabits, treat, vomit, urineHabits,  
    poopHabits, exercise, sleepHabits,  
	  coalesce(dayNote, 'None') as dayNote 
FROM dailyHealth; 

-- view for the pet's medications
CREATE OR REPLACE VIEW med_view 
  AS SELECT petID, medName,  
    coalesce(medNotes, 'None') as medNotes,  
    coalesce(prescriber, 'None') as prescriber,  
    coalesce(frequency, 'None') as frequency 
FROM medications; 

-- view for a pet's vaccinations
CREATE OR REPLACE VIEW  vacc_view 
  AS SELECT petID, vaccPerSpecies.vaccName,  
    coalesce(vaccDate, 'No Date Entered') as vaccDate, 
    coalesce(vaccNote, 'None') as vaccNote 
FROM petVacc 
INNER JOIN vaccPerSpecies ON petVacc.vaccID = vaccPerSpecies.vaccID; 

-- view to look at info for the pet's preferred vet clinic
CREATE OR REPLACE VIEW vet_view 
  AS SELECT petID, facilityName, address, 
    coalesce(phone, 'None') as phone, 
    coalesce(website, 'None') as website, 
    coalesce(notes, 'None') as notes 
FROM vetFacility;

-- a view to look at a pet's comprehensive pet report


-- a view to look at the vaccines in the database
CREATE OR REPLACE VIEW list_vacc_view  
  AS SELECT speciesID, vaccName 
FROM vaccPerSpecies; 

-- a view for to display all pets a user has
CREATE OR REPLACE VIEW user_pet_profiles
  AS SELECT userID, petName, petPic
FROM pet;
