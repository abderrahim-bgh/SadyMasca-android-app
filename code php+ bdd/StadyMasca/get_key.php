<?php
	require_once "DataConfig.php" ;  

		$q = "SELECT * from publication";
		
		//echo $q;
		
		$rs = $conn->query($q);
		//print_r($rs);
	
		if($r = mysqli_fetch_assoc($rs))
		{
			echo json_encode($r['fcm_key']);
		}
		else
		{
			echo "fail...";
		}
	
?>