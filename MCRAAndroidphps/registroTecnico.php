<?php
//1.- Abrir la conexion
include 'conexion.php';

//2.- Prepara la instrucción (query) para la base datos.
$query ="insert into users(name,tipo,password,created_at,updated_at) values(?,?,?,?,?)";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('sssss',
$_POST['usuario'],
$_POST['tipo'],
$_POST['password'],
$timepstamp,
$timepstamp);

$stmt->execute();

$id_usuario = $stmt->insert_id;
//4.- preparar la respuesta de la base de datos.
if($stmt->affected_rows>0){

    $query ="insert into tecnicos(nombre,apellido,puesto, id_usuario, created_at, updated_at) values(?,?,?,?,?,?)";
    //stmt se le conoce como statement
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('sssiss',
    $_POST['nombre'],
    $_POST['apellido'],
    $_POST['puesto'],
    $id_usuario,
    $timepstamp,
    $timepstamp);

    // 3.- Ejecutar la instrucción
    $stmt->execute();

    if($stmt->affected_rows>0){
            
        echo "1";
    }
}

 //5.- Cerrar la conexión de la base de datos.
 $mysqli->close();
?>
