<?php

   $servername = "localhost";
   $username = "root";
   $password = "";
   $db = "poetrydb";

   $conn = new mysqli($servername, $username, $password, $db);
   if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$query = "SELECT * FROM poetry";
$result = $conn->query($query);

$row = $result->fetch_all(MYSQLI_ASSOC);

if(empty($row)){
    $response = array("status" => "0", "message" => "Record id empty","data"=>$row);
}
else{
    $response = array("status" => "1", "message" => "Record avaiable","data"=>$row);
}

echo json_encode($response);


?>