<?php
// 2022-04-08, 2022-04-13

// function definitions file
require_once ".cnfg.php";


function createUser() {
    // establish connection once appropriate
    $conn = getConnection();
    $username = $_POST['username'];
    $email = $_POST['email'];
    $password = $_POST['password'];

    // note: must hash pw, now that this portion work

    // createAccount to be a prepared statement, make prepared call to stored
    // procedure. stored procedure accepts params in order: uname, email, pword
    $createAccount = $conn->prepare("CALL createAccount(?, ?, ?)");

    // bind parameters, in variatic function, 's' for strings, 'i' for integers
    $createAccount->bind_param("sss", $username, $email, $password);

    // now execute prepared stament
    if($createAccount->execute()){
        echo json_encode(array("testmssg" => "testing123")); // for debugging
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

function createPet() {

}

?>