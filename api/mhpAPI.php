<?php
// 2022-04-08, 2022-04-13
// main api file

require_once "userFuncs.php";

// following line for debugging/building purposes
require_once ".cnfg.php";



/* for user to create account, requires React Native client to send data
   note: in working order, now determine when sessionID will be generated
*/
if (isset($_POST['createAccount'])) {
    header('Content-type: application/json');
    unset($_POST['createAccount']);

    // from React Native Client or curl request
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // verify required data prior to calling function/establishing connection
    if ($username == null || $password == null || $email == null) {
        //echo json_encode($userID);
        echo json_encode(array("error" => "no username, email, or password sent."));
        return;
    } else {
    // echo's back userID or error message as json encoded array
    createUser();
    }
}

elseif (isset($_POST['createPet'])) {
    // in progress
}

// this branch is in progress
elseif(isset($_POST['petHeader'])) {
    //header('Content-type: application/json');
    //unset($_POST['petHeader']);
    //$petID = $_POST['petID'];
    $conn = getConnection();

    /* does not work when preparing a query to the pet_header *view* in the database
       but does work when preparing queries that will happen on *tables* in the database. 
       
       using
       curl -d "petHeader" -X POST https:...... on the target url

       At this point, I was expecting the following results in array format:
        +-------+-----------+----------+-------------+---------+
        | petID | petName   | ageYears | speciesName | sex     |
        +-------+-----------+----------+-------------+---------+
        |  1024 | Banjo     | 6        | Cat         | Male    |
        |  1025 | Daisy     | Unknown  | Dog         | Female  |
        |  1026 | Buttercup | 1        | Dog         | Female  |
        |  1027 | Rocco     | 1        | Cat         | Male    |
        |  1028 | Scarlete  | 1        | Cat         | Female  |
        |  1029 | Isaiah    | Unknown  | Cat         | Male    |
        |  1036 | Champ     | Unknown  | Dog         | Unknown |
        |  1037 | Champ Jr  | Unknown  | Dog         | Unknown |
        +-------+-----------+----------+-------------+---------+

    */
    $result = $conn->prepare("SELECT * FROM pet_header");
    $result->execute();

    $petHeaders = $result->get_result();

    $rows = [];
    while($row = $petHeaders->fetch_assoc()) {
        $rows [] = $row;
    }

    echo json_encode($rows);

}

elseif(isset($_POST['tostada'])) {
    $result = [];
    $result []= array("id" => 1, "response" => "tostada \u{1F920}");
    echo json_encode($result);
}

else {
    $result = [];
    // array as array = [[1, "text"], [2, "more text"], [3, "more text"]]
    $result []= array("id" => 1, "myResponse" => "no special tostada request, this will break my heart  \u{1F920}");
    echo json_encode($result);
}


?>
