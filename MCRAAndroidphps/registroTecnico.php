<?php
//1.- Abrir la conexion
include 'conexion.php';
$activo = 'Activo';
//2.- Prepara la instrucción (query) para la base datos.
$tipo = 'Empleado';
$query ="insert into users(name,tipo,password,estado,created_at,updated_at) values(?,?,?,?,?,?)";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('ssssss',
$_POST['usuario'],
$tipo,
$_POST['password'],
$activo,
$timepstamp,
$timepstamp);

$stmt->execute();

$id_usuario = $stmt->insert_id;
//4.- preparar la respuesta de la base de datos.
if($stmt->affected_rows>0){
    $zona = 'Tlaquepaque';
    $query ="insert into tecnicos(nombre,apellido,puesto,domicilio, zona ,estado, id_usuario, created_at, updated_at) values(?,?,?,?,?,?,?,?,?)";
    //stmt se le conoce como statement
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('ssssssiss',
    $_POST['nombre'],
    $_POST['apellido'],
    $_POST['puesto'],
    $_POST['domicilio'],
    // $_POST['foto'],
    $zona,
    $activo,//estado
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
