<?php
require "DataBase.php";
$db = new DataBase();
if (isset($_POST['id_compte'])) {
    if ($db->dbConnect()) {
        if ($db->delete("compte_rendu", $_POST['id_compte'])) {
            echo "Sign Up Success";
        } else {
            echo "Pseudo or Password wrong";
        }
    } else echo "Error: Database connection";
} else echo "All fields are required";
