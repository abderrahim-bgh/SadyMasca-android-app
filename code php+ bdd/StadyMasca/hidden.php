
<?php

require_once "DataConfig.php" ; 
    $sqll="SELECT * FROM hidden";

    $res = mysqli_query($conn,$sqll);
    $use= array();

    while($row = mysqli_fetch_assoc($res)){
        $index['id_pu']= $row['id_pu'];
        $index['id']= $row['id'];
       

        array_push($use, $index);

   
}
    echo json_encode($use);
    
    



?>