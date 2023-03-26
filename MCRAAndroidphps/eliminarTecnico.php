<?php
include 'conexion.php';

$url = 'http://localhost/MCRAAndroidphps/consultaUsuario.php?usuario='.$_POST['usuario'];
$curl = curl_init();

curl_setopt($curl, CURLOPT_URL, $url);
curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
curl_setopt($curl, CURLOPT_HEADER, false);

$data = curl_exec($curl);
curl_close($curl);

$json = json_decode($data)[0];
$id_usuario = $json->id;

$query = "update tecnicos set estado = 'Inactivo' where id_usuario = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('s', $id_usuario);
$stmt->execute();

$query = "update users set estado = 'Inactivo' where id = ?";
$stmt = $mysqli->prepare($query);
$stmt->bind_param('s', $id_usuario);
$stmt->execute();

if($stmt->affected_rows>0){
    echo "1";
}