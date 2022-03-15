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

$sql = "SELECT id_visiteur, titre, medic, duree, rdv, ante_medicaux, prix, prenom, nom FROM compte_rendu c INNER JOIN client c2 ON c2.id = c.id_visiteur WHERE c.id = '$id'";

$stmt = $conn->prepare($sql);
$stmt->execute();


$stmt->bind_result($id, $titre, $medic, $duree, $rdv, $ante, $prix, $prenom, $nom);
while ($stmt->fetch()) {
    $temp = [
        'id' => $id,
        'titre' => $titre,
        'medic' => $medic,
        'duree' => $duree,
        'rdv' => $rdv,
        'ante' => $ante,
        'prix' => $prix,
        'prenom' => $prenom, 
        'nom' => $nom
    ];

    array_push($tab, $temp);
}

echo json_encode($tab);
