<?php
// this is the main api file

// file with definitions for functions called in this file
require_once "userFuncs.php";

// for debugging/building
require_once ".cnfg.php";


/* branch handles creation of new USER account. React Native Client sends 
 * POST variables: "createAccount", username, email, password */
if (isset($_POST["createAccount"])) {
    header("Content-type: application/json");
    unset($_POST["createAccount"]);

    // from React Native Client or curl request
    $username = $_POST["username"];
    $email = $_POST["email"];
    $password = $_POST["password"];

    // verify required data prior to calling function/establishing connection
    if ($username == null || $password == null || $email == null) {
        //echo json_encode($userID);
        echo json_encode(array("error" => "no username/email/password sent."));
        return;
    } else {

    // echo's back key userID or key "error" message as json encoded array
    createUser();
    }
}

/* branch handles creation of new PET profile. React Native Client sends 
 * POST variables: "createPet", userID, petName, speciesID, birthDate, sex
 * microchipNum, petPic */
elseif (isset($_POST["createPet"])) {
    // in progress
    header("Content-type: application/json");
    unset($_POST["createPet"]);

    // debugging:
    $conn = getConnection();
    // from React Native Client or curl request
    /*
    $userID = $_POST["userID"];
    $petName = $_POST["petName"];
    $speciesID = $_POST["speciesID"];
    $birthDate = $_POST["birthDate"];
    $sex = $_POST["sex"];
    $microchipNum = $_POST["microchipNum"];
    */
    
    //$petPic = $_POST["petPic"];


    // check if any are null:
    $pet_data["userID"] = $_POST["userID"];
    $pet_data["petName"] = $_POST["petName"];
    $pet_data["speciesID"] = $_POST["speciesID"];
    $pet_data["birthDate"] = $_POST["birthDate"];
    $pet_data["sex"] = $_POST["sex"];
    $pet_data["microchipNum"] = $_POST["microchipNum"];
    // ***incorporate the  PIC ** but functional for now
    $pet_data["petPic"] = NULL;

    foreach($pet_data as $key => $val) {
        //echo $key." is ".$val."\n";
        if ($val == "") {
            $pet_data[$key] = NULL;
        }
        //echo $key." issss ".$val."\n";
    }

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

/* -------------------------------------GET VIEWS--------------------------- */

/* branch handles obtaining pet header banner. React Native Client sends 
 * POST variables: "petHeader", petID */
elseif(isset($_POST["userProfile"])) {
    header("Content-type: application/json");
    unset($_POST["petHeader"]);
    // echos back keys petName, ageYears, speciesName, sex, as json encoded array
    petHeader();
}

/* branch handles obtaining pet header banner. React Native Client sends 
 * POST variables: "petHeader", petID */
elseif(isset($_POST["petHeader"])) {
    header("Content-type: application/json");
    unset($_POST["petHeader"]);
    // echos back keys petName, ageYears, speciesName, sex, as json encoded array
    petHeader();
}

/* for React Native to test connection to API. React Native Client sends POST
 * variable "tostada" and gets back keys "id" and "response" as json arrays */
elseif(isset($_POST["tostada"])) {
    $result = [];
    $result []= array("id" => 1, "response" => "you requested a tostada  \u{1F32E}");
    echo json_encode($result);
}

/* -------------------------------------- OTHER ---------------------------- */

/* no POST var sent */
else {
    $result = [];
    // [key, "values blah"] ie as array = [[1, "text"], [2, "more text"], [3, "more more text"]]
    $result []= array("id" => 1, "myResponse" => "no special tostada request, this will break my heart  \u{1F624}");
    echo json_encode($result);
}


?>
