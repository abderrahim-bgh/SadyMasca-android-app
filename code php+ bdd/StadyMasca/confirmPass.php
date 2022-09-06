<?php
 require_once "DataConfig.php" ; 

if($_SERVER['REQUEST_METHOD']=='POST'){
    $email = $_POST['email'];
    $password = $_POST['password'];
    $sql = "select * from adherent where email = '$email'";
    $respons = mysqli_query($conn, $sql);
    $result= array();
    $result['login']= array();
    if (mysqli_num_rows($respons) === 1) {
        $row = mysqli_fetch_assoc($respons);
        if(password_verify($password, $row['password'])){
            $index['email'] = $row['email'];
            $index['password'] = $row['password'];
            array_push($result['login'],$index);
            
                $result['success']="1";
                $result['message']="success";
           echo json_encode($result);
           mysqli_close($conn);
    
        } else{
            $result['success']="0";
            $result['message']="error in the pass";
            echo json_encode($result);
            mysqli_close($conn);
    
        } 
    }}
?>