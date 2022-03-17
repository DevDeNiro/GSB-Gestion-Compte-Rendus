<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['pseudo']) && isset($_POST['mdp'])) {
    if ($db->dbConnect()) {
        if ($db->logIn("delegue", $_POST['pseudo'], $_POST['mdp'])) {
            $region = $db->getRegion("delegue", $_POST['pseudo']);
            $role = $db->getRole("delegue", $_POST['pseudo']);
            $tab = array();
            $temp = [
                'login' => "Login Success",
                'region' => $region,
                'role' => $role
            ];
            array_push($tab, $temp);
            echo json_encode($tab);
            } 
        else if ($db->logIn("client", $_POST['pseudo'], $_POST['mdp'])) {
            $region = $db->getRegion("client", $_POST['pseudo']);
            $role = $db->getRole("client", $_POST['pseudo']);
            $id = $db->getId("client", $_POST['pseudo']);
            $tab = array();
            $temp = [
                'login' => "Login Success",
                'region' => $region,
                'role' => $role,
                'id' => $id
            ];
            array_push($tab, $temp);
            echo json_encode($tab);
            } 
        else {
            echo "Pseudo or Password wrong";
        }
    } else echo "Error: Database connection";
} else echo "All fields are required";
