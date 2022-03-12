<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct(){
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect(){
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data){
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $pseudo, $password){
        $pseudo = $this->prepareData($pseudo);
        $password = $this->prepareData($password);
        $this->sql = "select * from " . $table . " where pseudo = '" . $pseudo . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        if (mysqli_num_rows($result) != 0) {
            $dbusername = $row['pseudo'];
            $dbpassword = $row['mdp'];
            if ($dbusername == $pseudo && password_verify($password, $dbpassword)) {
                $login = true;
            } else $login = false;
        } else $login = false;

        return $login;
    }

    function insertCompteRendu($table, $prenom, $nom, $naiss, $adresse, $tel, $email, $ante, $medic, $duree, $rdv){
        $prenom = $this->prepareData($prenom);
        $nom = $this->prepareData($nom);
        $naiss = $this->prepareData($naiss);
        $adresse = $this->prepareData($adresse);
        $tel = $this->prepareData($tel);
        $email = $this->prepareData($email);
        $ante = $this->prepareData($ante);
        $medic = $this->prepareData($medic);
        $duree = $this->prepareData($duree);
        $rdv = $this->prepareData($rdv);

        $this->sql =
            "INSERT INTO " . $table . " (prenom, nom, naiss, adresse, tel, email, ante, medic, duree, rdv) VALUES 
            ('" . $prenom . "','" . $nom . "','" . $naiss . "','" . $adresse . "','" . $tel . "','" . $email . "','" . $ante . "','" . $medic . "','" . $duree . "','" . $rdv . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function visiteurInsertCompteRendu($table, $medic, $duree, $rdv, $prix, $region){
        $prix = $this->prepareData($prix);
        $medic = $this->prepareData($medic);
        $duree = $this->prepareData($duree);
        $rdv = $this->prepareData($rdv);
        $region = $this->prepareData($region);

        $this->sql =
            "INSERT INTO " . $table . " (medic, duree, rdv, prix, region) VALUES 
            ('" . $medic . "','" . $duree . "','" . $rdv . "','" . $prix . "','" . $region . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function signUp($table, $pseudo, $mdp){
        $pseudo = $this->prepareData($pseudo);
        $mdp = $this->prepareData($mdp);
        $mdp = password_hash($mdp, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (pseudo, mdp) VALUES ('" . $pseudo . "','" . $mdp . "')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }
}
