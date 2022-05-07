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

    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];
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
            echo json_encode(array("userID" => $res_id));
            //echo json_encode(array("username" => $res_id));
            return;
        }
    } else {
        echo json_encode(array("error" => "an error has occurred!"));
    }
}

/*
 *  function description:
 *
 *  POST params from Client:
 *      
 *  returns to main API --> Client:
 * 
 *  interacts with databse:
 *  
 */
function isLogged() {

}

/*
 *  function description:
 *
 *  POST params from Client:
 *      
 *  returns to main API --> Client:
 * 
 *  interacts with databse:
 *  
 */
function loginUser() {
    $em = $_POST["email"];
    $password = $_POST["password"];

    $conn = getConnection();
    $validateStmnt = $conn->prepare("SELECT * FROM user WHERE email=?");
    $validateStmnt->bind_param('s', $em);
    $validateStmnt->execute();
    mysqli_stmt_bind_result($validateStmnt, $retUserID, $retUserName, $retEmail, $retPW);
    
    if ($validateStmnt->fetch()  && password_verify($password, $retPW)) {
        echo json_encode("in login func at userFuncs, query executed and pw validated");

        // in progress

    } else {
        echo json_encode("ERROR: invalid email or password! Try again.");
    }
}

/* branch handles creation of new pet profile. React Native Client sends 
 * following data via POST request:
 *
 * POST params:
 *      userID: an integer representing user's ID
 *      petID: an integer representing the pet's ID
 *      speciesID: an integer from 1-9 that map to the 'species' table
 *      birthDate:
 *      sex:
 *      microchipNum:
 *      petPic:
 * returns to Client:
 *      JSON encoded array with 'petID'
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
        if ($val == "") {
            $pet_data[$key] = NULL;
        }
        //echo $key." issss ".$val."\n";
    }

    // preparre statement prior to executing
    $createPet = $conn->prepare("CALL createPetProfile(?, ?, ?, ?, ?, ?, ?)");

    // bind parameters, in variatic function, 's' for strings, 'i' for integers
    $createPet->bind_param("isisisb", $pet_data["userID"], $pet_data["petName"], $pet_data["speciesID"],$pet_data["birthDate"],$pet_data["sex"],$pet_data["microchipNum"],$pet_data["petPic"]);

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
        echo json_encode(array("error" => "an error has occurred!"));
    }
}

/*
 *  function description:
 *
 *  POST params from Client:
 *      
 *  returns to main API --> Client:
 * 
 *  interacts with databse:
 *  
 */
function petHeader() {
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


?>
