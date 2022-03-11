<?php

$servername = 'localhost';
$username = 'root';
$password = '';
$databasename = 'gsb_doctor';


$conn = new mysqli($servername, $username, $password, $databasename);

if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

if (isset($_GET['id']))
    $id = $_GET['id'];
else
    $id = null;

$tab = array();

$sql = "SELECT * FROM compterendu where id = '$id'";

$stmt = $conn->prepare($sql);
$stmt->execute();


$stmt->bind_result($id, $title, $medic, $duree, $rdv, $ante, $prix);
while ($stmt->fetch()) {
    $temp = [
        'id' => $id,
        'title' => $title,
        'medic' => $medic,
        'duree' => $duree,
        'rdv' => $rdv,
        'ante' => $ante,
        'prix' => $prix,
    ];

    array_push($tab, $temp);
}

echo json_encode($tab);
