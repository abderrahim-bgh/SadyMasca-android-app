<?php
require_once "DataConfig.php" ; 
if($_SERVER['REQUEST_METHOD']=='POST'){

    $firstName = $_POST['firstName'];
    $lastName = $_POST['lastName'];
    $email = $_POST['email'];
    $oldEmail = $_POST['oldEmail'];
       $sql= "SELECT * FROM adherent WHERE email= '$oldEmail'" ; 
    $check=mysqli_query($conn, $sql);
    if(mysqli_num_rows($check)>0){
        $result= "UPDATE adherent SET firstName='$firstName' , lastName='$lastName', email='$email' WHERE email='$oldEmail' ";

        if(mysqli_query($conn, $result)){
      echo "User Edited Successfully";
    }else{
        echo"Some Error";
    } 
}
else{
    echo "UnAthorizeed user";
}

}

?>
