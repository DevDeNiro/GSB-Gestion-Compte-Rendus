<?php
require "DataBase.php";
$db = new DataBase();
$servername = $db->getServername();
$username = $db->getUserame();
$password = $db->getPassword();
$databasename = $db->getDatabasename();

$conn = new mysqli($servername, $username, $password, $databasename);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$tab = array();

$sql = "SELECT region FROM compte_rendu";

$stmt = $conn->prepare($sql);
$stmt->execute();

$stmt->bind_result($region);

while ($stmt->fetch()) {
    $region1 = 'region';
    $region1 = utf8_encode($region1);
    $temp = [
        
        $region1 => $region
    ];
    
    $temp = array_map('utf8_encode',$temp);
    array_push($tab, $temp);
}
echo json_encode($tab);