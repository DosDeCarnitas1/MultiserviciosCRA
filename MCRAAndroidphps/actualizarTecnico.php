<?php

include 'conexion.php';
// $usuario = 'pedrito';
$url = 'http://localhost/MCRAAndroidphps/consultaUsuario.php?usuario='.$_POST['usuario'];
$curl = curl_init();

curl_setopt($curl, CURLOPT_URL, $url);
curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
curl_setopt($curl, CURLOPT_HEADER, false);

$data = curl_exec($curl);

curl_close($curl);

$json = json_decode($data)[0];
// print_r($json->name);
// die;

$query = "select * from tecnicos where id_usuario = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('i', $json->id);
$stmt->execute();

$res = $stmt->get_result();

$arrResultados = array();
while($row = $res->fetch_assoc()){
    $arrResultados[] = $row;
}

$result = json_encode($arrResultados);

$contentTablaTecnico = json_decode($result)[0];
// print_r($contentTablaTecnico);
// die;

/**variables por si no las trae el post */
$user_id = '';
$domicilio = '';
$zona = '';

if(isset($_POST['id'])){
    $user_id = $_POST['id'];
}else{
    $user_id = $json->id; 
}

if(isset($_POST['domicilio'])){
    $domicilio = $_POST['domicilio'];
}else{
    $domicilio = $contentTablaTecnico->domicilio; 
}

if(isset($_POST['zona'])){
    $zona = $_POST['zona'];
}else{
    $zona = $contentTablaTecnico->zona; 
}

/**fin de las variables */

$query = "update users set name = ?, password = ?, updated_at = ? where id = ? and estado = 'Activo'";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('ssss',
$_POST['usuario'],
$_POST['password'],
$timepstamp,
// $_POST['id'],
$user_id);

$stmt->execute();

// if($stmt->affected_rows > 0){
    $query = "update tecnicos set nombre = ?,apellido = ?,puesto = ?,domicilio = ?, zona = ?, updated_at = ? where id_usuario = ? and estado = 'Activo'";
    $stmt = $mysqli->prepare($query);
    $stmt->bind_param('sssssss',
    $_POST['nombre'],
    $_POST['apellido'],
    $_POST['puesto'],
    // $_POST['domicilio'],
    $domicilio,
    // $_POST['zona'],
    $zona,
    $timepstamp,
    // $_POST['id'],
    $user_id);

    $stmt->execute();

    if($stmt->affected_rows>0){
            
        echo "1";
    }
// }
$mysqli->close();


?>