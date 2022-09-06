<?php

            require_once "DataConfig.php" ; 
                $sql="SELECT * FROM  publication";

                $result = mysqli_query($conn,$sql);
                $user= array();

                while($row = mysqli_fetch_assoc($result)){
                    $index['id_pub']= $row['id_pub'];
                    $index['title']= $row['title'];
                    $index['datte']= $row['datte'];
                    $index['receiver']= $row['receiver'];
                    $index['department']= $row['department'];
                    $index['id_t']= $row['id_t'];
                    $index['transmitter']= $row['transmitter'];
                    $index['pic']= $row['pic'];
                    $index['textt']= $row['textt'];
                    
            


                    array_push($user, $index);

               
            }
                echo json_encode($user);
              
            
               


?>