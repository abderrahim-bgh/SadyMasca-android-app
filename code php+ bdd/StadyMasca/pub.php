<?php
require_once "DataConfig.php" ;  
if($_SERVER['REQUEST_METHOD']=='POST'){
		$title=$_POST['title'];
		$id_t=$_POST['id_t'];
	   $textt=$_POST['textt'];	
	   $transmitter=$_POST['transmitter'];
	   $receiver=$_POST['receiver'];
	   $datte=$_POST['datte'];
	   $department=$_POST['department'];	      
	   $faculty=$_POST['fac'];	      	      
	   $img=$_POST['image'];

	   
                   $filename="IMG".rand().".jpg";
	   file_put_contents("Images/".$filename,base64_decode($img));

			$qry="INSERT INTO publication (id_pub, title, textt,id_t,transmitter, datte ,pic,department,faculty,receiver)
                VALUES ('','$title', '$textt','$id_t','$transmitter', '$datte','$filename','$department','$faculty','$receiver')";
			$res=mysqli_query($conn,$qry);

			if($res==true)
			 echo " Uploaded Successfully";
			else
			 echo "Could not upload File";
}
else  echo "Could not upload File";
?>