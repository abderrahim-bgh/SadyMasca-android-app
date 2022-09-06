
<?php
require_once "DataConfig.php" ; 
if(isset($_GET['id'])){
    $sql= "select * from adherent where id='".$_GET['id']."'";
    if(!$conn->query($sql)){
    echo "Error in excuting query";
   }
  else{
    $result = $conn->query($sql);
    if($result->num_rows>0){ 
        $return_arr['adherent'] = array();
        while($row = $result->fetch_array()){
            array_push($return_arr['adherent'],array(
                'N1'=>$row['N1']
            ));
            $json[] = $row;
        }
         echo json_encode($return_arr);
      }
}

}

?>