-- view user account info
CREATE OR REPLACE PROCEDURE getUserProfile (usrID int(10))
BEGIN
	SELECT *
	FROM user_profile
	WHERE userID = usrID;
END;

-- view pet header
CREATE OR REPLACE PROCEDURE getPetHeader (pID int(10))
BEGIN
	SELECT *
	FROM pet_header
	WHERE petID = pID;
END;

-- view pet picture
CREATE OR REPLACE PROCEDURE getPetPic (pID int(10))
BEGIN
	SELECT *
	FROM pet_pic
	WHERE petID = pID;
END;

-- view for today's health
CREATE OR REPLACE PROCEDURE getDaysHealth (pID int(10))
BEGIN
	SELECT *
	FROM todays_health_view
	WHERE petID = pID;
END;

-- view medications
CREATE OR REPLACE PROCEDURE getMedications (pID int(10))
BEGIN
	SELECT *
	FROM med_view
	WHERE petID = pID;
END;

-- view vaccines 
CREATE OR REPLACE PROCEDURE getPetVaccines (pID int(10))
BEGIN
	SELECT *
	FROM vacc_view
	WHERE petID = pID;
END;

-- view vet info
CREATE OR REPLACE PROCEDURE getVetView (pID int(10))
BEGIN
	SELECT *
	FROM vet_view
	WHERE petID = pID;
END;

-- view full pet health report
CREATE OR REPLACE PROCEDURE 
BEGIN
	SELECT 
	FROM
	WHERE
END;

-- view list of vaccines 
CREATE OR REPLACE PROCEDURE getVaccList (animalID int(10)) 
BEGIN 
	SELECT *
	FROM list_vacc_view 
	WHERE speciesID = animalID; 
END; 

-- view list of user's pets
CREATE OR REPLACE PROCEDURE getUserPets (usrID int(10))
BEGIN 
	SELECT *
	FROM user_pet_profiles
	WHERE userID = usrID;
END;
