<?php
include "conexion.php";

$query = "select * from spare_parts";

$stmt = $mysqli->prepare($query);
$stmt->execute();

$res = $stmt->get_result();

$arrResultados = array();
while($row = $res->fetch_assoc()){
    $arrResultados[] = $row;
}

$tipos = array("refaccionTipo"=>["Lavadora", "Secadora","Refrigerador","Estufa"]);
$respuesta[] = array_merge($tipos, $arrResultados);

echo json_encode($respuesta);