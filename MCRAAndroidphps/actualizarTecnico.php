<?php
include 'conexion.php';

$query = "update users set name = ?, password = ?, updated_at = ? where id = ? and estado = 'Activo'";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('ssss',
$_POST['usuario'],
$_POST['password'],
$timepstamp,
$_POST['id']);

$stmt->execute();

// if($stmt->affected_rows > 0){
    $query = "update tecnicos set nombre = ?,apellido = ?,puesto = ?,domicilio = ?, zona = ?, updated_at = ? where id_usuario = ? and estado = 'Activo'";
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('sssssss',
    $_POST['nombre'],
    $_POST['apellido'],
    $_POST['puesto'],
    $_POST['domicilio'],
    // $_POST['foto'],
    $_POST['zona'],
    $timepstamp,
    $_POST['id']);

    $stmt->execute();

    if($stmt->affected_rows>0){
            
        echo "1";
    }
// }
$mysqli->close();


?>