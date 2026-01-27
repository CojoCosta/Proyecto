<?php

Class Usuario{
    private $usuario;
    private $nombre;
    private $apellidos;
    private $nombreUsuario;
    private $email;
    private $password;
    private $fechaNacimiento;
    private $database;

    public function __construct(){
        $this->usuario = array();
        $this->nombre = "";
        $this->apellidos = "";
        $this->nombreUsuario = "";
        $this->email = "";
        $this->password = "";
        $this->fechaNacimiento = "";
        $this->database = require_once '../App_Web/conexion/Conexion.php';
    }
    
    // public function __construct(){

    // }
    #region SET Y GET
    public function setNombre($nombre){
        $this->nombre = $nombre;
    }
    public function getNombre(){
        return $this->nombre;
    }

    public function setApellidos($apellidos){
        $this->apellidos = $apellidos;
    }
    public function getApellidos(){
        return $this->apellidos;
    }

    public function setNombreUsuario($nombreUsuario){
        $this->nombreUsuario = $nombreUsuario;
    }
    public function getNombreUsuario(){
        return $this->nombreUsuario;
    }

    public function setEmail($email){
        $this->email = $email;
    }
    public function getEmail(){
        return $this->email;
    }

    public function setPassword($password){
        $this->password = $password;
    }
    public function getPassword(){
        return $this->password;
    }

    public function setfechaNacimiento($fechaNacimiento){
        $this->fechaNacimiento = $fechaNacimiento;
    }
    public function getFechaNacimineto(){
        return $this->fechaNacimiento;
    }
    #endregion

    public function setUsuario($nombre, $apellidos, $nombreUsuario, $email, $password, $fechaNacimiento){
        $sql = "INSERT INTO usuarios (nombre, apellidos, nombreUsuario, email, password, fechaNacimiento) VALUES ('$nombre', '$apellidos', '$nombreUsuario', '$email', '$fechaNacimiento')";
        $result = $this->database->query($sql);
        $this -> database = null;
        return $result;
    }

}