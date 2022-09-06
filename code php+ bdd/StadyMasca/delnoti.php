<?php

            require_once "DataConfig.php" ; 
            
            if($_SERVER['REQUEST_METHOD']=='POST'){

    
                $N1 = $_POST['N1'];
                $id_t = $_POST['id_t'];

            
                   $sql= "SELECT * FROM adherent WHERE id= '$id_t'" ; 
                $check=mysqli_query($conn, $sql);
                if(mysqli_num_rows($check)>0){
                    $result= "UPDATE adherent SET N1='$N1' WHERE id='$id_t' ";
            
                    if(mysqli_query($conn, $result)){
                  echo " Successfully";
                }else{
                    echo"Some Error";
                } 
            }
            else{
                echo "UnAthorizeed user";
            }
            
            }  

?>