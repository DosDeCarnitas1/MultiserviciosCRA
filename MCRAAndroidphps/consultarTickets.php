<?php
include "conexion.php";

//usamos el tipo de usuario que es para devolver los campos disponibles a su perfil

if($_GET['tipoUsuario'] == "Master"){
    //acesa a todos los estados: activo, inactivo y en progreso, y asignados a el
    // $estados = array("Estados"=>["Activo", "Pendiente","Inactivo", "Asignados"]);
    $query = "SELECT * FROM tickets";
    $stmt = $mysqli->prepare($query);
    // $stmt->bind_param('s', $_GET['id_usuario']);
    $stmt->execute();

    $reurn = $stmt->get_result();
    var_dump($reurn);
    $consulta = $reurn->fetch_assoc();
    $respuesta[] = array_merge(/*$estados,*/ array_map('utf8_decode',$consulta));
    
    echo json_encode($respuesta);
}else{
    //este es un array que yo creo es para filtrar en el spinner
    $estados = array("Estados"=>["Activo", "Pendiente","Inactivo"]);
    $query = "SELECT * FROM tickets where tecnicoAsignado = ?";
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('s', $_GET['id_usuario']);
    $stmt->execute();

    $reurn = $stmt->get_result();
    $consulta = $reurn->fetch_assoc();
    $respuesta[] = array_merge($estados, array_map('utf8_decode',$consulta));
    
    echo json_encode($respuesta);
    
}




