<?php
require_once "DataConfig.php" ;
if($_SERVER['REQUEST_METHOD']=='POST'){


 $department = $_POST['department'];

 $query= "SELECT * FROM depart WHERE department= '$department'";
 $check = mysqli_query($conn,$query);
 $result= array();
 if(mysqli_num_rows($check)=== 1){
     $sql = "DELETE FROM depart WHERE department= '$department'";
     if(mysqli_query($conn,$sql)){
 
        $result['state']= "delete";
          echo json_encode($result);

     }else{
         echo "department Already deleted";
     }
 }
}
 else{
     echo "invalide department or deleted department";
 }
 ?>
