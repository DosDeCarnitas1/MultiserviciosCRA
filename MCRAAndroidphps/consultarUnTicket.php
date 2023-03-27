<?php
include "conexion.php";

$query = "SELECT * FROM tickets where id = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('i',
                  $_GET['ticket_id']);
$stmt->execute();
$res = $stmt->get_result();

$arrResultados = array();
while($row = $res->fetch_assoc()){
    $arrResultados[] = $row;
}

$result = json_encode($arrResultados);
$content = json_decode($result)[0];

echo json_encode($content);