<?php
// function definitions file

// written and required .cnfg file to establish a secure connection
require_once ".cnfg.php";

/*  function handles creation of new USER profile. React Native Client sends 
 *  following data via POST request:
 *
 *  POST params from Client:
 *      createAccount: set to true or in POST variable 
 *      username: string username selected by user
 *      email: string representing email address of user
 *      password: password selected by user
 *  returns to main API --> Client:
 *      JSON encoded array with 'userID' or appropriate error message
 *      ***note- now determine when 'sessionID' will be generated
 *  interacts with databse:
 *      calls stored procedure
 */
function createUser() {
    // establish connection once appropriate
    $conn = getConnection();

    $username = $_POST["username"];
    $email = $_POST["email"];
    $password = $_POST["password"];
    $hash = password_hash($password, PASSWORD_DEFAULT);

    // createAccount to be a prepared statement, make prepared call to stored
    // procedure. stored procedure accepts params in order: uname, email, pword
    $createAccount = $conn->prepare("CALL createAccount(?, ?, ?)");

    // bind parameters, in variatic function, 's' for strings, 'i' for integers
    $createAccount->bind_param("sss", $username, $email, $hash);

    // now execute prepared stament
    if($createAccount->execute()){
        //echo json_encode(array("testmssg" => "testing123")); // for debugging
        // binding results to local variables so can fetch them and see if null
        mysqli_stmt_bind_result($createAccount, $res_id, $res_error);
        $createAccount->fetch();

        // do we have successful registratation--> check for duplicate email
        if (is_null($res_id)) {
            // error code/error message from the database
            echo json_encode(array("error" => $res_error));
            return;
        }
        // all is well, registration worked and you are given a user ID return
        else {
            echo json_encode(array("user_id" => $res_id));
            //echo json_encode(array("username" => $res_id));
            return;
        }
    } else {
        echo json_encode(array("error" => "ERROR: an error has occurred creating account!"));
    }
}

/*  function description: helper function to loginUser() to check if user is logged
 *
 *  POST params from Client: N/A
 *  returns to main API --> Client: N/A  
 *  interacts with databse: N/A
 */
function isLogged() {
    // before we start session, set ID to the ID the client provided
    session_id($_POST["session_id"]);
    session_start();

    // session variable "userID" is not set, so NOT logged in
    if(!isset($_SESSION["userID"])) {
        return false;
    }
    
    // mismatched, so NOT logged in, do not proceed
    if($_SESSION["userID"] != $_POST["userID"]) {
        return false;
    }

    // otherwise, they ARE logged in
    return true;
}

/*  function description: login the user, verifies that passed email and pw are 
 *  valid and match what is stored on the database. upon success, generates
 *  json encoded array with userID's pet data for the user's "home page" to list
 *  the pets.
 *
 *  POST params from Client:
 *      email: required for user lookup
 *      password: required to validate against
 *      
 *  returns to main API --> Client: json encoded array of sets of following per pet
 *      petID: IMPORTANT, needed to proceed to pet home pages from user home page
 *      petPic: currently not incorporated, will be NULL
 *      petName: for display
 *      speciesName: for display
 * 
 *  interacts with databse:
 *      queries user table
 *      calls stored procedure
 */
function loginUser() {
    $em = $_POST["email"];
    $password = $_POST["password"];

    $conn = getConnection();
    $validateStmnt = $conn->prepare("SELECT * FROM user WHERE email=?");
    $validateStmnt->bind_param("s", $em);
    $validateStmnt->execute();
    // upon execution get userID, username, email, and hashedPw; store returned values
    mysqli_stmt_bind_result($validateStmnt, $retUserID, $retUserName, $retEmail, $retPW);
    
    if ($validateStmnt->fetch()  && password_verify($password, $retPW)) {
        //echo json_encode("in login func at userFuncs, query executed and pw validated");
        session_start();
        
        // set the session variables
        $_SESSION["userID"] = $retUserID;
        $_SESSION["email"] = $retEmail;
        $_SESSION["username"] = $retUserName;
        // NOTE: may NEED TO change way generate session id later; ALSO R/T LINE 130
        $_SESSION["sessionID"] = session_id();
        
        // in progress
        // success so we can send them back session credentials
        /* echo json_encode(array(
            "userID" => $retUserID,
            "email" => $retEmail,
            "username" => $retUserName,
            // NOTE: for now use session_id(), also r/t line 122
            "sessionID" => session_id()
        )); */

        $validateStmnt->close();

        // obtain user's pets tiles    
        $result = $conn->prepare("CALL getUserPets(?)");
        $result->bind_param("i", $retUserID);
        $result->execute();
    
        $userPets = $result->get_result();
    
        $rows = [];
        while($row = $userPets->fetch_assoc()) {
            $rows [] = $row;
            //echo $row;
        }
        
        // temp sanity check
        $rows [] = array("message" => "login succeeeeeded");
        
        echo json_encode($rows);
        return true;

    } else {
        echo json_encode(array("message" => "ERROR: invalid email or password! Try again."));
        //echo json_encode("ERROR: invalid email or password! Try again.");
    }
}

