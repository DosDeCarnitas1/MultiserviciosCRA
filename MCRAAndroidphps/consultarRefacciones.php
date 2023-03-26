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

echo json_encode($arrResultados);