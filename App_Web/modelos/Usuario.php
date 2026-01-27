<?php

Class Usuario{
    private $nombre;
    private $apellidos;
    private $nombreUsuario;
    private $email;
    private $password;
    private $fechaNacimiento;

    public function __construct($nombre, $apellidos, $nombreUsuario, $email, $password, $fechaNacimiento){
        $this->nombre = $nombre;
        $this->apellidos = $apellidos;
        $this->nombreUsuario = $nombreUsuario;
        $this->email = $email;
        $this->password = $password;
        $this->$fechaNacimiento = $fechaNacimiento;
    }

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
    
}