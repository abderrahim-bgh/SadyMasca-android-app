<?php
require_once "DataConfig.php" ; 
if($_SERVER['REQUEST_METHOD']=='POST'){

    $email = $_POST['email'];
    $oldEmail = $_POST['oldEmail'];
    $status = $_POST['status'];

       $sql= "SELECT * FROM adherent WHERE email= '$oldEmail'" ; 
    $check=mysqli_query($conn, $sql);
    if(mysqli_num_rows($check)>0){
        $result= "UPDATE adherent SET status='$status'  WHERE email='$oldEmail' ";

        if(mysqli_query($conn, $result)){
      echo "User Edited Successfully";
    }else{
        echo"Some Error";
    } 
}
else{
    echo "Error";
}

}


?>
