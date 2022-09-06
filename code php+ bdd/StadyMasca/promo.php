<?php
require_once "DataConfig.php" ; 
if(isset($_GET['department'])){

    $sql= "select id_y , promoNom from promo where department_id=(select department_id from depart where department='".$_GET['department']."')";
    if(!$conn->query($sql)){
    echo "Error in excuting query";
   }
  else{
    $result = $conn->query($sql);
    if($result->num_rows>0){ 
        $return_arr['promo'] = array();
        while($row = $result->fetch_array()){
            array_push($return_arr['promo'],array(
                'id_y'=>$row['id_y'],
                'promoNom'=>$row['promoNom']
            ));
        }
         echo json_encode($return_arr);
      }
}
}


?>