<?php
include 'conexion.php';

$query = "update users set name = ?, password = ?, updated_at = ? where id = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('ssss',
$_POST['usuario'],
$_POST['password'],
$timepstamp,
$_POST['id']);

$stmt->execute();

// if($stmt->affected_rows > 0){
    $query = "update tecnicos set nombre = ?,apellido = ?,puesto = ?, updated_at = ? where id_usuario = ?";
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('sssss',
    $_POST['nombre'],
    $_POST['apellido'],
    $_POST['puesto'],
    $timepstamp,
    $_POST['id']);
    $stmt->execute();

    if($stmt->affected_rows>0){
            
        echo "1";
    }
// }
$mysqli->close();


?>