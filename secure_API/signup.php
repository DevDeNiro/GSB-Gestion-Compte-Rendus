<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['prenom']) && isset($_POST['nom']) && isset($_POST['naiss']) && isset($_POST['adresse']) && isset($_POST['tel']) && isset($_POST['email']) && isset($_POST['ante']) && isset($_POST['medic']) && isset($_POST['duree']) && isset($_POST['rdv'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("compterendu", $_POST['prenom'], $_POST['nom'], $_POST['naiss'], $_POST['adresse'], $_POST['tel'], $_POST['email'], $_POST['ante'], $_POST['medic'], $_POST['duree'], $_POST['rdv'])) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>