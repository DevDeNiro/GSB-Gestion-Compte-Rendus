<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['pseudo']) && isset($_POST['mdp'])) {
    if ($db->dbConnect()) {
        if (1 == 1){
            $region = "Ile-de-france";
        }
        if ($db->logIn("doctor", $_POST['pseudo'], $_POST['mdp'])) {
            $tab = array();
            $temp = [
                'login' => "Login Success",
                'region' => $region
            ];
            array_push($tab, $temp);
            echo json_encode($tab);
        } else echo "Pseudo or Password wrong";
    } else echo "Error: Database connection";
} else echo "All fields are required";
