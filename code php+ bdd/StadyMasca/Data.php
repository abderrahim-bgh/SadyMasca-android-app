    
    
  
<?php
include "DataConfig.php" ;
$sql= "select * from facult";
if(!$conn->query($sql)){
    echo"error in connecting Database.";

}
else{
    $result= $conn->query($sql);
    if($result->num_rows>0){
        $return_arr['facult']= array();
        while($row = $result->fetch_array()){
            array_push($return_arr['facult'],array(
                'faculty_id'=>$row['faculty_id'],
                'faculty'=>$row['faculty']
            ));
            $json[] = $row;
        }
        echo json_encode($return_arr);

    }
}
?>