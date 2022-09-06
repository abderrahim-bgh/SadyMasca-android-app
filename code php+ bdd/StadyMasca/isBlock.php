<?php

            require_once "DataConfig.php" ; 
                if($conn){
                    $sql = "SELECT * FROM adherent" ;
                    $result = mysqli_query($conn,$sql);
                  
                    if($result){ 
                        $return_arr['adherent'] = array();
                        while($row = $result->fetch_array()){
                            array_push($return_arr['adherent'],array(
                                'block'=>$row['block'],
                                'status'=>$row['status']
                            ));
                            $json[] = $row;
                
                
                        }
                         echo json_encode($return_arr);
                      }
                    
                    
                }else{
                    echo "DB connection failed";
                }


?>