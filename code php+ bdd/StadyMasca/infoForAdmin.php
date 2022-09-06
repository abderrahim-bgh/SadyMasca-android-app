<?php

            require_once "DataConfig.php" ; 
                $sql="SELECT * FROM adherent";

                $result = mysqli_query($conn,$sql);
                $user= array();

                while($row = mysqli_fetch_assoc($result)){
                   if($row['status']== 'etudient'){

                    $index['firstName']= $row['firstName'];
                    $index['lastName']= $row['lastName'];
                    $index['email']= $row['email'];
                    $index['faculty']= $row['faculty'];
                    $index['department']= $row['department'];
                    $index['p1']= $row['p1'];
                    $index['block']= $row['block'];



                    array_push($user, $index);

                }
            }
                echo json_encode($user);
                
                
            


?>