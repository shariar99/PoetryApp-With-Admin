<?php

   $servername = "localhost";
   $username = "root";
   $password = "";
   $db = "poetrydb";

   $conn = new mysqli($servername, $username, $password, $db);
   if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$p_id = $_POST['poetry_id'];

 $query = "DELETE FROM poetry WHERE id = $p_id";

 $result = $conn->query($query);

    if($result){
        $response = array("status" => "1", "message" => "Poetry deleted successfully");
    }
    else{
        $response = array("status" => "0", "message" => "Poetry not deleted");
    }

    echo json_encode($response);
 





?>