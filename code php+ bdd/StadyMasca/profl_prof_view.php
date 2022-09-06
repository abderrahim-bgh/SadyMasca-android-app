<?php
require_once "DataConfig.php" ; 
if(isset($_GET['id_t'])){
    $sql= "SELECT * FROM adherent WHERE id='".$_GET['id_t']."'";


        if(!$conn->query($sql)){
            echo "Error in excuting query";
           }
          else{
            $result = $conn->query($sql);
            if($result->num_rows>0){ 
                $return_arr['adherent'] = array();
                while($row = $result->fetch_array()){
                    array_push($return_arr['adherent'],array(
                        'id'=>$row['id'],
                        'email'=>$row['email'],
                        'photo'=>$row['photo'],
                        'department'=>$row['department']

                    ));
                    $json[] = $row;
                }
                 echo json_encode($return_arr);
              }
        }
        
       
    }
        
        ?>