<?php
require_once "DataConfig.php" ; 
if($_SERVER['REQUEST_METHOD']=='POST'){

    
    $email = $_POST['email'];
    $p1 = $_POST['p1'];

       $sql= "SELECT * FROM adherent WHERE email= '$email'" ; 
    $check=mysqli_query($conn, $sql);
    if(mysqli_num_rows($check)>0){
        $result= "UPDATE adherent SET p1='$p1' WHERE email='$email' ";

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