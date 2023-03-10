<?php
include 'conexion.php';

$query = "update tecnicos set estado = 'Inactivo' where id_usuario = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('s', $_POST['id']);
$stmt->execute();

$query = "update users set estado = 'Inactivo' where id = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('s', $_POST['id']);
$stmt->execute();

if($stmt->affected_rows>0){
    echo "1";
}