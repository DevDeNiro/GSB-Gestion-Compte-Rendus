<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['pseudo']) && isset($_POST['mdp'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("doctor", $_POST['pseudo'], $_POST['mdp'])) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
