<?php
require "DataConfig.php";


class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }
    public function blockUser($table, $block){
        $block = $this->prepareData($block);
        $this->sql =
        "INSERT INTO " . $table . " (block) VALUES ('". $block."')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;

     }
  
     public function addFacult($table, $faculty){
        $faculty = $this->prepareData($faculty);
        $this->sql =
        "INSERT INTO " . $table . " (faculty) VALUES ('". $faculty."')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;

     }
     public function addDepart($table,$tablee, $department,$faculty){
        $department = $this->prepareData($department);
        $faculty = $this->prepareData($faculty);
        $this->sql = "select * from " . $tablee . " where faculty = '" . $faculty . "'";
        $result = mysqli_query($this->connect, $this->sql);
        $row = mysqli_fetch_assoc($result);
        echo $row['faculty_id'];
        $faculty_id = $row['faculty_id'];
        $this->sql =
        "INSERT INTO " . $table . " (department,faculty_id) VALUES ('". $department."','". $faculty_id."')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }
        public function addPromo($table,$tablee, $promoNom,$department){
            if($this->isUserExistt($promoNom)){
                return 0;
            }else{
            $promoNom = $this->prepareData($promoNom);
            $department = $this->prepareData($department);
            $this->sql = "select * from " . $tablee . " where department = '" . $department . "'";
            $result = mysqli_query($this->connect, $this->sql);
            $row = mysqli_fetch_assoc($result);
            echo $row['department_id'];
            $department_id = $row['department_id'];
            $this->sql =
            "INSERT INTO " . $table . " (promoNom,department_id) VALUES ('". $promoNom."','". $department_id."')";
            if (mysqli_query($this->connect, $this->sql)) {
                return true;                                                                
            } else return false;
            if($stmt1->execute()){
                return 1;
            }else {
                return 2;
            }
        }
     }
    public function signUp($table, $firstName, $lastName, $email,$password,$numRegst,$department,$faculty,$status,$p1)
    {      if($this->isUserExist($email)){
        return 0;
    }else{
        $firstName = $this->prepareData($firstName);
        $lastName = $this->prepareData($lastName);
        $email = $this->prepareData($email);
        $password = $this->prepareData($password);
        $numRegst = $this->prepareData($numRegst);
        $department = $this->prepareData($department);
        $faculty = $this->prepareData($faculty);
        $status = $this->prepareData($status);
        $p1 = $this->prepareData($p1);
        
       
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql =
            "INSERT INTO " . $table . " (firstName, lastName, email, password, numRegst, department, faculty,status,p1) VALUES ('". $firstName."','". $lastName."','". $email."','". $password."','". $numRegst. "','".$department."','".$faculty."','".$status."','".$p1."')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;                                                                
        } else return false;
        if($stmt->execute()){
            return 1;
        }else {
            return 2;
        }
    }
    }
    private function isUserExist($email){
         $stmt =  $this->connect->prepare("SELECT id FROM adherent WHERE email = ? ");
        $stmt->bind_param("s",$email);
        $stmt->execute();
        $stmt->store_result();
         return $stmt->num_rows > 0;
    }
    private function isUserExistt($promoNom){
        $stmt1 =  $this->connect->prepare("SELECT id_y FROM promo WHERE promoNom = ? ");
        $stmt1->bind_param("s",$promoNom);
        $stmt1->execute();
        $stmt1->store_result();
         return $stmt1->num_rows > 0;

        
    }

}

?>
