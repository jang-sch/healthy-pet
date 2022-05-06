-- delete a user account 
CREATE OR REPLACE PROCEDURE deleteUserProfile (usrID int(10))
BEGIN
	SELECT COUNT(*) INTO @userCount 
    FROM user 
    WHERE userID = usrID; 

    IF @userCount < 1 THEN
		SELECT NULL as usrID, "User does not exist!" AS 'Error'; 
    ELSE 
		CALL deleteUserHelper(usrID);
		
		DELETE FROM calendar
		WHERE userID = usrID;
	
		DELETE FROM user
		WHERE userID = usrID;
    END IF; 
END;

-- delete user helper (delete pet profiles if not previously deleted)
CREATE OR REPLACE PROCEDURE deleteUserHelper (usrID int(10))
BEGIN 
	SELECT COUNT(*) INTO @petCount 
	FROM pet 
	WHERE userID = usrID; 
	
	WHILE @petCount > 0 DO
		SELECT(SELECT petID FROM pet WHERE userID = usrID LIMIT 1) INTO @pID;
		
		CALL deletePetProfile(@pID);
		
		SELECT COUNT(*) INTO @petCount 
		FROM pet 
		WHERE userID = usrID; 	
		
	END WHILE;
END;

-- delete a pet profile
CREATE OR REPLACE PROCEDURE deletePetProfile (pID int(10))
BEGIN
	SELECT COUNT(*) INTO @petCount 
    FROM pet 
    WHERE petID = pID; 

    IF @petCount < 1 THEN 
		SELECT NULL as pID, "Pet does not exist!" AS 'Error'; 
    ELSE 
		DELETE FROM petVacc
		WHERE petID = pID;
		
		DELETE FROM medicalConditions
		WHERE petID = pID;

		DELETE FROM vetFacility
		WHERE petID = pID;

		DELETE FROM genHealth
		WHERE petID = pID;

		DELETE FROM medications
		WHERE petID = pID;

		DELETE FROM dailyHealth
		WHERE petID = pID;

		DELETE FROM calendar
		WHERE petID = pID;	
		
		DELETE FROM pet
		WHERE petID = pID;
    END IF; 
END;

-- delete calendar entry
CREATE OR REPLACE PROCEDURE deleteCalendarEvent (eID int(10))
BEGIN
	DELETE FROM calendar
	WHERE eventID = eID;
END;

-- delete medication entry
CREATE OR REPLACE PROCEDURE deleteMedication (mID int(10))
BEGIN
	DELETE FROM medications
	WHERE medID = mID;
END;

-- delete medical condition 
CREATE OR REPLACE PROCEDURE deleteCondition (cID int(10))
BEGIN
	DELETE FROM medicalConditions
	WHERE conditionID = cID;
END;

-- delete vet clinic entry
CREATE OR REPLACE PROCEDURE deleteVetFacility (vID int(10))
BEGIN
	DELETE FROM vetFacility
	WHERE vetFacID = vID;
END;

-- delete a pet's vaccine entry
CREATE OR REPLACE PROCEDURE deletePetVacc(pID int(10), vID int(10))
BEGIN
	DELETE FROM petVacc
	WHERE petID = pID AND vaccID = vID;
END;
