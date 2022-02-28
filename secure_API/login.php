<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['pseudo']) && isset($_POST['mdp'])) {
    if ($db->dbConnect()) {
        if ($db->logIn("doctor", $_POST['pseudo'], $_POST['mdp'])) {
            echo "Login Success";
        } else echo "Pseudo or Password wrong";
    } else echo "Error: Database connection";
} else echo "All fields are required";
