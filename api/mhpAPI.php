<?php
// this is the main api file

require_once "userFuncs.php";


/* branch handles creation of new USER account. React Native Client sends 
 * POST variables: "createAccount", username, email, password */
if (isset($_POST["createAccount"])) {
    header("Content-type: application/json");
    unset($_POST['createAccount']);

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
 * michrochipNum, petPic */
elseif (isset($_POST["createPet"])) {
    // in progress
}

/* branch handles obtaining pet header banner. React Native Client sends 
 * POST variables: "petHeader", petID */
elseif(isset($_POST['petHeader'])) {
    header('Content-type: application/json');
    unset($_POST['petHeader']);
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

/* no POST var sent */
else {
    $result = [];
    // [key, "values blah"] ie as array = [[1, "text"], [2, "more text"], [3, "more more text"]]
    $result []= array("id" => 1, "myResponse" => "no special tostada request, this will break my heart  \u{1F624}");
    echo json_encode($result);
}


?>
