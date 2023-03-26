<?php
include "conexion.php";

$query = "update tickets set estado = ? where id = ?";
$stmt = $mysqli->prepare($query);

$stmt->bind_param('si',
                  $_POST['estado'],
                  $_POST['ticket_id']);

$stmt->execute();

if($stmt->affected_rows>0){
    echo "1";
}
