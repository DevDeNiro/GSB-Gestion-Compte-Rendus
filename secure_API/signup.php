<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_GET['pseudo']) && isset($_GET['mdp'])) {
    if ($db->dbConnect()) {
        if ($db->signUp("client", $_GET['pseudo'], $_GET['mdp'])) {
            echo "Sign Up Success";
        } else echo "Sign up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";
