<?php
require_once "DataConfig.php" ;  
if($_SERVER['REQUEST_METHOD']=='POST'){
		$id_pub=$_POST['id_pub'];
		$id=$_POST['id'];
	   

			$qry="INSERT INTO hidden (id_pu, id)
                VALUES ('$id_pub','$id')";
			$res=mysqli_query($conn,$qry);

			if($res==true)
			 echo " hide Successfully";
			else
			 echo "Could not hide File";
}
else  echo "Could not hide File";
?>