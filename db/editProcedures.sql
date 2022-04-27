-- procedure to edit user account info
CREATE OR REPLACE PROCEDURE editUserInfo (usrID int(10), uname varchar(30), em varchar(30)) 
BEGIN 
    SELECT COUNT(*) INTO @userCount 
    FROM user 
    WHERE userID = usrID; 
 
    IF @userCount < 1 THEN 
		SELECT NULL as usrID, "User does not exist!" AS 'Error'; 
    ELSE 
		UPDATE user 
		SET userName = uname, email = em 
		WHERE userID = usrID; 
    END IF; 
END; 

-- edit (change) account password
CREATE OR REPLACE PROCEDURE changePassword (usrID int(10), pword varchar(300)) 
BEGIN 
    SELECT COUNT(*) INTO @userCount 
    FROM user 
    WHERE userID = usrID; 
 
    IF @userCount < 1 THEN 
		SELECT NULL as usrID, "User does not exist!" AS 'Error'; 
    ELSE 
		UPDATE user 
		SET password = pword 
		WHERE userID = usrID; 
    END IF; 
END; 

-- edit Pet Profile info
CREATE OR REPLACE PROCEDURE editPet (pID int(10), name varchar(30), bday date, chipNum varchar(30), pic mediumblob) 
BEGIN 
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @petCount < 1 THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
		UPDATE pet 
		SET petName = name, birthDate = bday, microchipNum = chipNum, petPic = pic 
		WHERE petID = pID; 
    END IF; 
END;

-- edit a calendar entry
CREATE OR REPLACE PROCEDURE editCalendarEvent (pID int(10), eID int(10), day datetime, note varchar(300)) 
BEGIN 
    SELECT COUNT(*) INTO @eventCount 
    FROM calendar 
    WHERE eventID = eID; 

    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @eventCount < 1 THEN 
		SELECT NULL as eID, "Event does not exist!" AS 'Error'; 
    ELSEIF @petCount < 1 AND pID IS NOT NULL THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
		UPDATE calendar 
		SET petID = pID, date = day, freeText = note 
		WHERE eventID = eID; 
    END IF; 
END; 

-- edit a medication entry
CREATE OR REPLACE PROCEDURE editMed (pID int(10), mID int(10), name varchar(60), notes varchar(300), ps varchar(60), freq varchar(60), alrm time) 
BEGIN 
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    SELECT COUNT(*) INTO @medCount 
    FROM medications 
    WHERE medID = mID; 
 
    IF @petCount < 1 THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSEIF @medCount < 1 THEN 
		SELECT NULL as mID, "Medication does not exist!" AS 'Error'; 
    ELSE 
		UPDATE medications 
		SET medName = name, medNotes = notes, prescriber = ps, frequency = freq, alarm = alrm 
		WHERE petID = pID AND medID = mID; 
    END IF; 
END; 

-- edit a day's health entry
CREATE OR REPLACE PROCEDURE editDailyHealth (pID int(10), day date, food ENUM('Not Eating', 'Eating Less', 'Regular', 'Excessive Eating', 'Other'), snack ENUM('None', 'One', 'Multiple'), throwup ENUM('No Vomit', 'Vomit', 'Excessive Vomiting'), pee ENUM('Not Peeing', 'Peeing Less', 'Regular', 'Excessive Peeing', 'Other'), bathroom ENUM('No Poop', 'Less Poop', 'Regular', 'Diarrhea', 'Other'), play ENUM('None', 'One Play or Walk Session', 'Multiple Play or Walk Sessions', 'Other'), sleep ENUM('Not Sleeping', 'Regular', 'Excessive Sleeping', 'Other'), notes varchar(500)) 
BEGIN 
    SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @petCount < 1 AND pID IS NOT NULL THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
		UPDATE dailyHealth
		SET eatingHabits = food, treat = snack, vomit = throwup, urineHabits = pee, poopHabits = bathroom, exercise = play, sleepHabits = sleep, dayNote = notes
		WHERE petID = pID AND logDay = day;
    END IF; 
END; 

-- edit general health
CREATE OR REPLACE PROCEDURE editGenHealth (pID int(10), w varchar(30), note varchar(150), about varchar(500)) 
BEGIN 
	SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @petCount < 1 THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
		UPDATE genHealth 
		SET weight = w, foodNote = note, aboutMe = about 
		WHERE petID = pID; 
    END IF; 
END; 

-- edit a medical condition entry
CREATE OR REPLACE PROCEDURE editCondition (pID int(10), cID int(10), name varchar(40), descr varchar(300)) 
BEGIN 
	SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

	SELECT COUNT(*) INTO @conCount 
    FROM medicalConditions 
    WHERE conditionID = cID; 
 
    IF @petCount < 1 THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSEIF @conCount < 1 THEN 
		SELECT NULL as cID, "Condition does not exist!" AS 'Error'; 
    ELSE 
		UPDATE medicalConditions 
		SET conditionName = name, description = descr 
		WHERE conditionID = cID AND petID = pID; 
    END IF; 
END; 

-- edit a vet clinic entry
CREATE OR REPLACE PROCEDURE editVet (vID int(10), pID int(10), name varchar(60), addy varchar(80), number int(10), site varchar(100), note varchar(300)) 
BEGIN 
	SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

	SELECT COUNT(*) INTO @vetCount 
    FROM vetFacility 
    WHERE vetFacID = vID; 

    IF @petCount < 1 THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSEIF @vetCount < 1 THEN 
		SELECT NULL as vID, "Vet facility does not exist!" AS 'Error'; 
    ELSE 
		UPDATE vetFacility 
		SET facilityName = name, address = addy, phone = number, website = site, notes = note 
		WHERE vetFacID= vID AND petID = pID; 
    END IF; 
END;

-- edit a vaccination entry for a pet
CREATE OR REPLACE PROCEDURE editPetVacc (pID int(10), vID int(10), vDate date, note varchar(150)) 
BEGIN 
	SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

	SELECT COUNT(*) INTO @vaccCount 
    FROM petVacc 
    WHERE vaccID = vID; 

    IF @petCount < 1 THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSEIF @vaccCount < 1 THEN 
		SELECT NULL as vID, "Vaccine entry does not exist!" AS 'Error'; 
    ELSE 
		UPDATE petVacc 
		SET vaccDate = vDate, vaccNote = note 
		WHERE vaccID= vID AND petID = pID; 
    END IF; 
END; 
