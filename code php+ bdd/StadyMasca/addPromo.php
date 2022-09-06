<?php
require "DataBase.php";
$db = new DataBase();


  if (isset($_POST['promoNom'])&& isset($_POST['department'])) {
    if ($db->dbConnect()) {
       
          $result =$db->addPromo("promo","depart", $_POST['promoNom'],$_POST['department']);
          if ($result==1) {
            echo "Add Success";
           

        } elseif($result==2){ echo "Sign up Failed";}
        elseif($result== 0){
            echo " It seems you are already registered , choose a defrent promo";
        }
    } else echo "Error: Database connection";
} else echo "All fields are required";

?>