
<?php
$servername = 'localhost';
$username = 'root';
$password = '';
$databasename = 'projetSoutnoce';
$conn = mysqli_connect($servername, $username, $password, $databasename);
if(!$conn){
    dire("Connection failed".mysqli_connect);
}


?>
<?php

class DataBaseConfig
{
    public $servername;
    public $username;
    public $password;
    public $databasename;

    public function __construct()
    {

        $this->servername = 'localhost';
        $this->username = 'root';
        $this->password = '';
        $this->databasename = 'projetSoutnoce';

    }
}

?>
