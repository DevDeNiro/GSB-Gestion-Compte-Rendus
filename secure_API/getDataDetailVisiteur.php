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

$sql = "SELECT prenom, nom, ante_medicaux, medic, duree, rdv, prix, titre, c.id FROM compte_rendu c INNER JOIN client c2 ON c2.id = c.id_visiteur WHERE c.id = '$id'";

$stmt = $conn->prepare($sql);
$stmt->execute();


$stmt->bind_result($prenom, $nom, $ante, $medic, $duree, $rdv, $prix, $titre, $id);
while ($stmt->fetch()) {
    $temp = [
        'prenom' => $prenom, 
        'nom' => $nom,
        'ante' => $ante,
        'medic' => $medic,
        'duree' => $duree,
        'rdv' => $rdv,
        'prix' => $prix,
        'titre' => $titre,
        'id' => $id
    ];

    array_push($tab, $temp);
}

echo json_encode($tab);
