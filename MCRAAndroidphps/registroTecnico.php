<?php
//1.- Abrir la conexion
include 'conexion.php';

//2.- Prepara la instrucción (query) para la base datos.
$query ="insert into users(name,tipo,password) values(?,?,?)";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('sss',
$_POST['usuario'],
$_POST['tipo'],
$_POST['password']);

$stmt->execute();

$id_usuario = $stmt->insert_id;
//4.- preparar la respuesta de la base de datos.
if($stmt->affected_rows>0){

    $query ="insert into tecnicos(nombre,apellido,puesto, id_usuario) values(?,?,?,?)";
    //stmt se le conoce como statement
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('sssi',
    $_POST['nombre'],
    $_POST['apellido'],
    $_POST['puesto'],
    $id_usuario);

    // 3.- Ejecutar la instrucción
    $stmt->execute();

    if($stmt->affected_rows>0){
            
        echo "1";
    }
}

 //5.- Cerrar la conexión de la base de datos.
 $mysqli->close();
?>
