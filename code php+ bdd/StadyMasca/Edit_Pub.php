<?php
require_once "DataConfig.php" ; 
if($_SERVER['REQUEST_METHOD']=='POST'){

    $title = $_POST['title'];
    $textt = $_POST['textt'];
    $id_pu = $_POST['id_pub'];
       $sql= "SELECT * FROM publication WHERE id_pub= '$id_pu'" ; 
    $check=mysqli_query($conn, $sql);
    if(mysqli_num_rows($check)>0){
        $result= "UPDATE publication SET title='$title' , textt='$textt' WHERE id_pub='$id_pu' ";

        if(mysqli_query($conn, $result)){
      echo "User Edited Successfully";
    }else{
        echo"Some Error";
    } 
}
else{
    echo "UnAthorizeed user";
}

}

?>