<?php
require_once "DataConfig.php" ; 
if($_SERVER['REQUEST_METHOD']=='POST'){
    $responsability = $_POST['responsibility'];
    $oldEmail = $_POST['oldEmail'];
       $sql= "SELECT * FROM adherent WHERE email= '$oldEmail'" ; 
    $check=mysqli_query($conn, $sql);
    if(mysqli_num_rows($check)>0){
        $result= "UPDATE adherent SET responsibility='$responsability' WHERE email='$oldEmail' ";

        if(mysqli_query($conn, $result)){
      echo "User Edited Successfully";
    }else{
        echo" Error";
    } 
}
else{
    echo "UnAthorizeed user";
}

}

?>
