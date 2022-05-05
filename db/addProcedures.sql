-- create user account
CREATE OR REPLACE PROCEDURE createAccount (uname varchar(30), em varchar(30), pword varchar(300)) 
BEGIN	 
    SELECT COUNT(*) INTO @emailCount 
    FROM user 
    WHERE email = em; 

    IF @emailCount > 0 THEN 
	SELECT NULL as userID, "Email already exists" AS 'Error'; 
    ELSE 
        INSERT INTO user (userName, email, password) VALUES (uname, em, pword); 
        SELECT userID AS userID, NULL as 'Error' FROM user WHERE email=em; 
    END IF; 
END; 

-- create pet profile
CREATE OR REPLACE PROCEDURE createPetProfile (usrID int(10), name varchar(30), species int(10), birthday date, sx enum('Female', 'Male', 'Other'), mcNum varchar(30), pic mediumblob) 
BEGIN	 
    SELECT COUNT(*) INTO @userCount 
    FROM user 
    WHERE userID = usrID; 

    IF @userCount < 1 THEN 
	SELECT NULL as petID, "User does not exist!" AS 'Error'; 
    ELSE 
        INSERT INTO pet (userID, petName, speciesID, birthDate, sex, microchipNum, petPic) VALUES (usrID,name, species, birthday, sx, mcNum, pic); 
        SELECT petID AS petID, NULL as 'Error' FROM pet WHERE userID=usrID AND petName = name; 
    END IF; 
END; 

-- create calendar entry
CREATE OR REPLACE PROCEDURE addCalendarEntry (usrID int(10), pID int(10), day datetime, notes varchar(300)) 
BEGIN 
    SELECT COUNT(*) INTO @userCount 
    FROM user 
    WHERE userID = usrID; 

    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @userCount < 1 THEN 
	SELECT NULL as usrID, "Event does not exist!" AS 'Error'; 
    ELSEIF @petCount < 1 AND pID IS NOT NULL THEN
	SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
	INSERT INTO calendar(userID, petID, date, freeText) VALUES (usrID, pID, day, notes); 
    END IF; 
END; 

-- create a medication entry
CREATE OR REPLACE PROCEDURE addMed (pID int(10), name varchar(60), notes varchar(300), ps varchar(60), freq varchar(60), alrm time) 
BEGIN 
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 
	
    IF @petCount < 1 AND pID IS NOT NULL THEN
	SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
	INSERT INTO medications(petID, medName, medNotes, prescriber, frequency, alarm) VALUES (pID, name, notes, ps, freq, alrm); 
    END IF; 
END; 

-- create daily health entry
CREATE OR REPLACE PROCEDURE addDailyHealth (pID int(10), day date, food ENUM('Not Eating', 'Eating Less', 'Regular', 'Excessive Eating', 'Other'), snack ENUM('None', 'One', 'Multiple'), throwup ENUM('No Vomit', 'Vomit', 'Excessive Vomiting'), pee ENUM('Not Peeing', 'Peeing Less', 'Regular', 'Excessive Peeing', 'Other'), bathroom ENUM('No Poop', 'Less Poop', 'Regular', 'Diarrhea', 'Other'), play ENUM('None', 'One Play or Walk Session', 'Multiple Play or Walk Sessions', 'Other'), sleep ENUM('Not Sleeping', 'Regular', 'Excessive Sleeping', 'Other'), notes varchar(500)) 
BEGIN 
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @petCount < 1 AND pID IS NOT NULL THEN 
	SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
	INSERT INTO dailyHealth(petID, logDay, eatingHabits, treat, vomit, urineHabits, poopHabits, exercise, sleepHabits, dayNote) VALUES (pID, food, snack, throwup, pee, bathroom, play, sleep, notes); 
    END IF; 
END; 

-- add general health 
CREATE OR REPLACE PROCEDURE addGenHealth (pID int(10), w varchar(30), fNote varchar(150), petInfo varchar(500)) 
BEGIN 
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 
 
    IF @petCount < 1 AND pID IS NOT NULL THEN 
	SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
	INSERT INTO genHealth(petID, weight, foodNote, aboutMe) VALUES (pID, w, fNote, petInfo); 
    END IF; 
END; 

-- add a medical condition entry 
CREATE OR REPLACE PROCEDURE addCondition (pID int(10), name varchar(40), descr varchar(300)) 
BEGIN  
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @petCount < 1 AND pID IS NOT NULL THEN 
	SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
	INSERT INTO medicalConditions(petID, conditionName, description) VALUES (pID, name, descr); 
    END IF; 
END; 

-- add an entry for a pet's preferred vet facility 
CREATE OR REPLACE PROCEDURE addVetFac (pID int(10), name varchar(60), addy varchar(80), number varchar(15), site varchar(100), note varchar(300)) 
BEGIN 
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @petCount < 1 AND pID IS NOT NULL THEN 
	SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
	INSERT INTO vetFacility(petID,  facilityName, address, phone, website, notes) VALUES (pID, name, addy, number, site, note); 
    END IF; 
END; 

-- add a vaccination entry for a pet
CREATE OR REPLACE PROCEDURE addPetVacc (pID int(10), day date, notes varchar(150)) 
BEGIN  
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 
 
    IF @petCount < 1 AND pID IS NOT NULL THEN 
	SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
	INSERT INTO petVacc(petID, vaccDate, vaccNote) VALUES (pID, day, notes); 
    END IF; 
END; 
