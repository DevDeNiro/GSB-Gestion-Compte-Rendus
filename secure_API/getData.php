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

$sql = "SELECT prenom, nom, rdv, id FROM compte_rendu;";

$stmt = $conn->prepare($sql);
$stmt->execute();

$stmt->bind_result($prenom, $nom, $rdv, $id);

while ($stmt->fetch()) {
    $temp = [
        'prenom' => $prenom,
        'nom' => $nom,
        'rdv' => $rdv,
        'id' => $id
    ];

    $temp = array_map('utf8_encode',$temp);
    array_push($tab, $temp);
}

echo json_encode($tab);
