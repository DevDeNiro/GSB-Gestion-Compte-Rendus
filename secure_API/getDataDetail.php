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
        
        $sql = "SELECT * FROM compterendu where id = '$id'";

        $stmt = $conn->prepare($sql);
        $stmt->execute();


        $stmt->bind_result($id, $prenom, $nom, $naiss, $adresse, $tel, $email, $ante, $medic, $duree, $rdv);
        while($stmt->fetch()) {
            $temp = [
                'id'=>$id,
                'prenom'=>$prenom,
                'nom'=>$nom,
                'naiss'=>$naiss,               
                'adresse'=>$adresse,
                'tel'=>$tel,
                'email'=>$email,
                'ante'=>$ante,
                'medic'=>$medic,
                'duree'=>$duree,
                'rdv'=>$rdv
            ];

            array_push($tab, $temp);
        }

        echo json_encode($tab);