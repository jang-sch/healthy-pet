<?php
// 2022-04-08, 2022-04-13

// function definitions file
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
    // to be moved from mhpAPI.php into here, in progress
}

/* parameters: none
 * 
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
