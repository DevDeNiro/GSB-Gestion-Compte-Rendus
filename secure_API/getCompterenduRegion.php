<?php

$servername = 'localhost';
$username = 'root';
$password = '';
$databasename = 'gsb_doctor';


$conn = new mysqli($servername, $username, $password, $databasename);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$tab = array();

$sql = "SELECT region FROM compte_rendu;";

$stmt = $conn->prepare($sql);
$stmt->execute();

$stmt->bind_result($region);

while ($stmt->fetch()) {
    $temp = [
        'region' => $region
    ];

    array_push($tab, $temp);
}

echo json_encode($tab);