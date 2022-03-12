<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['titre']) && isset($_POST['medic']) && isset($_POST['duree']) && isset($_POST['rdv']) && isset($_POST['prix']) && isset($_POST['region'])) {
    if ($db->dbConnect()) {
        if ($db->visiteurInsertCompteRendu("compte_rendu", $_POST['medic'], $_POST['duree'], $_POST['rdv'], $_POST['titre'], $_POST['prix'], $_POST['region'])) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
