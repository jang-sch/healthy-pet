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

/* branch handles LOGGING IN. React Native Client sends POST variables: 
 * "login", username, email, password. returns mesage and pet json array */
elseif (isset($_POST["login"])) {
    header("Content-type: application/json");
    unset($_POST["login"]);

    // from React Native Client or curl request
    $em = $_POST["email"];
    $password = $_POST["password"];

    // verify required data prior to calling function/establishing connection
    if ($password == null || $em == null) {
        //echo json_encode($userID);
        echo json_encode(array("message" => "ERROR: CANNOT LOGIN, no email/password sent"));
        return;
    } else {
        // if login is successful, let client know (message may be removed later)
        if(loginUser()) {
            echo json_encode(array("message" => "Login Successful"));
        }
    }
}

/* branch handles creation of new PET profile. React Native Client sends 
 * POST variables: "createPet", userID, petName, speciesID, birthDate, sex
 * microchipNum, petPic */
elseif (isset($_POST["createPet"])) {
    header("Content-type: application/json");
    unset($_POST["createPet"]);

    // note: future plan incorporate session creds

    $userID = $_POST["userID"];

    // determine if a userID was sent prior to establishing connection
    if($userID == NULL || $userID == "") {
        echo json_encode(array("message" =>"ERROR! Cannot create pet, no user ID sent!!"));
    } else {
        // ready to call create pet
        createPet();
    }
}

/* branch retrieves specific pet header for specified pet's home page
 * POST variables: "petHome", petID 
 * returns: json encoded array from the getPetHomeHeader() function */
elseif (isset($_POST["petHome"])) {
    echo "you are in pet home page branch";
    getPetHomeHeader();
}

/* branch lets user add a daily health note for a specific pet 
 * POST variables: "petTodaysHealth", petID,  
 * returns: json encoded array from the  */
elseif (isset($_POST["petTodaysHealth"])) {
    
    header("Content-type: application/json");
    unset($_POST["petTodaysHealth"]);
    
    echo "you are in the today's health branch";
    // note: future plan incorporate session creds

    $petID = $_POST["petID"];

    // determine if a userID was sent prior to establishing connection
    if($petID == NULL || $petID == "") {
        echo json_encode(array("message" =>"ERROR! Cannot create pet day log, no pet ID sent!!"));
    } else {
        // ready to call create pet
        addDailyHealth();
    }

}


/* branch retrieves specific pet header for specified pet 
 * POST variables: "addMed", petID,  
 * returns: json encoded array from the getPetHomeDeets() function */
elseif (isset($_POST["addMed"])) {
    header("Content-type: application/json");
    unset($_POST["addMed"]);

    $petID = $_POST["petID"];

    echo "you are in the addMed branch";

    // determine if a userID was sent prior to establishing connection
    if($petID == NULL || $petID == "") {
        echo json_encode(array("message" =>"ERROR! Cannot add medication, no pet ID sent!!"));
    } else {
        // ready to call create pet
        addMed();
    }
}

/* branch retrieves specific pet header for specified pet 
 * POST variables: "addMed", petID,  
 * returns: json encoded array from the getPetHomeDeets() function */
elseif (isset($_POST["addVacc"])) {
    echo "you are in the addVacc branch";
}


/* -------------------------------------GET VIEWS--------------------------- */

/* branch handles obtaining pet header banner. React Native Client sends 
 * POST variables: "petHeader", petID */
elseif(isset($_POST["userProfile"])) {
    header("Content-type: application/json");
    unset($_POST["userProfile"]);
    // echos back keys petName, ageYears, speciesName, sex, as json encoded array
    displayUserProfile();
}

/* branch handles obtaining pet header banner. React Native Client sends 
 * POST variables: "petHeader", petID */
elseif(isset($_POST["petHeader"])) {
    header("Content-type: application/json");
    unset($_POST["petHeader"]);
    // echos back keys petName, ageYears, speciesName, sex, as json encoded array
    getPetHomeHeader();
}

/* -------------------------------------- OTHER ---------------------------- */

/* for React Native to test connection to API. React Native Client sends POST
 * variable "tostada" and gets back keys "id" and "response" as json arrays */
//elseif(isset($_POST["tostada"]==true)) {
 elseif(isset($_POST["tostada"])) {
    header("Content-type: application/json");
    unset($_POST["tostada"]);
    $result = [];
    $result []= array("id" => 1, "response" => "SUCCESS! You requested a tostada  \u{1F32E}");
    echo json_encode($result);
}

/* no POST keyword sent, so fall into this branch */
else {
    $result = [];
    // [key, "values blah"] ie as array = [[1, "text"], [2, "more text"], [3, "more more text"]]
    $result []= array("id" => 1, "myResponse" => "no special tostada request, this will break my heart  \u{1F624}");
    echo json_encode($result);
}

?>