/* function description: handles creation of new pet profile. React Native 
 * Client sends the following data via POST request:
 *
 * POST params:
 *      -- required from client
 *      userID: an integer representing user's ID
 *      petID: an integer representing the pet's ID
 *      petName: name
 *      speciesID: an integer from 1-9 that map to the 'species' table
 *      
 *      -- optional for the client, MUST BE null or empty strings
 *      birthDate: in format YYYY-MM-DD
 *      sex:
 *      microchipNum:
 *      petPic:
 * 
 * returns to Client:
 *      JSON encoded array with 'petID' in format {"petID":1057}
 *      
 */
function createPet() {
    $conn = getConnection();

    // setup associative array to check if any sent POST variables are null
    $pet_data["userID"] = $_POST["userID"];
    $pet_data["petName"] = $_POST["petName"];
    $pet_data["speciesID"] = $_POST["speciesID"];
    $pet_data["birthDate"] = $_POST["birthDate"];
    $pet_data["sex"] = $_POST["sex"];
    $pet_data["microchipNum"] = $_POST["microchipNum"];

    // goal: ***incorporate the  PHOTO **; functional this way for now
    $pet_data["petPic"] = NULL;

    // if any POST variables were empty strings, convert to NULL in order
    // to properly call the createPetProfile procedure on database
    foreach($pet_data as $key => $val) {
        //echo $key." is ".$val."\n";
        if ($val == ""|| $val == NULL) {
            $pet_data[$key] = NULL;
        }
        //echo $key." issss ".$val."\n";
    }

    // preparre statement prior to executing
    $createPet = $conn->prepare("CALL createPetProfile(?, ?, ?, ?, ?, ?, ?)");

    // bind parameters, in variatic function, 's' for strings, 'i' for integers
    $createPet->bind_param("isisisb", 
        $pet_data["userID"], 
        $pet_data["petName"], 
        $pet_data["speciesID"],
        $pet_data["birthDate"],
        $pet_data["sex"],
        $pet_data["microchipNum"],
        $pet_data["petPic"]);

    // now execute prepared stament
    if($createPet->execute()){
        //echo json_encode(array("testmssg" => "testing123")); // for debugging
        // binding results to local variables so can fetch them and see if null
        mysqli_stmt_bind_result($createPet, $res_id, $res_error);
        $createPet->fetch();

        // do we have successful registratation--> check for duplicate email
        if (is_null($res_id)) {
            // error code/error message from the database
            echo json_encode(array("error" => $res_error));
            return;
        }
        // all is well, registration worked and you are given a user ID return
        else {
            echo json_encode(array("petID" => $res_id));
            //echo json_encode(array("username" => $res_id));
            return;
        }
    } else {
        echo json_encode(array("message" => "ERROR: an error has occurred creating a pet!"));
    }
}

/*  function description: to add a daily health log of symptoms and pet 
 *  behavior for specified pet.
 *
 *  POST params from Client: 
 *      petID: REQUIRED, NOT NULL
 * 
 *      --following MUST be in POST, but can be null/empty strings--
 *      logDay: 
 *      eatingHabits:
 *      treat:
 *      vomit:
 *      urineHabits:
 *      poopHabits:
 *      exercise:
 *      sleepHabits:
 *      dayNote:
 *        
 *  returns to main API --> Client:
 *      JSON encoded array with error message if error occured, otherwise
 *      the error message will be NULL
 * 
 *  interacts with databse:
 *      databases's stored procedure addDailyHealth(...)
 */
