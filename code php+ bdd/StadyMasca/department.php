
<?php
require_once "DataConfig.php" ; 
if(isset($_GET['faculty'])){
    $sql= "select department_id , department from depart where faculty_id=(select faculty_id from facult where faculty='".$_GET['faculty']."')";
    if(!$conn->query($sql)){
    echo "Error in excuting query";
   }
  else{
    $result = $conn->query($sql);
    if($result->num_rows>0){ 
        $return_arr['depart'] = array();
        while($row = $result->fetch_array()){
            array_push($return_arr['depart'],array(
                'department_id'=>$row['department_id'],
                'department'=>$row['department']
            ));
            $json[] = $row;
        }
         echo json_encode($return_arr);
      }
}

}

?>