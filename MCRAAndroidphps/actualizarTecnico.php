<?php
include 'conexion.php';

$query = "update users set name = ?, password = ? where id = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('sss',
$_POST['usuario'],
$_POST['password'],
$_POST['id']);

$stmt->execute();

if($stmt->affected_rows > 0){
    $query = "update tecnicos set nombre = ?,apellido = ?,puesto = ? where id_usuario = ?";
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('ssss',
    $_POST['nombre'],
    $_POST['apellido'],
    $_POST['puesto'],
    $_POST['id']);
    $stmt->execute();

    if($stmt->affected_rows>0){
            
        echo "1";
    }
}
$mysqli->close();


?>