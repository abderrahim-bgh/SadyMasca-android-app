<?php
require_once "DataConfig.php" ;
if($_SERVER['REQUEST_METHOD']=='POST'){


 $id_pub = $_POST['id_pub'];

 $query= "SELECT * FROM publication WHERE id_pub= '$id_pub'";
 $check = mysqli_query($conn,$query);
 $result= array();
 if(mysqli_num_rows($check)=== 1){
     $sql = "DELETE FROM publication WHERE id_pub= '$id_pub'";
     if(mysqli_query($conn,$sql)){
 
        $result['state']= "delete";
          echo json_encode($result);

     }else{
         echo "publication Already deleted";
     }
 }
}
 else{
     echo "invalide publication or deleted publication";
 }
 ?>