<?php
require "DataBase.php";

        
        $db = new DataBase();
        if (isset($_POST['faculty']) ) {
            if ($db->dbConnect()) {
                if ($db->addFacult("facult", $_POST['faculty'])) {
                    echo "Add Success";
                } else echo "Add up Failed";
            } else echo "Error: Database connection";
        } else echo "All fields are required";
        
        
      

?>