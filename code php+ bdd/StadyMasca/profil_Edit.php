<?php
require_once "DataConfig.php" ; 

if($_SERVER['REQUEST_METHOD']=='POST'){
    $firstName = $_POST['firstName'];
    $lastName = $_POST['lastName'];
    $email = $_POST['email'];
    $id = $_POST['id'];

    $sql= "UPDATE adherent SET firstName='$firstName' , lastName='$lastName', email='$email' WHERE id='$id' ";
   
    if(mysqli_query($conn, $sql)){
        $result["success"]="1";
        $result["message"]="success";
        echo json_encode($result);
        mysqli_close($conn);
    }
}

else{
    $result["success"]="0";
        $result["message"]="error";
        echo json_encode($result);
        mysqli_close($conn);

}
?>
