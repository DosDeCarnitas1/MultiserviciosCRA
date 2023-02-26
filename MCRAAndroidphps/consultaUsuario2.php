<?php
//1.- Abrir la conexion
include 'conexion.php';

//2.- Prepara la instrucción (query) para la base datos.
$query ="select id, name, tipo, password from users where name = ?";
//stmt se le conoce como statement
$stmt = $mysqli->prepare($query);
//sustituir comodines por valores reales
// $usu="lmeza";
// $stmt->bind_param('s',$usu);
$stmt->bind_param('s',$_GET['usuario']);

// 3.- Ejecutar la instrucción
$stmt->execute();
$resultado = $stmt->get_result();
//4.- preparar la respuesta de la base de datos.
$resultado = $resultado->fetch_assoc();
$resultado = json_encode($resultado);
echo $resultado;
//si el numero de filas retornadas es mayor a cero si exite el registro
 
 //5.- Cerrar la conexión de la base de datos.
 $mysqli->close();
?>