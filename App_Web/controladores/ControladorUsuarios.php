<?php
require_once '../modelos/Usuarios.php';

$usuario = new Usuario();
$result = $usuario -> getUsuario();

require_once '../vistas/Registro.php';
    