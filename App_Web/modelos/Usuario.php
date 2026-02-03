<?php

Class Usuario{
    private $path;
    private $usuario;
    private $nombre;
    private $apellidos;
    private $nombreUsuario;
    private $email;
    private $password;
    private $fechaNacimiento;
    private $database;

    public function __construct(){
        $this->path = "http://10.0.2.2:8080/apirest/rest/usuario/";
        $this->usuario = array();
        $this->nombre = "";
        $this->apellidos = "";
        $this->nombreUsuario = "";
        $this->email = "";
        $this->password = "";
        $this->fechaNacimiento = "";
        $this->database = Conexion::conexion();
    }
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
        $sql = "INSERT INTO usuarios (nombre, apellidos, nombreUsuario, email, password, fechaNacimiento) VALUES ('$nombre', '$apellidos', '$nombreUsuario', '$email', '$password', '$fechaNacimiento')";
        $result = $this->database->query($sql);
        $this -> database = null;
        return $result;
    }
        public function getUsuario(){
        $sql = "SELECT * FROM usuarios";
        $result = $this -> database -> query($sql);
        $this -> usuario = $result -> fetchAll(PDO:: FETCH_ASSOC);
        $this -> database = null;
        return $this -> usuario;
    }

    public function borrarUsuario($nombreUsuario){
        $sql = "DELETE FROM usuarios WHERE nombreUsuario LIKE '$nombreUsuario'";
        $result = $this->database->query($sql);
        $this->database = null;
        return $result;
    }



}