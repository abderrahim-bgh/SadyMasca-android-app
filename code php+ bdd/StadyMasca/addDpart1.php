<?php
require "DataBase.php";
$db = new DataBase();


  if (isset($_POST['department'])&& isset($_POST['faculty'])  ) {
    if ($db->dbConnect()) {
        if ($db->addDepart("depart","facult", $_POST['department'],$_POST['faculty'])) {
            echo "Add Success";
        } else echo "Add up Failed";
    } else echo "Error: Database connection";
} else echo "All fields are required";


?>