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

//4.- preparar la respuesta de la base de datos.
$resultado = $stmt->get_result();
//si el numero de filas retornadas es mayor a cero si exite el registro
 if($resultado->num_rows>0){
     $row = $resultado->fetch_assoc();

     if($row['tipo']=="Empleado"){
        $query="select nombre, apellido, puesto, id_usuario from tecnicos where id_usuario = ?";

       $stmt = $mysqli->prepare($query);
       $stmt->bind_param('i',$row['id']);
   
        $stmt->execute();

        $resultado2= $stmt->get_result();
        if($resultado2->num_rows>0){
            $row2= $resultado2->fetch_assoc();
            // $respuesta = json_encode($row2);
            $respuesta[]= array_merge(array_map('utf8_encode',$row),array_map('utf8_encode',$row2));

            echo json_encode($respuesta);
        }
     }
     else if($row['tipo']=="Master"){
        $query="select id, nombre, apellido, puesto, id_usuario from tecnicos where id_usuario = ?";

       $stmt = $mysqli->prepare($query);
       $stmt->bind_param('i',$row['id']);
   
        $stmt->execute();

        $resultado2= $stmt->get_result();
        if($resultado2->num_rows>0){
            $row2= $resultado2->fetch_assoc();
            // $respuesta = json_encode($row2);
            $respuesta[]= array_merge(array_map('utf8_encode',$row),array_map('utf8_encode',$row2));

            echo json_encode($respuesta);
        }

     }
     else//en este codigo es si eres cliente
     {
        $query="select nombre,apellido from clientes where idclientes=?";

        $stmt= $mysqli->prepare($query);
        $stmt->bind_param('i',$row['codigoref']);

        $stmt->execute();

        $resultado2= $stmt->get_result();
        if($resultado2->num_rows>0){
            $row2= $resultado2->fetch_array();
            $respuesta[]= array_merge(array_map('utf8_encode',$row),array_map('utf8_encode',$row2));
            echo json_encode($respuesta);
        }
     } 
 }
 //5.- Cerrar la conexión de la base de datos.
 $mysqli->close();
?>