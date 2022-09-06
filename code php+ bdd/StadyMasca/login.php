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
            $index['firstName'] = $row['firstName'];
            $index['lastName'] = $row['lastName'];
            $index['p1'] = $row['p1'];
            $index['p2'] = $row['p2'];
            $index['p3'] = $row['p3'];
            $index['id'] = $row['id'];
            $index['faculty'] = $row['faculty'];
            $index['department'] = $row['department'];
            $index['password'] = $row['password'];
            $index['photo'] = $row['photo'];
            $index['status'] = $row['status'];
            $index['responsibility'] = $row['responsibility'];



            array_push($result['login'],$index);
            if($row['block']== 'yes'){
                $result['success']="4";
                $result['message']="your account is bloked";

            }
            else{   
            if($row['status']== 'etudient'){
                $result['success']="1";
                $result['message']="success";
            }else if($row['status']== 'prof' || $row['status']== 'admin'){
                if($row['status']== 'admin'){
                    $result['success']="2";
                    $result['message']="success";
                }else{
                    $result['success']="3";
                    $result['message']="success";
                }
            }
        }

        
          
    
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