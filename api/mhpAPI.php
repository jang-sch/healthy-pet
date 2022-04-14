<?php
// 2022-04-08, 2022-04-13

// main api file
require_once "userFuncs.php";

// for user to create account, requires React Native client to send data
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

?>