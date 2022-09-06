<?php
require "database.php";

$db = new DataBase();
if (isset($_POST['firstName']) && isset($_POST['lastName']) && isset($_POST['email'])  && isset($_POST['password'])  && isset($_POST['department']) && isset($_POST['faculty']) &&  isset($_POST['numRegst']) && isset($_POST['status']))  {

    if ($db->dbConnect()) {
        $result =$db->signUp("adherent",$_POST['firstName'], $_POST['lastName'], $_POST['email'], $_POST['password'], $_POST['numRegst'], $_POST['department'], $_POST['faculty'], $_POST['status'], $_POST['p1']);
        if ($result==1) {
            echo "Sign Up Success";
           

        } elseif($result==2){ echo "Sign up Failed";}
        elseif($result== 0){
            echo " It seems you are already registered , choose a defrent email";
        }
    } else echo "Error: Database connection";
} else echo "All fields are required";
?>
