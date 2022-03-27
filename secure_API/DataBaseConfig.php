<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;
    // public $table;

    public function __construct()
    {
        $this->servername = 'localhost';
        $this->username = 'root';
        $this->password = '';
        $this->databasename = 'gsb_doctor';

        // $this->servername = 'apicomogsb.mysql.db';
        // $this->username = 'apicomogsb';
        // $this->password = 'Password2000';
        // $this->databasename = 'apicomogsb';
    }
}
