<?php
$mysqli = new mysqli("localhost", "root", "", "MCRADB");
if ($mysqli->connect_errno) {
    echo "Fallo al conectar a MySQL: (" . $mysqli->connect_errno . ") " . $mysqli->connect_error;
}
$fecha = new DateTime();
$timepstamp =  $fecha->format('Y-m-d H:i:s');
// echo $mysqli->host_info . "\n";
?>