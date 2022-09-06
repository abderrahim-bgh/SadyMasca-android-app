<?php
require_once "DataConfig.php" ;
if($_SERVER['REQUEST_METHOD']=='POST'){


 $promoNom = $_POST['promoNom'];

 $query= "SELECT * FROM promo WHERE promoNom= '$promoNom'";
 $check = mysqli_query($conn,$query);
 $result= array();
 if(mysqli_num_rows($check)=== 1){
     $sql = "DELETE FROM promo WHERE promoNom= '$promoNom'";
     if(mysqli_query($conn,$sql)){
 
        $result['state']= "delete";
          echo json_encode($result);

     }else{
         echo "faculty Already deleted";
     }
 }
}
 else{
     echo "invalide faculty or deleted faculty";
 }
 ?>
