<?php
include "conexion.php";

//usamos el tipo de usuario que es para devolver los campos disponibles a su perfil

if($_GET['tipoUsuario'] == "Master"){
    //acesa a todos los estados: activo, inactivo y en progreso, y asignados a el
    $estados = array("Estados"=>["Activo", "Pendiente","Inactivo", "Asignados"]);
    $query = "SELECT * FROM tickets";
    $stmt = $mysqli->prepare($query);
    // $stmt->bind_param('s', $_GET['id_usuario']);
    $stmt->execute();

    $reurn = $stmt->get_result();
    // var_dump($reurn);
    $arrResultados = array();
    while($row = $reurn->fetch_assoc()){
        $arrResultados[] = $row;
    }
    // print_r($arrResultados);
    $consulta = $arrResultados;
    // var_dump($arrResultados);
    $respuesta[] = array_merge($estados, $consulta);
    
    echo json_encode($respuesta);
}else{
    //este es un array que yo cree es para filtrar en el spinner
    $estados = array("Estados"=>["Activo", "Pendiente","Inactivo"]);
    $query = "SELECT * FROM tickets where tecnicoAsignado = ?";
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('s', $_GET['id_usuario']);
    $stmt->execute();

    $reurn = $stmt->get_result();
    // var_dump($reurn);
    $arrResultados = array();
    while($row = $reurn->fetch_assoc()){
        $arrResultados[] = $row;
    }
    // print_r($arrResultados);
    $consulta = $arrResultados;
    // var_dump($arrResultados);
    $respuesta[] = array_merge($estados, $consulta);
    
    echo json_encode($respuesta);
    
}




