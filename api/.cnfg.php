<?php
// push later
// 2022-03-25 - configuration file for establishing connection
function getConnection() {
    static $conn;
    if(!isset($conn)) {
        $credPath = "../../.mhp-db-config.ini";
    }
    if (!file_exists($credPath)) {
        die("Unable to find configurations file!!!");
    }
    $dbConfig = parse_ini_file($credPath);
    $conn = mysqli_connect('lh', $dbConfig['un'], $dbConfig['p'],
        $dbConfig['db']) or die(mysqli_connect_error());
    if ($conn === false) {
        echo "Unable to connect to database<br/>";
        echo mysqli_connect_error();
    } 
    return $conn;
}

?>
