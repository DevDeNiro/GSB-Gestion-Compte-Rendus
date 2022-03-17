<?php

$servername = 'localhost';
$username = 'root';
$password = '';
$databasename = 'gsb_doctor';


$conn = new mysqli($servername, $username, $password, $databasename);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (isset($_GET['region']))
    $region = $_GET['region'];
else
    $region = null;

$tab = array();

$sql = "SELECT prenom, nom FROM medecin WHERE region = '$region'";

$stmt = $conn->prepare($sql);
$stmt->execute();

$stmt->bind_result($prenom, $nom);

while ($stmt->fetch()) {
    $temp = [
        'prenom' => $prenom,
        'nom' => $nom
    ];

    array_push($tab, $temp);
}

echo json_encode($tab);
