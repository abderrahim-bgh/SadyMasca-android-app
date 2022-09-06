<?php

            require_once "DataConfig.php" ; 
                $sql="SELECT * FROM adherent";

                $result = mysqli_query($conn,$sql);
                $user= array();

                while($row = mysqli_fetch_assoc($result)){
                   if($row['status']== 'prof'){

                    $index['firstName']= $row['firstName'];
                    $index['lastName']= $row['lastName'];
                    $index['email']= $row['email'];
                    $index['faculty']= $row['faculty'];
                    $index['block']= $row['block'];
                    $index['responsibility']= $row['responsibility'];




                    array_push($user, $index);

                }
            }
                echo json_encode($user);
                
                
            


?>