function addDailyHealth() {
    $conn = getConnection();

    // setup associative array to check if any sent POST variables are null
    $day_data["petID"] = $_POST["petID"];
    $day_data["logDay"] = $_POST["logDay"];
    $day_data["eatingHabits"] = $_POST["eatingHabits"];
    $day_data["treat"] = $_POST["treat"];
    $day_data["vomit"] = $_POST["vomit"];
    $day_data["urineHabits"] = $_POST["urineHabits"];
    $day_data["poopHabits"] = $_POST["poopHabits"];
    $day_data["exercise"] = $_POST["exercise"];
    $day_data["sleepHabits"] = $_POST["sleepHabits"];
    $day_data["dayNote"] = $_POST["dayNote"];

    // if any POST variables were empty strings, convert to NULL in order
    // to properly call the daily Health
    foreach($day_data as $key => $val) {
        //echo $key." is ".$val."\n";
        if ($val == "" || $val == NULL) {
            $day_data[$key] = NULL;
        }
        //echo $key." issss ".$val."\n";
    }

    // preparre statement prior to executing
    $createDayNote = $conn->prepare("CALL addDailyHealth(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
 
    $createDayNote->bind_param("isssssssss",
        $day_data["petID"],
        $day_data["logDay"],
        $day_data["eatingHabits"],
        $day_data["treat"],
        $day_data["vomit"],
        $day_data["urineHabits"],
        $day_data["poopHabits"],
        $day_data["exercise"],
        $day_data["sleepHabits"],
        $day_data["dayNote"]);

    // now execute prepared stament
    if($createDayNote->execute()){
        //echo json_encode(array("testmssg" => "testing123")); // for debugging
        // binding results to local variables so can fetch them and see if null
        mysqli_stmt_bind_result($createDayNote, $res_id, $res_error);
        $createDayNote->fetch();
        

        // DELETE IF-ELSE LATER, not serving a purpose at this point
        // do we have successful registratation--> check for duplicate email
        if (is_null($res_id)) {
            // error code/error message from the database
            echo json_encode(array("message" => $res_error));
            return;
        }
        // pet addition worked
        else {
            echo json_encode(array("petID" => $res_id));
            //echo json_encode(array("username" => $res_id));
            return;
        }
    } else {
        echo json_encode(array("message" => "ERROR: an error has occurred creating day log!"));
    }
}

/*  function description: called by main API file to add a medication
 *  for the specified pet by calling the database's stored procedure
 *      
 *
 *  POST params from Client:
 *      petID: int(10) NOT NULL
 *      medName: varchar(60) NOT NULL
 *      
 *      -- can be NULL or empty but MUST be in POST
 *      medNotes: varchar(300)
 *      prescriber: varchar(60)
 *      frequency: varchar(60)
 *      alarm: time -- NULL for now 
 *      
 *  returns to main API --> Client:
 *      JSON encoded array with error message if error occured, otherwise
 *      the error message will be NULL
 * 
 *  interacts with databse:
 *      stored database procedure named addMed(...) 
 *  
 */
function addMed() {
    $conn = getConnection();
    
    $med_data["petID"] = $_POST["petID"];
    $med_data["medName"] = $_POST["medName"];
    $med_data["medNotes"] = $_POST["medNotes"];
    $med_data["prescriber"] = $_POST["prescriber"];
    $med_data["frequency"] = $_POST["frequency"];
    $med_data["alarm"] = $_POST["alarm"];

    // if any POST variables were empty strings, convert to NULL in order
    // to properly call the addMed procedure on database
    foreach($med_data as $key => $val) {
        //echo $key." is ".$val."\n";
        if ($val == ""|| $val == NULL) {
            $med_data[$key] = NULL;
        }
        //echo $key." issss ".$val."\n";
    }

    // ready to start prepping query to db
    $addMedQuery = $conn->prepare("CALL addMed(?, ?, ?, ?, ?, ?)");

    // bind parameters, 's' for strings, 'i' for integers
    $addMedQuery->bind_param("isssss", 
        $med_data["petID"], 
        $med_data["medName"], 
        $med_data["medNotes"],
        $med_data["prescriber"],
        $med_data["frequency"],
        $med_data["alarm"]);

    // now execute prepared stament
    if($addMedQuery->execute()){
        //echo json_encode(array("testmssg" => "testing123")); // for debugging
        // binding results to local variables so can fetch them and see if null
        mysqli_stmt_bind_result($addMedQuery, $res_error);
        $addMedQuery->fetch();


        // do we have successful registratation--> check for duplicate email
        if (is_null($res_id)) {
            // error code/error message from the database
            echo json_encode(array("message" => $res_error));
            return;
        }
    } else {
        echo json_encode(array("message" => "ERROR: an error occurred adding medication!"));
    }
    
}

/*  function description: called by main api file to add vaccination record
 *  for specific pet      
 *
 *  POST params from Client:
 *      
 *      
 *  returns to main API --> Client:
 * 
 *  interacts with databse:
 *  
 */
function addVacc() {
    
}

/* ------------------------------VIEW FUNCS------------------------------- */

/*  function description: currently not in use, used for building
 *
 *  POST params from Client:
 *      userID: for procedure call
 *        
 *  returns to main API --> Client:
 *      json encoded array with pet keys
 * 
 *  interacts with databse:
 *      getUserPets() procedures
 */
function displayUserProfile() {
    $userID = $_POST["userID"];
    
    $conn2 = getConnection();

    $result = $conn2->prepare("CALL getUserPets(?)");
    $result->bind_param("i", $userID);
    $result->execute();

    $userPets = $result->get_result();

    $rows = [];
    while($row = $userPets->fetch_assoc()) {
        $rows [] = $row;
    }

    echo json_encode($rows);
}

/*  function description: for building
 *      to display at the top of a pet's home page
 *
 *  POST params from Client:
 *      
 *  returns to main API --> Client:
 * 
 *  interacts with databse:
 *  
 */
function getPetHomeHeader() {
    $petID = $_POST['petID'];
    
    $conn = getConnection();

    $result = $conn->prepare("CALL getPetHeader(?)");
    $result->bind_param("i", $petID);
    $result->execute();

    $petHeader = $result->get_result();

    $rows = [];
    while($row = $petHeader->fetch_assoc()) {
        $rows [] = $row;
    }

    echo json_encode($rows);
}

/*  function description: comprehensive pet data, discuss with group what details want
 *      
 *
 *  POST params from Client:
 *      
 *  returns to main API --> Client:
 * 
 *  interacts with databse:
 *  
 */
function viewGeneralHealth() {
    
}


?>
